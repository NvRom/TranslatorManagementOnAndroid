package cn.cowis.hydrilla.app.connected.activities;

import java.util.HashMap;
import java.util.Map;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.RoboGuice;
import org.androidannotations.annotations.ViewById;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import cn.cowis.hydrilla.app.Dialogs;
import cn.cowis.hydrilla.app.R;
import cn.cowis.hydrilla.app.entity.Constants;
import cn.cowis.hydrilla.app.entity.SensorManager;
import cn.cowis.hydrilla.app.parent.SuperSensorActivity;

import com.google.inject.Inject;

@EActivity(R.layout.dema_tb_input_1)
@RoboGuice
public class DemaTbInputActivity1 extends SuperSensorActivity {
	
	@ViewById(R.id.text)
	TextView text;

	
	protected SensorManager tbSensorManager;
	
	//static float[] values=new float[]{(float) 100.0, 0, (float) 20.0 ,(float) 4.0};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setTitleName(R.string.dema_one);

    }  
	
	@SuppressLint("HandlerLeak")
	@AfterViews
	public void init() {
		//text.setBackgroundResource(resid);;
		text.setText("请将电极插入零浊度水中！");
		
		
	}


	/**
	 * 标定按钮
	 * 
	 * @param view
	 */
	@Click(R.id.dema_tb_button)
	public void demaButton(View view) {
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put(Constants.DEMA_SWITCH, (byte)(1));
		
		myApp.sensorClient.sensorService.writeCacheParams(this,map, sensorManager);
		
//		checkWriter.demaInputWriter(tbSensorManager, values, null, position);
		
//		checkDialog.show();
//		
//		new Thread(){
//			public void run() {
//				try {
//					Message m1 =new Message();
//					m1.getData().putString("write", "系统正在写入参数，请勿中断连接.....");
//					handler.sendMessage(m1);
//					
//					//写入输入参数 打开标定
//					tbSensorManager.writeInputOpenCount(new float[]{100.0f, 0, 20.0f ,4.0f});
//					
//					Message m2 =new Message();
//					m2.what=6;
//					handler.sendMessage(m2);
//				} catch (Exception e) {
//					
//					Message m3 =new Message();
//					m3.what=5;
//					handler.sendMessage(m3);
//					
//					e.printStackTrace();
//				}
//			};
//		}.start();

	}

	 

}

