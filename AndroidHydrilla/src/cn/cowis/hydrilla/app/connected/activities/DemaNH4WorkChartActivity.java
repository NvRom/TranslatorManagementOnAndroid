package cn.cowis.hydrilla.app.connected.activities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.RoboGuice;
import org.androidannotations.annotations.ViewById;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.cowis.hydrilla.app.Dialogs;
import cn.cowis.hydrilla.app.R;
import cn.cowis.hydrilla.app.entity.SensorManager;
import cn.cowis.hydrilla.app.parent.SuperSensorActivity;

import com.google.inject.Inject;

@EActivity(R.layout.dema_nh4_work_chart)
@RoboGuice
public class DemaNH4WorkChartActivity extends SuperSensorActivity{
	
	@ViewById(R.id.nh4_tem)
	TextView nh4_tem ; // 输入 的温度

	@ViewById(R.id.methodText)
	TextView methodText ; // 
	
	@ViewById(R.id.unitText)
	TextView unitText ; // 单位
	
	@ViewById(R.id.data)
	TextView data ; // 单位
	
	/**
	 * 校准液浓度
	 */
	@ViewById(R.id.emf_text_0)
	TextView emf_text_0 ; //emf1
	
	@ViewById(R.id.emf_text_1)
	TextView emf_text_1 ; //emf1
	
	@ViewById(R.id.emf_text_2)
	TextView emf_text_2 ; //emf2
	
	@ViewById(R.id.emf_text_3)
	TextView emf_text_3 ; //emf3
	
	@ViewById(R.id.emf_text_4)
	TextView emf_text_4 ; //emf4
	
	@ViewById(R.id.emf_text_5)
	TextView emf_text_5 ; //emf5
	
	@ViewById(R.id.emf_text_6)
	TextView emf_text_6 ; //emf6
	
	@ViewById(R.id.emf_text_7)
	TextView emf_text_7 ; //emf7
	
	@ViewById(R.id.emf_text_8)
	TextView emf_text_8 ; //emf8
	
	/**
	 * 电动势
	 */
	@ViewById(R.id.correct_1)
	TextView correct_1 ; //text1
	
	@ViewById(R.id.correct_2)
	TextView correct_2 ; //text2
	
	@ViewById(R.id.correct_3)
	TextView correct_3 ; //text3
	
	@ViewById(R.id.correct_4)
	TextView correct_4 ; //text4
	
	@ViewById(R.id.correct_5)
	TextView correct_5 ; //text5
	
	@ViewById(R.id.correct_6)
	TextView correct_6 ; //text6
	
	@ViewById(R.id.correct_7)
	TextView correct_7 ; //text7
	
	@ViewById(R.id.correct_8)
	TextView correct_8 ; //text8
	
	/**
	 * 各组的布局
	 */
	@ViewById(R.id.densityLayout)
	LinearLayout densityLayout ; //densityLayout
	
	@ViewById(R.id.group_1)
	LinearLayout group_1 ; //group1
	
	@ViewById(R.id.group_2)
	LinearLayout group_2 ; //group2
	
	@ViewById(R.id.group_3)
	LinearLayout group_3 ; //group3
	
	@ViewById(R.id.group_4)
	LinearLayout group_4 ; //group4
	
	@ViewById(R.id.group_5)
	LinearLayout group_5 ; //group5
	
	@ViewById(R.id.group_6)
	LinearLayout group_6 ; //group6
	
	@ViewById(R.id.group_7)
	LinearLayout group_7 ; //group7
	
	@ViewById(R.id.group_8)
	LinearLayout group_8 ; //group8
	
	@Inject
	Dialogs dialog;
	
	@Inject
	Context context;
	
//	@Inject
//	CheckWriter checkWriter;
	
	private int position;
	private SensorManager nh4SensorManager;
	private int numberTeam = 5;//显示的组数
	private List<LinearLayout> layoutList; 
	private List<TextView> correctTextList;
	private List<TextView> emfTextList;
	private List<List<double[]>> list =new ArrayList<List<double[]>>(); //两个数组的数据集合

	int ionic;
	int method;
	int unit;
	
