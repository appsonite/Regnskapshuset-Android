package com.src.appsonite;

import java.util.Calendar;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.src.golobal.Download;
import com.src.golobal.Global;
import com.src.group.NavigationActivity;

public class CalendarActivity extends NavigationActivity implements OnClickListener {

	int year = 0;
	int month = 0;
	int isReport = 0;
	String strURL;
	Activity instance;
	
	int btnHeight = 60;
	DisplayMetrics metrics;	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendar_layout);

		strURL = getIntent().getStringExtra("urlStr");
		isReport = getIntent().getIntExtra("isReport", 0);
		
		instance = this;
		
		metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		
		initRes();
	}
	
	private void initRes() {
		((TextView)findViewById(R.id.calendar_layout_text_id)).setVisibility(View.GONE);
		if (isReport == 1) {
			((Button)findViewById(R.id.calendar_jan_id)).setBackgroundResource(R.drawable.calendar_sel);
			((Button)findViewById(R.id.calendar_feb_id)).setBackgroundResource(R.drawable.calendar_sel);
			((Button)findViewById(R.id.calendar_march_id)).setBackgroundResource(R.drawable.calendar_sel);
			((Button)findViewById(R.id.calendar_april_id)).setBackgroundResource(R.drawable.calendar_sel);
			((Button)findViewById(R.id.calendar_may_id)).setBackgroundResource(R.drawable.calendar_sel);
			((Button)findViewById(R.id.calendar_june_id)).setBackgroundResource(R.drawable.calendar_sel);
			((Button)findViewById(R.id.calendar_july_id)).setBackgroundResource(R.drawable.calendar_sel);
			((Button)findViewById(R.id.calendar_aug_id)).setBackgroundResource(R.drawable.calendar_sel);
			((Button)findViewById(R.id.calendar_sep_id)).setBackgroundResource(R.drawable.calendar_sel);
			((Button)findViewById(R.id.calendar_oct_id)).setBackgroundResource(R.drawable.calendar_sel);
			((Button)findViewById(R.id.calendar_nov_id)).setBackgroundResource(R.drawable.calendar_sel);
			((Button)findViewById(R.id.calendar_dec_id)).setBackgroundResource(R.drawable.calendar_sel);
			
			((LinearLayout)findViewById(R.id.calendar_layout_id)).setVisibility(View.GONE);
			((TextView)findViewById(R.id.calendar_layout_text_id)).setVisibility(View.VISIBLE);
		}
		
		LinearLayout.LayoutParams lParam = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		lParam.bottomMargin = 10;
		((RelativeLayout)findViewById(R.id.calendar_first_id)).setLayoutParams(lParam);
		((RelativeLayout)findViewById(R.id.calendar_first_id)).setPadding(20, 10, 0, 100);
		((TextView)findViewById(R.id.calendar_layout_text_id)).setTextSize((metrics.heightPixels) * 22 / 1024 / metrics.density);
		if (metrics.widthPixels >= 600) {
			lParam = new LinearLayout.LayoutParams(500, 500);
			((LinearLayout)findViewById(R.id.calendar_layout_id)).setLayoutParams(lParam);
			
			((RelativeLayout)findViewById(R.id.calendar_first_id)).setPadding(25, 10, 0, 200);
			
			btnHeight = 70;
			
			// change the calendar icons
			RelativeLayout.LayoutParams rParam = new RelativeLayout.LayoutParams(200, 70);
			rParam.bottomMargin = 15;
			((Button)findViewById(R.id.calendar_back_id)).setLayoutParams(rParam);
			
			((TextView)findViewById(R.id.calendar_txt_year_id)).setTextSize(30);
			((Button)findViewById(R.id.calendar_back_id)).setTextSize(24);
			
			lParam = new LinearLayout.LayoutParams(100, 80);
			((Button)findViewById(R.id.calendar_jan_id)).setLayoutParams(lParam);
			((Button)findViewById(R.id.calendar_feb_id)).setLayoutParams(lParam);
			((Button)findViewById(R.id.calendar_march_id)).setLayoutParams(lParam);
			((Button)findViewById(R.id.calendar_april_id)).setLayoutParams(lParam);
			((Button)findViewById(R.id.calendar_may_id)).setLayoutParams(lParam);
			((Button)findViewById(R.id.calendar_june_id)).setLayoutParams(lParam);
			((Button)findViewById(R.id.calendar_july_id)).setLayoutParams(lParam);
			((Button)findViewById(R.id.calendar_aug_id)).setLayoutParams(lParam);
			((Button)findViewById(R.id.calendar_sep_id)).setLayoutParams(lParam);
			((Button)findViewById(R.id.calendar_oct_id)).setLayoutParams(lParam);
			((Button)findViewById(R.id.calendar_nov_id)).setLayoutParams(lParam);
			((Button)findViewById(R.id.calendar_dec_id)).setLayoutParams(lParam);
		}
		((Button)findViewById(R.id.calendar_back_id)).setOnClickListener(this);
		
		final Calendar calendar = Calendar.getInstance();		
		year = calendar.get(Calendar.YEAR);
		((TextView)findViewById(R.id.calendar_txt_year_id)).setText(String.valueOf(year));
		
		LinearLayout hScroll = (LinearLayout)findViewById(R.id.calendar_year_id);
		int btnWidth = metrics.widthPixels / 6;
		for (int i=0; i<4; i++) {
			Button button = new Button(this);
			lParam = new LinearLayout.LayoutParams(btnWidth, btnHeight);
			lParam.weight = 1;
			button.setLayoutParams(lParam);
			button.setText(String.valueOf(year - i));
			button.setTextColor(Color.WHITE);
			
			if (metrics.widthPixels >= 600) {
				button.setTextSize(24);
			} else
				button.setTextSize(12);
			
			if (i == 0) {
				if (isReport == 1)
					button.setBackgroundResource(R.drawable.seg_l);
				else 
					button.setBackgroundResource(R.drawable.seg_l_sel);
			} else if (i == 3) {
				button.setBackgroundResource(R.drawable.seg_r);
			} else {
				button.setBackgroundResource(R.drawable.seg_m);
			}
			
			button.setId(i);
			button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					for (int pos = 0; pos < 4; pos++) {
						if (pos == 0)
							((Button)findViewById(pos)).setBackgroundResource(R.drawable.seg_l);
						else if (pos == 3)
							((Button)findViewById(pos)).setBackgroundResource(R.drawable.seg_r);
						else
							((Button)findViewById(pos)).setBackgroundResource(R.drawable.seg_m);
							
					}
					
					if (v.getId() == 0) {
						v.setBackgroundResource(R.drawable.seg_l_sel);
					} else if (v.getId() == 3) {
						v.setBackgroundResource(R.drawable.seg_r_sel);
					} else {
						v.setBackgroundResource(R.drawable.seg_m_sel);
					}
					
					year = calendar.get(Calendar.YEAR) - v.getId();
					((TextView)findViewById(R.id.calendar_txt_year_id)).setText(String.valueOf(year));
					if (isReport == 1) {
						String url = "http://www.regnskapshuset.info/pdfs/";

						url += String.valueOf(Global.user_id) + "/";
						url += String.valueOf(year) + "/0/";
						url += strURL;

						Download task = new Download(instance, instance.getParent(), url);
						task.execute();
					}
				}
			});
			
			hScroll.addView(button);
		}
		
		((Button)findViewById(R.id.calendar_jan_id)).setOnClickListener(this);
		((Button)findViewById(R.id.calendar_feb_id)).setOnClickListener(this);
		((Button)findViewById(R.id.calendar_march_id)).setOnClickListener(this);
		((Button)findViewById(R.id.calendar_april_id)).setOnClickListener(this);
		((Button)findViewById(R.id.calendar_may_id)).setOnClickListener(this);
		((Button)findViewById(R.id.calendar_june_id)).setOnClickListener(this);
		((Button)findViewById(R.id.calendar_july_id)).setOnClickListener(this);
		((Button)findViewById(R.id.calendar_aug_id)).setOnClickListener(this);
		((Button)findViewById(R.id.calendar_sep_id)).setOnClickListener(this);
		((Button)findViewById(R.id.calendar_oct_id)).setOnClickListener(this);
		((Button)findViewById(R.id.calendar_nov_id)).setOnClickListener(this);
		((Button)findViewById(R.id.calendar_dec_id)).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.calendar_jan_id:
			month = 1;
			break;
		case R.id.calendar_feb_id:
			month = 2;
			break;
		case R.id.calendar_march_id:
			month = 3;
			break;
		case R.id.calendar_april_id:
			month = 4;
			break;
		case R.id.calendar_may_id:
			month = 5;
			break;
		case R.id.calendar_june_id:
			month = 6;
			break;
		case R.id.calendar_july_id:
			month = 7;
			break;
		case R.id.calendar_aug_id:
			month = 8;
			break;
		case R.id.calendar_sep_id:
			month = 9;
			break;
		case R.id.calendar_oct_id:
			month = 10;
			break;
		case R.id.calendar_nov_id:
			month = 11;
			break;
		case R.id.calendar_dec_id:
			month = 12;
			break;
		case R.id.calendar_back_id:
			super.onBackPressed();
			return;
		}
		
		if (isReport == 1) 
			return;
		
		showRemotePDF();
	}

	private void showRemotePDF() {
		String url = "http://www.regnskapshuset.info/pdfs/";

		url += String.valueOf(Global.user_id) + "/";
		url += String.valueOf(year) + "/";
		url += String.valueOf(month) + "/";
		url += strURL;

		Download task = new Download(instance, instance.getParent(), url);
		task.execute();
	} 
}
