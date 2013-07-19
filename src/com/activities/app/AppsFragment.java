package com.activities.app;

import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import com.actionbarsherlock.widget.SearchView;
import com.activities.CommonActivity;
import com.activities.R;
import com.activities.lock.LockActivity;
import com.activities.lock.PwdActivity;
import com.activities.lock.pattern.PatterenActivity;
import com.dao.interfaze.AppDao;
import com.dao.interfaze.VerfyDao;
import com.dao.model.inter.App;
import com.dao.model.inter.Verfy;
import com.db.OrmDateBaseHelper;
import com.service.AppService;
import com.service.interfaze.LockDateChange;
import com.util.AppUtil;
import com.util.LockPatternUtils;
import com.util.LockWatcher;
import com.widget.AlertDialog;
import com.widget.AppItemView;
import com.widget.LockSwitch;

/**
 * @author chenyuruan Ö÷½çÃæapp list
 */
public class AppsFragment extends Fragment {
	private View view;
	private OrmDateBaseHelper ormDateBaseHelper;
	AppDao appDao;
	private SharedPreferences sharedPreferences;
	private DataHandler dateDataHandler;
	private ListView listView;
	private static AppAdapter appAdapter;
	private static LockSwitch switchLock;
	private CommonActivity activity;
	private static SearchView searchView;
	private Button btnRecommend;
	private FragmentManager fManager;
	public static LockDateChange lockDateChange;
	private VerfyDao verfyDao;
	public static boolean unLock;

