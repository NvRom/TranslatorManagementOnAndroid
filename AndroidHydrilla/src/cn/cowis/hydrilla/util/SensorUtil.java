package cn.cowis.hydrilla.util;

import android.annotation.SuppressLint;
@SuppressLint("DefaultLocale")
public class SensorUtil {

	public static String statueName(int statue) {

		switch (statue) {
		case 1:
			return "状态：正常";
		case 2:
			return "状态：不正常";
		case 0:
			return "状态：已卸载";
		default:
			return "状态：未知";
		}

	}
	
	/**
	 * 由温度 计算的电导率 并且保留一位小数的字符串
	 * 
	 * @param t
	 * @return
	 */
	
	public static String countTToEc(double t) {
		t = ToolUtil.roundValue(t, 1);
		
		return String.format("%3.1f", ToolUtil.roundValue((776.21213 + 23.57169 * t + 0.07629 * t * t),
				0));
	}
	
	
	/**
	 * 标定的时候 ph的输入参数 验证 特殊 至少两个
	 * 
	 * @param textViews
	 * @return
	 * @throws Exception
	 */
	public static float[] stringsToFloatsPh(String[] strs){

		int demaPoint = 0;

		float[] values = new float[strs.length];

		try {
			values[0] = Float.parseFloat(strs[0]);

		} catch (Exception e) {
			return null;
		}
		try {
			values[1] = Float.parseFloat(strs[1]);
			demaPoint++;
		} catch (Exception e) {
			values[1] = ToolUtil.getNaN();
		}
		try {
			values[2] = Float.parseFloat(strs[2]);
			demaPoint++;
		} catch (Exception e) {
			values[2] = ToolUtil.getNaN();
		}
		try {
			values[3] = Float.parseFloat(strs[3]);
			demaPoint++;
		} catch (Exception e) {
			values[3] = ToolUtil.getNaN();
		}

		if (demaPoint < 2) { // 若正确数据少于两组，则报错信息

			// sendErr(handler);
			return null;
		}
		return values;
	}
	
	/**
	 * @param strs
	 *       验证溶解氧
	 * @return
	 */
	public static float[] stringsToFloatsDo(String[] strs){

		float[] values = new float[] { ToolUtil.getNaN(), ToolUtil.getNaN(), ToolUtil.getNaN(), ToolUtil.getNaN(),
				ToolUtil.getNaN() };

		float[] actualValues = ToolUtil.stringsToFloats(strs);
		if(actualValues==null)return null;

		System.arraycopy(actualValues, 0, values, 0, actualValues.length);

		return values;

	}
	
	/**
	 * ph标定时 由温度计算获得三个标准ph
	 * 
	 * @param strT
	 *            由界面获得的 温度值的 string 类型
	 * @return
	 * @throws SensorException
	 */
	public static String[] countTToPh(double t) {
		String[] values = new String[3];

		t = ToolUtil.roundValue(t, 1);

		values[0] = String
				.format("%3.3f",
				ToolUtil.roundValue(4.00589 - 0.0017 * t + 7.67958e-5 * t * t
						- 6.33745e-7 * t * t * t + 4.15884e-9 * t * t * t * t
						- 1.40633e-11 * t * t * t * t * t, 3));

		values[1] = String
				.format("%3.3f",ToolUtil.roundValue(6.98106 - 0.00698 * t + 1.04826e-4 * t
						* t - 5.43666e-7 * t * t * t + 1.1486e-9 * t * t * t
						* t, 3));

		values[2] = String
				.format("%3.3f",
				ToolUtil.roundValue(9.45783 - 0.01404 * t + 1.37435e-4 * t * t
						- 7.47021e-7 * t * t * t + 1.40939e-9 * t * t * t * t,
						3));
		return values;
	}
	
	/**溶解氧校正
	 * @param t1
	 * @param t2
	 * @param i1
	 * @param i2
	 * @return
	 */
	public static double[] countDoITwoParams(double t1, double t2, double i1, double i2) {
		double[] values = new double[2];

		t1 = ToolUtil.roundValue(t1, 1);
		t2 = ToolUtil.roundValue(t2, 1);
		values[0] = ToolUtil.roundValue((i2 - i1) / (t2 - t1), 4);
		values[1] = ToolUtil.roundValue(i1 - t1 * values[0], 4);
		return values;
	}

	
	/**
	 * 残余电流校正 一点（格式化显示）
	 * 
	 * @param i1
	 * @return
	 */
	public static double[] countDoIOne(double i1) {
		double[] values = new double[2];
		values[1] = ToolUtil.roundValue(i1,4);
		return values;
	}
	

}
