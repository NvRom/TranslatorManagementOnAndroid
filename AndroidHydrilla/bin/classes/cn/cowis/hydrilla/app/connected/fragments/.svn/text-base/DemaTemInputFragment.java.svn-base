package cn.cowis.hydrilla.app.connected.fragments;


import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.view.View;
import cn.cowis.hydrilla.app.R;
import cn.cowis.hydrilla.app.parent.SuperDemaInputFragment;
import cn.cowis.hydrilla.util.ToolUtil;
import cn.cowis.hydrilla.view.CustomLinearLayoutHandInput;
import cn.cowis.hydrilla.view.CustomLinearLayoutReadInput;

/**
 * @author Administrator 温度标定的界面
 */
@EFragment(R.layout.dema_tem_input)
public class DemaTemInputFragment extends SuperDemaInputFragment {
	@ViewById(R.id.dema_tem_input_value1)
	CustomLinearLayoutHandInput inputValue1; // 温度标称温度

	@ViewById(R.id.dema_tem_input_value2)
	CustomLinearLayoutReadInput inputValue2; // 标称电阻

	
	@ViewById(R.id.dema_tem_input_value3)
	CustomLinearLayoutHandInput inputValue3;// 标定温度
	
	@ViewById(R.id.dema_tem_input_value4)
	CustomLinearLayoutReadInput inputValue4;// 标称电阻

//	/**
//	 * 手动输入的dialog
//	 * 
//	 * 温度标称温度
//	 * 
//	 * @param view
//	 */
//	@Click(R.id.dema_tem_input_layout1)
//	public void getInput1(View view) {
//
//		dialog.inputDialog("请输入标称温度", createHandler(inputValue1));
//	}

	/**
	 * 手动输入的dialog
	 * 
	 * 标称电阻
	 * 
	 * @param view
	 */
	@Click(R.id.dema_tem_input_value2)
	public void getInput2(View view) {

		//dialog.inputDialog("请输入标称电阻", createHandler(inputValue2));
		dialog.dynamicInputDialog("请输入标称电阻", createHandler(inputValue2),
				sensorManager, true);
	}

//	/**
//	 * 手动输入的dialog
//	 * 
//	 * 输入标定温度
//	 * 
//	 * @param view
//	 */
//	@Click(R.id.dema_tem_input_layout3)
//	public void getInput3(View view) {
//		dialog.inputDialog("请输入标定温度", createHandler(inputValue3));
//	}

	/**
	 * 手动输入的dialog
	 * 
	 * 温度标称温度
	 * 
	 * @param view
	 */
	@Click(R.id.dema_tem_input_value4)
	public void getInput4(View view) {
		dialog.dynamicInputDialog("零功率电阻值", createHandler(inputValue4),
				sensorManager, true);
	}
	/**
	 * 标定按钮
	 */
	@Click(R.id.dema_tem_button)
	public void demaTem() {

		String[] inputStrs= new String[]{inputValue1.getValue(),inputValue2.getValue(),
				inputValue3.getValue(),inputValue4.getValue()};
		
		beginDema(ToolUtil.stringsToFloats(inputStrs));
		
	}

}
