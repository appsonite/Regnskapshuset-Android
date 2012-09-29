package com.src.appsonite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.src.group.NavigationGroupActivity;

public class AboutGroupActivity extends NavigationGroupActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	
		Intent intent = new Intent(this, AboutActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		View view = getLocalActivityManager().startActivity(
				"AboutGroupActivity", intent).getDecorView();
		replaceView(view, "AboutGroupActivity");
	}
	
	@Override
	public void onBackPressed() { 
		super.onBackPressed();
	}
}
