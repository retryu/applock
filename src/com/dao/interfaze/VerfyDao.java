package com.dao.interfaze;

import com.dao.model.inter.Verfy;
import com.j256.ormlite.dao.Dao;

public interface VerfyDao extends Dao<Verfy, String> {

	public boolean isExits();

	public boolean check(String pw);

	public String getPassWord();

	public Verfy getVerfy();
}
