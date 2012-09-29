package com.src.appsonite;

import android.annotation.SuppressLint;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;

public class TabManageActivity extends TabActivity {
	final static int arrIcons[] = { R.drawable.home_sel, R.drawable.about_sel, R.drawable.company_sel, R.drawable.contact_sel };
	final  static String arrTabLabel[] = { "LOGIN", "ABOUT", "COMPANY", "CONTACT" };
	public static TabHost tabHost;
	public static TabManageActivity tabActivity;
	public static TabManageActivity gInstance = null;
	
	TabHost.TabSpec spec;
	TabWidget tabWidget;
	MyTabView arrTabs[] = new MyTabView[4];
	private DisplayMetrics metrics;
	int statusBarHeight = 0;
	boolean initFlag = false;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.tab_manage);
    }
    
    @Override
	public void onWindowFocusChanged(boolean hasFocus) {
		Rect rect = new Rect();
		Window win = getWindow();
		win.getDecorView().getWindowVisibleDisplayFrame(rect);

		metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		statusBarHeight = metrics.heightPixels - (rect.bottom - rect.top);

		if (!initFlag) {
			initRes();
		}
	};
    
    private void initRes() {
    	initFlag = true;
    	
		tabHost = getTabHost();
		tabWidget = getTabWidget();
		
		FrameLayout.LayoutParams rParam = new FrameLayout.LayoutParams(metrics.widthPixels, 98 * metrics.widthPixels / 640);
		rParam.gravity = Gravity.BOTTOM;
		tabWidget.setLayoutParams(rParam);
		
		Intent intent;

		// home
		intent = new Intent().setClass(this, LoginGroupActivity.class);
		arrTabs[0] = new MyTabView(this, 0, arrTabLabel[0]);
		spec = tabHost.newTabSpec(arrTabLabel[0]).setIndicator(arrTabs[0])
				.setContent(intent);
		tabHost.addTab(spec);

		// about
		intent = new Intent().setClass(this, AboutGroupActivity.class);
		arrTabs[1] = new MyTabView(this, 1, arrTabLabel[1]);
		spec = tabHost.newTabSpec(arrTabLabel[1]).setIndicator(arrTabs[1])
				.setContent(intent);
		tabHost.addTab(spec);

		// company
		intent = new Intent().setClass(this, CompanyGroupActivity.class);
		arrTabs[2] = new MyTabView(this, 2, arrTabLabel[2]);
		spec = tabHost.newTabSpec(arrTabLabel[2]).setIndicator(arrTabs[2])
				.setContent(intent);
		tabHost.addTab(spec);

		// contact
		intent = new Intent().setClass(this, ContactGroupActivity.class);
		arrTabs[3] = new MyTabView(this, 3, arrTabLabel[3]);
		spec = tabHost.newTabSpec(arrTabLabel[3]).setIndicator(arrTabs[3])
				.setContent(intent);
		tabHost.addTab(spec);

		tabHost.setOnTabChangedListener(mTabChanged);
		tabHost.setCurrentTab(0);
	}
    
    public void setCurrentTab(int nIdx)
	{
		tabHost.setCurrentTab(nIdx);
	}

	OnTabChangeListener mTabChanged = new OnTabChangeListener() {

		@Override
		public void onTabChanged(String tabId) {
			if (tabId.compareTo("LOGIN") == 0)
				tabWidget.setBackgroundResource(arrIcons[0]);
			else if (tabId.compareTo("ABOUT") == 0)
				tabWidget.setBackgroundResource(arrIcons[1]);
			else if (tabId.compareTo("COMPANY") == 0)
				tabWidget.setBackgroundResource(arrIcons[2]);
			else if (tabId.compareTo("CONTACT") == 0)
				tabWidget.setBackgroundResource(arrIcons[3]);
		}
	};
    
    @SuppressLint("ResourceAsColor")
	private class MyTabView extends LinearLayout {
		int nIdx = -1;

		public MyTabView(Context c, int drawableIdx, String label) {
			super(c);
			
			ImageView iv = new ImageView(c);
			nIdx = drawableIdx;
			setId(0x100 + nIdx);
			
			LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
			layout.weight = 1;
			
			iv.setLayoutParams(layout);
			setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
			
			if (nIdx != 0) {
				ImageView imgView = new ImageView(c);
				LinearLayout.LayoutParams layout1 = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, 64);
				imgView.setLayoutParams(layout1);
				addView(imgView);
			}
			addView(iv);
		}
	}
    
    public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}
}

