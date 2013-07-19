package com.widget;

import java.util.ArrayList;

import android.content.Context;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.activities.R;
import com.util.UnitsUtil;

public class EditTextPassword extends LinearLayout {

	ArrayList<TextView> passwordViews;
	private Context context;
	private int padding;
	private int width;
	private int height;
	private String text;
	private TextWatcher textWatcher;
	private boolean passwordVisiable;

	public EditTextPassword(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public EditTextPassword(Context context) {
		super(context);
		init(context);
	}

	public void init(Context context) {
		this.context = context;
		passwordVisiable = false;
		text = "";
		passwordViews = new ArrayList<TextView>();
		setOrientation(LinearLayout.HORIZONTAL);
		padding = UnitsUtil.dip2px(context, 15);
		width = UnitsUtil.dip2px(context, 50);
		height = UnitsUtil.dip2px(context, 50);
		for (int i = 0; i < 4; i++) {
			TextView passwordView = new TextView(context);
			LayoutParams lp = new LayoutParams(width, height);
			lp.setMargins(padding, 0, 0, 0);
			if (i == 0) {
				passwordView
						.setBackgroundResource(R.drawable.edittext_password_current);

			} else {
				passwordView
						.setBackgroundResource(R.drawable.edittext_password_unpressed);
			}
			passwordView.setGravity(Gravity.CENTER);

			passwordView.setTextColor(getResources().getColor(
					R.color.android_blue));
			passwordView.setLayoutParams(lp);
			passwordViews.add(passwordView);
			addView(passwordView);
		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
		if (isInEditMode()) {
			return;
		}
	}

	@Override
	protected void onFinishInflate() {
		// TODO Auto-generated method stub
		super.onFinishInflate();
		if (isInEditMode()) {
			return;
		}
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		if (text.length() <= 4) {
			this.text = text;
		} else {
			this.text = text.substring(0, 4);
		}
		if (textWatcher != null) {
			textWatcher.afterTextChanged(null);
		}
		refershView();
	}

	private void refershView() {
		TextView imgPassword;
		int current = text.length();
		for (int i = 0; i < 4; i++) {
			imgPassword = passwordViews.get(i);

			if (i < current) {
				imgPassword.setBackgroundResource(R.drawable.eidtext_pressed);
			} else if (i == current) {
				imgPassword
						.setBackgroundResource(R.drawable.edittext_password_current);
			} else {
				imgPassword
						.setBackgroundResource(R.drawable.edittext_password_unpressed);
			}

			//如果是密码可见的
			if (passwordVisiable == true) {
				if (i < current) {
					imgPassword
							.setBackgroundResource(R.drawable.edittext_password_unpressed);
					String number = text.charAt(i) + "";
					imgPassword.setText(number);
				}
				else{
					imgPassword.setText("");
				}
			} 
		}

	}

	public void addTextChangedListener(TextWatcher watcher) {
		textWatcher = watcher;
	}

	public void setVisiable(Boolean visiable) {
		passwordVisiable = visiable;
	}

	class PassWordView extends View {

		public PassWordView(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		public PassWordView(Context context) {
			super(context);

		}

	}

}
