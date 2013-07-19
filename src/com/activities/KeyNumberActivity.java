package com.activities;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.widget.EditTextPassword;
import com.widget.NumberKeyboard;

public class KeyNumberActivity extends Activity {
	EditTextPassword editText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_number_board);
		editText = (EditTextPassword) findViewById(R.id.edittextPassword);
//		editText.setVisiable(true);
		editText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				Log.e("debug", "atferTextChange" + editText.getText());
			}
		});

		NumberKeyboard numberKeyboard = (NumberKeyboard) findViewById(R.id.NumberKeyBoard);
		numberKeyboard.bindEditText(editText);
	}

}
