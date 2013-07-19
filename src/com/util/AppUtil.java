package com.util;
 
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.activities.RecommendUtil;
import com.dao.model.inter.App;
import com.service.AppService;

public class AppUtil {
	private static Context contex;
	private static PackageManager packageManager;

	public AppUtil(Context contex) {
		this.contex = contex;
		packageManager = contex.getPackageManager();
	}

	public static List<PackageInfo> getAppList() {
		List<PackageInfo> apps = packageManager.getInstalledPackages(0);
		for (PackageInfo packageInfo : apps) {
			Log.e("debug", "name:" + packageInfo.packageName + "  ");
		}
		return apps;
	}

	public static App getAppInfo(String packageName) {
		PackageInfo packInfo = null;
		App app = new App();

		try { 
			Log.e("debug", "packageName" + packageName);
//			if (packageManager == null) {
//				contex = AppService.context;
//				Log.e("debug packageManager  is  null ",
//						contex == null ? "context is  null"
//								: "context  is not  null");
//				packageManager = contex.getPackageManager();
//			}
			packInfo = packageManager.getPackageInfo(packageName,
					PackageManager.GET_GIDS);
			info2App(app, packInfo);

		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return app;
	}

	public static List<App> getAppInfo() {
		List<PackageInfo> packageInfos = getAppList();
		List<App> appItems = new ArrayList<App>();
		for (int i = 0; i < packageInfos.size(); i++) {
			App app = new App();
			PackageInfo info = packageInfos.get(i);

			info2App(app, info);
			appItems.add(app);
		}
		return appItems;
	}

	private static void info2App(App app, PackageInfo info) {
		String appName = info.applicationInfo.loadLabel(packageManager)
				.toString();
		int type = getAppType(info);
		app.setName(appName);
		app.setPackageName(info.applicationInfo.packageName);
		app.setIsLocked(false);
		app.setType("" + type);
		Drawable iconDraw = info.applicationInfo.loadIcon(packageManager);
		app.setIconDraw(iconDraw);
	}

	/**
	 * @return 0代表第三方程序 1代表系统程序 2代表快捷程序
	 */
	public static int getAppType(PackageInfo info) {
		String packageName = info.applicationInfo.packageName;

		// 自身这个程序当做系统应用
		if (packageName.equals(AppService.lockApp)) {
			return 1;
		}
		if (RecommendUtil.isQuickApp(packageName) == true) {
			return 3;
		}
		// 非系统应用
		if ((info.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
			return 0;
		}
		// 系统应用
		else {
			return 1;
		}
	}

	public static void bindDrawable(List<App> apps) {
		App app;
		for (int i = 0; i < apps.size(); i++) {
			app = apps.get(i);
			try {
				Drawable drawable = packageManager.getApplicationIcon(app
						.getPackageName());
				app.setIconDraw(drawable);
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
			}
		}
	}

}
