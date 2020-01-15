package com.daling.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.GenericValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author liuyi
 * @since 2020/1/15
 */
public class ValidateTool {

    public static final String PHONE_REGEXP = "^[1][0-9][0-9]{9}$";
    /**
     * 手机号验证
     * @param phone
     * @return
     */
    public  static boolean checkPhone(String phone) {
        boolean flag = GenericValidator.matchRegexp(phone, PHONE_REGEXP);
        if(!flag){
            return false;
        }else {
            return true;
        }
    }

    /**
     * 邮箱验证
     * @param email
     * @return
     */
    public  static boolean checkMail(String email) {
        String regEx1 = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$";
        Pattern p = Pattern.compile(regEx1);
        Matcher m = p.matcher(email);
        if(m.matches()){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 证件号码验证
     * @param card
     * @return
     */
    public static String checkCard(String card) {
        String maskCard="";
        if(StringUtils.isNotBlank(card)) {
            maskCard=card.replaceAll("(\\w{4})\\w+(\\w{5})", "$1********$2");
        }
        return maskCard;
    }

    /**
     * 例如 手机号13693622526 start = 3,end = 7,result = 136****2526
     * 数据脱敏处理
     * @param data
     * @param start
     * @param end
     * @return
     */
    public static String dataEncrypt(String data, int start, int end) {
        String result = "";
        StringBuilder sb = new StringBuilder();
        for (int i = start; i< end; i++) {
            sb.append("*");
        }
        if (StringUtils.isNotBlank(data)) {
            result = data.replaceAll(data.substring(start, end), sb.toString());
        }
        return result;
    }

}
