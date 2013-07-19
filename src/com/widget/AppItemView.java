package com.widget;

import java.sql.SQLException;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dao.interfaze.AppDao;
import com.dao.model.inter.App;
import com.activities.R;
import com.service.AppService;
import com.service.interfaze.LockDateChange;

public class AppItemView extends RelativeLayout {
	private LayoutInflater layoutInflater;
	private ImageView imgApp;
	private TextView tvApp;
	private CheckBox ckLock;
	View view;
	private static LockDateChange lockDateChange;
	private static AppDao appDao;
	private static AlertDialog alertDialog;
	private boolean SYNC_DATE;
	

	public AppItemView(Context context) {
		super(context);
		SYNC_DATE = true;
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = layoutInflater.inflate(R.layout.layout_app_item, null);
		initWidget();
		addView(view);

	}

	public void initWidget() {
		imgApp = (ImageView) view.findViewById(R.id.Img_App);
		ckLock = (CheckBox) view.findViewById(R.id.Ck_Lock);
		tvApp = (TextView) view.findViewById(R.id.Tv_App_Name);

	}

	public void bindData(App appItem) {
		alertDialog.setTitle("asd");
		final App app = appItem;
		if (appItem.getName() != null) {
			tvApp.setText(appItem.getName());
		}
		if (appItem.getIconDraw() != null) {
			imgApp.setImageDrawable(appItem.getIconDraw());
		}
		if (appItem.getIsLocked() == null) {
			ckLock.setChecked(false);
		} else {
			ckLock.setChecked(appItem.getIsLocked());
		}

		ckLock.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// alertDialog.show();
				Log.e("debug", "click" + ckLock.isChecked());
				if (app.getIsLocked() == true) {
					app.setIsLocked(false);
				} else {
					app.setIsLocked(true);
				}
				if (SYNC_DATE == true) {
					try {
						appDao.update(app);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					lockDateChange.notifyLockDataChange();
				}
			}
		});

	}

	public static AppDao getAppDao() {
		return appDao;
	}

	public static void setAppDao(AppDao dao) {
		appDao = dao;
	}

	public static LockDateChange getLockDateChange() {
		return lockDateChange;
	}

	public static void setLockDateChange(LockDateChange lockDateChange) {
		AppItemView.lockDateChange = lockDateChange;
	}

	public static AlertDialog getAlertDialog() {
		return alertDialog;
	}

	public static void setAlertDialog(AlertDialog alertDialog) {
		AppItemView.alertDialog = alertDialog;
	}

	public boolean isSYNC_DATE() {
		return SYNC_DATE;
	}

	public void setSYNC_DATE(boolean sYNC_DATE) {
		SYNC_DATE = sYNC_DATE;
	}
	

}
