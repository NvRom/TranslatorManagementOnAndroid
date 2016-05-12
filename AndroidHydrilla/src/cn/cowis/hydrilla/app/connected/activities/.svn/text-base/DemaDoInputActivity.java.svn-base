package cn.cowis.hydrilla.app.connected.activities;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.RoboGuice;
import org.androidannotations.annotations.ViewById;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import cn.cowis.hydrilla.app.Dialogs;
import cn.cowis.hydrilla.app.MyRoboApplication;
import cn.cowis.hydrilla.app.R;
import cn.cowis.hydrilla.app.entity.SensorManager;
import cn.cowis.hydrilla.app.parent.SuperDemaInputActivity;
import cn.cowis.hydrilla.util.SensorUtil;
import cn.cowis.hydrilla.view.CustomLinearLayoutHandInput;
import cn.cowis.hydrilla.view.CustomLinearLayoutReadInput;

import com.google.inject.Inject;

@EActivity(R.layout.dema_do_input)
@RoboGuice
public class DemaDoInputActivity extends SuperDemaInputActivity {
	
	private static String[] methodList = new String[] { "两点标定法", "饱和湿度空气单点校准法" };

	@ViewById(R.id.dema_do_input_value1)
	CustomLinearLayoutHandInput inputValue1;// 输入的气压值

	@ViewById(R.id.dema_do_input_value2)
	CustomLinearLayoutReadInput inputValue2;// 输入的第一组温度

	@ViewById(R.id.dema_do_input_value3)
	CustomLinearLayoutReadInput inputValue3;// 输入的第一组模拟量

	@ViewById(R.id.dema_do_input_value4)
	CustomLinearLayoutReadInput inputValue4;// 输入的第二组温度

	@ViewById(R.id.dema_do_input_value5)
	CustomLinearLayoutReadInput inputValue5;// 输入的第二组模拟量

//	@ViewById(R.id.dema_analog_unit_name1)
//	TextView changeUnit1; // 根据传感器改变单位
//
//	@ViewById(R.id.dema_analog_unit_name2)
//	TextView changeUnit2; // 根据传感器改变单位

	@ViewById(R.id.dema_do_group2)
	LinearLayout group2; // 用来控制第二组的显示与隐藏

	@ViewById(R.id.dema_do_method_selected)
	CustomLinearLayoutReadInput selectedMethod;

	@Inject
	Dialogs dialog;
	

//	private android.hardware.SensorManager sm;
//	private android.hardware.Sensor sensor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
//		ActionBar actionbar = getActionBar();
		setTitleName(R.string.dema_elec_param);
	
//		position = getIntent().getExtras().getInt("position");
//		
//		sm = (android.hardware.SensorManager) getSystemService(Context.SENSOR_SERVICE);
//		sensor = sm.getDefaultSensor(Sensor.TYPE_PRESSURE);
	
    }  
	

	@AfterViews
	public void init() {
	
		sensorManager = (SensorManager) ((MyRoboApplication)getApplicationContext())
				.getSensorManager(position);
	
		inputValue3.setLabelName(inputValue3.getLabelNalue()+"/"+scp.analogUnit);
		inputValue5.setLabelName(inputValue5.getLabelNalue()+"/"+scp.analogUnit);
		

		selectedMethod.setValue(methodList[0]);// 初始默认是两点
	}
	
//	/**
//	 * 第一组    输入气压
//	 * 
//	 * @param view
//	 */
//	@Click(R.id.dema_do_input_layout1)
//	public void getPressure(View view) {
//		
//		if (sm != null) {
//			dialog.dynamicReadPressureDialog("当前的气压值", createHandler(inputValue1),sm,sensor,false);
//		} else {
//			dialog.inputDialog("当前的气压值", createHandler(inputValue1),false);
//		}
//
//	}
	

	/**
	 * 选择的方法
	 * 
	 * @param view
	 */
	@Click(R.id.dema_do_method_selected)
	public void selectMethod(View view) {

		Handler handler = new Handler() {

			public void handleMessage(Message msg) {

				int methodIndex = msg.getData().getInt("back");

				selectedMethod.setValue(methodList[methodIndex]);

				if (methodIndex == 0) {// 选择两点标定
					group2.setVisibility(View.VISIBLE);// 可见
				} else {
					group2.setVisibility(View.GONE);// 不可见
				}

			}
		};

		dialog.listInputDialog("请选择标定方法", methodList, handler);
	}

	/**
	 * 第一组 输入的温度 (判断是否带有温度传感器)
	 * 
	 * @param view
	 */
	@Click(R.id.dema_do_input_value2)
	public void getInput2(View view) {

		readTem(inputValue2);
	}

	/**
	 * 第一组 输入的模拟量
	 * 
	 * @param view
	 */
	@Click(R.id.dema_do_input_value3)
	public void getInput3(View view) {

		readAnalog(inputValue3);
	}

	/**
	 * 第二组 输入的温度 (判断是否带有温度传感器)
	 * 
	 * @param view
	 */
	@Click(R.id.dema_do_input_value4)
	public void getInput4(View view) {

		readTem(inputValue4);
	}

	/**
	 * 第二组 模拟量
	 * 
	 * @param view
	 */
	@Click(R.id.dema_do_input_value5)
	public void getInput5(View view) {
		readAnalog(inputValue5);
	}

	/**
	 * 标定按钮
	 * 
	 * @param view
	 */
	@Click(R.id.dema_do_button)
	public void demaButton(View view) {
		
		String[] inputStrs = null;

		if (group2.getVisibility() != View.GONE) {// 两点标定
			inputStrs = new String[] { inputValue1.getValue(), inputValue2.getValue(),
					inputValue3.getValue(), inputValue4.getValue(), inputValue5.getValue()};
		} else {
			inputStrs = new String[] { inputValue1.getValue(), inputValue2.getValue(),
					inputValue3.getValue() };
		}
		
		beginDema(SensorUtil.stringsToFloatsDo(inputStrs));
		
	}

	

	/**
	 * 读温度
	 */
	private void readTem(CustomLinearLayoutReadInput view) {

		
		if (temSensorManager != null) {

			dialog.dynamicInputDialog("当前的温度", createHandler(view),
										temSensorManager, false);
		} else {
			dialog.inputDialog("当前的温度", createHandler(view));
		}

	}

	/**
	 * 读模拟量
	 * 
	 * @param view
	 */
	private void readAnalog(CustomLinearLayoutReadInput view) {

		dialog.dynamicInputDialog("当前标定电流", createHandler(view),
				sensorManager, true);

	}
	
	
}
