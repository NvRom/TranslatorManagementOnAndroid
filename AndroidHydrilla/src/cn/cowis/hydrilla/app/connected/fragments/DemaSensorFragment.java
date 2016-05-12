package cn.cowis.hydrilla.app.connected.fragments;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.cowis.hydrilla.app.R;
import cn.cowis.hydrilla.app.entity.Constants;
import cn.cowis.hydrilla.app.entity.SensorManager;
import cn.cowis.hydrilla.app.entity.SensorManager.SensorCommonParams;
import cn.cowis.hydrilla.app.entity.SensorManagerFactory;
import cn.cowis.hydrilla.app.parent.SuperFragment;
import cn.cowis.hydrilla.app.service.BackEvents.OnBackListener;
import cn.cowis.hydrilla.util.SensorUtil;

/**
 * 整个标定界面 (是一个tab 里面有温度传感器标定和主传感器标定)
 * 
 * @author Administrator
 */

@EFragment(R.layout.dema_tab)
public class DemaSensorFragment extends SuperFragment {

	@ViewById(R.id.dema_layout_1)
	LinearLayout demaLayout_1;

	@ViewById(R.id.dema_layout_2)
	LinearLayout demaLayout_2;

	@ViewById(R.id.dema_state_1)
	TextView demaState_1;

	@ViewById(R.id.dema_state_2)
	TextView demaState_2;

	@ViewById(R.id.dema_name_1)
	TextView textViewDema1; // 第一个选项卡

	@ViewById(R.id.dema_name_2)
	TextView textViewDema2; // 第二个选项卡

	@ViewById(R.id.dema_content)
	LinearLayout layoutContext;

	@ViewById(R.id.dema_pager)
	ViewPager viewPage;

	MyViewPage adapter = null;

	FragmentManager fragmentManager;

	int listNum = 0;

	@SuppressLint("NewApi")
	@AfterViews
	public void init() {

		listNum = currectSensorManagers.size();

		fragmentManager = this.getChildFragmentManager();

		TextView[] textView1 = new TextView[] { textViewDema1, textViewDema2 };

		TextView[] textView2 = new TextView[] { demaState_1, demaState_2 };

		for (int i = 0; i < listNum; i++) {

			textView1[i].setText(currectSensorManagers.get(i).scp.name + "传感器");

			textView2[i].setText(SensorUtil.statueName(currectSensorManagers
					.get(i).scp.getState()));

		}
		adapter = new MyViewPage(fragmentManager);
		viewPage.setAdapter(adapter);
		setSelectBlue1();

		viewPage.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}

			//
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				//
			}

			//
			@Override
			public void onPageSelected(int arg0) {
				//
//				Fragment[] fragments = adapter.fragments;

				switch (arg0) {

				case 0:

					saveObject(adapter.fragments[0],0);
					deleteObject(adapter.fragments[1],1);

					setSelectBlue1();

					break;
				case 1:

					saveObject(adapter.fragments[1],1);
					deleteObject(adapter.fragments[0],0);
					setSelectBlue2();
					break;
				}

			}

		});

	}

	/**
	 * 点击 name1 选项卡
	 * 
	 * @param view
	 */
	@Click(R.id.dema_layout_1)
	public void click1(View view) {

		if (listNum < 1)
			return;

		viewPage.setCurrentItem(0);
		setSelectBlue1();
	}

	/**
	 * 点击name2
	 * 
	 * @param view
	 */
	@Click(R.id.dema_layout_2)
	public void click2(View view) {

		if (listNum < 2)
			return;

		viewPage.setCurrentItem(1);
		setSelectBlue2();
	}

	/**
	 * 根据传感器类型 和状态 来判断激活nage
	 * 
	 * @param type
	 * @param statue
	 * @return
	 */
	private Fragment getFragment(int position) {

		Fragment fragment = new Fragment();

		if(listNum==0)return fragment;
		if(position==1&&listNum!=2)return fragment;
		SensorCommonParams scp = currectSensorManagers.get(position).scp;

		if (scp.getState() != 0) {// 状态

			if (scp.getType() == SensorManagerFactory.SENSOR_ALL_TYPE[0]) {// 如果是温度传感器
				fragment = new DemaTemInputFragment_();

			} else if (scp.getType() == SensorManagerFactory.SENSOR_ALL_TYPE[1]) {

				fragment = new DemaPhInputFragment_();

			} else if (scp.getType() == SensorManagerFactory.SENSOR_ALL_TYPE[2]) {

				fragment = new DemaEcInputFragment_();

			} else if (scp.getType() == SensorManagerFactory.SENSOR_ALL_TYPE[3]
					|| scp.getType() == SensorManagerFactory.SENSOR_ALL_TYPE[4]) {

				fragment = new DemaDoFragment_();

			} else if (scp.getType() == SensorManagerFactory.SENSOR_ALL_TYPE[6]) {

				fragment = new DemaOrpFragment_();

			} else if (scp.getType() == SensorManagerFactory.SENSOR_ALL_TYPE[5]) {
				fragment = new DemaTbFragment_();

			} else if (scp.getType() == SensorManagerFactory.SENSOR_ALL_TYPE[7]) {
				fragment = new DemaNH4Fragment_();
			}
		}

		Bundle bundle = new Bundle();
		bundle.putInt("position", position);
		fragment.setArguments(bundle);
		return fragment;
	}
	
	
	@Override
	public void onStart(){
		
		if(viewPage.getCurrentItem()==0){
			saveObject(adapter.fragments[0],0);
		}else{
			saveObject(adapter.fragments[1],0);
		}
		super.onStart();
		
	}
	

	@Override
	public void onStop() {

		deleteObject(adapter.fragments[0],0);
		deleteObject(adapter.fragments[1],1);
		super.onStop();
	}

	public class MyViewPage extends FragmentPagerAdapter {

		public Fragment[] fragments = new Fragment[] { getFragment(0),
				getFragment(1) };
		
		public SensorManager[] sensorManagers=new SensorManager[]{};

		public MyViewPage(FragmentManager fm) {
			super(fm);

		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			switch (arg0) {
			case 0:

				return fragments[0];
			case 1:

				return fragments[1];
			}
			return null;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listNum;
		}

	}

	public void setSelectBlue1() {

		demaLayout_1.setBackgroundResource(R.drawable.bg_shape_nostroke_blue);
		demaLayout_2.setBackgroundResource(R.drawable.bg_shape_nostroke_white);
	}

	public void setSelectBlue2() {

		demaLayout_2.setBackgroundResource(R.drawable.bg_shape_nostroke_blue);
		demaLayout_1.setBackgroundResource(R.drawable.bg_shape_nostroke_white);
	}

	public void saveObject(Fragment fragment,int i) {

		if (fragment instanceof OnBackListener) {

			myApp.sensorClient.sensorService.backEvents
					.addBackListener((OnBackListener) fragment);

			if (fragment instanceof DemaOrpFragment_) {

				start(currectSensorManagers.get(i));
				
			}
		}

	}

	public void deleteObject(Fragment fragment,int i) {

		if (fragment instanceof OnBackListener) {

			myApp.sensorClient.sensorService.backEvents
					.removeBackListener((OnBackListener) fragment);

			if (fragment instanceof DemaOrpFragment_) {

				destory(currectSensorManagers.get(i));
			}
		}

	}
	
private void start(SensorManager sensorManager){
		
		myApp.sensorClient.sensorService.startCycle(Constants.NO_CACHE_ANALOG, sensorManager,2*1000, -1);
	}
	
	private void destory(SensorManager sensorManager){
		
		myApp.sensorClient.sensorService.destoryCycle();
	}
	

}
