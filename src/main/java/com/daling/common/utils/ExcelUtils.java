package com.daling.common.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTMarker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author liuyi
 * @since 2020/1/14
 */
public class ExcelUtils {

    private static Logger logger = LoggerFactory.getLogger(DateUtil.class);

    /**
     * 从流中读取数据 返回数组数据
     * @param byteArrayInputStream
     * @return
     * @throws Exception
     */
    public static String[][] read(ByteArrayInputStream byteArrayInputStream) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook(byteArrayInputStream);
        XSSFSheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.rowIterator();
        List<String> cellList = null;
        List<List<String>> rowList = new ArrayList<>();
        Integer cellMax = sheet.getRow(0).getPhysicalNumberOfCells();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            cellList = new ArrayList<>();
            for (int cellIndex = 0; cellIndex < cellMax; cellIndex++) {
                Cell cell = row.getCell(cellIndex);
                String value = null;
                if (cell != null) {
                    switch (cell.getCellType()) {
                        case XSSFCell.CELL_TYPE_STRING:
                            value = cell.getStringCellValue();
                            break;
                        case XSSFCell.CELL_TYPE_NUMERIC:
                            if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                                Date date = DateUtil.getJavaDate(cell.getNumericCellValue());
                                value = new DateTime(date.getTime()).toString(DateUtils.dateTimeFormatter);
                            } else {
                                double num = cell.getNumericCellValue();
                                if (num == (int) num) {
                                    value = String.valueOf((int) num);
                                } else {
                                    value = String.valueOf(num);
                                }
                            }
                            break;
                        case XSSFCell.CELL_TYPE_BOOLEAN:
                            value = String.valueOf(cell.getBooleanCellValue());
                            break;
                        case XSSFCell.CELL_TYPE_FORMULA:
                            // 取得公式
                            value = cell.getCellFormula();
                            break;
                        default:
                            value = null;
                    }
                } else {
                    // nothing to do
                }
                cellList.add(value);
            }
            // 判断是否为最后一行
            if (!cellList.stream().allMatch(x -> x == null)) {
                rowList.add(cellList);
            }
        }
        String[][] page = new String[rowList.size()][rowList.get(0).size()];
        for (int y = 0; y < page.length; y++) {
            for (int x = 0; x < page[y].length; x++) {
                page[y][x] = rowList.get(y).get(x);
            }
        }
        return page;
    }

    /**
     * 从流中读取数据
     * @param byteArrayInputStream
     * @return
     * @throws IOException
     */
    public static Map<String, byte[]> readPicture(ByteArrayInputStream byteArrayInputStream) throws IOException {
        Map<String, byte[]> sheetIndexPicMap = new HashMap<>();
        XSSFWorkbook workbook = new XSSFWorkbook(byteArrayInputStream);
        XSSFSheet sheet = workbook.getSheetAt(0);
        for (POIXMLDocumentPart dr : sheet.getRelations()) {
            if (dr instanceof XSSFDrawing) {
                XSSFDrawing drawing = (XSSFDrawing) dr;
                List<XSSFShape> shapes = drawing.getShapes();
                for (XSSFShape shape : shapes) {
                    XSSFPicture pic = (XSSFPicture) shape;
                    XSSFClientAnchor anchor = pic.getPreferredSize();
                    CTMarker ctMarker = anchor.getFrom();
                    String picIndex = ctMarker.getRow() + "_" + ctMarker.getCol();
                    sheetIndexPicMap.put(picIndex, pic.getPictureData().getData());
                }
            }
        }
        return sheetIndexPicMap;
    }

    /**
     * 解析excel为map集合
     * @param in
     * @param fileName
     * @param fields
     * @param startRow
     * @param startColumn
     * @return
     * @throws IOException
     */
    public static List<Map<String, Object>> resolveExcel(InputStream in, String fileName, String[] fields, int startRow, int startColumn) throws IOException {
        Workbook wb = getWorkbook(in, fileName);
        Sheet sheet = wb.getSheetAt(0);
        int rownum = sheet.getPhysicalNumberOfRows();
        Row row = sheet.getRow(0);
        int colnum = row.getPhysicalNumberOfCells();
        List<Map<String, Object>> dataList = new ArrayList<>();
        for (int i = startRow; i < rownum; i++) {
            row=sheet.getRow(i);
            Map<String, Object> data = new HashMap<>();
            for (int k = startColumn; k < colnum; k++) {
                Object cellData = getCellFormatValue(row.getCell(k));
                data.put(fields[k - startColumn], cellData);
            }
            dataList.add(data);
        }
        return dataList;
    }

    /**
     * 获取workbook
     * @param in
     * @param fileName
     * @return
     * @throws IOException
     */
    public static Workbook getWorkbook(InputStream in, String fileName) throws IOException {
        Workbook wb = null;
        try{
            String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
            if ("xls".equalsIgnoreCase(ext)) {
                wb = new HSSFWorkbook(in);
            } else if ("xlsx".equalsIgnoreCase(ext)) {
                wb = new XSSFWorkbook(in);
            }
        } catch (IOException e) {
            throw new GenericBusinessException(e);
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return wb;
    }

    /**
     * 获取excel单元格的值
     * @param cell
     * @return
     */
    public static Object getCellFormatValue(Cell cell) {
        Object cellValue = null;
        if (cell != null) {
            //判断cell类型
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC: {
                    cellValue = String.valueOf(cell.getNumericCellValue());
                    break;
                }
                case Cell.CELL_TYPE_FORMULA: {
                    //判断cell是否为日期格式
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        //转换为日期格式YYYY-mm-dd
                        cellValue = cell.getDateCellValue();
                    } else {
                        //数字
                        cellValue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }
                case Cell.CELL_TYPE_STRING: {
                    cellValue = cell.getRichStringCellValue().getString();
                    break;
                }
                default:
                    cellValue = "";
            }
        } else {
            cellValue = "";
        }
        return cellValue;
    }

    /**
     * map转为对象
     * @param dataMap
     * @param clazz
     * @return
     */
    public static Object mapToBean(Map<String, Object> dataMap, Class clazz) {
        Object object = null;
        try {
            object = clazz.newInstance();
            BeanUtils.populate(object, dataMap);
        } catch (InstantiationException e) {
            throw new GenericBusinessException(e);
        } catch (IllegalAccessException e) {
            throw new GenericBusinessException(e);
        } catch (InvocationTargetException e) {
            throw new GenericBusinessException(e);
        }
        return object;
    }

}
