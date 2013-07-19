package com.register;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.service.AppService;

public class RebootReciver extends BroadcastReceiver {
	private OnReciveCallBack onReciveCallBack;
	private static final String TAG = "BootBroadcastReveiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.e("debug", "AppBoradCastReciver had  get  BroadCast");

		String intentName = intent.getAction();
		Log.e("debug", "BootBroadcastReveiver  onRecive");
		Log.e("debug", "BootBroadcastReveiver intent name:"+intent.getAction());

		// onReciveCallBack.startActivity();
		if (intentName.equals(Intent.ACTION_BOOT_COMPLETED)) {
			Intent i = new Intent(context, AppService.class);
			context.startService(intent);
		}
		
 
	}

	public OnReciveCallBack getOnReciveCallBack() {
		return onReciveCallBack;
	}

	public void setOnReciveCallBack(OnReciveCallBack onReciveCallBack) {
		this.onReciveCallBack = onReciveCallBack;
	}

}
