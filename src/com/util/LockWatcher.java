package com.util;

import java.sql.SQLException;
import java.util.List;

import android.util.Log;

import com.dao.interfaze.AppDao;
import com.dao.model.inter.App;

public class LockWatcher {
	static List<App> lockedApps;
	private static AppDao appDao;
	private static String invalideApp;
	public static final String SP_NAME = "lock";
	public static final String IsLock = "isLock";
	public LockWatcher(AppDao dao) {
		appDao = dao;
		try {
			lockedApps = appDao.queryForEq("isLocked", true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void dataChange() {
		Log.e("debug", "loack  watcher lockDateChange");
		try {
			lockedApps = appDao.queryForEq("isLocked", true);
			App app = null;
			for (int i = 0; i < lockedApps.size(); i++) {
				Log.e("debugq", "" + lockedApps.get(i));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean checkLock(String appName) {
		//程序列表不为空
		if (lockedApps == null)
			return false;
		if (invalideApp != null) {
			if (invalideApp.endsWith(appName) == true)
				return false;
		}
		App app = null;
		for (int i = 0; i < lockedApps.size(); i++) {
			app = lockedApps.get(i);
			if (app.getPackageName().equals(appName)) {
				return true;
			}
		}
		return false;
	}

	public static void filterAdd(List<App> apps) {
		App newApp = null;
		for (int i = 0; i < apps.size(); i++) {
			newApp = apps.get(i);
			for (int j = 0; j < lockedApps.size(); j++) {
				App app = lockedApps.get(j);
				String appPackageName = app.getPackageName();
				String newAppPackageNaem = newApp.getPackageName();
				if (appPackageName.contains(newAppPackageNaem)) {
					break;
				}
			}
			lockedApps.add(newApp);
		}
	}

	public static void filterRemove(List<App> apps) {
		App newApp = null;
		App app = null;
		for (int i = 0; i < apps.size(); i++) {
			newApp = apps.get(i);
			for (int j = 0; j < lockedApps.size(); j++) {
				app = lockedApps.get(j);
				String appPackageName = app.getPackageName();
				String newAppPackageNaem = newApp.getPackageName();
				if (appPackageName.contains(newAppPackageNaem)) {
					break;
				}
			}
			lockedApps.remove(app);
		}
	}
	
	public  static  int  getLockCount(){
		return lockedApps.size();
	}

	public AppDao getAppDao() {
		return appDao;
	}

	public void setAppDao(AppDao dao) {
		appDao = dao;
	}

	public String getInvalideApp() {
		return invalideApp;
	}

	public void setInvalideApp(String last) {
		invalideApp = last;
	}
	
	

}
