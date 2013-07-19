package com.activities.lock;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.activities.R;

public class NewPassWordDialog extends DialogFragment {

	View  view;
	
	FragmentManager fm;
	FragmentTransaction  ft;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		  view = inflater
				.inflate(R.layout.fragment_newpassword_dialog, null);
		  
		  return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		fm=getFragmentManager();
		  ft=fm.beginTransaction();
		  NewPassFragment  newPassFragment=new NewPassFragment();
		  ft.replace(R.id.Layout_Content, newPassFragment);
		  ft.commit();
		
		builder.setView(view);
		
		return  builder.create();
		
	}

}
