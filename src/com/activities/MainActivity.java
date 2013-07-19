package com.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.Animation.AnimationListener;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.internal.widget.ActionBarView;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnActionExpandListener;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;
import com.actionbarsherlock.view.SubMenu;
import com.actionbarsherlock.widget.SearchView;
import com.activities.app.AppAdapter;
import com.activities.app.AppsFragment;
import com.activities.app.RecommendFragment;
import com.activities.app.TabsAdapter;
import com.activities.lock.typelist.LockTypeListActivity;
import com.activities.setting.SettingActivity;
import com.dao.interfaze.AppDao;
import com.dao.model.inter.App;
import com.service.AppService;
import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.UMFeedbackService;
import com.util.AppUtil;
import com.util.ReflectUtil;

public class MainActivity extends CommonActivity {
	private AppUtil appUtil;

	AppDao appDao;

	// private Switch switchLock;

	private SearchView searchView;
	private ActionBar actionBar;

	ViewPager mViewPager;
	TabsAdapter mTabsAdapter;
	private MenuItem mItem;
	private SubMenu smSearch;
	private SubMenu smLock;
	private SubMenu smSetting;
	private Menu menu;
	private int position;
	private AppsFragment appsFragment;

	private RecommendFragment recommendFragment;
	
 
	private  Animation  hideAnimation;
	private  Animation  showAnimation;
	private static boolean isHide = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initTab();
		appUtil = new AppUtil(this);
		AppService.addActivity(this);
		startServie();
	}

	public void startServie() {
		Intent intent = new Intent(MainActivity.this, AppService.class);
		startService(intent);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		actionBar = getSupportActionBar();
		mItem = item;
		Log.e("onMenuItemSelected", "id:" + item.getItemId()+"feature :"+featureId);
 
		switch (item.getItemId()) {
		case 1:
			if (isHide == false) {
				Log.e("debug", "hide");
				mViewPager.startAnimation(hideAnimation);
				isHide = true;
			} else {
				Log.e("debug", "show");
				mViewPager.startAnimation(showAnimation);
				isHide = false;
			}

			break;
		case R.id.action_search:

			break;
		case  3:
			Intent  intent=new Intent(this, LockTypeListActivity.class);
			startActivity(intent);
			break;
		case  4:
			UMFeedbackService.openUmengFeedbackSDK(this);
			break;
		default:
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu m) {
		getSupportMenuInflater().inflate(R.menu.main, m);
		searchView = (SearchView) m.findItem(R.id.action_search)
				.getActionView();
		this.menu = m;
		AppsFragment.setSearchView(searchView);

		smLock = m.addSubMenu(1, 1, 0, "");
		smLock.setIcon(R.drawable.icon_lock);
		smLock.getItem().setShowAsAction(
				MenuItem.SHOW_AS_ACTION_ALWAYS
						| MenuItem.SHOW_AS_ACTION_WITH_TEXT);

		smSetting = m.addSubMenu(2, 2, 0, "");	
		smSetting.setIcon(R.drawable.icon_setting_bottom);
		MenuItem  mChangePassword=smSetting.add(3, 3, 0, getText(R.string.change_password));
		MenuItem  mSettingItem=smSetting.add(3, 4, 0, getText(R.string.feedBack));
		
		mChangePassword.setIcon(R.drawable.icon_key);
		mSettingItem.setIcon(R.drawable.icon_msg);
		smSetting.getItem().setShowAsAction(
				MenuItem.SHOW_AS_ACTION_ALWAYS
						| MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		setSearchView(item);
		return super.onOptionsItemSelected(item);
	}

	private void setSearchView(MenuItem item) {
		item.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem arg0) {
				// TODO Auto-generated method stub
				Log.e("debug", "onclick");
				return false;
			}
		});
		item.setOnActionExpandListener(new OnActionExpandListener() {
			@Override
			public boolean onMenuItemActionExpand(MenuItem item) {
				Log.e("debug", "onMenuItemActionExpand");
				return true;
			}

			@Override
			public boolean onMenuItemActionCollapse(MenuItem item) {
				Log.e("debug", "onMenuItemActionCollapse");
				AppAdapter appAdapter = AppsFragment.getAppAdapter();
				if (appAdapter != null) {
					appAdapter.showNoramalList();
				}
				return true;
			}
		});
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		Log.e("debug", "onPrepareOptionsMenu" + position);
		MenuItem menuItem = menu.getItem(0);
		if (position == 1) {
			menuItem.setVisible(true);
			Log.e("debug", "set visiable");
		} else {
			menuItem.setVisible(false);
			Log.e("debug", "set invisiable");
		}
		return super.onPrepareOptionsMenu(menu);
	}

	@SuppressLint("NewApi")
	public void initTab() {
		actionBar = getSupportActionBar();
		actionBar.setTitle(getText(R.string.app_name));
		// ActionBarImpl actionBarImpl=(ActionBarImpl) actionBar;
		int tv = (Integer) ReflectUtil.getFieldValue(actionBar,
				"NAVIGATION_MODE_TABS");
		ActionBarView actionBarView = (ActionBarView) ReflectUtil
				.getFieldValue(actionBar, "mActionView");

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		mViewPager = (ViewPager) findViewById(R.id.ViewPaper_Content);
		mTabsAdapter = new TabsAdapter(this, mViewPager);

		mTabsAdapter.addTab(actionBar.newTab().setText("快捷方式"),
				RecommendFragment.class, null);
		mTabsAdapter.addTab(actionBar.newTab().setText("程序列表"),
				AppsFragment.class, null);
		appsFragment = (AppsFragment) mTabsAdapter.getItem(1);
		recommendFragment = (RecommendFragment) mTabsAdapter.getItem(0);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int index) {
				// TODO Auto-generated method stub
				int current = mViewPager.getCurrentItem();
				mViewPager.setCurrentItem(current);
				position = current;
				Log.e("debug", "" + current);
				invalidateOptionsMenu();
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
		 
		
		hideAnimation=getHideAnimation();
		showAnimation=getShowAnimation();
		
	}

	@Override
	protected void onStop() {
		Log.e("debug", "MainActivity stop");
		super.onStop();
	}

	
	public AnimationSet getHideAnimation() {
		AnimationSet animationSet = new AnimationSet(true);
		Animation hideAnimation = new AlphaAnimation(1.0f, 0.0f);
		hideAnimation.setFillAfter(true);
		hideAnimation.setDuration(300);
		animationSet.addAnimation(hideAnimation);
		animationSet.setFillAfter(true);
		animationSet.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				 
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
