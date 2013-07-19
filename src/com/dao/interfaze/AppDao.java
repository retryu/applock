package com.dao.interfaze;

import java.util.List;

import com.dao.model.inter.App;
import com.j256.ormlite.dao.Dao;

public interface AppDao extends Dao<App, String> {

	public boolean isDataExits();

	public void batchInsert(List<App> apps);

	public List<App> getThirdApp();

	public void saveWithAppName(App app);

	public void deleteWithAppName(App app);

	public boolean isExits(App app);
}
