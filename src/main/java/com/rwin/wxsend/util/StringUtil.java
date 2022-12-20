package com.rwin.wxsend.util;


/**
 *
 * @author chenli
 */
public class StringUtil {


	/**
	 * 功能描述:  首字母转大写
	 * @param s
	 * @result
	 * @author
	 * @date 2019/5/8
	 */
	public static String toUpperCaseFirstOne(String s){
		if (Character.isUpperCase(s.charAt(0))) {
			return s;
		} else {
			return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
		}
	}

}
