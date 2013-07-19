package com.activities.lock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.activities.R;
import com.activities.lock.callback.PassWordCallBack;
import com.activities.lock.typelist.LockTypeListActivity;
import com.dao.interfaze.VerfyDao;
import com.db.OrmDateBaseHelper;
import com.service.AppService;
import com.widget.EditTextPassword;
import com.widget.NumberKeyboard;
import com.widget.OkCallBack;

public class PwdActivity extends CommonLockActivity {

	private EditTextPassword editText;
//	private Button btnOk;
	private VerfyDao verfyDao;
	private Activity activity;
	public final static String NEED_FINISH = "NEEDFINISH";
	public final  static  String  JUMP_TO_LIST="LIST";
	private boolean finishSelf;
	private boolean  toList;
	private TextView tvAlert;
	private  NumberKeyboard  numberKeyboard;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pwd);
		activity = this;
		editText = (EditTextPassword) findViewById(R.id.Et_Pwd);
		AppService.addActivity(this);
		OrmDateBaseHelper ormDateBaseHelper = new OrmDateBaseHelper(this,
				"lock.db", null, 1);
		verfyDao = OrmDateBaseHelper.getVerfyDao();
		initWidget();
	}

	public void initWidget() {
		
		numberKeyboard=(NumberKeyboard)findViewById(R.id.NumberKeyBoard);
		numberKeyboard.bindEditText(editText);
		setPassWordCallBack(new PassWordCallBack() {
			@Override
			public void wrong() {
				editText.setText("");
			}
		});
		activity = this;
		Intent intent = getIntent();
		if (intent != null) {
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				finishSelf = bundle.getBoolean(NEED_FINISH, false);
				 finishType=bundle.getString("type");
			}
		}
		tvAlert = (TextView) findViewById(R.id.Alert);

		setTvAlert(tvAlert);
		 
		editText.addTextChangedListener(new  TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
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
				check();
			}
		});
		
		numberKeyboard.setOnOkCallBack(new OkCallBack() {
			@Override
			public void onOkClick() {
				String pw = editText.getText().toString();
				if (verfyDao.check(pw) == false) {
				editText.setText("");
				worngCount++;
				WrongState();
				}
			}
		});
		
	 
		
	}
	
	public  void  check(){
		String pw = editText.getText().toString();
		if (verfyDao == null) {
			Log.e("debug", "veryDao  is  null");
			OrmDateBaseHelper ormDateBaseHelper = new OrmDateBaseHelper(
					activity, "lock.db", null, 1);
			verfyDao = ormDateBaseHelper.getVerfyDao();
		}
		if (verfyDao.check(pw) == true) {
			Log.e("debug", "finish");
			AppService.check();
			AppService.addActivity(activity);

			if(finishType!=null){
				if(finishType.equals(JUMP_TO_LIST)){
					Intent intent = new Intent(activity,
							LockTypeListActivity.class);
					startActivity(intent);
					finish();
					return;
				}
			}
			
			if (finishSelf == false) {
				Log.e("debug", "finish all");
				AppService.finishAllActivity();
			} else {
				Log.e("debug", "finish self");
				back2MainActivity();
			}
		} 
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addCategory(Intent.CATEGORY_HOME);
		this.startActivity(intent);
		finish();
	}

}
