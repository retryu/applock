package com.activities.app;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class AppItem {
	private String appName;
	private Boolean islock;
	private Drawable iconDraw;
	private String packageName;
	private String version;
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public Boolean getIslock() {
		return islock;
	}
	public void setIslock(Boolean islock) {
		this.islock = islock;
	}
	public Drawable getIconDraw() {
		return iconDraw;
	}
	public void setIconDraw(Drawable iconDraw) {
		this.iconDraw = iconDraw;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
 
	
	

  

}
