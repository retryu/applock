package com.activities.setting;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.activities.CommonActivity;
import com.activities.R;
import com.activities.app.AppsFragment;
import com.activities.lock.PwdActivity;
import com.activities.lock.pattern.PatterenActivity;
import com.dao.interfaze.VerfyDao;
import com.dao.model.inter.Verfy;
import com.db.OrmDateBaseHelper;
import com.service.AppService;
import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.UMFeedbackService;

public class SettingActivity extends CommonActivity {

	ListView listView;
	private Activity activity;
	private OrmDateBaseHelper ormDateBaseHelper;
	private VerfyDao verfyDao;

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		getSupportActionBar().setTitle(R.string.setting);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		setContentView(R.layout.activity_setting);
		activity = this;
		AppService.addActivity(activity);
		initData();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void initData() {
		ormDateBaseHelper = new OrmDateBaseHelper(this, "lock.db", null, 1);
		verfyDao = ormDateBaseHelper.getVerfyDao();

		listView = (ListView) findViewById(R.id.ListView_Setting);
		SettingAdapter settingAdapter = new SettingAdapter(this);
		List<SettingItem> settingItems = new ArrayList<SettingItem>();
		 
		SettingItem settingItem = getTypeItem();
		settingItems.add(settingItem);
		SettingItem feedBackItem = getFeedBackItem();
		settingItems.add(feedBackItem);
		settingAdapter.setSettingItems(settingItems);
		listView.setAdapter(settingAdapter);
	}

	public SettingItem getTypeItem() {
		SettingItem settingItem = new SettingItem();
		settingItem.setName(getText(R.string.change_password).toString());
		settingItem.setIconRes(R.drawable.icon_lock);
		settingItem.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Verfy verfy = verfyDao.getVerfy();
				String type = verfy.getType();
				if (type.equals("1")) {
					Intent intent = new Intent(activity,
							PwdActivity.class);
					intent.putExtra("type", PwdActivity.JUMP_TO_LIST);
					startActivity(intent);
				} else if (type.equals("2")) {
					Intent intent = new Intent(activity, PatterenActivity.class);
					intent.putExtra(PatterenActivity.type,
							PatterenActivity.verify);
					startActivity(intent);
				}
			}
		});
		return settingItem;
	}

	public SettingItem getFeedBackItem() {
		SettingItem settingItem = new SettingItem();
		settingItem.setName(getText(R.string.feedBack).toString());
		settingItem.setIconRes(R.drawable.icon_msg);
		settingItem.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UMFeedbackService.openUmengFeedbackSDK(activity);
			}
		});
		return settingItem;
	}

	@Override
	public void onBackPressed() {
		AppsFragment.unlock();
		super.onBackPressed();
	}

}
