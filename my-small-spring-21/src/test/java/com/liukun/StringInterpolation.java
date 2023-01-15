package com.liukun;

import java.util.HashMap;
import java.util.Map;

/**
 *@ClassName StringInterpolation
 *@Description 字符串插值
 *@Author 刘坤 liukunchn@outlook.com
 *@Date 2022/10/20 19:56
 *@Version 1.0
 */
public class StringInterpolation {
    /**
     * 前缀
     */
    private String prefix = "{{";

    /**
     * 后缀
     */
    private String suffix = "}}";

    /**
     * 字符串插值
     * @param content 将要进行插值的字符串。必选参数。
     * @param values 一个键值对对象，具有字符串键和任何类型的值。必选参数。
     * @return 插值后的字符串。
     */
    public String interpolation(String content, Map<String, Object> values) {
        int start = content.indexOf(this.prefix);
        if (start == -1) {
            return content;
        }

        StringBuilder result = new StringBuilder(content);
        while (start != -1) {
            int end = findEndIndex(result, start);
            if (end != -1) {
                String placeholder = result.substring(start + this.prefix.length(), end).trim();
                Object value = values.get(placeholder);
                if (value == null) {
                    throw new IllegalArgumentException("the variable '" + placeholder + "'" + " is missing from the keys of the \"" + value + "\" object");
                }
                result.replace(start, end + this.suffix.length(), value.toString());
                start = result.indexOf(this.prefix, start + value.toString().length());

            } else {
                start = -1;
            }
        }
        return result.toString();
    }

    /**
     * 找到下一个后缀的index
     * @param buf 将要进行插值的字符串
     * @param startIndex 从那个index开始
     * @return 下一个后缀的index
     */
    private int findEndIndex(CharSequence buf, int startIndex) {
        int index = startIndex + this.prefix.length();
        while (index < buf.length()) {
            if (substringMatch(buf, index, this.suffix)) {
                return index;
            }
            else {
                index++;
            }
        }
        return -1;
    }

    /**
     * 判断字符串从某个index开始是否和另一个子串匹配
     * @param str 原始字符串
     * @param index 原始字符串的开始index
     * @param substring 要匹配的子串
     * @return
     */
    private boolean substringMatch(CharSequence str, int index, String substring) {
        if (index + substring.length() > str.length()) {
            return false;
        }
        for (int i = 0; i < substring.length(); i++) {
            if (str.charAt(index + i) != substring.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 测试程序
     * @param args
     */
    public static void main(String[] args) {
        StringInterpolation interpolation = new StringInterpolation();
        Map map = new HashMap();
        map.put("name", "Bill");
        map.put("age", 21);
        String result = interpolation.interpolation("My name is {{ name }} and I am forever {{ age }}.", map);
        System.out.println(result);

        Map map2 = new HashMap();
        map2.put("name", "Bill");
        map2.put("age", 21);
        map2.put("male", true);
        String result2 = interpolation.interpolation("Say hello to {{ name }}. He is {{ age }}.", map2);
        System.out.println(result2);

        Map map3 = new HashMap();
        map3.put("name", "Bill");
        String result3 = interpolation.interpolation("Tommy is a good friend of {{ name }}. He lives in {{ city }}.", map3);
        System.out.println(result3);
    }
}
