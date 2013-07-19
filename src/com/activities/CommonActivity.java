package com.activities;

import android.os.Bundle;
import android.util.Log;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.dao.interfaze.VerfyDao;
import com.db.OrmDateBaseHelper;
import com.umeng.analytics.MobclickAgent;

public class CommonActivity extends SherlockFragmentActivity {

	public static int THEME = R.style.Theme_Sherlock_Light;
	private OrmDateBaseHelper ormDateBaseHelper;
	VerfyDao verfyDao;
	private  ActionBar  actionBar;

	@Override
	public void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		setTheme(THEME);
		initDB();
		actionBar=getSupportActionBar();
//	    actionBar.setDisplayHomeAsUpEnabled(true);
		super.onCreate(arg0);
	}

	public void initDB() {
 		ormDateBaseHelper = new OrmDateBaseHelper(this, "lock.db", null, 1);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.e("debug", "CommonActivity  is  Stop");

	}

	public void verfy() {
		MyApplication app = (MyApplication) getApplication();
	}

	public OrmDateBaseHelper getOrmDateBaseHelper() {
		return ormDateBaseHelper;
	}

	public void setOrmDateBaseHelper(OrmDateBaseHelper ormDateBaseHelper) {
		this.ormDateBaseHelper = ormDateBaseHelper;
	}

}
