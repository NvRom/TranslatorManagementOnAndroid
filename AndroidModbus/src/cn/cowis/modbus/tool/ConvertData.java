package cn.cowis.modbus.tool;


import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConvertData {

	private static final SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd");
	{
		sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
	}

	
	/**
	 * short to byte
	 * @param value
	 * @return
	 */
	public static byte[] toByte(short value) {
		byte [] datas=new byte[2];
		datas[0]=(byte)(value>>8);
		datas[1]=(byte)(value);
		return datas;
	}
	
	
	public static byte[] toByte(short[] values){
		
		byte[] datas=new byte[2*values.length];
		
		for(int i=0;i<values.length;i++){
			
			datas[0+2*i]=(byte)(values[i]>>8);
			datas[1+2*i]=(byte)(values[i]);
		}
		return datas;
	}
	
	/**
	 * int to byte
	 * @param value
	 * @return
	 */
	public static byte[] toByte(int value){
		
		byte[] datas=new byte[4];
		
		datas[0]=(byte)(value>>24);
		datas[1]=(byte)(value>>16);
		datas[2]=(byte)(value>>8);
		datas[3]=(byte)(value);
		
		return datas;
	}
	
	/**
	 * float to byte
	 * @param value
	 * @return
	 */
	public static byte[] toByte(float value){
		
		int data= Float.floatToIntBits(value);
		
		return toByte(data);
		
	}
	
	/**
	 * float [] to byte
	 * @param values
	 * @return
	 */
	public static byte[] toByte(float[] values){
		byte[] datas=new byte[values.length*4];
		
		int j=0;
		for(int i=0;i<values.length;i++){
			
			int value=Float.floatToIntBits(values[i]);
			datas[0+j]=(byte)(value>>24);
			datas[1+j]=(byte)(value>>16);
			datas[2+j]=(byte)(value>>8);
			datas[3+j]=(byte)(value);
			j=j+4;
		}
		return datas;
		
	}
	
	
	/**
	 * date to byte
	 * @param date
	 * @return
	 */
	public static byte[] toByte(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR) << 16;
		int month = (cal.get(Calendar.MONDAY) + 1) << 8;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		;
		return toByte(year + month + day);
		
	}
	/**
	 * byte to int
	 * @param values
	 * @return
	 */
	public static int toInt(byte[] values){
		
		int value = ((values[0] & 0xFF) << 24) | ((values[1] & 0xFF) << 16)
				| ((values[ 2] & 0xFF) << 8) | ((values[ 3] & 0xFF));
		
		return value;
	}
	
	/**
	 * @param args
	 */
	/**
	 * 将4个字节转换成float
	 * 
	 * @param b
	 * @param b1
	 * @param b2
	 * @param b3
	 * @return
	 */
	public static float[] toFloat(byte[] values) {

		System.out.println("values.length="+values.length);
		float[] datas = new float[values.length / 4];

		int value = 0, j = 0;

		for (int i = 0; i < values.length;) {

			value = ((values[i] & 0xFF) << 24) | ((values[i + 1] & 0xFF) << 16)
					| ((values[i + 2] & 0xFF) << 8) | ((values[i + 3] & 0xFF));
			
			datas[j] = Float.intBitsToFloat(value);
			
			j++;
			
			i = i + 4;

		}
		//System.out.println(datas[0]);
		return datas;
		// ModbusLocator.bytesToValue(values, RegisterRange.HOLDING_REGISTER,
		// DataType.FOUR_BYTE_FLOAT, bit)
	}

	/**
	 * 将float 转换成byte[]
	 * 
	 * @param value
	 * @return
	 */
	public static short[] toShort(float[] values) {

		short[] b = new short[2];
		short[] datas = new short[2 * values.length];

		int j = 0, val = 0;

		for (int i = 0; i < values.length; i++) {
			val = Float.floatToIntBits(values[i]);
			b = toShort(val);
			datas[j] = b[0];
			j++;
			datas[j] = b[1];
			j++;
		}

		return datas;
	}

	/**
	 * short to int
	 * 
	 * @param n
	 * @return
	 */
	public static short[] toShort(int n) {
		byte[] b = new byte[4];
		short[] s = new short[2];
		for (int i = 0; i < 4; i++) {
			b[i] = (byte) (n >> (i * 8));
		}
		s[0] = (short) ((b[3] << 8) | (b[2] & 0xff));
		s[1] = (short) ((b[1] << 8) | (b[0] & 0xff));
		
		
		return s;
	}

	public static String byteToString(byte[] values) {
		
		ArrayList<Byte> list=new ArrayList<Byte>();
		for (int i = 0; i < values.length; i++) {
			if (values[i] == 0) {
				break;
			}
			list.add(values[i]);
		}
		
		byte [] datas=new byte[list.size()];
		
		for(int j=0;j<datas.length;j++){
			datas[j]=list.get(j);
		}
		
		String str=null;
		try {
			str=new String(datas,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
		
	}

	

	public static Date byteToDate(byte[] values) {
		byte day = values[3];
		byte mouth = values[2];
		short year = (short) ((values[0]<<8)+values[1]);
		String sDate = year + "-" + mouth + "-" + day;

		Date date = null;
		try {
			date = sdf.parse(sDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 其实不关心转化成short型后数字变成多大，只关心short型高字节低字节位
	 * 与原来的Byte[]数组的位向对应
	 * @param values
	 * @return
	 */
	public static short[] toShort(byte[] values) {
		short data = 0;
		short[] datas=null;
		
		if(values.length%2==1){
			datas = new short[values.length / 2+1];
		}else{
			datas=new short[values.length/2];
		}
		
		for (int i = 0,j = 0; i < values.length;i = i + 2,j++) {
			
			try{
			    data = (short) ((values[i] << 8) | (values[i + 1] & 0xff));
			}catch(ArrayIndexOutOfBoundsException e){
				data= (short) ((values[i] << 8));
			}
		
			datas[j] = data;
			
		}

		return datas;
	}

	

	// 修约
//	public static float roundValue(float val, int n) {
//		Pattern pat = Pattern.compile("((-?[0-9]{1,}\\.[0-9]{" + (n - 1)
//				+ "})([0-9]))(5[0-9]*)");
//		Matcher ma = pat.matcher(String.valueOf(val));
//		float value = 0;
//		if (ma.find()) {
//			value = Float.parseFloat(ma.group(1));
//
//			float v = Float.parseFloat(ma.group(4));
//			System.out.println(v);
//
//			if ((v == 5 && (Float.parseFloat(ma.group(3)) % 2 == 1)) || v != 5) {
//				value = (float) (value + Math.pow(10, -1 * n));
//			}
//		} else {
//			value = (float) ((Math.round(val * Math.pow(10, n)) / Math.pow(10,
//					n)));
//		}
//		return value;
//	}

	public static void main(String[] argo) {
		float[] values= toFloat(new byte[]{(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff});
		System.out.println(values[0]);
		float v=(float)0/0;
		System.out.println(v);

	}
}
