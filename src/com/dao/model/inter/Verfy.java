package com.dao.model.inter;

import com.dao.impl.VerfyDaoImpl;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(daoClass=VerfyDaoImpl.class)
public class Verfy {

	@DatabaseField(generatedId=true)
	private  int id;
	
	@DatabaseField
	private String type;
	
	@DatabaseField
	private String password;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	
	
}
