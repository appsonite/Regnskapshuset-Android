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

public class HomeAccountingActivity extends NavigationActivity implements
		OnClickListener {
	int statusBarHeight = 0;
	boolean initFlag = false;
	DisplayMetrics metrics;
	
	Activity instance;
	
	String accountStr = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.accounting_layout);

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

		Button accountButton = (Button) findViewById(R.id.accounting_account_id);
		rParam = new RelativeLayout.LayoutParams(
				metrics.widthPixels * 200 / 768,
				(metrics.heightPixels - statusBarHeight) * 60 / 1024);
		rParam.leftMargin = metrics.widthPixels * 50 / 768;
		rParam.topMargin = (metrics.heightPixels - statusBarHeight) * 40 / 1024;
		accountButton.setLayoutParams(rParam);
		accountButton.setTextSize(textSize);
		accountButton.setOnClickListener(this);
		
		ImageView logoView = (ImageView) findViewById(R.id.accounting_logo_id);
		rParam = new RelativeLayout.LayoutParams(
				metrics.widthPixels * 380 / 768,
				(metrics.heightPixels - statusBarHeight) * 110 / 1024);
		rParam.leftMargin = metrics.widthPixels * 300 / 768;
		rParam.topMargin = (metrics.heightPixels - statusBarHeight) * 40 / 1024;
		logoView.setLayoutParams(rParam);

		Button resultatButton = (Button) findViewById(R.id.accounting_resultat_id);
		rParam = new RelativeLayout.LayoutParams(
				metrics.widthPixels * 330 / 768,
				(metrics.heightPixels - statusBarHeight) * 105 / 1024);
		rParam.leftMargin = metrics.widthPixels * 232 / 768;
		rParam.topMargin = (metrics.heightPixels - statusBarHeight) * 207 / 1024;
		resultatButton.setLayoutParams(rParam);
		resultatButton.setTextSize(textSize);
		resultatButton.setOnClickListener(this);

		Button balanceButton = (Button) findViewById(R.id.accounting_balance_id);
		rParam = new RelativeLayout.LayoutParams(
				metrics.widthPixels * 330 / 768,
				(metrics.heightPixels - statusBarHeight) * 105 / 1024);
		rParam.leftMargin = metrics.widthPixels * 232 / 768;
		rParam.topMargin = (metrics.heightPixels - statusBarHeight) * 338 / 1024;
		balanceButton.setLayoutParams(rParam);
		balanceButton.setTextSize(textSize);
		balanceButton.setOnClickListener(this);

		Button kundeButton = (Button) findViewById(R.id.accounting_kunde_id);
		rParam = new RelativeLayout.LayoutParams(
				metrics.widthPixels * 330 / 768,
				(metrics.heightPixels - statusBarHeight) * 105 / 1024);
		rParam.leftMargin = metrics.widthPixels * 232 / 768;
		rParam.topMargin = (metrics.heightPixels - statusBarHeight) * 469 / 1024;
		kundeButton.setLayoutParams(rParam);
		kundeButton.setTextSize(textSize);
		kundeButton.setOnClickListener(this);

		Button leverandButton = (Button) findViewById(R.id.accounting_leverand_id);
		rParam = new RelativeLayout.LayoutParams(
				metrics.widthPixels * 330 / 768,
				(metrics.heightPixels - statusBarHeight) * 105 / 1024);
		rParam.leftMargin = metrics.widthPixels * 232 / 768;
		rParam.topMargin = (metrics.heightPixels - statusBarHeight) * 600 / 1024;
		leverandButton.setLayoutParams(rParam);
		leverandButton.setTextSize(textSize);
		leverandButton.setOnClickListener(this);

		Button merverButton = (Button) findViewById(R.id.accounting_merver_id);
		rParam = new RelativeLayout.LayoutParams(
				metrics.widthPixels * 330 / 768,
				(metrics.heightPixels - statusBarHeight) * 105 / 1024);
		rParam.leftMargin = metrics.widthPixels * 232 / 768;
		rParam.topMargin = (metrics.heightPixels - statusBarHeight) * 731 / 1024;
		merverButton.setLayoutParams(rParam);
		merverButton.setTextSize(textSize);
		merverButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.accounting_account_id:
			super.onBackPressed();
			return;
		case R.id.accounting_resultat_id:
			accountStr = "accounting/pl.pdf";
			break;
		case R.id.accounting_balance_id:
			accountStr = "accounting/balance.pdf";
			break;
		case R.id.accounting_kunde_id:
			accountStr = "accounting/ar.pdf";
			break;
		case R.id.accounting_leverand_id:
			accountStr = "accounting/ap.pdf";
			break;
		case R.id.accounting_merver_id:
			accountStr = "accounting/vat.pdf";
			break;
		}

		Intent intent = new Intent(HomeAccountingActivity.this, CalendarActivity.class);
		intent.putExtra("urlStr", accountStr);
		goNextHistory("CalendarActivity", intent);
	}
}
