package com.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import com.activities.app.AppsFragment;
import com.activities.lock.PwdActivity;
import com.activities.lock.pattern.PatterenActivity;
import com.dao.interfaze.AppDao;
import com.dao.interfaze.VerfyDao;
import com.dao.model.inter.App;
import com.dao.model.inter.Verfy;
import com.db.OrmDateBaseHelper;
import com.service.interfaze.LockDateChange;
import com.util.AppUtil;
import com.util.LockWatcher;
import com.widget.AppItemView;

public class AppService extends Service {
	private static LockWatcher lockWatcher;
	private static String lastApp;
	public static String lockApp = "com.activities";
	private static List<Activity> acivityStack = new ArrayList<Activity>();
	public static boolean isLock;
	public Context context;
	private VerfyDao verfyDao;
	private AppDao appDao;
	private LockDateChange lockDateChange;
	private TimerTasker timerTasker;
	private OrmDateBaseHelper ormDateBaseHelper;
	private SharedPreferences sharedPreferences;
	private ActivityManager activityManager;
	private List<ActivityManager.RunningTaskInfo> taskList;
	private PackageReciver packageReciver;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		init();
		Log.e("debug", "service  start");
		context = getApplicationContext();
		timerTasker = new TimerTasker();
		timerTasker.start();

		lockDateChange = new LockDateChange() {
			@Override
			public void notifyLockDataChange() {
				lockWatcher.dataChange();
				AppsFragment.lockDateChange.notifyLockDataChange();
			}
		};
		AppItemView.setLockDateChange(lockDateChange);
	}

	public void init() {
		sharedPreferences = getSharedPreferences(LockWatcher.SP_NAME, 0);
		if (sharedPreferences.contains(LockWatcher.IsLock) == false) {
			sharedPreferences.edit().putBoolean(LockWatcher.IsLock, false)
					.commit();
		}
		isLock = sharedPreferences.getBoolean(LockWatcher.IsLock, true);
		ormDateBaseHelper = new OrmDateBaseHelper(this, "lock.db", null, 1);
		appDao = ormDateBaseHelper.getAppDao();
		verfyDao = ormDateBaseHelper.getVerfyDao();
		lockWatcher = new LockWatcher(appDao);

	}

	public void registerPackageReciver() {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
		intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
		intentFilter.addDataScheme("package");
		packageReciver = new PackageReciver(getApplicationContext());
		registerReceiver(packageReciver, intentFilter);
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.e("debug", "service  create");
		registerPackageReciver();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.e("debug", "service  destory");
		unregisterReceiver(packageReciver);
	}

	class TimerTasker extends Thread {
		public void run() {
			while (true) {
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// Log.e("debug context", context == null ?
				// "  context  is  null"
				// : "contex  not  null");

				if (isLock == false)
					continue;
				String currentApp = getLast();
				if (!currentApp.equals(lockApp)) {
					lastApp = currentApp;
				} else {
					continue;
				}
				if (lockWatcher.getInvalideApp() != null) {
					if (!lockWatcher.getInvalideApp().equals(currentApp)) {
						lockWatcher.setInvalideApp(null);
					}
				}
				if (lockWatcher.checkLock(currentApp) == true) {
					jumpToActivity(false);
				}

				 Log.e("debug", "running:  " + currentApp);

			}
		};
	}

	public void jumpToActivity(boolean needFinish) {
		Log.e("debug", "jumpToActivity");
		try {
			Verfy verfy = verfyDao.queryForId("1");
			Intent intent;
			if (verfy.getType().equals("1")) {
				intent = new Intent(context, PwdActivity.class);
			} else {
				intent = new Intent(context, PatterenActivity.class);
				intent.putExtra(PatterenActivity.type, PatterenActivity.unLock);
			}
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			if (needFinish == true) {
				intent.putExtra("NEEDFINISH", true);
			}

			startActivity(intent);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getLast() {
		activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		taskList = activityManager.getRunningTasks(1);
		// for (RunningTaskInfo runTaskInfo : taskList) {
		// // Log.d("debug", runningAppProcessInfo.processName);
		// }
		RunningTaskInfo info = taskList.get(0);
		return info.topActivity.getPackageName();
	}

	public static void check() {
		lockWatcher.setInvalideApp(lastApp);
	}

	public static void addActivity(Activity activity) {
		acivityStack.add(activity);
	}

	public static void finishAllActivity() {
		for (int i = 0; i < acivityStack.size(); i++) {
			Activity activity = acivityStack.get(i);
			Log.e("debug", "finishing "+activity);
			activity.finish();
		}
	}

}
