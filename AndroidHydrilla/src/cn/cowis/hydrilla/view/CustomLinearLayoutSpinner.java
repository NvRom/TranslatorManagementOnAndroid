package cn.cowis.hydrilla.view;

import cn.cowis.hydrilla.app.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputType;
import android.text.method.NumberKeyListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;


/**
 * @author Administrator 一个 LinearLayout的选择 属性功能
 */
public class CustomLinearLayoutSpinner extends LinearLayout implements OnItemSelectedListener{

	
	TextView textView=null;
	
	Spinner spinner=null;
	
	private Context context=null;
	
	private int position=0; 
	
	public CustomLinearLayoutSpinner(Context context) {
		this(context, null);

	}

	public CustomLinearLayoutSpinner(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CustomLinearLayoutSpinner(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		
		this.context=context;
		createView(attrs);
	}

	
	/**
	 * @param context
	 * @param attrs
	 */
	private void createView(AttributeSet attrs) {

		TypedArray typedArray = context.getTheme().obtainStyledAttributes(
				attrs, R.styleable.LinearLayoutSpinner, 0, 0);// 匹配

		String name = typedArray
				.getString(R.styleable.LinearLayoutSpinner_labelSpinner);

		// textview
		 textView = new TextView(context);

		LayoutParams layoutTextView = new LayoutParams(0,
				LayoutParams.WRAP_CONTENT, 1);
		layoutTextView.leftMargin = 10;

		textView.setLayoutParams(layoutTextView);
		textView.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
		textView.setPadding(10, 15, 10, 15);
		textView.setText(name);

		// 选择功能
		
		spinner=new Spinner(context);
		
		LayoutParams layoutSpinner=  new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
		 
		spinner.setGravity(Gravity.CENTER_VERTICAL|Gravity.RIGHT);
		
		layoutSpinner.rightMargin=20;
		spinner.setLayoutParams(layoutSpinner);
		
		spinner.setOnItemSelectedListener(this);
		
		this.addView(textView);
		this.addView(spinner);
		
	}
	
	public void setLabelName(String name){
		
		textView.setText(name);
	}
	
	
	/**
	 * 初始化 spinner
	 * @param resId
	 */
	public void initSpinner(int resId){
		
		ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter
				.createFromResource(context
				, resId
				, android.R.layout.simple_spinner_item);
		adapterSpinner.setDropDownViewResource(R.layout.spinner_item_stypelayout);

       spinner.setAdapter(adapterSpinner);
       
       
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		
		this.position=position;
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	/**
	 * 获取位置
	 * @return
	 */
	public int getPosition(){
		
		return position;
	}
	
	

}
