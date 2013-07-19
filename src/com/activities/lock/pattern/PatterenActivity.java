package com.activities.lock.pattern;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.activities.R;
import com.activities.app.AppsFragment;
import com.activities.lock.CommonLockActivity;
import com.activities.lock.typelist.LockTypeListActivity;
import com.service.AppService;
import com.util.LockPatternUtils;
import com.widget.LockPatternView;
import com.widget.LockPatternView.Cell;
import com.widget.LockPatternView.DisplayMode;
import com.widget.LockPatternView.OnPatternListener;

public class PatterenActivity extends CommonLockActivity {
	private LockPatternView lockPatternView;
	private TextView tvAlert;
	private LockPatternUtils lockPatternUtils;
	public static final String verify = "VERIFY";
	public static final String reCreate = "RECREATE";
	public static final String unLock = "UNLOCK";
	public static final String canBack = "CANBACK";
	public static final String type = "TYPE";
	private String prePass;
	private String typeMark;
	private boolean backMark;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.e("debug", "onCreate");
		setContentView(R.layout.layout_pattern);
		typeMark = reCreate;
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			typeMark = bundle.getString(type);
			backMark = bundle.getBoolean(canBack, true);
		}
		Log.e("debug", "type:" + typeMark);
		initWidget();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		Log.e("debug", "onNewIntent");
	}

	public void initWidget() {
		AppService.addActivity(this);
		tvAlert = (TextView) findViewById(R.id.TV_Alert);
		lockPatternView = (LockPatternView) findViewById(R.id.LockPatternView);
		setTvAlert(tvAlert);

		lockPatternUtils = new LockPatternUtils(this);

		lockPatternView.setOnPatternListener(new OnPatternListener() {

			@Override
			public void onPatternStart() {
				Log.e("debug", "onclick");
			}

			@Override
			public void onPatternDetected(List<Cell> pattern) {
				// TODO Auto-generated method stub
				if (check(pattern) == false) {
					lockPatternView.setDisplayMode(DisplayMode.Wrong);
					return;
				}

				if (typeMark.equals(reCreate)) {
					lockPatternUtils.check(pattern);
					if (prePass == null) {
						prePass = lockPatternUtils.patternToString(pattern);
						lockPatternView.clearPattern();
						getSupportActionBar().setTitle(
								getText(R.string.resetPattern));
					} else {
						String secondPass = lockPatternUtils
								.patternToString(pattern);
						if (secondPass.equals(prePass)) {
							lockPatternUtils.saveLockPattern(pattern);
							Toast.makeText(PatterenActivity.this,
									getText(R.string.pw_had_set),
									Toast.LENGTH_LONG).show();
							finish();
						} else {
							Toast.makeText(PatterenActivity.this,
									getText(R.string.pw_not_same),
									Toast.LENGTH_LONG).show();
							prePass = null;
							lockPatternView.setDisplayMode(DisplayMode.Wrong);
							getSupportActionBar().setTitle(
									getText(R.string.setPattern));
						}
					}

				} else {
					int result = lockPatternUtils.checkPattern(pattern);
					if (result != 1) {
						if (result == 0) {
							lockPatternView.setDisplayMode(DisplayMode.Wrong);
							// Toast.makeText(PatterenActivity.this,
							// getText(R.string.pw_wrong),
							// Toast.LENGTH_LONG).show();
							worngCount++;
							WrongState();
						} else {
							lockPatternView.clearPattern();
							Toast.makeText(PatterenActivity.this,
									getText(R.string.please_set_pw),
									Toast.LENGTH_LONG).show();

						}
					} else {
						Toast.makeText(PatterenActivity.this,
								getText(R.string.pw_correct), Toast.LENGTH_LONG)
								.show();
						if (typeMark.equals(verify)) {

							if (backMark != false) {
								Intent intent = new Intent(
										PatterenActivity.this,
										LockTypeListActivity.class);
								startActivity(intent);
							}
							AppsFragment.unlock();
							finish();
							// back2MainActivity();
						} else {
							AppService.check();
							AppService.finishAllActivity();
						}
					}
				}
			}

			@Override
			public void onPatternCleared() {
				// TODO Auto-enerated method stub

			}

			@Override
			public void onPatternCellAdded(List<Cell> pattern) {
				// TODO Auto-generated method stub
 			}
		});
	}

	public boolean check(List<Cell> pattern) {
		if (lockPatternUtils.check(pattern) == false) {
			Toast.makeText(this, "密码长度必须大于3", Toast.LENGTH_LONG).show();
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addCategory(Intent.CATEGORY_HOME);
		this.startActivity(intent);
		finish();
	}

}
