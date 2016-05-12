package cn.cowis.hydrilla.app.parent;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.inputmethod.InputMethodManager;
import cn.cowis.hydrilla.app.Dialogs;
import cn.cowis.hydrilla.app.MyRoboApplication;
import cn.cowis.hydrilla.app.activities.AfterConnectedActivity;
import cn.cowis.hydrilla.app.entity.SensorManager;
import cn.cowis.hydrilla.app.entity.TransmitterManager;

public class SuperFragment extends Fragment{

	public MyRoboApplication myApp=null;
	
	public TransmitterManager currectTsmtManager=null;
	
	public List<TransmitterManager> tsmtManagers=null;
	
	public Dialogs dialog;
	
	public List<SensorManager>  currectSensorManagers=null;
	
	
	@Override  
    public void onAttach(Activity activity) {  
        super.onAttach(activity);  
          
        try {  
        	AfterConnectedActivity  afterActivity=(AfterConnectedActivity) activity;
        	
        	dialog=afterActivity.getDialog();
        	
        } catch (ClassCastException e) {  
            throw new ClassCastException(activity.toString()  
                    + " must implement OnHeadlineSelectedListener");  
        }  
        
        myApp= (MyRoboApplication) getActivity().getApplicationContext();
		 
		 currectTsmtManager= myApp.currectTsmtManager;
		 
		 currectSensorManagers=currectTsmtManager.getAvalibleSensorManagers();
		 
		 tsmtManagers=myApp.listTsmt;
		 
        
    }
	@Override
	public void onDestroy(){
		
		System.out.println("onDestroy");
		super.onDestroy();
		
		
//		 ((InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE))  
//         .hideSoftInputFromWindow(getActivity().getCurrentFocus()  
//                 .getWindowToken(),  
//                 InputMethodManager.HIDE_NOT_ALWAYS);   
		
//		InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);  
//
//		//得到InputMethodManager的实例 
//
//		if (imm.isActive()) { 
//
//		//如果开启 
//
//		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS); 
//
//		//关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的 
//
//		} 
		
	}
	



}
