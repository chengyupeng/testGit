package com.casic.dubbo.Consumer.sources.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	public static boolean checkString(String str){
		boolean fl=false;
		if(str!=null && !"".equals(str) && !"null".equals(str)){
			fl=true;
		}
		return fl;
	}
	
	public static boolean checkObject(Object str){
		boolean fl=false;
		if(str!=null && !"".equals(str) && !"null".equals(str)){
			fl=true;
		}
		return fl;
	}
	public static boolean checkLong(Long lg){
		boolean fl=false;
		if(lg!=null){
			fl=true;
		}
		return fl;
	}

	
	/**
	 * 判断long值是否为0或NULL
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(Long s){
		if(s == null || s == 0) return true;
		return false;
	}
	
	/**
	 * 判断Integer值是否为0或NULL
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(Integer s){
		if(s == null || s == 0) return true;
		return false;
	}
	
	/**
	 * 判断字符是否为空串或NULL
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s){
		if(s == null || "".equals(s.trim())) return true;
		return false;
	}
	
	/**
	 * 
	 * @param s
	 * @return
	 */
	public static String getString(Object s){
		if(s == null){
			return "";
		}
		return String.valueOf(s);
	}
	
	/**
	 * 
	 * @param s
	 * @return
	 */
	public static String getString(Object s, String defaultValue){
		if(s == null){
			return defaultValue;
		}
		return String.valueOf(s);
	}
	
	/**
	 * 
	 * @param s
	 * @return
	 */
	public static String getString(String s){
		if(s == null){
			return "";
		}
		return s;
	}
	
	/**
	 * 
	 * @param s
	 * @return
	 */
	public static String getString(Integer s){
		if(s == null){
			return "";
		}
		return String.valueOf(s);
	}
	
	
	/**
	 * 
	 * @param s
	 * @return
	 */
	public static String getString(Float s){
		if(s == null){
			return "";
		}
		return String.valueOf(s);
	}
	
	/**
	 * 
	 * @param s
	 * @return
	 */
	public static String getString(Long s){
		if(s == null){
			return "";
		}
		return String.valueOf(s);
	}
	
	
	/**
	 * 查询获取OR集合
	 * @param field
	 * @param os
	 * @param separator
	 * @param open
	 * @param close
	 * @return
	 */
	public static String getBySeparator(String field, Object[] os, String separator, String open, String close){
		StringBuffer buf = new StringBuffer();
		if(os != null){
			buf.append(open);
			for(int i = 0; i < os.length; i++){
				buf.append(" ");
				buf.append(field);
				buf.append("=");
				buf.append(os[i]);
				buf.append(" ");
				if(i != os.length-1){
					buf.append(separator);
				}
			}
			buf.append(close);
		}
		return buf.toString().replaceAll("'", ""); 
	}
	
	public static String formatStr2UrlStr(String str){
		String realStr = null;
		if(!isEmpty(str)){
			realStr = str.replaceAll("&", "&amp;")
					.replaceAll("\"", "&quot;")
					.replaceAll(">", "&gt;")
					.replaceAll("<", "&lt;");
		}
		return realStr;
	}
	
	/**
	 * 判断数字是否为手机号
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[^4,\\D])|(17[0-9])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
	/**
	 * 判断字符是否为空串或NULL
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(Object s){
		if(s == null || "".equals(s.toString().trim())) return true;
		return false;
	}
	
	public static boolean checkEnAndNum(String str){ 
		String reg="^[a-z]+[a-zA-Z0-9_]*$";
		Pattern p = Pattern.compile(reg);    
		Matcher m = p.matcher(str); 
		boolean matches = m.matches();
		int i = str.length();
		if(matches && i<=14){
			return true;
		}else{
			return false;
		}
	}
	public static boolean checkMyNum(String str){ 
		String reg="^[A-Za-z-0-9_()/]+$";
		Pattern p = Pattern.compile(reg);    
		Matcher m = p.matcher(str); 
		boolean matches = m.matches();
		return matches;
	}

}
