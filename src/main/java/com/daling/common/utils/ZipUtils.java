package com.daling.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author liuyi
 * @since 2020/1/15
 */
public class ZipUtils {

    private static Logger logger = LoggerFactory.getLogger(ZipUtils.class);

    private static class ZipUtilHolder {
        private static final ZipUtils instance = new ZipUtils();
    }

    public static ZipUtils getInstance() {
        return ZipUtilHolder.instance;
    }

    /**
     * 压缩文件
     * @param fillNamePath
     * @param basePath
     * @param imagePath
     * @param out
     * @param isDeleteFile
     * @throws IOException
     */
    public static void createZipStream(Set<String> fillNamePath, String basePath,String imagePath,  ZipOutputStream out, boolean isDeleteFile) throws IOException {
        if (fillNamePath == null || fillNamePath.size() == 0) {
            logger.info("createZipStream return null, because of no files");
            return;
        }
        if (basePath != null) {
            if (!basePath.endsWith("/")) {
                basePath += "/";
            }
        }
        byte[] buffer = new byte[1024];
        for (String fileName : fillNamePath) {
            //网络资源获取
            if(fileName.startsWith("http://") || fileName.startsWith("https://")){
                URL url = new URL(fileName);
                InputStream is = url.openStream();
                String targetName = "售后保障卡.jpg";
                out.putNextEntry(new ZipEntry(targetName));
                int len;
                while ((len = is.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
                is.close();
            }else{
                File sourceFile = new File(fileName);
                FileInputStream fis = new FileInputStream(sourceFile);
                String targetName = fileName.replaceAll(basePath, "");
                targetName = targetName.replaceAll(imagePath, "");
                out.putNextEntry(new ZipEntry(targetName));
                int len;
                while ((len = fis.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
                fis.close();
                if (!"售后保障卡.jpg".equals(targetName)){
                    if (isDeleteFile && sourceFile.isFile() && sourceFile.exists()) {
                        sourceFile.delete();
                    }
                }
            }
        }
    }
}
