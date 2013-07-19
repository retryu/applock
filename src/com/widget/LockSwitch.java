package com.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.CheckBox;
import android.widget.ListView;

public class LockSwitch extends CheckBox {

	private ListView listView;
	private int oldLeft;
	private int oldTop;
	private int oldRight;
	private int oldBottom;

	public LockSwitch(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public LockSwitch(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public ListView getListView() {
		return listView;
	}

	public void setListView(ListView listView) {
		this.listView = listView;
	}

	public void showList() {
		listView.startAnimation(getShowAnimation());
	}

	public void hideList() {
		listView.startAnimation(getHideAnimation());
	}

	public AnimationSet getHideAnimation() {
		AnimationSet animationSet = new AnimationSet(true);
		Animation hideAnimation = new AlphaAnimation(1.0f, 0.0f);
		Animation scalaAniamtion = new ScaleAnimation(1.0f, 1.0f, 1.0f, 0.0f);
		scalaAniamtion.setFillAfter(true);
		hideAnimation.setFillAfter(true);
		scalaAniamtion.setDuration(300);
		hideAnimation.setDuration(300);
		animationSet.addAnimation(hideAnimation);
		animationSet.setFillAfter(true);
		animationSet.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				oldLeft = listView.getLeft();
				oldBottom = listView.getBottom();
				oldRight = listView.getRight();
				oldTop = listView.getTop();
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				Log.e("debug", "l:"+oldLeft+"  t:"+oldTop+"r:"+oldRight+"  b"+oldBottom);
				listView.layout(0, 0, 0, 0);
			}
		});
		// animationSet.addAnimation(scalaAniamtion);
		return animationSet;
	}

	public Animation getShowAnimation() {
		Animation animation = new AlphaAnimation(0.0f, 1.0f);
		animation.setFillAfter(true);
		animation.setDuration(300);
		animation.setAnimationListener(new  AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				listView.layout(oldLeft, oldTop, oldRight, oldBottom);
			 }
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				 
			}
		});
		return animation;
	}

}
