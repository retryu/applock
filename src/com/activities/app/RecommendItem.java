package com.activities.app;
public class RecommendItem {

	private String Title;

	private String hint;

	private Boolean isSwitch;

	private String sharePerfenceName;

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

	public Boolean getIsSwitch() {
		return isSwitch;
	}

	public void setIsSwitch(Boolean isSwitch) {
		this.isSwitch = isSwitch;
	}

	public String getSharePerfenceName() {
		return sharePerfenceName;
	}

	public void setSharePerfenceName(String sharePerfenceName) {
		this.sharePerfenceName = sharePerfenceName;
	}

}
