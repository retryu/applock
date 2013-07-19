package com.activities.setting;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.activities.R;

public class SettingAdapter extends  BaseAdapter{
	List<SettingItem>  settingItems;
	LayoutInflater  layoutInflater;
	private  Context context;
	
	public SettingAdapter(Context  context) {	
		this.context=context;
		layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return settingItems.size();
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
	public View getView(int position, View converView, ViewGroup parentView) {
		if(converView==null	){
			View  view=layoutInflater.inflate(R.layout.layout_setting_pwd, null);
			SettingItem settingItem=settingItems.get(position);
			TextView tvName=(TextView) view.findViewById(R.id.Tv_ItemName);
			ImageView  imgIcon=(ImageView)view.findViewById(R.id.Img_Icon);
			if(settingItem.getName()!=null)
			{
				tvName.setText(settingItem.getName());
			}
			if(settingItem.getOnClickListener()!=null){
				view.setOnClickListener(settingItem.getOnClickListener());
			}
			if(settingItem.getIconRes()!=null){
				imgIcon.setBackgroundResource(settingItem.getIconRes());
			}
			return  view;
		}
		
		return converView;
	}


	public List<SettingItem> getSettingItems() {
		return settingItems;
	}


	public void setSettingItems(List<SettingItem> settingItems) {
		this.settingItems = settingItems;
	}
	

}
