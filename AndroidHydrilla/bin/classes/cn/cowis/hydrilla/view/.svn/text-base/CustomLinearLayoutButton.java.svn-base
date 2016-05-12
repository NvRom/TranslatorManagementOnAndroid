package cn.cowis.hydrilla.view;

import cn.cowis.hydrilla.app.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

/**
 * @author Administrator
 *一个 LinearLayout 的按钮功能
 */
public class CustomLinearLayoutButton extends LinearLayout {

	
	TextView textName=null;
	
	TextView textMark=null;
	
	public CustomLinearLayoutButton(Context context) {
		this(context, null);

	}

	public CustomLinearLayoutButton(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CustomLinearLayoutButton(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		
		createView(context,attrs);
	}


	private void createView(Context context, AttributeSet attrs) {

		
		TypedArray typedArray = context.getTheme().obtainStyledAttributes(
				attrs, R.styleable.LinearLayoutButton, 0, 0);// 匹配

		
		String name = typedArray.getString(R.styleable.LinearLayoutButton_buttonName);
		
		int fontSize=typedArray.getInteger(R.styleable.LinearLayoutButton_customfontSize, 15);
		
		
		LayoutParams layoutTextView= new LayoutParams(0,
				LayoutParams.MATCH_PARENT,1);
		layoutTextView.leftMargin=10;
		 textName=new TextView(context);//设置名称
		textName.setLayoutParams(layoutTextView);
		//android:gravity="left|center_vertical"
		textName.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
		textName.setPadding(10, 20, 10, 20);
		textName.setTextSize(fontSize);
		textName.setText(name);
		
		
		 textMark=new TextView(context);//mark
		LayoutParams layoutMark= new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.MATCH_PARENT);
		layoutMark.rightMargin=5;
		textMark.setLayoutParams(layoutMark);
		textMark.setGravity(Gravity.CENTER_VERTICAL);
		//textMark.setBackgroundResource(R.drawable.bg_selector_mark);
		textMark.setText("＞");
		textName.setTextSize(fontSize);
		
		this.addView(textName);
		this.addView(textMark);
	}
	
	public void setButtonLabel(String label){
		
		textName.setText(label);
	}
	
	/**
	 * 设置右边的marker
	 * @param marker
	 */
	public void setMarker(String marker){
		
		textMark.setText(marker);
		
	}
	
	public String getMarker(){
		
		return textMark.getText().toString();
	}

}
