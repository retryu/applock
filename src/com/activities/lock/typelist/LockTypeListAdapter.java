package com.activities.lock.typelist;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.activities.R;
import com.activities.setting.SettingItem;

public class LockTypeListAdapter extends BaseAdapter {

	private LayoutInflater layoutInflater;
	static List<SettingItem> settingItems;

	public LockTypeListAdapter(Context context) {

		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return settingItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		SettingItem settingItem = settingItems.get(position);
		if (convertView == null) {
			convertView = (View) layoutInflater.inflate(
					R.layout.layout_lock_item, null);
			bindView(convertView, settingItem);
		} else {
			bindView(convertView, settingItem);
		}
		return convertView;
	}

	public void bindView(View view, SettingItem settingItem) {
		ImageView imgType = (ImageView) view.findViewById(R.id.Img_Lock_Type);
		TextView tvLockType = (TextView) view.findViewById(R.id.Tv_Lock_Type);
		if (settingItem.getName() != null) {
			tvLockType.setText(settingItem.getName());
		}
		if (settingItem.getIconRes() != null) {
			imgType.setBackgroundResource(settingItem.getIconRes());
		}
		if (settingItem.getOnClickListener() != null) {
			view.setOnClickListener(settingItem.getOnClickListener());
		}

	}

	public static List<SettingItem> getSettingItems() {
		return settingItems;
	}

	public static void setSettingItems(List<SettingItem> settingItems) {
		LockTypeListAdapter.settingItems = settingItems;
	}

}
