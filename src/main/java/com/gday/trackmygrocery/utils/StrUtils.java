package com.gday.trackmygrocery.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class StrUtils {

    /**
     *
     * @param obj
     * @return obj == null;
     */
    public static boolean isNull(Object obj) {
        return obj == null;
    }


    /**
     * 判断list是否为Null
     * @param list
     * @return
     */
    public static <T> boolean isNullList(List<T> list) {
        return (list == null);
    }

    /**
     * 判断list是否为空
     * @param list
     * @return (list == null) || (list.size() < 1)
     */
    public static <T> boolean isEmptyList(List<T> list) {
        return (list == null) || (list.size() < 1);
    }

    /**
     * 判断Map是否为Null
     * @param map
     * @return
     */
    public static <K, V> boolean isNullMap(Map<K, V> map) {
        return (map == null);
    }

    /**
     * 判断Map是否为空
     * @param map
     * @return
     */
    public static <K, V> boolean isEmptyMap(Map<K, V> map) {
        return (map == null || map.size() < 1);
    }

    /**
     * 判断数组是否为Null
     * @param obj
     * @return
     */
    public static boolean isNullArray(Object[] obj) {
        return (obj == null);
    }
    /**
     * 判断数组是否为空
     * @param obj
     * @return
     */
    public static boolean isEmptyArray(Object[] obj) {
        return (obj == null || obj.length < 1);
    }

    /**
     * <p>Checks if a String is empty ("") or null.</p>
     *
     * <pre>
     * StringUtils.isEmpty(null)      = true
     * StringUtils.isEmpty("")        = true
     * StringUtils.isEmpty(" ")       = false
     * StringUtils.isEmpty("bob")     = false
     * StringUtils.isEmpty("  bob  ") = false
     * </pre>
     *
     * <p>NOTE: This method changed in Lang version 2.0.
     * It no longer trims the String.
     * That functionality is available in isBlank().</p>
     *
     * @param str  the String to check, may be null
     * @return <code>true</code> if the String is empty or null
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * <p>Checks if a String is whitespace, empty ("") or null.</p>
     *
     * <pre>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre>
     *
     * @param str  the String to check, may be null
     * @return <code>true</code> if the String is null, empty or whitespace
     * @since 2.0
     */
    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }


    public static void main(String[] args) throws Exception {
		/*System.out.println("getUUID = "+getUUID());
		System.out.println("getUUID = "+getUUID());
		System.out.println("getUUID = "+getUUID());
		System.out.println("getUUIDNumberOnly = "+getUUIDNumberOnly());*/
        //System.out.println(removeLastCode("     ab "));
    }


}

