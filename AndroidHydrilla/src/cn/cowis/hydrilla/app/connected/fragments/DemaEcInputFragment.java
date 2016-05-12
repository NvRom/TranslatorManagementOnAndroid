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
//import android.app.FragmentManager;
//import android.app.FragmentTransaction;
/**
 * @author Administrator
 *电导标定的输入界面
 */
@EFragment(R.layout.dema_ec_input)
public class DemaEcInputFragment extends SuperDemaInputFragment{

	@ViewById(R.id.dema_ec_input_value1)
	CustomLinearLayoutReadInput inputValue1; //标定状态温度
	
	@ViewById(R.id.dema_ec_input_value2)
	CustomLinearLayoutReadInput inputValue2;//标定的阻抗响应
	
	@ViewById(R.id.dema_ec_count1)
	CustomLinearLayoutReadInput countValue;// 通过温度计算的电导值
	
	
	/**
	 * 
	 * 输入温度
	 * @param view
	 */
	@Click(R.id.dema_ec_input_value1)
	public void getInput1(View view){
		
		Handler handler=new Handler() {

			public void handleMessage(Message msg) {
				String backValue= msg.getData().getString("back");
				inputValue1.setValue(backValue);
				try{
				  double t=Double.parseDouble(backValue);
				  String countEcValue= SensorUtil.countTToEc(t);
				  countValue.setValue(countEcValue);
				}catch(Exception e){
					
				}
			}
		};
		
		if(temSensorManager==null){
			dialog.inputDialog("标准溶解温度/℃", handler);
		}else{
			dialog.dynamicInputDialog("标准溶解温度/℃", handler, temSensorManager, false);
		}
	}
	
	/**
	 * 输入校准液阻抗
	 * @param view
	 */
	@Click(R.id.dema_ec_input_value2)
	public void getInput2(View view){
		
		dialog.dynamicInputDialog("校准液阻抗值/Ω", createHandler(inputValue2), sensorManager, true);
	}

	/**
	 * 标定按钮
	 * @param view
	 */
	@Click(R.id.dema_ec_button)
	public void demaButton(View view){

		String[] inputStrs= new String[]{inputValue1.getValue(),inputValue2.getValue()};
		
		
		beginDema(ToolUtil.stringsToFloats(inputStrs));
		
		
	}
	
	
	
}
