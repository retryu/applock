package com.activities.lock.typelist;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.activities.CommonActivity;
import com.activities.R;
import com.activities.app.AppsFragment;
import com.activities.lock.LockActivity;
import com.activities.lock.pattern.PatterenActivity;
import com.activities.setting.SettingItem;
import com.service.AppService;

public class LockTypeListActivity extends CommonActivity {

	private ListView lockTypListView;
	private  Activity activity;
	private List<SettingItem> settingItems;
	@Override  
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lock_type_list);
		initData();
		initWidget();
		AppService.addActivity(this);
	}

	public void initWidget() {
		LockTypeListAdapter.setSettingItems(settingItems);
		lockTypListView = (ListView) findViewById(R.id.ListView_LockType);
		LockTypeListAdapter lockTypeListAdapter = new LockTypeListAdapter(this);
		lockTypListView.setAdapter(lockTypeListAdapter);
	}

	public void initData() {
		getSupportActionBar().setTitle(getText(R.string.choice_type));
		activity=this;
		settingItems=new ArrayList<SettingItem>();
		SettingItem  settingItem;
		settingItem=getPattern();
		settingItems.add(settingItem);
		settingItem=getText();
		settingItems.add(settingItem);
	}
	  
	public SettingItem getPattern(){
		SettingItem  settingItem=new  SettingItem();
		settingItem.setIconRes(R.drawable.icon_pattern);
		settingItem.setName(getText(R.string.pattern).toString());
		settingItem.setOnClickListener(new  OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent  intent=new  Intent(activity,PatterenActivity.class);
				intent.putExtra(PatterenActivity.type, PatterenActivity.reCreate);
				startActivity(intent);
				finish();
			}
		});
		return  settingItem;
	}
	
	public SettingItem  getText(){
		SettingItem settingItem=new  SettingItem();
		settingItem.setIconRes(R.drawable.icon_password);
		settingItem.setName(getText(R.string.text).toString());
		settingItem.setOnClickListener(new  OnClickListener() {
			@Override
			public void onClick(View v) {
			Intent  intent=new  Intent(activity, LockActivity.class);
			intent.putExtra(LockActivity.type, LockActivity.newType);
			startActivity(intent);
			finish();			 
			}
		});
		return  settingItem;
	}
	@Override
	public void onBackPressed() {
		AppsFragment.unlock();
		super.onBackPressed();
	
	}
	

}
