package com.src.group;

import java.util.ArrayList;

import android.app.ActivityGroup;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;

import com.src.golobal.UIUtils;

public class NavigationGroupActivity extends ActivityGroup {
	
	final static int LEFT_SLIDE_OUT = 1;
	final static int LEFT_SLIDE_IN = 2;
	final static int RIGHT_SLIDE_OUT = 3;
	final static int RIGHT_SLIDE_IN = 4;
	
	@Override
	protected void onDestroy() {
		history.clear();
		strID.clear();
		strAllID.clear();
		super.onDestroy();
	}

	ArrayList<View> history;
	ArrayList<String> strID;
	ArrayList<String> strAllID;
	NavigationGroupActivity group;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		setRequestedOrientation(1);
		history = new ArrayList<View>();
		strID = new ArrayList<String>();
		strAllID = new ArrayList<String>();
		group = this;
	}

	public void changeView(View v, String id) {
		
		strID.remove(history.size() - 1);
		history.remove(history.size() - 1);
		history.add(v);
		strID.add(id);
		setContentView(v);
	}

	public void replaceView(View v, String id) {
		
		Log.d("MK", "REPLACE VIEW...");
		strID.add(id);
		strAllID.add(id);
		
		if (history.size() > 0)
		{
			View oldView = history.get(history.size() - 1);
			Animation animation1 = getAnimation(LEFT_SLIDE_OUT);
			if (animation1 != null)
				oldView.startAnimation(animation1);
			
			Animation animation2 = getAnimation(RIGHT_SLIDE_IN);
			if (animation2 != null)
				v.startAnimation(animation2);
		}
		
		history.add(v);
		setContentView(v);
	}

	public void back() {
		if (history.size() > 2) {
			group.getLocalActivityManager().destroyActivity(strID.get(strID.size() - 1), true);
			View curView = null;
			if (history.size() > 1)
			{
				curView = history.get(history.size() - 1);
				Animation animation1 = getAnimation(RIGHT_SLIDE_OUT);
				if (animation1 != null)
					curView.startAnimation(animation1);
			}
			history.remove(history.size() - 1);
			strID.remove(strID.size() - 1);
			View oldView = history.get(history.size() - 1);
			Animation animation2 = getAnimation(LEFT_SLIDE_IN);
			if (animation2 != null)
				oldView.startAnimation(animation2);
			setContentView(history.get(history.size() - 1));
		} else {
			finish();
			//killProcess();									 
		}
	}
	
	public void reset(String strDestroyID){
		group.getLocalActivityManager().destroyActivity(strDestroyID, true);
		history.clear();
		strID.clear();
		strAllID.clear();
	}

	@Override
	public void onBackPressed() {
		group.back();
		return;
	}
	
	public void killProcess(){
		System.exit(0);
		android.os.Process.killProcess(
			android.os.Process.myPid());
	}
	
	private Animation getAnimation(int nAnimationType)
	{
		Animation anim = null;
		
		switch(nAnimationType) {
		case LEFT_SLIDE_OUT:
			anim = UIUtils.outToLeftAnimation();
			break;
		case LEFT_SLIDE_IN:
			anim = UIUtils.inFromLeftAnimation();
			break;
		case RIGHT_SLIDE_OUT:
			anim = UIUtils.outToRightAnimation();
			break;
		case RIGHT_SLIDE_IN:
			anim = UIUtils.inFromRightAnimation();
			break;
		default:
			return null;
		}
		anim.setDuration(300);
		return anim;
	}

	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}	
}