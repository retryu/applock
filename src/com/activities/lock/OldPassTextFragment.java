package com.activities.lock;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dao.interfaze.VerfyDao;
import com.db.OrmDateBaseHelper;
import com.activities.R;

@SuppressLint("NewApi")
public class OldPassTextFragment extends OldPassFragemnt {

	private Button btnOk;
	private EditText etOldPw;
	private View view;
	private  VerfyDao  verfyDao;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.layout_lock_old_text, null);
		btnOk = (Button) view.findViewById(R.id.Btn_ok);
		etOldPw = (EditText) view.findViewById(R.id.Et_Old);
		initWidget();
		return view;
	}

	public void initWidget() {
		btnOk = (Button) view.findViewById(R.id.Btn_ok);
		etOldPw = (EditText) view.findViewById(R.id.Et_Old);
		btnOk.setOnClickListener(new  OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Log.e("debug", "cick");
				String  pw=etOldPw.getText().toString();
				verfyDao=OrmDateBaseHelper.getVerfyDao();
				if(verfyDao.check(pw)==true){
				getToChangeFragment().toChangeFragmnet();
				}
				else{
					Toast.makeText(getActivity(), getText(R.string.alery_wrong_pw), Toast.LENGTH_LONG).show();
					
				}
			}
		});
	}
}
