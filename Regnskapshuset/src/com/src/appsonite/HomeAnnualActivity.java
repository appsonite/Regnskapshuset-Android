package com.src.appsonite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.src.group.NavigationActivity;

public class HomeAnnualActivity extends NavigationActivity implements OnClickListener {
	int statusBarHeight = 0;
	boolean initFlag = false;
	DisplayMetrics metrics;
	
	Activity instance;
	
	String annualStr = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.annual_layout);

		metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		instance = this;

		initRes();
	}

	/**
	 * initialize resources
	 */
	private void initRes() {
		initFlag = true;
		
		int textSize = 16;
		if (metrics.widthPixels > 600) {
			textSize = 28;
		}

		RelativeLayout.LayoutParams rParam;

		Button accountButton = (Button) findViewById(R.id.annual_annual_id);
		rParam = new RelativeLayout.LayoutParams(
				metrics.widthPixels * 200 / 768,
				(metrics.heightPixels - statusBarHeight) * 60 / 1024);
		rParam.leftMargin = metrics.widthPixels * 50 / 768;
		rParam.topMargin = (metrics.heightPixels - statusBarHeight) * 40 / 1024;
		accountButton.setLayoutParams(rParam);
		accountButton.setTextSize(textSize);
		accountButton.setOnClickListener(this);
		
		ImageView logoView = (ImageView) findViewById(R.id.annual_logo_id);
		rParam = new RelativeLayout.LayoutParams(
				metrics.widthPixels * 380 / 768,
				(metrics.heightPixels - statusBarHeight) * 110 / 1024);
		rParam.leftMargin = metrics.widthPixels * 300 / 768;
		rParam.topMargin = (metrics.heightPixels - statusBarHeight) * 40 / 1024;
		logoView.setLayoutParams(rParam);

		Button resultatButton = (Button) findViewById(R.id.annual_skat_id);
		rParam = new RelativeLayout.LayoutParams(
				metrics.widthPixels * 330 / 768,
				(metrics.heightPixels - statusBarHeight) * 105 / 1024);
		rParam.leftMargin = metrics.widthPixels * 232 / 768;
		rParam.topMargin = (metrics.heightPixels - statusBarHeight) * 307 / 1024;
		resultatButton.setLayoutParams(rParam);
		resultatButton.setTextSize(textSize);
		resultatButton.setOnClickListener(this);

		Button balanceButton = (Button) findViewById(R.id.annual_lign_id);
		rParam = new RelativeLayout.LayoutParams(
				metrics.widthPixels * 330 / 768,
				(metrics.heightPixels - statusBarHeight) * 105 / 1024);
		rParam.leftMargin = metrics.widthPixels * 232 / 768;
		rParam.topMargin = (metrics.heightPixels - statusBarHeight) * 498 / 1024;
		balanceButton.setLayoutParams(rParam);
		balanceButton.setTextSize(textSize);
		balanceButton.setOnClickListener(this);
		
		Button reportButton = (Button) findViewById(R.id.annual_report_id);
		rParam = new RelativeLayout.LayoutParams(
				metrics.widthPixels * 330 / 768,
				(metrics.heightPixels - statusBarHeight) * 105 / 1024);
		rParam.leftMargin = metrics.widthPixels * 232 / 768;
		rParam.topMargin = (metrics.heightPixels - statusBarHeight) * 689 / 1024;
		reportButton.setLayoutParams(rParam);
		reportButton.setTextSize(textSize);
		reportButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.annual_annual_id:
			super.onBackPressed();
			return;
		case R.id.annual_skat_id:
			annualStr = "tax/cal.pdf";
			break;
		case R.id.annual_lign_id:
			annualStr = "tax/paper.pdf";
			break;
		case R.id.annual_report_id:
			annualStr = "tax/report.pdf";
			break;
		}

		Intent intent = new Intent(HomeAnnualActivity.this, CalendarActivity.class);
		intent.putExtra("urlStr", annualStr);
		intent.putExtra("isReport", 1);
		goNextHistory("CalendarActivity", intent);
	}
}
