package cn.cowis.hydrilla.app;

import java.util.List;

import cn.cowis.hydrilla.app.entity.SensorManager;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

/**
 * @author Administrator
 *建立不同的dialog的集合
 *
 */
public interface Dialogs {

	
	/**这个dialog 手动输入一个参数
	 * @param title
	 *              dialog的标题
	 * @param handler
	 *              用handler 返回给界面
	 */
	void inputDialog(String title,Handler handler);
	
	/**这个dialog 手动输入两个参数
	 * @param title
	 *              dialog的标题
	 * @param handler
	 *              用handler 返回给界面
	 */
	void inputDialogMinMax(String title, final Handler handler,float min,float max);
	
	/**这个dialog 手动输入参数，可以加减
	 * @param title
	 *              dialog的标题
	 * @param handler
	 *              用handler 返回给界面
	 */
	void inputDialogPlus(String title, Handler handler,int id);
	
	/**
	 * 
	 * 这个dialog 是单选框
	 * @param title
	 *            dialog的标题
	 * @param handler
	 *           用handler 返回给界面
	 */
	void listInputDialog(String title,String[] contextNames,Handler handler);
	
	
	/**
	 * 这个dialog 是动态 读模拟量和温度
	 * @param title
	 *           标题
	 * @param handler
	 *            用handler 返回给界面
	 * @param sensorManager
	 *           传递的sensorManager
	 *           
	 * @param isAnalog
	 *           如果动态读取模拟量是true 读取结果值是false
	 */
	void dynamicInputDialog(String title,Handler handler,SensorManager sensorManager,boolean isAnalog);
	
//	/**
//	 * 这个dialog 是动态 读取气压值
//	 * @param title
//	 *           标题
//	 * @param handler
//	 *            用handler 返回给界面
//	 * @param sensorManager
//	 *           传递的气压sensorManager
//	 *           
//	
//	 */
//	void dynamicReadPressureDialog(String title
//									,Handler handler
//									,android.hardware.SensorManager sensorManager
//									,android.hardware.Sensor sensor
//									,boolean flag);
	
	public void setNH4ConfigDialog(String title, final Handler handler) ;

}
