package com.activities.lock;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.actionbarsherlock.app.ActionBar;
import com.activities.CommonActivity;
import com.activities.R;
import com.service.AppService;

public class LockActivity extends CommonActivity {

	public static final String newType = "NEWTYPE";
	public static final String oldType = "OLDTYPE";
	public static final String canBACK = "CANBACK";
	public static final String type = "TYPE";
	private String typeMark;
	private boolean backMark;
	private OldPassFragemnt oldPassFragemnt;
	private FragmentManager fragmentManager;
	private FragmentTransaction fragmentTransaction;
	private NewPassTextFragment newPassTextFragment;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		AppService.addActivity(this);
		setContentView(R.layout.activity_lock);
		fragmentManager = getSupportFragmentManager();
		newPassTextFragment = new NewPassTextFragment();

		Intent intent = getIntent();
		typeMark = oldType;
		if (intent != null) {
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				typeMark = bundle.getString(type);
				backMark = bundle.getBoolean(canBACK, true);
			}
		}

		 init();
	}

	public void init() {
		ActionBar actionBar = getSupportActionBar();
		if (backMark == false) {
			actionBar.setTitle(getText(R.string.first_set_pass));

		} else {
			actionBar.setTitle(getText(R.string.change_password));
		}
		oldPassFragemnt = new OldPassTextFragment();
		fragmentTransaction = fragmentManager.beginTransaction();
		if (typeMark.equals(newType)) {
			fragmentTransaction.add(R.id.content, newPassTextFragment);
			fragmentTransaction.commit();
		} else {
			fragmentTransaction.add(R.id.content, oldPassFragemnt);
			fragmentTransaction.commit();
		}

		oldPassFragemnt.setToChangeFragment(new ToChangeFragment() {
			@Override
			public void toChangeFragmnet() {
				Log.e("debug", "toChangeFragment");
				fragmentTransaction = fragmentManager.beginTransaction();
				fragmentTransaction.replace(R.id.content, newPassTextFragment);
				fragmentTransaction.commit();
			}
		});
	}

	@Override
	public void onBackPressed() {
		if (backMark == false) {
			
		} else {
			super.onBackPressed();
		}
	}

}