	@AfterViews
	public void init() {

		
		layoutList = new ArrayList<LinearLayout>();
		correctTextList = new ArrayList<TextView>();
		emfTextList = new ArrayList<TextView>();
		
		layoutList.add(group_1);
		layoutList.add(group_2);
		layoutList.add(group_3);
		layoutList.add(group_4);
		layoutList.add(group_5);
		layoutList.add(group_6);
		layoutList.add(group_7);
		layoutList.add(group_8);
		correctTextList.add(correct_1);
		correctTextList.add(correct_2);
		correctTextList.add(correct_3);
		correctTextList.add(correct_4);
		correctTextList.add(correct_5);
		correctTextList.add(correct_6);
		correctTextList.add(correct_7);
		correctTextList.add(correct_8);
		emfTextList.add(emf_text_1);
		emfTextList.add(emf_text_2);
		emfTextList.add(emf_text_3);
		emfTextList.add(emf_text_4);
		emfTextList.add(emf_text_5);
		emfTextList.add(emf_text_6);
		emfTextList.add(emf_text_7);
		emfTextList.add(emf_text_8);
		
		showVisibility(numberTeam);
		
		
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		position = getIntent().getExtras().getInt("key");
		
		Handler handler =new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				
				Bundle bun = msg.getData();
				ionic =bun.getInt("ionic");
				method =bun.getInt("method");
				unit = bun.getInt("unit");
			
				switch(method){
				
				case 0 :densityLayout.setVisibility(View.GONE);
					methodText.setText("NH4+标准液浓度/");
					break;
				case 1 :densityLayout.setVisibility(View.VISIBLE);
					methodText.setText("NH4+标准使用液稀释倍数/");
					break;
				}
				
				switch(unit){
				case 0 :unitText.setText("(mol/L)");break;
				case 1 :unitText.setText("(mg/L)");break;
				case 2 :unitText.setText("M");break;
				case 3 :unitText.setText("PPm");break;
				}

			}
		};
		
		dialog.setNH4ConfigDialog("校准操作选项", handler);
		
		setTitleName("工作曲线标定");
	
    }  
	
	/**
	 * 点击添加一个校准组
	 * @param view
	 */
	@Click(R.id.button_plus)
	public void button_plus(View view) {
		if(++numberTeam>8)
			numberTeam--;
		
		showVisibility(numberTeam);
	}
	
	/**
	 * 点击减去一个校准组
	 * @param view
	 */
	@Click(R.id.button_minus)
	public void button_minus(View view) {
		if(--numberTeam<0)
			numberTeam++;
		
		showVisibility(numberTeam);
	}
	
	/**
	 * 输入温度
	 */
	@Click(R.id.nh4_tem_layout)
	public void input_NH4_Tem(View view) {
		System.out .println("标定状态温度");
		Handler handler = new Handler() {

			public void handleMessage(Message msg) {
				String backValue = msg.getData().getString("back");
				nh4_tem.setText(backValue);
			}
		};
		
		if (temSensorManager == null) {	// 如果没有温度补偿
			dialog.inputDialog("请输入标定状态温度/℃", handler);
		} else {
			dialog.dynamicInputDialog("请输入标定状态温度/℃", handler,temSensorManager, false);
		}
	}
	
	/**
	 * 模拟量  空白（电动势）
	 * 
	 * @param view
	 */
	@Click(R.id.emf_layout_0)
	public void emf_layout_0(View view) {

		dialog.dynamicInputDialog("标准液电动势 /mV", createHandler(emf_text_0),
				nh4SensorManager, true);

	}
	
	/**
	 * 模拟量1 （电动势）
	 * 
	 * @param view
	 */
	@Click(R.id.emf_layout_1)
	public void emf_layout_1(View view) {

		dialog.dynamicInputDialog("标准液电动势 /mV", createHandler(emf_text_1),
				nh4SensorManager, true);

	}
	
	/**
	 * 模拟量2
	 * 
	 * @param view
	 */
	@Click(R.id.emf_layout_2)
	public void emf_layout_2(View view) {

		dialog.dynamicInputDialog("标准液电动势 /mV", createHandler(emf_text_2),
				nh4SensorManager, true);

	}
	
	/**
	 * 模拟量3
	 * 
	 * @param view
	 */
	@Click(R.id.emf_layout_3)
	public void emf_layout_3(View view) {

		dialog.dynamicInputDialog("标准液电动势 /mV", createHandler(emf_text_3),
				nh4SensorManager, true);

	}
	
	/**
	 * 模拟量4
	 * 
	 * @param view
	 */
	@Click(R.id.emf_layout_4)
	public void emf_layout_4(View view) {

		dialog.dynamicInputDialog("标准液电动势 /mV", createHandler(emf_text_4),
				nh4SensorManager, true);

	}
	
	/**
	 * 模拟量5
	 * 
	 * @param view
	 */
	@Click(R.id.emf_layout_5)
	public void emf_layout_5(View view) {

		dialog.dynamicInputDialog("标准液电动势 /mV", createHandler(emf_text_5),
				nh4SensorManager, true);

	}
	
	/**
	 * 模拟量6
	 * 
	 * @param view
	 */
	@Click(R.id.emf_layout_6)
	public void emf_layout_6(View view) {

		dialog.dynamicInputDialog("标准液电动势 /mV", createHandler(emf_text_6),
				nh4SensorManager, true);

	}
	
	/**
	 * 模拟量7
	 * 
	 * @param view
	 */
	@Click(R.id.emf_layout_7)
	public void emf_layout_7(View view) {

		dialog.dynamicInputDialog("标准液电动势 /mV", createHandler(emf_text_7),
				nh4SensorManager, true);

	}
	
	/**
	 * 模拟量8
	 * 
	 * @param view
	 */
	@Click(R.id.emf_layout_8)
	public void emf_layout_8(View view) {

		dialog.dynamicInputDialog("标准液电动势 /mV", createHandler(emf_text_8),
				nh4SensorManager, true);

	}
	
	/**
	 * 标定按钮
	 */
	@Click(R.id.workchart_nh4_button)
	public void onButtonClick() {
		
		Handler handler = new Handler(){
			public void handleMessage(Message msg) {
				List<List<double[]>> list = (List<List<double[]>>) msg.getData().getSerializable("dataXY");
				double[] values = msg.getData().getDoubleArray("params");
				
				System.out.println("ionic=ionic="+ionic);
				
				 Intent intent =new Intent(DemaNH4WorkChartActivity.this,DemaWorkchartResultActivity_.class);
				 Bundle bund = new Bundle();
				 bund.putSerializable("x", (Serializable) list.get(0));//x轴坐标数组
				 bund.putSerializable("y", (Serializable) list.get(1));//y轴坐标数组
				 bund.putDoubleArray("param", values); //斜率，lod，pts，相关系数，截距
				 bund.putDouble("tem", Double.parseDouble((String) nh4_tem.getText()));//温度
				 bund.putInt("ionic", ionic);//是否离子强度剂
				 intent.putExtra("bundle", bund);
				 startActivity(intent);
				
			};
		};
	
//		if(method==0)
//			checkWriter.checkWriter_NH4_1(nh4SensorManager,nh4_tem,emf_text_0, correctTextList,emfTextList,numberTeam,unit,ionic,handler );
//		else if(method==1)
//			checkWriter.checkWriter_NH4_2(nh4SensorManager, nh4_tem,emf_text_0,data,correctTextList,emfTextList,numberTeam,unit,ionic,handler);

	}
	
	/**
	 * 建立handle
	 * 
	 * @return
	 */
	@SuppressLint("HandlerLeak")
	private Handler createHandler(final TextView view) {

		return new Handler() {

			public void handleMessage(Message msg) {
				view.setText(msg.getData().getString("back"));
			}
		};

	}
	
	private void showVisibility(int number){
		for(int i=0 ;i< 8 ;i++ ){
			if(i<number)
				layoutList.get(i).setVisibility(View.VISIBLE);
			else
				layoutList.get(i).setVisibility(View.GONE);
		}
	
	}
	
} 
