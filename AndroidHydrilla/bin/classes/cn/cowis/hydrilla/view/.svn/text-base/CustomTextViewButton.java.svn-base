package cn.cowis.hydrilla.view;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.InputType;
import android.text.method.NumberKeyListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import cn.cowis.hydrilla.app.R;

/**
 * @author Administrator
 *                  shuang ge
 *                  这个是一个+号，中间一个textView,一个-号
 *
 */
public class CustomTextViewButton extends LinearLayout{
	
	int defalutValue;
	int max;
	int min;
	
	 EditText editText=null;
	public CustomTextViewButton(Context context) {
		super(context);
	}

	public CustomTextViewButton(Context context, AttributeSet attrs) {
		this(context,attrs,0);
		
	}

	public CustomTextViewButton(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		
		createView(context,attrs);
	}
	
	/**
	 * @param context
	 *              context 
	 * @param attrs
	 *              attrs
	 */
	private void createView(Context context,AttributeSet attrs){
		
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.InputValue, 0, 0);//匹配
		
        defalutValue=typedArray.getInt(0, 1);
        max=typedArray.getInt(1, 255);
        min=typedArray.getInt(2, 1);
        
        //组件
         editText=new EditText(context);
        editText.setLayoutParams(new LayoutParams(0,
					LayoutParams.MATCH_PARENT,2));
        editText.setText(String.valueOf(defalutValue));
       
       editText.setBackgroundResource(R.drawable.bg_selector_shape_oblong_edit);
        editText.setGravity(Gravity.CENTER);
        //editText.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        
        editText.setKeyListener(new NumberKeyListener() {//这个是选择的键盘
	    	   @Override
	    	   public int getInputType() {
	    	   return InputType.TYPE_CLASS_PHONE;
	    	   }

	    	   @Override
	    	   protected char[] getAcceptedChars() {
	    	   char[] numberChars = { '1', '2', '3', '4', '5', '6', '7', '8',
	    	   '9', '0','-'};
	    	   return numberChars;
	    	   }
	    	   });
        
        
        
         //按钮+
		 Button addButton=new Button(context);
		 addButton.setLayoutParams(new LayoutParams(0,
				 LayoutParams.MATCH_PARENT,1));
		 addButton.setText("+");
		 addButton.setGravity(Gravity.CENTER);
		
		addButton.setBackgroundResource(R.drawable.bg_selector_shape_oblong_button);
		
		 addButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				int currentValue=0;
				try{
				 currentValue= Integer.parseInt(editText.getText().toString());
				}catch(Exception e){
					
				}
				currentValue++;
				if(currentValue>max||currentValue<min){
					
					editText.setText(String.valueOf(min));
				}else{
					
					editText.setText(String.valueOf(currentValue));
				}
			}
			 
		 });
		 
		 //按钮-
		 Button reduceButton=new Button(context);
		 reduceButton.setLayoutParams(new LayoutParams(0,
				 LayoutParams.MATCH_PARENT,1));
		 reduceButton.setText("一");
		
		 reduceButton.setGravity(Gravity.CENTER);
		 reduceButton.setBackgroundResource(R.drawable.bg_selector_shape_oblong_button);
		 reduceButton.setOnClickListener(new OnClickListener(){
			// style="?android:attr/buttonStyleSmall"
			 
			@Override
			public void onClick(View v) {
				
				int currentValue=0;
				try{
				 currentValue= Integer.parseInt(editText.getText().toString());
				}catch(Exception e){
					
				}
				currentValue--;
				if(currentValue>max||currentValue<min){
					
					editText.setText(String.valueOf(max));
				}else{
					
					editText.setText(String.valueOf(currentValue));
				}
				
			}
			 
		 });
		 
		addView(reduceButton);
		addView(editText);
		addView(addButton);
        
	}
	
	
	/**
	 * 获得这个的当前值
	 * @return
	 */
	public String getCurrentValue(){
		
		 return editText.getText().toString();
	}
	
	
	public void setCurrentValue(int value){
		
		editText.setText(String.valueOf(value));
	}


}
