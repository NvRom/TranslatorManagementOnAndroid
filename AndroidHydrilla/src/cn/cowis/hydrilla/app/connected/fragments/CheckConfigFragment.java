package cn.cowis.hydrilla.app.connected.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import cn.cowis.hydrilla.app.R;
import cn.cowis.hydrilla.app.connected.activities.CheckResultActivity_;
import cn.cowis.hydrilla.app.entity.CacheParams;
import cn.cowis.hydrilla.app.entity.CacheParams.ParamType;
import cn.cowis.hydrilla.app.entity.Constants;
import cn.cowis.hydrilla.app.parent.SuperSensorFragment;
import cn.cowis.hydrilla.app.service.BackEvents.BackEventsType;
import cn.cowis.hydrilla.app.service.BackEvents.OnBackListener;
import cn.cowis.hydrilla.view.CustomLinearLayoutButton;
import cn.cowis.hydrilla.view.CustomLinearLayoutHandInput;
import cn.cowis.hydrilla.view.CustomTextViewButton;


/**
 * 
 * 检测参数的配置(补偿配置，曲线配置)
 * @author Administrator shuang
 *
 * 所有传感器 (在检测设置中 只有补偿设置 模块不一样)
 */
@EFragment(R.layout.check_config)
public class CheckConfigFragment extends SuperSensorFragment implements
		OnItemSelectedListener,OnBackListener{

	@ViewById(R.id.check_param_button)
	CustomLinearLayoutButton paramButton;

	@ViewById(R.id.check_comp_button)
	CustomLinearLayoutButton checkButton;
	
	@ViewById(R.id.check_param_layout)
	LinearLayout paramLayout;// 补偿的group

	int interval, num;
	// 设置参数
	// @ViewById(R.id.check_param_nh4)
	// LinearLayout compLayoutNh4;//离子
	//
	// @ViewById(R.id.spinner_project)
	// CustomLinearLayoutSpinner spinnerProject;
	//
	// @ViewById(R.id.spinner_method)
	// CustomLinearLayoutSpinner spinnerMethod;
	//
	// @ViewById(R.id.radio_auto)
	// RadioButton radioAuto;
	//
	// @ViewById(R.id.spinner_unit)
	// CustomLinearLayoutSpinner spinnerUnit;
	//
	// @ViewById(R.id.spinner_round)
	// 检测周期
	@ViewById(R.id.check_period)
	CustomLinearLayoutHandInput checkPeriod;

	@ViewById(R.id.check_period_unit)
	Spinner periodUnit;
	
	
	static int[] periods = new int[] { 1, 60, 60 * 60 };
	int currectPeriods = 1;// 默认是秒

	// 检测方式
	@ViewById(R.id.radio_dyn)
	RadioButton dynamicCheckButton;

	@ViewById(R.id.radio_num)
	RadioButton numberCheckButton;

	@ViewById(R.id.custom_times)
	CustomTextViewButton customTimes;

	// 补偿的group
	@ViewById(R.id.check_comp_button)
	CustomLinearLayoutButton compButton;

	
	@ViewById(R.id.check_comp_layout)
	LinearLayout compLayout;// 补偿的group

	List<CustomLinearLayoutHandInput> listInputParams = new ArrayList<CustomLinearLayoutHandInput>();

	String[] marks = new String[] { "∨", "＞" };

	CacheParams cp=null;
	@AfterViews
	public void init() {
		
		// 时间单位 时分秒
		ArrayAdapter<CharSequence> adapterDataUnit = ArrayAdapter
				.createFromResource(getActivity(), R.array.timeUnit,
						android.R.layout.simple_spinner_item);
		adapterDataUnit.setDropDownViewResource(R.layout.sherlock_spinner_item);

		periodUnit.setAdapter(adapterDataUnit);
		periodUnit.setSelection(0);
		periodUnit.setOnItemSelectedListener(this);

		cp = sensorManager.getParams(Constants.COMPENSATE_PARAMS);
		// float[] compDefaultValues=sensorCompensate.getDefaultCompValues();

		insertCompView(cp);
		
		compLayout.setVisibility(View.GONE);
		paramButton.setMarker(marks[0]);

	}

	/**
	 * 动态添加
	 */
	public void insertCompView(CacheParams cp) {

		String[] comNames = (String[]) cp.getName();
		ParamType[] comTypes = cp.getParamTypes();
		float[] compValues = (float[]) cp.getHasInitValue();// 标定参数开始已经初始化了，他们
															// 3个数组是一一对应的

		if (comNames.length == 0)
			checkButton.setVisibility(View.GONE);

		for (int i = 0; i < comNames.length; i++) {

			if (ParamType.T == comTypes[i] && temSensorManager != null
					&& scp.getTemSwitch() == 1) {// 如果这个补偿参数类型是温度

				if (comNames.length == 1) {// 代表就一个温度补偿 而且还被设置好了
					checkButton.setVisibility(View.GONE);
				}
				continue;
			}

			CustomLinearLayoutHandInput cllhi = new CustomLinearLayoutHandInput(
					getActivity());

			LayoutParams layoutParams = new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

			cllhi.setLayoutParams(layoutParams);

			cllhi.setLabelName(comNames[i]);
			cllhi.setValue(String.valueOf(compValues[i]));
			cllhi.setBackgroundResource(R.drawable.bg_selector_nostroke_layout);

			compLayout.addView(cllhi);

			listInputParams.add(cllhi);

			if (i != comNames.length - 1) {// 如果不是最后一个还要加一条线分开

				TextView textView = new TextView(getActivity());// 一条细线
				textView.setLayoutParams(new LayoutParams(
						LayoutParams.MATCH_PARENT, 2));
				textView.setBackgroundResource(R.color.litter_gray);
				compLayout.addView(textView);
				//
			}

		}

	}

	@Click(R.id.radio_dyn)
	public void dynamicCheckButton() {
		dynamicCheckButton.setChecked(true);
		numberCheckButton.setChecked(false);

	}

	@Click(R.id.radio_num)
	public void numberCheckButton() {
		dynamicCheckButton.setChecked(false);
		numberCheckButton.setChecked(true);
	}

	@Click(R.id.check_comp_button)
	public void checkComp(View view) {

		if (compButton.getMarker().equals(marks[0])) {
			compLayout.setVisibility(View.GONE);
			compButton.setMarker(marks[1]);
		} else {

			compLayout.setVisibility(View.VISIBLE);
			compButton.setMarker(marks[0]);
		}

	}

	@Click(R.id.check_param_button)
	public void checkParam(View view) {

		if (paramButton.getMarker().equals(marks[0])) {
			paramLayout.setVisibility(View.GONE);
			paramButton.setMarker(marks[1]);
		} else {

			paramLayout.setVisibility(View.VISIBLE);
			paramButton.setMarker(marks[0]);
		}

	}

	/**
	 * 确定按钮
	 */
	@Click(R.id.result_sure)
	public void seletedSure() {

		if(initCheckParams()){
			
			if(writeCompParams()){
				
				System.out.println("跳转1次");
				skipActivity();
			}
			
		}

	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		currectPeriods = periods[position];

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * 写入补偿参数（返回是否通过）
	 */
	public boolean writeCompParams() {

		
		float[] values= null;
		
		
		if (listInputParams.size() > 0) {

			values= ((float[]) cp.getHasInitValue()).clone();
			try{
			int j=values.length-1;
			for (int i = listInputParams.size()-1; i >=0; i--) {

				values[j] = Float.parseFloat(listInputParams.get(i).getValue());
				j--;
			}
			}catch(Exception e){
			
			Toast.makeText(getActivity(), "请输入正确的参数!", Toast.LENGTH_SHORT)
						.show();
			}
			Map<String, Object> map = new HashMap<String, Object>();

			map.put(Constants.COMPENSATE_PARAMS, values);
			myApp.sensorClient.sensorService.writeCacheParams(getActivity(),
					map, sensorManager);

			
		}else return true;
		return false;

	}
	/**
	 * 用来跳转界面
	 */
	public void skipActivity() {

		Intent intent = new Intent(afterActivity, CheckResultActivity_.class);
		Bundle bundle = new Bundle();
		bundle.putInt("interval", interval);
		bundle.putInt("num", num);
		bundle.putInt("position", position);
		intent.putExtras(bundle);
		afterActivity.startActivity(intent);
	}

	/**
	 * @return
	 *       
	 */
	public boolean initCheckParams() {

		try {
			interval = Integer.parseInt(checkPeriod.getValue())
					* currectPeriods;

			if (numberCheckButton.isChecked()) {
				num = Integer.parseInt(customTimes.getCurrentValue());
			} else {
				num = -1;// 当数量是-1的时候一直跑下去
			}

		} catch (Exception e) {
			Toast.makeText(getActivity(), "数据输入有误！", Toast.LENGTH_SHORT).show();
			return false;

		}
		return true;

	}
	
	@Override
	public void onDroneEvent(BackEventsType event, Object object) {
		
		switch(event){
			
		case CACHE_WRITE_VALUE:
			
			System.out.println("跳转2次");
			skipActivity();
		}
		
		
	}
	
	
}
