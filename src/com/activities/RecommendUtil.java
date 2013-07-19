package com.activities;

import java.util.ArrayList;
import java.util.List;

import com.dao.model.inter.App;

public class RecommendUtil {
	public static final String swicthSetting = "switchSetting";
	public static final String switchPrivacy = "switchPrivacy";
	public static final String switchPrograme = "switchPrograme";

	public static List<App> getSettingList() {
		List<App> apps = new ArrayList<App>();
		App app = new App();
		app.setPackageName("com.android.settings");
		apps.add(app);
		return apps;
	}

	public static List<App> getPrivacyList() {
		List<App> apps = new ArrayList<App>();
		App app = new App();
		app.setPackageName("com.android.gallery3d");
		apps.add(app);
		return apps;
	}

	public static List<App> getInstallList() {
		List<App> apps = new ArrayList<App>();
		App app = new App();
		app.setPackageName("com.android.packageinstaller");
		apps.add(app);
		return apps;
	}

	public static boolean isQuickApp(String appName) {
		if (appName.equals("com.android.packageinstaller")
				|| appName.equals("com.android.gallery3d")
				|| appName.equals("com.android.settings")
				|| appName.equals("com.android.contacts")
				|| appName.equals("com.android.mms")
				|| appName.equals("com.android.browser")
				|| appName.equals("com.android.vending")

		) {
			return true;
		}

		return false;
	}

	// setting
	// com.android.settings

	// privacy
	// com.android.gallery3d c

	// install
	// com.android.packageinstaller

}
