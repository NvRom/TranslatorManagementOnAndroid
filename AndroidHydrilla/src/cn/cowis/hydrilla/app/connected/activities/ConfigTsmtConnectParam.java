package cn.cowis.hydrilla.app.connected.activities;

import java.util.HashMap;
import java.util.Map;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.RoboGuice;
import org.androidannotations.annotations.ViewById;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import cn.cowis.hydrilla.app.Dialogs;
import cn.cowis.hydrilla.app.R;
import cn.cowis.hydrilla.app.entity.Constants;
import cn.cowis.hydrilla.app.entity.TransmitterManager.TsmtCommonParams;
import cn.cowis.hydrilla.app.parent.SuperActivity;
import cn.cowis.hydrilla.util.TsmtUtil;
import cn.cowis.hydrilla.view.CustomLinearLayoutReadInput;

import com.google.inject.Inject;

@EActivity(R.layout.config_param_3)
@RoboGuice
public class ConfigTsmtConnectParam extends SuperActivity {

	
	@ViewById(R.id.config_slave_id)
	CustomLinearLayoutReadInput slaveIdView;
	
	@ViewById(R.id.group_usb)
	LinearLayout groupUsb;
	
	@ViewById(R.id.usb_spinner_parity)
	Spinner spinnerParity;// 基偶校验位

	@ViewById(R.id.usb_spinner_stop)
	Spinner spinnerStop;// 停止位

	@ViewById(R.id.usb_spinner_boud)
	Spinner spinnerBaud;// 波特率
	
	@Inject
	Dialogs dialog;
	
	
	TsmtCommonParams tcp=null;
	
	@AfterViews
	public void afterView(){
		
		 tcp= myApp.currectTsmtManager.tcp;
		
		slaveIdView.setValue(String.valueOf(tcp.getSlaveId()));//设置从机
		
		//设置
		setInitSpinner(R.array.usb_baud,spinnerBaud,TsmtUtil.getBaudPosition(tcp.getBaud()));//波特率
		
		setInitSpinner(R.array.usb_stop,spinnerStop,TsmtUtil.getStopPosition(tcp.getParityStop()));//停止位
		setInitSpinner(R.array.usb_parity,spinnerParity,TsmtUtil.getParityPosition(tcp.getParityStop()));//基偶校见位
		
		
		if(myApp.isBlueToothConnect()){
			groupUsb.setVisibility(View.GONE);
		}
	}
	
	@Click(R.id.config_slave_id)
	public void clickId(View view){
		
		Handler handler = new Handler() {//获得数据的handle
			public void handleMessage(Message msg) {
				final int data = msg.getData().getInt("back");
				
				if(data>=1&&data<=255){
					slaveIdView.setValue(String.valueOf(data));
				}else{
					Toast.makeText(ConfigTsmtConnectParam.this, "从机范围在1~255之间", Toast.LENGTH_SHORT).show();
				}

			};
	};
	
	dialog.inputDialogPlus("设置从机地址", handler, tcp.getSlaveId());
	}
	
	public void setInitSpinner(int stringsID,Spinner spinner,int position){
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter
				.createFromResource(this, stringsID,
						android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(R.layout.spinner_item_stypelayout);

		spinner.setAdapter(adapter);
		spinner.setSelection(position);
		
	}

	@Click(R.id.tsmt_save_param)
	
	public void saveParam(View view){
		
		Map<String,Object> map=new HashMap<String,Object>();
		
		byte baud= TsmtUtil.getBaud(spinnerBaud.getSelectedItem().toString());
		
		if(!myApp.isBlueToothConnect()){
		  byte stopParity= TsmtUtil.getParityStopbit(spinnerParity.getSelectedItem().toString(), spinnerStop.getSelectedItem().toString());
		  map.put(Constants.BAUD, baud);
		  map.put(Constants.PARITY_STOP, stopParity);
		}
		map.put(Constants.SLAVE_ID, Integer.parseInt(slaveIdView.getValue()));
		
		myApp.sensorClient.sensorService.writeCacheParams(this, map, myApp.currectTsmtManager);
		
		
	}

}