	private static boolean isHide = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_apps, null);
		initDB();
		init();
		return view;
	}

	@Override
	public void onResume() {
		Log.e("debug", "appFragemnt  resume");
		super.onResume();
		checkLock();
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		lock();
	}

	private void init() {
		dateDataHandler = new DataHandler();
		Message msg = new Message();
		msg.what = DataHandler.INIT;
		dateDataHandler.sendMessage(msg);

	}

	private void checkLock() {
		if (verfyDao.isExits() == false) {
			Intent intent = new Intent(getActivity(), LockActivity.class);
			intent.putExtra(LockActivity.type, LockActivity.newType);
			intent.putExtra(LockActivity.canBACK, false);
			startActivity(intent);
		} else {
			if (unLock == true)
				return;
			Verfy verfy = verfyDao.getVerfy();
			if (verfy.getType().equals("1")) {
				Intent intent = new Intent(getActivity(), PwdActivity.class);
				intent.putExtra(PwdActivity.NEED_FINISH, true);
				startActivity(intent);
			} else {
				Intent intent = new Intent(getActivity(),
						PatterenActivity.class);
				intent.putExtra(PatterenActivity.canBack, false);
				intent.putExtra(PatterenActivity.type, PatterenActivity.verify);
				startActivity(intent);
			}

		}

	}

	public void initWidget() {
		Message msg = new Message();
		msg.what = DataHandler.NEW_ACTIVITY;
		dateDataHandler.sendMessage(msg);
		lockDateChange = new LockDateChange() {
			@Override
			public void notifyLockDataChange() {
				int lockCount = LockWatcher.getLockCount();
				if (switchLock.isChecked() == false && lockCount != 0) {
					Log.e("debug", " AppsFragment lockDateChange   lockCount:"
							+ lockCount);
					setLock(true);
					switchLock.setChecked(true);
				}
				if (switchLock.isChecked() == true && lockCount == 0) {
					setLock(false);
					switchLock.setChecked(false);
				}
			}
		};
		fManager = getFragmentManager();
		AppItemView.setAlertDialog(new AlertDialog(getActivity()));
		listView = (ListView) view.findViewById(R.id.ListView_App);
		switchLock = (LockSwitch) view.findViewById(R.id.Switch_Lock);
		switchLock.setListView(listView);
		btnRecommend = (Button) view.findViewById(R.id.Btn_Recommend_Mode);
		switchLock.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Boolean checked = switchLock.isChecked();
				// setLock(checked);
				// if (isHide == false) {
				// isHide=true;
				// switchLock.hideList();
				// } else {
				// isHide=false;
				// switchLock.showList();
				// }

			}

		});

		btnRecommend.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				RecommendModeFragment recomentMode = new RecommendModeFragment();
				// recomentMode.setOrmDateBaseHelper(ormDateBaseHelper);
				recomentMode.show(fManager, "recomend");

			}
		});
	}

	public static void hideList() {
		switchLock.hideList();
	}

	public static void showList() {
		switchLock.showList();
	}

	private void setLock(Boolean checked) {
		AppService.isLock = checked;
		sharedPreferences = activity.getSharedPreferences(LockWatcher.SP_NAME,
				0);
		sharedPreferences.edit().putBoolean(LockWatcher.IsLock, checked)
				.commit();
		Boolean isLock = sharedPreferences
				.getBoolean(LockWatcher.IsLock, false);
		Log.e("debug", "check:" + checked);
	}

	public void initData() {
		new Thread() {
			@Override
			public void run() {
				super.run();
				if (appDao.isDataExits() == true) {
					List<App> apps = appDao.getThirdApp();
					AppUtil.bindDrawable(apps);
					Message msg = new Message();
					msg.what = DataHandler.CHANGE_DATA;
					msg.obj = apps;
					Log.e("debug", "send  CHANGE_DATA");
					dateDataHandler.sendMessage(msg);
				} else {
					List<App> apps = AppUtil.getAppInfo();
					appDao.batchInsert(apps);
					apps = appDao.getThirdApp();
					AppUtil.bindDrawable(apps);
					Message msg = new Message();
					msg.what = DataHandler.INIT_DATA;
					msg.obj = apps;
					Log.e("debug", "send  INIT_DATA");
					dateDataHandler.sendMessage(msg);
				}
			}
		}.start();

		sharedPreferences = getActivity().getSharedPreferences(
				LockWatcher.SP_NAME, 0);
		if (sharedPreferences.contains(LockWatcher.IsLock) == false) {
			sharedPreferences.edit().putBoolean(LockWatcher.IsLock, false)
					.commit();
		}
		Boolean isLock = sharedPreferences
				.getBoolean(LockWatcher.IsLock, false);
		AppService.isLock = isLock;
		switchLock.setChecked(isLock);
		LockPatternUtils.setVerfyDao(verfyDao);
		appAdapter = new AppAdapter(activity, appDao);
		appAdapter.setAppItems(null);
		listView.setAdapter(appAdapter);
	}

	private void initDB() {
		activity = (CommonActivity) getActivity();
		ormDateBaseHelper = activity.getOrmDateBaseHelper();
		appDao = ormDateBaseHelper.getAppDao();
		verfyDao = ormDateBaseHelper.getVerfyDao();
	}

	class DataHandler extends Handler {
		public static final int INIT = 0;
		public static final int INIT_DATA = 1;
		public static final int CHANGE_DATA = 2;
		public static final int NEW_ACTIVITY = 3;
		private List<App> apps;

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case INIT_DATA:
				apps = (List<App>) msg.obj;
				Log.e("debug", "CHANGE_DATA apps Size:" + apps.size());
				appAdapter.setAppItems(apps);
				appAdapter.notifyDataSetChanged();
				break;
			case CHANGE_DATA:
				apps = (List<App>) msg.obj;
				Log.e("debug", "send  INIT_DATA apps's  size:" + apps.size());
				appAdapter.setAppItems(apps);
				appAdapter.notifyDataSetChanged();
				break;
			case NEW_ACTIVITY:
				checkLock();
				break;
			case INIT:
				initWidget();
				initData();
				break;
			}

		}
	}

	public static void setSearchView(SearchView view) {
		searchView = view;
		if (appAdapter != null) {
			searchView.setOnQueryTextListener(appAdapter
					.getOnQueryTextListener());
			searchView.setOnCloseListener(appAdapter.getOnCloseListener());
		}
	}

	private void setbindAdapter() {
		searchView.setOnQueryTextListener(appAdapter.getOnQueryTextListener());
		searchView.setOnCloseListener(appAdapter.getOnCloseListener());
	}

	public static AppAdapter getAppAdapter() {
		return appAdapter;
	}

	public static void lock() {
		unLock = false;
	}

	public static void unlock() {
		unLock = true;
	}

}
