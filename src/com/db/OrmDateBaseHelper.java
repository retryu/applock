package com.db;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.dao.interfaze.AppDao;
import com.dao.interfaze.VerfyDao;
import com.dao.model.inter.App;
import com.dao.model.inter.Verfy;
import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class OrmDateBaseHelper extends SQLiteOpenHelper {

	private static AppDao appDao;
	private static VerfyDao verfyDao;

	public OrmDateBaseHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		initScheme();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.e("debug", "onCreate");

	}

	public void initScheme() {
		ConnectionSource connectionSource = new AndroidConnectionSource(this);
		try {
			TableUtils.createTableIfNotExists(connectionSource, App.class);
			TableUtils.createTableIfNotExists(connectionSource, Verfy.class);
			appDao = DaoManager.createDao(connectionSource, App.class);
			verfyDao = DaoManager.createDao(connectionSource, Verfy.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.e("debug", "onCreate");
	}

	public AppDao getAppDao() {
		return appDao;
	}

	public void setAppDao(AppDao appDao) {
		this.appDao = appDao;
	}

	public static VerfyDao getVerfyDao() {
		return verfyDao;
	}

	public static void setVerfyDao(VerfyDao verfyDao) {
		OrmDateBaseHelper.verfyDao = verfyDao;
	}

}
