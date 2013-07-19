package com.dao.model.inter;

import android.graphics.drawable.Drawable;

import com.dao.impl.AppDaoImpl;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(daoClass=AppDaoImpl.class)
public class App {
	
	@DatabaseField(generatedId=true)
	private int  id;
	@DatabaseField
	private  String name;
	@DatabaseField
	private String  packageName;
	@DatabaseField
	private String  type;
	@DatabaseField
	private  String  passWord;
	@DatabaseField
	private  Boolean  isLocked;
	
	private  Drawable iconDraw;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	 
	public Boolean getIsLocked() {
		return isLocked;
	}
	public void setIsLocked(Boolean isLocked) {
		this.isLocked = isLocked;
	}
	public Drawable getIconDraw() {
		return iconDraw;
	}
	public void setIconDraw(Drawable iconDraw) {
		this.iconDraw = iconDraw;
	}
	@Override
	public String toString() {
		return "App [id=" + id + ", name=" + name + ", packageName="
				+ packageName + ", type=" + type + ", passWord=" + passWord
				+ ", isLocked=" + isLocked + ", iconDraw=" + iconDraw + "]";
	}
	
	
	
	
	
	

}
