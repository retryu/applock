package com.activities.app;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.PowerManager.WakeLock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.TextView;

import com.dao.model.inter.App;
import com.activities.R;
import com.activities.RecommendUtil;
import com.util.LockWatcher;

public class RecommendAdapter extends BaseAdapter {
	List<RecommendItem> recommendItems;
	private LayoutInflater layoutInflater;
	private SharedPreferences sharedPreferences;

	public RecommendAdapter(Context context) {
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return recommendItems.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.layout_recormend,
					null);
			RecommendItem recommendItem = recommendItems.get(position);
			bindData(convertView, recommendItem);
		}
		return convertView;
	}

	public void bindData(View view, RecommendItem recommendItem) {
		final String recommentName = recommendItem.getSharePerfenceName();
		TextView tvTitle = (TextView) view.findViewById(R.id.Tv_Title);
		TextView tvHint = (TextView) view.findViewById(R.id.Tv_Hint);
		final CheckBox switchItem = (CheckBox) view.findViewById(R.id.Switch_Item);
		if (recommendItem.getTitle() != null) {
			tvTitle.setText(recommendItem.getTitle());
		}
		if (recommendItem.getHint() != null) {
			tvHint.setText(recommendItem.getHint());
		}
		if (recommendItem.getIsSwitch() == null) {
			switchItem.setChecked(false);
		} else {
			switchItem.setChecked(recommendItem.getIsSwitch());
		}
		switchItem.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Boolean result = switchItem.isChecked();
				sharedPreferences.edit()
						.putBoolean(recommentName, switchItem.isChecked())
						.commit();

				if (recommentName.endsWith(RecommendUtil.switchPrivacy)) {
					List<App> apps = RecommendUtil.getPrivacyList();
					checkState(result, apps);
				} else if (recommentName.equals(RecommendUtil.swicthSetting)) {
					List<App> apps = RecommendUtil.getSettingList();
					checkState(result, apps);
				} else if (recommentName.equals(RecommendUtil.switchPrograme)) {
					List<App> apps = RecommendUtil.getInstallList();
					checkState(result, apps);
				}
			}
		});
	}

	private void checkState(Boolean result, List<App> apps) {
		if (result == true) {
			LockWatcher.filterAdd(apps);
		} else {
			LockWatcher.filterRemove(apps);
		}
	}
	public List<RecommendItem> getRecommendItems() {
		return recommendItems;
	}

	public void setRecommendItems(List<RecommendItem> recommendItems) {
		this.recommendItems = recommendItems;
	}

	public SharedPreferences getSharedPreferences() {
		return sharedPreferences;
	}

	public void setSharedPreferences(SharedPreferences sharedPreferences) {
		this.sharedPreferences = sharedPreferences;
	}

}
