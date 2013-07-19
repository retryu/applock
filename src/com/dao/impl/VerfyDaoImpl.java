package com.dao.impl;

import java.sql.SQLException;
import java.util.List;

import com.dao.interfaze.VerfyDao;
import com.dao.model.inter.App;
import com.dao.model.inter.Verfy;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

public class VerfyDaoImpl extends BaseDaoImpl<Verfy, String> implements
		VerfyDao {

	public VerfyDaoImpl(ConnectionSource connectionSource) throws SQLException {
		super(connectionSource, Verfy.class);

	}

	@Override
	public boolean isExits() {
		QueryBuilder<Verfy, String> qb = this.queryBuilder();
		List<Verfy> apps = null;
		try {
			apps = this.queryForAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (apps == null || apps.size() == 0)
			return false;
		else {
			return true;
		}
	}

	@Override
	public boolean check(String pw) {
		try {
			Verfy verfy = this.queryForId("1");
			String settingPw = verfy.getPassword();
			if (settingPw.equals(pw)) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public String getPassWord() {
		try {
			Verfy verfy = this.queryForId("1");
			String settingPw = null;
			if (verfy != null) {
				settingPw = verfy.getPassword();
			}
			return settingPw;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Verfy getVerfy() {
		Verfy verfy = null;
		try {
			verfy = this.queryForId("1");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 
		return verfy;
	}

}
