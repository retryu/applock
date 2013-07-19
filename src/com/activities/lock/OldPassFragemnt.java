package com.activities.lock;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;

@SuppressLint("NewApi")
public class OldPassFragemnt extends Fragment {
	private ToChangeFragment toChangeFragment;

	public ToChangeFragment getToChangeFragment() {
		return toChangeFragment;
	}

	public void setToChangeFragment(ToChangeFragment toChangeFragment) {
		this.toChangeFragment = toChangeFragment;
	}
}
