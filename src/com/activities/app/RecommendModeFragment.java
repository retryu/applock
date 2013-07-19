package com.activities.app;

import java.sql.SQLException;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.activities.R;
import com.dao.interfaze.AppDao;
import com.dao.model.inter.App;
import com.db.OrmDateBaseHelper;
import com.umeng.analytics.MobclickAgent;
import com.util.AppUtil;
import com.widget.AppItemView;

public class RecommendModeFragment extends DialogFragment {

	private LayoutInflater layoutInflater;
	private ListView recommendListView;
	private View view;
	private AppAdapter appAdapter;
	private OrmDateBaseHelper ormDateBaseHelper;
	private AppDao appDao;
	private Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = getActivity();
		initWidget();
		initDate();
		Log.e("debug", "RecommendModeFragment onCreateView");
		MobclickAgent.onEvent(getActivity(), "recommend");
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		builder.setView(view);
		builder.setTitle(getText(R.string.recommend_mode));
		builder.setNeutralButton(getText(R.string.lock_programer), new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				List<App> apps = appAdapter.getAppItems();
				lockRecommondApp(apps);
				AppItemView.getLockDateChange().notifyLockDataChange();
				AppAdapter  adapter=AppsFragment.getAppAdapter();
				adapter.RefershData();
				dialog.dismiss();
			}
		});
		builder.setNegativeButton(getText(R.string.cancel), new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Log.e("debug", "cancel");
				dialog.dismiss();
			}
		});

		return builder.create();
	}

	public void lockRecommondApp(List<App> apps) {
		for (int i = 0; i < apps.size(); i++) {
			App app = apps.get(i);
			try {
				appDao.update(app);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void initWidget() {
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = layoutInflater.inflate(R.layout.fragment_recommend_mode, null);
		recommendListView = (ListView) view
				.findViewById(R.id.ListView_Recommend);
	}

	public void initDate() {
		ormDateBaseHelper = new OrmDateBaseHelper(getActivity(), "lock.db",
				null, 1);
		appDao = ormDateBaseHelper.getAppDao();
		appAdapter = new AppAdapter(context, appDao);
		appAdapter.setSYNC_DATE(false);
		try {
			List<App> apps = appDao.queryForEq("type", "3");
			for(int  i=0;i<apps.size();i++){
				App  app=apps.get(i);
				app.setIsLocked(true);
			}
			AppUtil.bindDrawable(apps);
			appAdapter.setAppItems(apps);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		recommendListView.setAdapter(appAdapter);
	}

	public OrmDateBaseHelper getOrmDateBaseHelper() {
		return ormDateBaseHelper;
	}

}
