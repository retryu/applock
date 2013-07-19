package com.activities.app;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;

import com.actionbarsherlock.widget.SearchView.OnCloseListener;
import com.actionbarsherlock.widget.SearchView.OnQueryTextListener;
import com.activities.R;
import com.dao.interfaze.AppDao;
import com.dao.model.inter.App;
import com.service.interfaze.LockDateChange;
import com.util.AppUtil;
import com.widget.AppItemView;

public class AppAdapter extends BaseAdapter {
	List<App> appItems;
	public List<App> queryAppItems;
	Animation animation;

	/*
	 * 是否使控件和数据库同步
	 */
	private boolean SYNC_DATE;

	/*
	 * 0 stand for normal list 1 stand for query list
	 */
	public static int MODEL = 0;

	private Context context;
	private LayoutInflater layoutInflater;
	private AppDao appDao;
	private OnQueryTextListener onQueryTextListener;
	private static LockDateChange lockDateChange;
	private String queryText;
	private OnCloseListener onCloseListener;

	public AppAdapter(Context contex, AppDao dao) {
		this.context = contex;
		layoutInflater = (LayoutInflater) contex
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		appDao = dao;
		AppItemView.setAppDao(appDao);
		SYNC_DATE = true;
		onQueryTextListener = new OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				Log.e("onQueryTextSubmit", query);
				notifyDataSetChanged();
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				// TODO Auto-generated method stub
				if (newText.equals("")) {
					showNoramalList();
					return false;
				}
				// return false;
				MODEL = 1;
				Log.e("onQueryTextChange", newText);
				queryText = newText;

				getQueryApp(queryText);
				printQueryApp();
				notifyDataSetChanged();
				return false;
			}
		};
		onCloseListener = new OnCloseListener() {

			@Override
			public boolean onClose() {
				Log.e("debug", "onCloseListener   onClose");
				MODEL = 0;
				notifyDataSetChanged();
				return true;
			}
		};

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (MODEL == 1 && queryAppItems != null) {
			return queryAppItems.size();
		}
		if (appItems == null) {
			return 0;
		} else {
			return appItems.size();
		}

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
		App app = null;
		if (MODEL == 1 && queryAppItems != null) {
			app = queryAppItems.get(position);
		} else {
			app = appItems.get(position);
		}

		if (convertView == null) {

			AppItemView appItemView = new AppItemView(context);
			appItemView.setSYNC_DATE(SYNC_DATE);
			appItemView.bindData(app);
			return appItemView;
		} else {
			AppItemView appItemView = (AppItemView) convertView;
			appItemView.setSYNC_DATE(SYNC_DATE);
			appItemView.bindData(app);
		}
		

		return convertView;
	}

	public List<App> getAppItems() {
		return appItems;
	}

	public void setAppItems(List<App> appItems) {
		this.appItems = appItems;
	}

	public AppDao getAppDao() {
		return appDao;
	}

	public void setAppDao(AppDao appDao) {
		this.appDao = appDao;
	}

	public OnQueryTextListener getOnQueryTextListener() {
		return onQueryTextListener;
	}

	public void setOnQueryTextListener(OnQueryTextListener onQueryTextListener) {
		this.onQueryTextListener = onQueryTextListener;
	}

	private void getQueryApp(String queryText) {
		if (queryText.equals(""))
			return;
		queryAppItems = new ArrayList<App>();

		for (int i = 0; i < appItems.size(); i++) {
			App app = appItems.get(i);
			if (Match(app.getName(), queryText)) {
				queryAppItems.add(app);
			}
		}
	}

	public boolean Match(String content, String quert) {
		content = content.toLowerCase();
		quert = quert.toLowerCase();
		if (content.contains(quert)) {
			return true;
		} else {
			return false;
		}

	}

	public void showNoramalList() {
		Log.e("appAdapter", "showNoramalList");
		MODEL = 0;
		notifyDataSetChanged();
	}

	public void printQueryApp() {
		if (queryAppItems == null)
			return;
		for (int i = 0; i < queryAppItems.size(); i++) {
			App app = queryAppItems.get(i);
			Log.e("query", app + "");
		}
	}

	public OnCloseListener getOnCloseListener() {
		return onCloseListener;
	}

	public boolean isSYNC_DATE() {
		return SYNC_DATE;
	}

	public void setSYNC_DATE(boolean sYNC_DATE) {
		SYNC_DATE = sYNC_DATE;
	}

	public List<App> getLockApp() {
		List<App> lockApps = new ArrayList<App>();
		for (int i = 0; i < appItems.size(); i++) {
			App app = appItems.get(i);
			if (app.getIsLocked() == true) {
				lockApps.add(app);
			}
		}
		System.out.print(lockApps);
		return lockApps;
	}

	public void RefershData() {
		appItems = appDao.getThirdApp();
		AppUtil.bindDrawable(appItems);
		notifyDataSetChanged();
	}

}
