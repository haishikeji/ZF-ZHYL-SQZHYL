package com.px.modulars.cdms.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Set;
import java.util.TreeSet;

/**
 * Title: TODO 标题... Description: TODO 描述..
 *
 * @author 品讯科技
 * @created 2017-11-16 17:37
 */
public class StringUtil extends StringUtils {


    /**
     *  数字如果小于10 前面补0
     */
    public static String fillZeroString(Integer val){
        if (val!=null && val<10){
            return "0"+val;
        }else {
            return val+"";
        }
    }





    /**
     * 把数组转换成set
     *
     * @param array
     * @return
     */
    public static Set<?> array2Set(Object[] array) {
        Set<Object> set = new TreeSet<>();
        for (Object id : array) {
            if (null != id) {
                set.add(id);
            }
        }
        return set;
    }

    /**
     * 转化为字符串
     *
     * @param obj
     * @return
     */
    public static String toString(Object obj) {

        if (null == obj) {
            return "";
        }

        return obj.toString();
    }

    /**
     * string转化为int
     *
     * @param obj
     * @param defaultValue
     * @return
     */
    public static Integer str2int(Object obj, Integer defaultValue) {

        try {
            if (null == obj) {
                return defaultValue;
            }

            String str = toString(obj);

            return Integer.parseInt(str);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * string转化为float
     *
     * @param obj
     * @param defaultValue
     * @return
     */
    public static Float str2Float(Object obj, Float defaultValue) {

        try {
            if (null == obj) {
                return defaultValue;
            }

            String str = toString(obj);

            return Float.parseFloat(str);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * string转化为int
     *
     * @param obj
     * @return
     */
    public static Integer str2int(Object obj) {

        try {
            if (null == obj) {
                return null;
            }

            String str = toString(obj);

            return Integer.parseInt(str);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将字符串分隔为数组
     *
     * @param str
     * @param seperator
     * @param defaults
     * @return
     */
    public static String[] split2Array(String str, String seperator,
                                       String[] defaults) {

        if (isAnyEmpty(str, seperator)) {
            return (null != defaults) ? defaults : new String[]{"", ""};
        }

        String[] array = toString(str).split(seperator);

        if ((null == array) || (0 >= array.length)) {
            return (null != defaults) ? defaults : new String[]{"", ""};
        }

        return str.split(seperator);
    }

    public static String[] string2Replace(String oldchar, String newchar, String... strings) {
        String[] arrayAlias = new String[strings.length];
        for (int i = 0; i < strings.length; i++) {
            String singleAlias = strings[i].replace(oldchar, newchar);
            arrayAlias[i] = singleAlias;
        }
        return arrayAlias;
    }
    
    public static String replaceParams(String template, String...strs) {
		if(template != null && strs != null && strs.length > 0) {
			for(int i = 0;i<strs.length;i++) {
				template = template.replace("$"+(i+1), strs[i]);
			}
			return template;
		}
		return null;
	}
}
