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
import android.widget.TextView;

import com.src.group.NavigationActivity;

public class HomeActivity extends NavigationActivity implements OnClickListener {

	boolean initFlag = false;
	DisplayMetrics metrics;
	
	private Activity instance;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_layout);
		
		instance = this;
		metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

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

		ImageView logoImg = (ImageView) findViewById(R.id.home_logoimg_id);

		RelativeLayout.LayoutParams rParam = new RelativeLayout.LayoutParams(
				metrics.widthPixels * 425 / 768,
				(metrics.heightPixels) * 105 / 1024);
		rParam.leftMargin = metrics.widthPixels * 189 / 768;
		rParam.topMargin = (metrics.heightPixels) * 166 / 1024;
		logoImg.setLayoutParams(rParam);

		Button accountButton = (Button) findViewById(R.id.home_account_id);
		rParam = new RelativeLayout.LayoutParams(
				metrics.widthPixels * 330 / 768,
				(metrics.heightPixels) * 105 / 1024);
		rParam.leftMargin = metrics.widthPixels * 232 / 768;
		rParam.topMargin = (metrics.heightPixels) * 315 / 1024;
		accountButton.setLayoutParams(rParam);
		accountButton.setTextSize(textSize);
		accountButton.setOnClickListener(this);
		
		Button reportButton = (Button) findViewById(R.id.home_salary_id);
		rParam = new RelativeLayout.LayoutParams(
				metrics.widthPixels * 330 / 768,
				(metrics.heightPixels) * 105 / 1024);
		rParam.leftMargin = metrics.widthPixels * 232 / 768;
		rParam.topMargin = (metrics.heightPixels) * 482 / 1024;
		reportButton.setLayoutParams(rParam);
		reportButton.setTextSize(textSize);
		reportButton.setOnClickListener(this);
		
		Button salaryButton = (Button) findViewById(R.id.home_annualreports_id);
		rParam = new RelativeLayout.LayoutParams(
				metrics.widthPixels * 330 / 768,
				(metrics.heightPixels) * 105 / 1024);
		rParam.leftMargin = metrics.widthPixels * 232 / 768;
		rParam.topMargin = (metrics.heightPixels) * 645 / 1024;
		salaryButton.setLayoutParams(rParam);
		salaryButton.setTextSize(textSize);
		salaryButton.setOnClickListener(this);
		
		TextView txtCopyright = (TextView)findViewById(R.id.home_copyright_id);
		txtCopyright.setTextSize(textSize);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.home_account_id:
			goNextHistory("HomeAccountingActivity", new Intent(instance.getParent(), HomeAccountingActivity.class));
			break;
		case R.id.home_annualreports_id:
			goNextHistory("HomeAnnualActivity", new Intent(instance.getParent(), HomeAnnualActivity.class));
			break;
		case R.id.home_salary_id:
			goNextHistory("HomeSalaryActivity", new Intent(instance.getParent(), HomeSalaryActivity.class));
			break;
		}
	}
	
}
