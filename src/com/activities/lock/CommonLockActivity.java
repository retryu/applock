package com.activities.lock;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.activities.MainActivity;
import com.activities.R;
import com.activities.app.AppsFragment;
import com.activities.lock.callback.PassWordCallBack;
import com.umeng.analytics.MobclickAgent;

public class CommonLockActivity extends SherlockActivity {
	public static int THEME = R.style.Theme_Sherlock_Light;
	private final static int MSG_UPDATE_TIME = 1;
	private final static int MSG_UPDATE_ALERT = 2;
	private final int maxCount = 5;
	private boolean canTouch;
	private PassWordCallBack passWordCallBack;
	protected String finishType;
	
	/*
	 * ÊäÈë´íÎóÃÜÂë´ÎÊý
	 */
	public int worngCount;
	WrongHandler wrongHandler;
	private TextView tvAlert;
	private View inputView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setTheme(THEME);
		super.onCreate(savedInstanceState);
		wrongHandler = new WrongHandler();
		worngCount = 0;
		canTouch = true;
	}

	public void WrongState() {

		if (worngCount >= maxCount) {

			new Thread() {
				public void run() {
					for (int i = 30; i > 0; i--) {
						Message msgTime = new Message();
						msgTime.what = MSG_UPDATE_TIME;
						msgTime.obj = i;
						wrongHandler.sendMessage(msgTime);
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					worngCount = 0;
					Message msgAlery = new Message();
					msgAlery.what = MSG_UPDATE_ALERT;
					msgAlery.obj = getText(R.string.input_old_pwd);
					wrongHandler.sendMessage(msgAlery);

				};
			}.start();
		} else {
			Message msgAlery = new Message();
			msgAlery.what = MSG_UPDATE_ALERT;
			String alert = (String) getText(R.string.last_time);
			int time = maxCount - worngCount;
			alert = alert.replace("x", time + "");
			msgAlery.obj = alert;
			wrongHandler.sendMessage(msgAlery);
		}

	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		if (canTouch == true) {
			return super.dispatchTouchEvent(ev);
		} else {
			return true;
		}
	}

	class WrongHandler extends Handler {

		@Override
		public void dispatchMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case MSG_UPDATE_TIME:
				int time = (Integer) msg.obj;
				tvAlert.setText(getText(R.string.wrong_to_much).toString()
						+ "(" + time + "s)");
				setCanTouch(false);
				break;

			case MSG_UPDATE_ALERT:
				String alert = (String) msg.obj;
				tvAlert.setText(alert);
				setCanTouch(true);
				break;
			}
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void setTvAlert(TextView tvAlert) {
		this.tvAlert = tvAlert;
	}

	public boolean isCanTouch() {
		return canTouch;
	}

	public void setCanTouch(boolean canTouch) {
		this.canTouch = canTouch;
	}

	public PassWordCallBack getPassWordCallBack() {
		return passWordCallBack;
	}

	public void setPassWordCallBack(PassWordCallBack passWordCallBack) {
		this.passWordCallBack = passWordCallBack;
	}

	public void back2MainActivity() {
		AppsFragment.unlock();
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
	}

}
