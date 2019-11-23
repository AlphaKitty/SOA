package com.proshine.base.common.utils;

import org.apache.commons.lang.CharUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * String处理工具类
 */
public class StringUtils {
    public static final String SPACE = " ";
    public static final String LF = "\n";
    public static final String CR = "\r";

    public StringUtils() {
    }

    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNotEmpty(CharSequence cs) {
        return !isEmpty(cs);
    }

    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs != null && (strLen = cs.length()) != 0) {
            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public static boolean isNotBlank(CharSequence cs) {
        return !isBlank(cs);
    }

    public static String substringBeforeLast(String str, String separator) {
        if (!isEmpty(str) && !isEmpty(separator)) {
            int pos = str.lastIndexOf(separator);
            return pos == -1 ? str : str.substring(0, pos);
        } else {
            return str;
        }
    }

    public static String substringAfterLast(String str, String separator) {
        if (isEmpty(str)) {
            return str;
        } else if (isEmpty(separator)) {
            return "";
        } else {
            int pos = str.lastIndexOf(separator);
            return pos != -1 && pos != str.length() - separator.length() ? str.substring(pos + separator.length()) : "";
        }
    }

    /**
     * 首字母转小写
     */
    public static String toLowerCaseFirstOne(String s) {
        if (isEmpty(s)) {
            return s;
        }
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return Character.toLowerCase(s.charAt(0)) + s.substring(1);
        }
    }

    /**
     * 首字母转大写
     */
    public static String toUpperCaseFirstOne(String s) {
        if (isEmpty(s)) {
            return s;
        }
        if (Character.isUpperCase(s.charAt(0))) {
            return s;
        } else {
            return Character.toUpperCase(s.charAt(0)) + s.substring(1);
        }
    }

    /**
     * kb M G 存储大小单温转换
     */
    public static String sizeUnitConversion(long value) {
        double x;
        String unit;
        if (value / 1024.0 / 1024 / 1024 / 1024 > 1) {

            unit = "TB";
        }
        if ((x = value / 1024.0 / 1024 / 1024) > 1) {

            unit = "GB";
        } else if ((x = value / 1024.0 / 1024) > 1) {

            unit = "MB";
        } else if ((x = value / 1024.0) > 1) {

            unit = "KB";
        } else {
            unit = "B";
        }
        return Double.toString(x).substring(0, 4) + unit;
    }

    /**
     * 对象属性转换为字段  例如：userName to user_name
     *
     * @param property 字段名
     */
    public static String propertyToField(String property) {
        if (null == property) {
            return "";
        }
        char[] chars = property.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            if (CharUtils.isAsciiAlphaUpper(c)) {
                sb.append("_").append(StringUtils.toLowerCaseFirstOne(CharUtils.toString(c)));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 字段转换成对象属性 例如：user_name to userName
     */
    public static String fieldToProperty(String field) {
        if (null == field) {
            return "";
        }
        char[] chars = field.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c == '_') {
                int j = i + 1;
                if (j < chars.length) {
                    sb.append(StringUtils.toUpperCaseFirstOne(CharUtils.toString(chars[j])));
                    i++;
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 将 拼接起来的字符串分割开来并存入list
     */
    public static List changeStringsToMapList(String ids, String split) {
        String[] idArray = ids.split(split);
        List list = new ArrayList();
        for (String s : idArray) {
            Map map = new HashMap();
            map.put("id", s);
            list.add(map);
        }
        return list;
    }

    /**
     * 根据特定字符切割,返回字符串数组
     */
    public static String[] splitByChar(String ids, String split) {
        if (ids == null) {
            return null;
        }
        return ids.split(split);
    }

}