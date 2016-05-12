 package cn.cowis.hydrilla.app.connected.fragments;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import cn.cowis.hydrilla.app.R;
import cn.cowis.hydrilla.app.parent.SuperDemaInputFragment;
import cn.cowis.hydrilla.util.SensorUtil;
import cn.cowis.hydrilla.util.ToolUtil;
import cn.cowis.hydrilla.view.CustomLinearLayoutReadInput;

/** 
 * @author Administrator ph标定的输入界面
 */
@EFragment(R.layout.dema_ph_input)
public class DemaPhInputFragment extends SuperDemaInputFragment {

	@ViewById(R.id.dema_ph_input_value1)
	CustomLinearLayoutReadInput inputValue1 = null; // 输入 的温度

	@ViewById(R.id.dema_ph_input_value2)
	CustomLinearLayoutReadInput inputValue2 = null; // 输入的ph的模拟量1
	
	@ViewById(R.id.dema_ph_input_value3)
	CustomLinearLayoutReadInput inputValue3 = null; // 输入的ph的模拟量2

	
	@ViewById(R.id.dema_ph_input_value4)
	CustomLinearLayoutReadInput inputValue4 = null; // 输入的ph的模拟量3

	@ViewById(R.id.dema_ph_count1)
	CustomLinearLayoutReadInput cTextView1 = null;// 由温度计算的ph1

	@ViewById(R.id.dema_ph_count2)
	CustomLinearLayoutReadInput cTextView2 = null;// 由温度计算的ph2

	@ViewById(R.id.dema_ph_count3)
	CustomLinearLayoutReadInput cTextView3 = null;// 由温度计算的ph3
	
	
	/**
	 * 输入温度
	 */
	@Click(R.id.dema_ph_input_value1)
	public void getInput1(View view) {
		Handler handler = new Handler() {

			public void handleMessage(Message msg) {
				String backValue = msg.getData().getString("back");
				inputValue1.setValue(backValue);
				if("".equals(backValue)){
					cTextView1.setValue("");
					cTextView2.setValue("");
					cTextView3.setValue("");
					return;
				}
				try {
					double t = Double.parseDouble(backValue);
					String[] countValues = SensorUtil.countTToPh(t);
					cTextView1.setValue(countValues[0]);
					cTextView2.setValue(countValues[1]);
					cTextView3.setValue(countValues[2]);
				} catch (Exception e) { }

			}
		};

	
		if (temSensorManager == null) {// 如果代表没有温度
			dialog.inputDialog("请输入标定状态温度/℃", handler);
		} else {
			dialog.dynamicInputDialog("请输入标定状态温度/℃", handler,
					temSensorManager, false);

		}
	}

	/**
	 * ph的 模拟量1
	 * 
	 * @param view
	 */
	@Click(R.id.dema_ph_input_value2)
	public void getInput2(View view) {

		dialog.dynamicInputDialog("B4校准液(邻苯二甲酸氢钾)", createHandler(inputValue2),
				sensorManager, true);

	}

	/**
	 * ph的 模拟量2
	 * 
	 * @param view
	 */
	@Click(R.id.dema_ph_input_value3)
	public void getInput3(View view) {
		dialog.dynamicInputDialog("B6校准液(混合磷酸盐)", createHandler(inputValue3),
				sensorManager, true);

	}

	/**
	 * ph的 模拟量3
	 * 
	 * @param view
	 */
	@Click(R.id.dema_ph_input_value4)
	public void getInput4(View view) {

		dialog.dynamicInputDialog("B9校准液(四硼酸钠)", createHandler(inputValue4),
				sensorManager, true);

	}

	/**
	 * 标定按钮
	 */
	@Click(R.id.dema_ph_button)
	public void onButtonClick() {
		
		String[] inputStrs = new String[] { inputValue1.getValue(), inputValue2.getValue(),
				inputValue3.getValue(), inputValue4.getValue() };
		
		
		beginDema(SensorUtil.stringsToFloatsPh(inputStrs));
		

	}


}
