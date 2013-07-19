package com.activities;

import android.app.Application;

public class MyApplication extends Application {

	private static boolean hasVerfy;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		hasVerfy = false;
	}

	public static boolean isHasVerfy() {
		return hasVerfy;
	}

	public static void setHasVerfy(boolean hasVerfy) {
		MyApplication.hasVerfy = hasVerfy;
	}
	

}
