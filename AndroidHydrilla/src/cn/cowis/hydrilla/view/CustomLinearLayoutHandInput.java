package cn.cowis.hydrilla.view;

import cn.cowis.hydrilla.app.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputType;
import android.text.method.NumberKeyListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * @author Administrator 一个 LinearLayout的手动输入功能
 */
public class CustomLinearLayoutHandInput extends LinearLayout {

	
	TextView textView=null;
	
	EditText editText=null;
	
	public CustomLinearLayoutHandInput(Context context) {
		this(context, null);

	}

	public CustomLinearLayoutHandInput(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CustomLinearLayoutHandInput(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);

		createView(context, attrs);
	}

	
	/**
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

		// textview
		 textView = new TextView(context);

		LayoutParams layoutTextView = new LayoutParams(0,
				LayoutParams.WRAP_CONTENT, 1);
		layoutTextView.leftMargin = 10;

		textView.setLayoutParams(layoutTextView);
		textView.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
		textView.setPadding(10, 10, 10, 10);
		textView.setText(name);

		// 编辑的数据
		 editText = new EditText(context);

		editText.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		editText.setText(String.valueOf(defalutValue));

		editText.setBackgroundResource(R.drawable.bg_selector_shape_rect_edit);
		editText.setGravity(Gravity.CENTER);
		editText.setEms(6);
		editText.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);

		editText.setSelectAllOnFocus(true);
		editText.setKeyListener(new NumberKeyListener() {
			@Override
			public int getInputType() {
				return InputType.TYPE_CLASS_PHONE;
			}

			@Override
			protected char[] getAcceptedChars() {
				char[] numberChars = { '1', '2', '3', '4', '5', '6', '7', '8',
						'9', '0', '.', '-' };
				return numberChars;
			}
		});
		
		this.addView(textView);
		
		this.addView(editText);
		
	}
	
	public void setLabelName(String name){
		
		textView.setText(name);
	}
	
	public void setValue(String value){
		
		editText.setText(value);
	}
	
	
	public String  getValue(){
		
		return editText.getText().toString();
		
	}
	

}
