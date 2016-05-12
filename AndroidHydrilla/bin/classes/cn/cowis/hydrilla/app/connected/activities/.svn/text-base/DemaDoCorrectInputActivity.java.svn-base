package cn.cowis.hydrilla.app.connected.activities;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.RoboGuice;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import cn.cowis.hydrilla.app.Dialogs;
import cn.cowis.hydrilla.app.R;
import cn.cowis.hydrilla.app.parent.SuperSensorActivity;
import cn.cowis.hydrilla.util.SensorUtil;
import cn.cowis.hydrilla.util.ToolUtil;
import cn.cowis.hydrilla.view.CustomLinearLayoutReadInput;

import com.google.inject.Inject;

/**
 * 溶解氧标定
 * @author Administrator
 *
 */
@EActivity(R.layout.dema_do_correct)
@RoboGuice
public class DemaDoCorrectInputActivity extends SuperSensorActivity{

	private static String[] methodList = new String[] { "两点调零法", "单点调零法" };

	@ViewById(R.id.dema_do_correct_value1)
	CustomLinearLayoutReadInput correctValue1;// 输入的第一组温度

	@ViewById(R.id.dema_do_correct_value2)
	CustomLinearLayoutReadInput correctValue2;// 输入的第一组模拟量

	@ViewById(R.id.dema_do_correct_value3)
	CustomLinearLayoutReadInput correctValue3;// 输入的第二组温度

	@ViewById(R.id.dema_do_correct_value4)
	CustomLinearLayoutReadInput correctValue4;// 输入的第二组模拟量

//	@ViewById(R.id.dema_analog_unit_name1)
//	TextView changeUnit1; // 根据传感器改变单位
//
//	@ViewById(R.id.dema_analog_unit_name2)
//	TextView changeUnit2; // 根据传感器改变单位

	@ViewById(R.id.dema_do_correct_group2)
	LinearLayout group2; // 用来控制第二组的显示与隐藏

	@ViewById(R.id.dema_do_method_correct)
	CustomLinearLayoutReadInput cllMethod;


	@Inject
	Dialogs dialog;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		
	}

	@AfterViews
	public void init() {

		setTitleName(scp.name+"残余电流校正");
//		doSensorManager = (SensorManager) ((MyRoboApplication)getApplicationContext())
//				.getSensorManager(position);

		// 改变溶氧单位
		
		correctValue2.setLabelName(correctValue2.getLabelNalue()+"/"+sensorManager.scp.analogUnit);
		correctValue4.setLabelName(correctValue4.getLabelNalue()+"/"+sensorManager.scp.analogUnit);

		cllMethod.setValue(methodList[0]);
//		cllMethod.setText();// 初始默认是两点

	}

	/**
	 * 选择的方法
	 * 
	 * @param view
	 */
	@Click(R.id.dema_do_method_correct)
	public void selectMethod(View view) {

		Handler handler = new Handler() {

			public void handleMessage(Message msg) {

				int methodIndex = msg.getData().getInt("back");

				cllMethod.setValue(methodList[methodIndex]);

				if (methodIndex == 0) {// 选择两点标定
					group2.setVisibility(View.VISIBLE);// 可见
				} else {

					group2.setVisibility(View.GONE);// 不可见
				}

			}
		};

		dialog.listInputDialog("请选择校正方法", methodList, handler);
	}

	/**
	 * 第一组 输入的温度 (判断是否带有温度传感器)
	 * 
	 * @param view
	 */
	@Click(R.id.dema_do_correct_value1)
	public void getInput1(View view) {

		readTem(correctValue1);
	}

	/**
	 * 第一组 输入的模拟量
	 * 
	 * @param view
	 */
	@Click(R.id.dema_do_correct_value2)
	public void getInput2(View view) {
		readAnalog(correctValue2);
	}

	/**
	 * 第二组 输入的温度 (判断是否带有温度传感器)
	 * 
	 * @param view
	 */
	@Click(R.id.dema_do_correct_value3)
	public void getInput3(View view) {

		readTem(correctValue3);
	}

	/**
	 * 第二组 模拟量
	 * 
	 * @param view
	 */
	@Click(R.id.dema_do_correct_value4)
	public void getInput4(View view) {

		readAnalog(correctValue4);
	}

	/**
	 * 计算按钮 (矫正)
	 * 
	 * @param view
	 */
	@Click(R.id.dema_do_button)
	public void demaButton(View view) {

		String[] values = new String[0];
		try {

			if (group2.getVisibility() == View.GONE) {// 单点标定
				double i1 = Double.parseDouble(correctValue2.getValue()
						);
				values =ToolUtil.valueToString(SensorUtil.countDoIOne(i1), 4);
			} else {// 两点
				double t1 = Double.parseDouble(correctValue1.getValue()
						.toString());
				double i1 = Double.parseDouble(correctValue2.getValue()
						.toString());

				double t2 = Double.parseDouble(correctValue3.getValue()
						.toString());
				double i2 = Double.parseDouble(correctValue4.getValue()
						.toString());

				values =ToolUtil.valueToString(SensorUtil.countDoITwoParams(t1,t2,i1,i2), 4);
				

			}
			
			Bundle bundle = new Bundle();
			bundle.putStringArray("correct", values);
			bundle.putInt("position", position);

			Intent intent = new Intent(this,DemaSensorResultCorrect_.class);
			intent.putExtras(bundle);
			startActivity(intent);
			

		} catch (Exception e) {
			Toast.makeText(this, "输入错误", Toast.LENGTH_SHORT).show();
		}

	}

	

	/**
	 * 读温度
	 */
	private void readTem(CustomLinearLayoutReadInput view) {

		if (temSensorManager != null) {
			dialog.dynamicInputDialog("当前的温度", createHandler(view),temSensorManager, false);
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
	
	/**
	 * 建立一个handle
	 * @param view
	 * @return
	 */
	public Handler createHandler(final CustomLinearLayoutReadInput view) {

		return new Handler() {

			public void handleMessage(Message msg) {
				
				view.setValue(msg.getData().getString("back"));
				
			}
		};

	}

}
