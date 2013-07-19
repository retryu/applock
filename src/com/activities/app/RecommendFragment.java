package com.activities.app;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ListView;

import com.activities.R;
import com.activities.RecommendUtil;
import com.dao.model.inter.App;
import com.util.LockWatcher;

public class RecommendFragment extends Fragment {
	private View view;
	private static ListView recommendList;
	private RecommendAdapter recommendAdapter;
	private Activity activity;
	
 

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_recommend, null);
		initData();
		initWidget();
	 
		return view;
	}

	public void initWidget() {
		recommendList = (ListView) view.findViewById(R.id.ListView_Recommend);
		recommendList.setAdapter(recommendAdapter);
	}

	public void initData() {
		activity = getActivity();
		SharedPreferences sharedPreferences = activity.getSharedPreferences(
				LockWatcher.SP_NAME, 0);
		Boolean switchItem = false;

		recommendAdapter = new RecommendAdapter(getActivity());
		recommendAdapter.setSharedPreferences(sharedPreferences);
		List<RecommendItem> recommendItems = new ArrayList<RecommendItem>();

		RecommendItem recommendItem = new RecommendItem();
		recommendItem.setTitle(getText(R.string.recormond_setting).toString());
		recommendItem.setHint(getText(R.string.hint_setting).toString());
		switchItem=sharedPreferences.getBoolean(RecommendUtil.swicthSetting, false);
		recommendItem.setIsSwitch(switchItem);
		recommendItem.setSharePerfenceName(RecommendUtil.swicthSetting);
		recommendItems.add(recommendItem);
		if(switchItem==true){
			List<App> apps=RecommendUtil.getSettingList();
			LockWatcher.filterAdd(apps);
		}

		recommendItem = new RecommendItem();
		recommendItem.setTitle(getText(R.string.recormond_privacy).toString());
		recommendItem.setHint(getText(R.string.hint_privacy).toString());
		switchItem=sharedPreferences.getBoolean(RecommendUtil.switchPrivacy, false);
		recommendItem.setSharePerfenceName(RecommendUtil.switchPrivacy);
		recommendItem.setIsSwitch(switchItem);
		recommendItems.add(recommendItem);
		if(switchItem==true){
			List<App> apps=RecommendUtil.getPrivacyList();
			LockWatcher.filterAdd(apps);
		}

		recommendItem = new RecommendItem();
		recommendItem.setTitle(getText(R.string.recormond_program).toString());
		recommendItem.setHint(getText(R.string.hint_program).toString());
		switchItem=sharedPreferences.getBoolean(RecommendUtil.switchPrograme, false);
		recommendItem.setSharePerfenceName(RecommendUtil.switchPrograme);
		recommendItem.setIsSwitch(switchItem);
		recommendItems.add(recommendItem);
		if(switchItem==true){
			List<App> apps=RecommendUtil.getInstallList();
			LockWatcher.filterAdd(apps);
		}
		recommendAdapter.setRecommendItems(recommendItems);
	}
	
 
	
	 
	
}
