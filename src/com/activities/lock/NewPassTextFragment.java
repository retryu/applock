package com.activities.lock;

import java.sql.SQLException;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dao.interfaze.VerfyDao;
import com.dao.model.inter.Verfy;
import com.db.OrmDateBaseHelper;
import com.widget.EditTextPassword;
import com.widget.NumberKeyboard;
import com.activities.R;
import com.activities.app.AppsFragment;

public class NewPassTextFragment extends NewPassFragment {

	// private EditText edNewPassWord;
	private Button btnOk;
	private View view;
	private VerfyDao verfyDao;
	private String prePass;
	private TextView tvAlert;
	private EditTextPassword editTextPassword;
	private NumberKeyboard numberKeyboard;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.activity_pwd, null);
		numberKeyboard = (NumberKeyboard) view
				.findViewById(R.id.NumberKeyBoard);
		editTextPassword = (EditTextPassword) view.findViewById(R.id.Et_Pwd);
		numberKeyboard.bindEditText(editTextPassword);
		verfyDao = OrmDateBaseHelper.getVerfyDao();
		 initWidget();
		return view;
	}

	public void initWidget() {
		btnOk = (Button) view.findViewById(R.id.Btn_ok);
		tvAlert = (TextView) view.findViewById(R.id.Alert);

//		btnOk.setOnClickListener(new OnClickListener() {
//			@SuppressWarnings("unused")
//			@SuppressLint("NewApi")
//			@Override
//			public void onClick(View arg0) {
//				input();
//			}
//		});

		editTextPassword.addTextChangedListener(new TextWatcher() {

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
				String textString = editTextPassword.getText();
				if (textString.length() >= 4) {
					input();
				}
			}
		});

	}

	private void input() {
		String pw = editTextPassword.getText().toString();
		String settingPw = verfyDao.getPassWord();
		if (check(pw, settingPw) == false) {
			// prePass = null;
			return;
		}

		if (prePass == null) {
			prePass = pw;
			tvAlert.setText(getText(R.string.rest_password));
			editTextPassword.setText("");
		} else {
			if (pw.endsWith(prePass)) {
				Verfy verfy = verfyDao.getVerfy();
				try {
					if (verfy == null) {
						verfy = new Verfy();
						verfy.setPassword(pw);
						verfy.setId(1);
						verfy.setType("1");
						verfyDao.create(verfy);
					} else {
						verfy.setPassword(pw);
						verfy.setType("1");
						verfyDao.update(verfy);
					}
					AppsFragment.unLock = true;
					getActivity().finish();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				Toast.makeText(getActivity(),
						getText(R.string.second_pw_should_same),
						Toast.LENGTH_LONG).show();
				editTextPassword.setText("");
			}
		}
	}

	@SuppressLint("NewApi")
	public boolean check(String pw, String settingPw) {

		if (pw.length() < 3) {
			Toast.makeText(getActivity(), getText(R.string.to_short),
					Toast.LENGTH_LONG).show();
			return false;
		}
		if (settingPw == null) {
			return true;
		}
		if (settingPw.equals(pw) && prePass == null) {
			Toast.makeText(getActivity(), getText(R.string.pw_cannot_same),
					Toast.LENGTH_LONG).show();

			return false;
		}
		return true;
	}

}
