package cn.cowis.hydrilla.app.connected.activities;

import java.util.HashMap;
import java.util.Map;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.RoboGuice;
import org.androidannotations.annotations.ViewById;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import cn.cowis.hydrilla.app.Dialogs;
import cn.cowis.hydrilla.app.R;
import cn.cowis.hydrilla.app.entity.Constants;
import cn.cowis.hydrilla.app.parent.SuperDemaInputActivity;
import cn.cowis.hydrilla.app.parent.SuperSensorActivity;
import cn.cowis.hydrilla.util.ToolUtil;
import cn.cowis.hydrilla.view.CustomLinearLayoutReadInput;

import com.google.inject.Inject;

@EActivity(R.layout.dema_tb_input_2)
@RoboGuice
public class DemaTbInputActivity2 extends SuperSensorActivity{

	@ViewById(R.id.dema_tb_input_value1)
	CustomLinearLayoutReadInput inputValue1;// 输入的第一组浊度
	
	@ViewById(R.id.dema_tb_input_value2)
	CustomLinearLayoutReadInput inputValue2;// 输入的第一组浊度

	@ViewById(R.id.dema_tb_input_value3)
	CustomLinearLayoutReadInput inputValue3;// 输入的第一组模拟量

	@ViewById(R.id.dema_tb_input_value4)
	CustomLinearLayoutReadInput inputValue4;// 输入的第二组浊度
	
	@Inject
	Dialogs dialog;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setTitleName(R.string.dema_two);

    }  

	/**
	 * 第一组 输入电流
	 * 
	 * @param view
	 */
	@Click(R.id.dema_tb_input_value1)
	public void getInput1(View view) {

		
		dialog.dynamicInputDialog("当前标定电流", createHandler(inputValue1),
				sensorManager, true);
		
	}

	/**
	 * 第一组 输入的UDT
	 * 
	 * @param view
	 */
	@Click(R.id.dema_tb_input_value2)
	public void getInput2(View view) {

		dialog.inputDialog("输入校准液浊度值", createHandler(inputValue2));
		
	}
	
	
	/**
	 * 第er组 输入电流
	 * 
	 * @param view
	 */
	@Click(R.id.dema_tb_input_value3)
	public void getInput3(View view) {
		dialog.dynamicInputDialog("当前标定电流", createHandler(inputValue3),
				sensorManager, true);
	}

	/**
	 * 第er组 输入的UDT
	 * 
	 * @param view
	 */
	@Click(R.id.dema_tb_input_value4)
	public void getInput4(View view) {

		dialog.inputDialog("输入校准液浊度值", createHandler(inputValue4));
	}


	

	/**
	 * 标定按钮
	 * 
	 * @param view
	 */
	@Click(R.id.dema_tb_button)
	public void demaButton(View view) {
		

		String[] inputStrs = new String[] { inputValue1.getValue(),inputValue2.getValue(), inputValue3.getValue(), inputValue4.getValue() };
		
		float[] values= ToolUtil.stringsToFloats(inputStrs);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put(Constants.DEMA_PARAMS,values );
		
		if(values!=null){
		 myApp.sensorClient.sensorService.writeCacheParams(this,map, sensorManager);
		}else{
			Toast.makeText(this, "输入有误!", Toast.LENGTH_SHORT).show();
		}

	}

}
