package cn.cowis.hydrilla.app.adapter;

import java.lang.reflect.Array;
import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.cowis.hydrilla.app.R;
import cn.cowis.hydrilla.app.entity.CacheParams;
import cn.cowis.hydrilla.app.entity.CacheParams.ValueType;
import cn.cowis.hydrilla.util.ToolUtil;

/**
 * @author Administrator 是myParamsAdapter 的嵌套 子适配器
 * 
 */
public class ChildParamsAdapter extends BaseAdapter {

	private Context context = null;
	private NeedWriter needWriter = null;
	private String id;
	private CacheParams cp;
	
	private Object changeValue=null;

	public ChildParamsAdapter(CacheParams cp, String id, NeedWriter needWriter,
			Context context) {

		this.needWriter = needWriter;
		this.context = context;
		this.id = id;
		this.cp = cp;
	}

	@Override
	public int getCount() {

		switch (cp.getValueType()) {

		case FLOATS:

		case BYTES:
			return  ((String[])cp.getName()).length;

		}
		return 1;
	}

	@Override
	public Object getItem(int position) {

		return position;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View myView, ViewGroup parent) {

		
		
	
			View view = LayoutInflater.from(context).inflate(
					R.layout.detail_params_child_listview, null);

			TextView textName = (TextView) view.findViewById(R.id.param_name);

			EditText textValue = (EditText) view.findViewById(R.id.param_value);

			

		// tag.textName.setText(cp.getName()[position]);

		Object values = cp.getHasInitValue();
		Object names = cp.getName();

		if (values.getClass().isArray()) {// 如果数据是个数组
			
			System.out.println(id+position);
			textValue.setText(Array.get(values, position).toString());
			textName.setText(((String[]) names)[position]);

			textValue
					.addTextChangedListener(new MyTextWatcherArray(position));

		} else {
			textName.setText(names.toString());

			
			
			if (values instanceof Date) {
				textValue.setText(ToolUtil.dateToString((Date) values));

				textValue.setFocusable(false);
				final Calendar c = Calendar.getInstance();
				c.setTime((Date) values);

				textValue.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(final View v) {

						new DatePickerDialog(context, new OnDateSetListener() {
							@Override
							public void onDateSet(DatePicker view, int year,
									int month, int day) {
								month++;
								setTextViewTime((TextView) v, year, month, day);
							}
						}, c.get(Calendar.YEAR), c.get(Calendar.MONTH) - 1, c
								.get(Calendar.DAY_OF_MONTH)).show();

					}
				});
				
			} else {
				textValue.setText(values.toString());
			}
			textValue.addTextChangedListener(new MyTextWatcher());

		}

		return view;

	}

	/**
	 * 给textView 设置时间 回调用的
	 * 
	 * @param tv
	 * @param year
	 * @param month
	 * @param day
	 */
	private void setTextViewTime(TextView tv, int year, int month, int day) {
		tv.setText(year + "-" + month + "-" + day);
	}

	public class MyTag {
		public TextView textName;
		public EditText textValue;

	}
	
	public class MyTextWatcher implements TextWatcher{
		
		
		boolean flag=true; 

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void afterTextChanged(Editable s) {
			
			
			
			String str= s.toString();
			
			Object changeValue=null;
			try{
				switch (cp.getValueType()) {
				
				case BYTE:
					changeValue=Byte.parseByte(str);
					break;
				case INT:
					changeValue=Integer.parseInt(str);
					break;
				case FLOAT:
					changeValue=Float.parseFloat(str);
					break;
				case SHORT:
					changeValue=Short.parseShort(str);
					break;
				case DATE:
					changeValue=ToolUtil.stringToDate(str);
					break;
					default:
						changeValue=str;
				}
				
				needWriter.addNeedValue(id, changeValue);
			}catch(Exception e){
				needWriter.removeNeedValue(id);
				Toast.makeText(context, "请输入正确的数据格式", Toast.LENGTH_SHORT).show();
			}
			
		}
		
		
	}
	
	

	public class MyTextWatcherArray implements TextWatcher {

		private int position;

		
		public MyTextWatcherArray(int position) {
			this.position = position;
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {

		}

		@Override
		public void afterTextChanged(Editable s) {
			
			//System.out.println(id+"进来一次了"+position+"editable"+s.toString());
			
			String str = s.toString();

			try{
			switch (cp.getValueType()) {

			case BYTES:
				System.out.println("类型是BYTES");
				
				if(changeValue==null){
				  changeValue=((byte[])cp.getHasInitValue()).clone();
				}
				
				 Array.set(changeValue, position, Byte.parseByte(str));
				
				break;
			case FLOATS:
				
				if(changeValue==null){
					  changeValue=((float[])cp.getHasInitValue()).clone();
					}
					 Array.set(changeValue, position, Float.parseFloat(str));

			}
			needWriter.addNeedValue(id, changeValue);
			
			}catch(Exception e){
				e.printStackTrace();
				needWriter.removeNeedValue(id);
				Toast.makeText(context, "请输入正确的数据格式", Toast.LENGTH_SHORT).show();
			}
		}

	}

	public interface NeedWriter {

		public void addNeedValue(String id, Object obj);
		
		public void removeNeedValue(String id);
	}

}
