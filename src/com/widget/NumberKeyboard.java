package com.widget;

import android.content.Context;
import android.opengl.ETC1;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.activities.R;

/**
 * @author chenyuruan
 * 
 * 
 *         虚拟键盘的控件
 */
public class NumberKeyboard extends LinearLayout implements OnClickListener {

	/**
	 * 内容view
	 */
	View view;
	/**
	 * 绑定当虚拟键盘按钮按下后 需要变化内容的EdittextView
	 */
	private EditTextPassword editText;
	
	/**
	 * 点击ok后的回调方法
	 */
	private OkCallBack onOkCallBack;
	private LayoutInflater layoutInflater;
	private Context c;

	public NumberKeyboard(Context context, AttributeSet attrs) {
		super(context, attrs);
		c = context;
		init();
	}

	public NumberKeyboard(Context context) {
		super(context);
		c = context;
		init();
	}

	public void init() {
		layoutInflater = (LayoutInflater) c
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = layoutInflater.inflate(R.layout.layout_number_keyboard,
				null);
		LayoutParams lp = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		addView(view, lp);
		view.findViewById(R.id.key_one).setOnClickListener(this);
		view.findViewById(R.id.key_two).setOnClickListener(this);
		view.findViewById(R.id.key_three).setOnClickListener(this);
		view.findViewById(R.id.key_four).setOnClickListener(this);
		view.findViewById(R.id.key_five).setOnClickListener(this);
		view.findViewById(R.id.key_six).setOnClickListener(this);
		view.findViewById(R.id.key_seven).setOnClickListener(this);
		view.findViewById(R.id.key_eight).setOnClickListener(this);
		view.findViewById(R.id.key_nine).setOnClickListener(this);
		view.findViewById(R.id.key_zero).setOnClickListener(this);
		view.findViewById(R.id.key_delete).setOnClickListener(this);
		view.findViewById(R.id.key_ok).setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		 
		switch (id) {
		case R.id.key_one:
			Log.e("debug", "one");
			addContent("1");
			break;
		case R.id.key_two:
			Log.e("debug", "two");
			addContent("2");
			break;
		case R.id.key_three:
			Log.e("debug", "three");
			addContent("3");
			break;
		case R.id.key_four:
			Log.e("debug", "four");
			addContent("4");
			break;
		case R.id.key_five:
			Log.e("debug", "five");
			addContent("5");
			break;
		case R.id.key_six:
			Log.e("debug", "six");
			addContent("6");
			break;
		case R.id.key_seven:
			Log.e("debug", "seven");
			addContent("7");
			break;
		case R.id.key_eight:
			Log.e("debug", "eight");
			addContent("8");
			break;
		case R.id.key_nine:
			Log.e("debug", "nine");
			addContent("9");
			break;
		case R.id.key_zero:
			Log.e("debug", "zero");
			addContent("0");
			break;
		case R.id.key_delete:
			Log.e("debug", "delete");
			deleteContent();
			break;
		case R.id.key_ok:
			Log.e("debug", "ok");
			if(onOkCallBack!=null){
				onOkCallBack.onOkClick();
			}
			break;
  
		default:
			break;
		}
		 Log.e("debug", "text" + editText.getText());
	}

	public void deleteContent() {
		if (editText == null)
			return;
		String contentString = editText.getText().toString();
		if (contentString.length() >= 1) {
			contentString = contentString.substring(0,
					contentString.length() - 1);
			editText.setText(contentString);
		}
	}

	public void addContent(String text) {
		if (editText == null)
			return;
		String content = editText.getText().toString();
		editText.setText(content + text);
	}

	public void bindEditText(EditTextPassword et) {
		this.editText = et;
	}

	public OkCallBack getOnOkCallBack() {
		return onOkCallBack;
	}

	public void setOnOkCallBack(OkCallBack onOkCallBack) {
		this.onOkCallBack = onOkCallBack;
	}

	
	 
	 
}
