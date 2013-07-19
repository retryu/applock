package com.activities.setting;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class SettingItem {
	private String name;
	private String value;
	private Integer iconRes;
	private OnClickListener onClickListener;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public OnClickListener getOnClickListener() {
		return onClickListener;
	}

	public void setOnClickListener(OnClickListener onClickListener) {
		this.onClickListener = onClickListener;
	}

	public Integer getIconRes() {
		return iconRes;
	}

	public void setIconRes(int iconRes) {
		this.iconRes = iconRes;
	}
	
	
	

}
