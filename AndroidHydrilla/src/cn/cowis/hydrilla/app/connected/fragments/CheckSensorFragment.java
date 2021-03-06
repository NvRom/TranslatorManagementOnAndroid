package cn.cowis.hydrilla.app.connected.fragments;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

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
import cn.cowis.hydrilla.app.parent.SuperFragment;
import cn.cowis.hydrilla.app.service.BackEvents.OnBackListener;
import cn.cowis.hydrilla.util.SensorUtil;



/**
 * 此界面是个检测界面的顶部选项卡 (传感器)
 * @author Administrator
 */
@EFragment(R.layout.check_tab)
public class CheckSensorFragment extends  SuperFragment{

	
	private FragmentManager fragmentManager;//要填充的Fragment的FragmentMenager,不是当前
	
	@ViewById(R.id.check_layout_1)
	LinearLayout checkLayout_1;       
	
	@ViewById(R.id.check_layout_2)
	LinearLayout checkLayout_2; 
	
	@ViewById(R.id.check_state_1)
	TextView checkState_1;      
	
	@ViewById(R.id.check_state_2)
	TextView checkState_2; 
	
	@ViewById(R.id.check_name_1)
	TextView textViewCheck1;       //第一个选项卡
	
	@ViewById(R.id.check_name_2)
	TextView textViewCheck2;       //第二个选项卡
	
	@ViewById(R.id.check_content)
	LinearLayout layoutContext;
	
	@ViewById(R.id.check_pager)
	ViewPager viewPage;
	
	 MyViewPageAdapter adapter=null;
	
	int listNum=0;
	
	@AfterViews
	public void init() {

		listNum=currectSensorManagers.size();
		
		fragmentManager= this.getChildFragmentManager();
		
		TextView[] textView1=new TextView[]{textViewCheck1,textViewCheck2};
		
		TextView[] textView2=new TextView[]{checkState_1,checkState_2};
		
		for(int i=0;i<listNum;i++){
			
			textView1[i].setText(currectSensorManagers.get(i).scp.name+"传感器");
			
			textView2[i].setText(SensorUtil.statueName(currectSensorManagers.get(i).scp.getState()));
			
		}
		
		
		
		 adapter=new MyViewPageAdapter(fragmentManager);
		
		viewPage.setAdapter(adapter);
		
		setSelectBlue1();
	
		
		viewPage.setOnPageChangeListener(new OnPageChangeListener(){

			@Override
			public void onPageScrollStateChanged(int arg0) {	
				
			}
//
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			
			}
//
			@Override
			public void onPageSelected(int arg0) {
				
				
				switch(arg0){
				
				case 0:
				
					saveObject(adapter.fragments[0]);
					deleteObject(adapter.fragments[1]);
					
					setSelectBlue1();
//					
				break;
				case 1:
					saveObject(adapter.fragments[1]);
					deleteObject(adapter.fragments[0]);
					setSelectBlue2();
				break;
				}
				
			}
			
		});
	}
	
	@Override
	public void onStart(){
		
		if(viewPage.getCurrentItem()==0){
			saveObject(adapter.fragments[0]);
		}else{
			saveObject(adapter.fragments[1]);
		}
		super.onStart();
	}
	
	
	@Override
	public void onStop(){
		
		deleteObject(adapter.fragments[1]);
		deleteObject(adapter.fragments[0]);
		super.onStop();
	}

	/**
	 * 点击 name1 选项ka
	 * @param view
	 */
	@Click(R.id.check_layout_1)
	public void click1(View view){

		if (listNum < 1)
			return;
		
		viewPage.setCurrentItem(0);
		setSelectBlue1();
	}
	/**
	 * 点击name2 选项卡
	 * @param view
	 */
	@Click(R.id.check_layout_2)
	public void click2(View view){
		
		
		if (listNum < 2)
			return;
		
		viewPage.setCurrentItem(1);
		setSelectBlue2();
	}
	
	
	
	private Fragment getFragment(int position) {

		Fragment fragment = new Fragment();

		if(listNum==0)return fragment;
		if(position==1&&listNum!=2)return fragment;
		if (currectSensorManagers.get(position).scp.getState()!=0) {
			fragment = new CheckConfigFragment_();
			
		}
		Bundle bundle = new Bundle();
		bundle.putInt("position", position);
		fragment.setArguments(bundle);
		return fragment;
	}

	
	public class MyViewPageAdapter extends FragmentPagerAdapter{

		public Fragment [] fragments=new Fragment[]{getFragment(0),getFragment(1)};
		
		public MyViewPageAdapter(FragmentManager fm) {
			super(fm);
			
		}
//		
//		/**
//		 * @param position
//		 *          getFragment
//		 */
//		public Fragment getCurrectFragment(int position){
//			
//			
//			return fragments[position];
//		}
//
//		@Override
		public Fragment getItem(int arg0) {
			
			switch(arg0){
			case 0:
				
				return fragments[0];
			case 1:
				return fragments[1];
			}
			return null;
		}
//
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listNum;
		}
				
	}
//	
	
	
	public void setSelectBlue1(){
		
		checkLayout_1.setBackgroundResource(R.drawable.bg_shape_nostroke_blue);
		checkLayout_2.setBackgroundResource(R.drawable.bg_shape_nostroke_white);
	}
	
	public void setSelectBlue2(){
		
		checkLayout_2.setBackgroundResource(R.drawable.bg_shape_nostroke_blue);
		checkLayout_1.setBackgroundResource(R.drawable.bg_shape_nostroke_white);
	}
	
//	@Override
//	public void onDestroy(){
//	InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);  
//
//			//得到InputMethodManager的实例
//			if (imm.isActive()) { 
//
//			imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS); 
//	
//			//关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的 
//	
//			} 
//	
//	}
	
	public void saveObject(Fragment fragment) {

		if (fragment instanceof OnBackListener) {

			myApp.sensorClient.sensorService.backEvents
					.addBackListener((OnBackListener) fragment);

		}

	}

	public void deleteObject(Fragment fragment) {

		if (fragment instanceof OnBackListener) {

			myApp.sensorClient.sensorService.backEvents
					.removeBackListener((OnBackListener) fragment);

		}

	}

}
