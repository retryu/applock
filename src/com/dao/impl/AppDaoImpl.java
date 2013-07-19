package com.dao.impl;

import java.sql.SQLException;
import java.util.List;

import android.util.Log;

import com.dao.interfaze.AppDao;
import com.dao.model.inter.App;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;

public class AppDaoImpl extends BaseDaoImpl<App, String> implements AppDao {

	public AppDaoImpl(ConnectionSource connectionSource) throws SQLException {
		super(connectionSource, App.class);
	}

	public boolean isDataExits() {
		QueryBuilder<App, String> qb = this.queryBuilder();
		List<App> apps = null;
		try {
			apps = this.queryForAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (apps == null || apps.size() == 0){
			Log.e("debug", "true");
			return false;
		}
		else {
			Log.e("debug", "false");
			return true;
		}
	}

	@Override
	public void batchInsert(List<App> apps) {
		App app;
		for (int i = 0; i < apps.size(); i++) {
			app = apps.get(i);
			try {
				create(app);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<App> getThirdApp() {
		List<App> apps = null;
		try {
			QueryBuilder<App, String> queryBuilder = queryBuilder();
			Where<App, String> where = queryBuilder.where();
			where.ne("type", "1");
			PreparedQuery<App> preQuery = queryBuilder.prepare();
			apps = query(preQuery);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return apps;
	}

	@Override
	public void saveWithAppName(App app) {
		if (isExits(app) == false) {
			try {
				Log.e("debug", "saveWithAppName  "+app);
				create(app);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean isExits(App app) {
		List<App> apps = null;
		if (app.getPackageName() == null) {
			return false;
		}  
		try {
			QueryBuilder<App, String> queryBuilder = queryBuilder();
			Where<App, String> where = queryBuilder.where();
			where.eq("packageName", app.getPackageName());
			PreparedQuery<App> perQuery = queryBuilder.prepare();
			apps = query(perQuery);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (app != null && apps.size() > 0) {
				Log.e("debug", app.getPackageName()+"  is   Exits");
			return true;
		} else {
			Log.e("debug", app.getPackageName()+"  is not Exits");
			return false;
		}

	}

	@Override
	public void deleteWithAppName(App app) {
		List<App> apps=null;
		if (isExits(app) == true) {
			try {
				QueryBuilder<App, String> queryBuilder = queryBuilder();
				Where<App, String> where = queryBuilder.where();
				where.eq("packageName", app.getPackageName());
				PreparedQuery<App> perQuery = queryBuilder.prepare();
				apps = query(perQuery);
				Log.e("debug", "deleteWithAppName  apps.size"+apps.size());
				if(apps.size()>0){
					app=apps.get(0);
					delete(app);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		 
	}

}
