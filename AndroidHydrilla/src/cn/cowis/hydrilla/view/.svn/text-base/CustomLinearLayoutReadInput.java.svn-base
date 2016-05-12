package cn.cowis.hydrilla.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import cn.cowis.hydrilla.app.R;

/**
 * @author Administrator
 *
 * 另一种输入形式
 */
public class CustomLinearLayoutReadInput extends LinearLayout{

	public CustomLinearLayoutReadInput(Context context) {
		this(context, null);

	}

	public CustomLinearLayoutReadInput(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CustomLinearLayoutReadInput(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);

		createView(context, attrs);
	}

	TextView textName=null;
	
	TextView textValue=null;
	
	TextView textMark=null;
	/*
	 * 
	 * @param context
	 * @param attrs
	 */
	private void createView(Context context, AttributeSet attrs) {

		TypedArray typedArray = context.getTheme().obtainStyledAttributes(
				attrs, R.styleable.LinearLayoutInput, 0, 0);// 匹配

		String name = typedArray
				.getString(R.styleable.LinearLayoutInput_labelName);

		String defalutValue = typedArray
				.getString(R.styleable.LinearLayoutInput_defaultInputValue);
		
		
		LinearLayout layout=new LinearLayout(context);//layout
		LayoutParams layoutLayout= new LayoutParams(0,
				LayoutParams.WRAP_CONTENT,1);
		layoutLayout.leftMargin=10;
		layout.setLayoutParams(layoutLayout);
		layout.setOrientation(LinearLayout.VERTICAL);
		
		//layout的两个  子类
		 textName=new TextView(context);
		LayoutParams textNameLayout= new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		textName.setLayoutParams(textNameLayout);
		
		textName.setText(name);
		textName.setGravity(Gravity.CENTER_VERTICAL|Gravity.LEFT);
		layout.addView(textName);
		
		//value
		textValue=new TextView(context);
		LayoutParams textValueLayout= new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		textValueLayout.leftMargin=10;
		textValue.setLayoutParams(textValueLayout);
		
		textValue.setText(defalutValue);
		textValue.setGravity(Gravity.CENTER_VERTICAL|Gravity.LEFT);
		layout.addView(textValue);
		
		
		
		 textMark=new TextView(context);//mark
		LayoutParams layoutMark= new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.MATCH_PARENT);
		layoutMark.rightMargin=5;
		textMark.setLayoutParams(layoutMark);
		textMark.setGravity(Gravity.CENTER_VERTICAL);
		//textMark.setBackgroundResource(R.drawable.bg_selector_mark);
		textMark.setText(R.string.mark);
		
		this.addView(layout);
		this.addView(textMark);
	}
	
	
	/**
	 * 设置 labelname
	 * @param name
	 */
	public void setLabelName(String name){
		textName.setText(name);
	}
	
	/**设置值
	 * @param value
	 */
	public void setValue(String value){
		
		textValue.setText(value);
	}
	
	/**
	 * 获得值
	 * @return
	 */
	public String getValue(){
		
		return textValue.getText().toString();
	}
	
	/**
	 * 获得值
	 * @return
	 */
	public String getLabelNalue(){
		
		return textName.getText().toString();
	}
	
	public void setMarkShow(int visible){
		
		textMark.setVisibility(visible);
		
	}

}
