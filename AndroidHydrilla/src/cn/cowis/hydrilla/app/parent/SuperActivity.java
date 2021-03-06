package cn.cowis.hydrilla.app.parent;


import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import cn.cowis.hydrilla.app.MyRoboApplication;
import cn.cowis.hydrilla.app.service.SensorClient;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;

/**
 * 连接成功后的那些 activity 的actionBar
 * @author Administrator
 *
 */
public  class SuperActivity extends SherlockActivity {

	public MyRoboApplication myApp;
	
	@Override
	protected void onCreate(Bundle arg0) {
		
		super.onCreate(arg0);
		getSupportActionBar().setDisplayShowTitleEnabled(true);
		
		getSupportActionBar().setDisplayShowCustomEnabled(false);
		
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		
		
		getWindow().setSoftInputMode( WindowManager
				.LayoutParams
				.SOFT_INPUT_STATE_HIDDEN);// 默认软键盘不弹出
		
		getWindow().addFlags(WindowManager
				.LayoutParams.FLAG_KEEP_SCREEN_ON);//不自动锁屏
		
		
		myApp=((MyRoboApplication)getApplicationContext());
		
		
		
	}
	
	/**
	 * 设置title名称
	 * @param sourceId
	 */
	public void setTitleName(int sourceId){
		
		getSupportActionBar().setTitle(sourceId);
	}
	
	
	/**
	 * 设置title名称
	 * @param sourceId
	 */
	public void setTitleName(String value){
		
		getSupportActionBar().setTitle(value);
	}
	
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        
        if(item.getItemId() == android.R.id.home)
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


	@Override
	protected void onDestroy(){
		super.onDestroy();
		
//		System.out.println("销毁");
		
		if(getCurrentFocus() !=null)
		 ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE))  
         .hideSoftInputFromWindow(getCurrentFocus()  
                 .getWindowToken(),  
                InputMethodManager.HIDE_NOT_ALWAYS);   
		
//		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);  
//
//		//得到InputMethodManager的实例 
//
//		if (imm.) { 
//
//		//如果开启 
//
//		imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS); 
//
//		//关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的 
//
//		} 
		
		
	}
	
	
}
