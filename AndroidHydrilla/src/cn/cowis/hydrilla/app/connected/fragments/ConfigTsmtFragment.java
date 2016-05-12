package cn.cowis.hydrilla.app.connected.fragments;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.TextView;
import cn.cowis.hydrilla.app.R;
import cn.cowis.hydrilla.app.connected.activities.ConfigSensorActivity_;
import cn.cowis.hydrilla.app.connected.activities.ConfigTsmtConnectParam_;
import cn.cowis.hydrilla.app.entity.SensorManager;
import cn.cowis.hydrilla.app.parent.SuperFragment;
import cn.cowis.hydrilla.view.CustomLinearLayoutButton;


/**
 * 参数配置的activity (根据传感器的类型和个数 显示不同的配置)
 * @author Administrator
 * 
 */
@EFragment(R.layout.config_param_1)
public class ConfigTsmtFragment extends SuperFragment {

	
	@ViewById(R.id.father_tsmt_layout)
	LinearLayout fatherTsmtLayout;
	
	Map<RadioButton, LinearLayout> mapLayouts = new HashMap<RadioButton, LinearLayout>();
	
	// Handler handler = new Handler() {//获得数据的handle
	// public void handleMessage(Message msg) {
	// final int data = msg.getData().getInt("back");
	//
	// if(data<1 || data>255){
	// Toast.makeText(getActivity(),"从机地址超出 1~255 ", Toast.LENGTH_SHORT).show();
	// return;
	// }
	// myApp.sensorClient.sensorService.writeCacheParams(maps, t);
	//
	//
	// };
	// };
	
	/**
	 * @author Administrator 这个是点击传感器
	 * 
	 */
	public class MyOnClickSensorListener implements OnClickListener {

		private int position;

		public MyOnClickSensorListener(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View v) {

			Intent intent = new Intent(getActivity(),
					ConfigSensorActivity_.class);
			Bundle bundle = new Bundle();
			bundle.putInt("position", position);
			intent.putExtras(bundle);
			getActivity().startActivity(intent);
		}

	}

	/**
	 * @author Administrator 这个是点击连接配置
	 * 
	 */
	public class MyOnClickConnect implements OnClickListener {

		@Override
		public void onClick(View v) {

			Intent intent = new Intent(getActivity(),
					ConfigTsmtConnectParam_.class);// TODO
			Bundle bundle = new Bundle();
			intent.putExtras(bundle);
			startActivity(intent);
		}

	}

	
	@AfterViews
	public void init() {

		initTsmt();
	
	}

	/**
	 * 根据transmitterManage动态创建 传感器
	 */
	public void initTsmt() {


		CustomLinearLayoutButton connectButton = new CustomLinearLayoutButton(
				getActivity());
		connectButton.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		connectButton
				.setBackgroundResource(R.drawable.bg_selector_nostroke_layout);
		connectButton.setButtonLabel("连接配置");
		connectButton.setOnClickListener(new MyOnClickConnect());
		fatherTsmtLayout.addView(connectButton);

		List<SensorManager> sensorManagers = currectTsmtManager
				.getAvalibleSensorManagers();
		
		addLine();
		
		// mapLayouts.put(tsmtSelect, tsmtLayout);
		for (int i = 0; i < sensorManagers.size(); i++) {

			CustomLinearLayoutButton cllb = new CustomLinearLayoutButton(
					getActivity());

			cllb.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT));
			cllb.setBackgroundResource(R.drawable.bg_selector_nostroke_layout);
			cllb.setButtonLabel(sensorManagers.get(i).scp.getName() + "传感器");

			cllb.setOnClickListener(new MyOnClickSensorListener(i));

			fatherTsmtLayout.addView(cllb);

			if (i != sensorManagers.size() - 1) {// 如果不是最后一个还要加一条线分开

				addLine();
			}
		}
		// fatherTsmtLayout.addView(tsmtLayout);
		// }
		// return fristTsmt;
	}
	
	// /**
	// * 获得从机地址
	// */
	// @Click(R.id.config_slave_id)
	// public void inputSeriaId(View view) {
	//
	// dialog.inputDialogPlus("修改从机地址为：", handler,
	// Integer.parseInt(slaveIdLayout.getValue()));
	// }
	
	public void addLine(){
		
		
		TextView textView = new TextView(getActivity());// 一条细线
		textView.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, 2));
		textView.setBackgroundResource(R.color.litter_gray);
		fatherTsmtLayout.addView(textView);
		
	}

}
