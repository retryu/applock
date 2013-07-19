package com.service;

import com.dao.interfaze.AppDao;
import com.dao.model.inter.App;
import com.db.OrmDateBaseHelper;
import com.util.AppUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class PackageReciver extends BroadcastReceiver {

	private Context context;
	private AppDao appDao;
	private static OrmDateBaseHelper ormDateBaseHelper;
	public PackageReciver(Context c) {
		context=c;
	}
	@Override
	public void onReceive(Context context, Intent intent) {
		String intentName = intent.getAction();
		if (intentName.equals(Intent.ACTION_PACKAGE_ADDED)) {
			String packageName = intent.getDataString().substring(8);
			Log.e("debug", "package " + packageName + "  was  add");
			addAdpp(packageName);
		}
		if (intentName.equals(Intent.ACTION_PACKAGE_REMOVED)) {
			String packageName = intent.getDataString().substring(8);
			Log.e("debug", "package " + packageName + "  was  remove");
			removeApp(packageName);

		}
	}

	/**
	 * 新增一条新安装app的记录
	 * 
	 * @param packageName
	 *            包名
	 */
	public void addAdpp(String packageName) {
		if (!packageName.equals(AppService.lockApp)) {
			if (context == null) {
				Log.e("debug", "context  is  null");
			} else {
				Log.e("debug", "context  is  not  null");
			}
			ormDateBaseHelper = new OrmDateBaseHelper(context, "lock.db", null,
					1);
			AppUtil appUtil = new AppUtil(context);
			App app = AppUtil.getAppInfo(packageName);
			app.setType("0");
			app.setPackageName(packageName);
			appDao=ormDateBaseHelper.getAppDao();
			appDao.saveWithAppName(app);
		}
	}

	/**
	 * 删除一条新删除的app的记录
	 * 
	 * @param packageName
	 */
	public void removeApp(String packageName) {
		if (!packageName.equals(AppService.lockApp)) {
			if (context == null) {
				Log.e("debug", "context  is  null");
			} else {
				Log.e("debug", "context  is  not  null");
			}

			ormDateBaseHelper = new OrmDateBaseHelper(context, "lock.db", null,
					1);
			AppUtil appUtil = new AppUtil(context);
			App app = new App();
			app.setPackageName(packageName);
			if (appDao == null) {
				Log.e("debug", "appDao  is null");
				ormDateBaseHelper = new OrmDateBaseHelper(context, "lock.db",
						null, 1);
			}
			appDao=ormDateBaseHelper.getAppDao();
			appDao.deleteWithAppName(app);
		}
	}

}
