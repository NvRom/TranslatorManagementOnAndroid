package cn.cowis.hydrilla.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ToolUtil {

	private static final String DATE_FORMAT="yyyy-MM-dd";
	private static final SimpleDateFormat DATE_SDF = new SimpleDateFormat(DATE_FORMAT);
	
	
	
	
	/**
	 * 将String 转化 时间  yyyy-MM-dd
	 * @param date
	 * @return
	 */
	public static Date stringToDate(String date){
		
		if("".equals(date))return null;
		
		
		try {
			return DATE_SDF.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	/**将日期变为字符
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date){
		
		return DATE_SDF.format(date);
		
	}
	
	/**
	 * 将小数 近似到几位
	 * @param value
	 * @param n
	 * @return
	 */
	public static double roundValue(double value,int n){
		
		return  Math.round(value*Math.pow(10, n))/Math.pow(10, n);
		
	}
	
	
	/**
	 * @param v
	 *         近似小数
	 * @param n
	 * @return
	 */
	public static double roundValue(float v,int n){
		
		double value=Double.parseDouble(String.valueOf(v));
		return  Math.round(value*Math.pow(10, n))/Math.pow(10, n);
	}
	
	/**
	 * @param v
	 *          讲数据用几位显示
	 * @param n
	 * @return
	 */
	public static String roundTOString(double v,int n){
		
		double value= roundValue(v,n);
		return String.format("%1."+n+"f", value);
	}
	
	
	public static String[] valueToString(double[] values,int n){
		
		String[] strs=new String[values.length];
		
		for(int i=0;i<strs.length;i++){
			
			strs[i]=String.format("%1."+n+"f", values[i]);
		}
		return strs;
	}
	
	/**
	 * 将  String[] 变为float[]
	 * @param values
	 * @return
	 */
	public static float[] stringsToFloats(String[] values){
		
		float [] f_values=new float[values.length];
		
		try{
		 for(int i=0;i<values.length;i++){
			  f_values[i]=Float.parseFloat(values[i]);
			
		 }
		}catch(Exception e){
			return null;
		}
		return f_values;
	}
	
	
	
	
	/**
	 * 获得非法数据
	 * @return
	 */
	public static float getNaN(){
		
		return (float)0/0;//非法数据
		
	}

}
