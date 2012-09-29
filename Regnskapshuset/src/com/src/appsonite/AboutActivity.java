package com.src.appsonite;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

import com.src.group.NavigationActivity;

public class AboutActivity extends NavigationActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_layout);
		
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		
		TextView txtView = (TextView)findViewById(R.id.about_image_id);
		
		if (metrics.widthPixels >= 600) {
			txtView.setPadding(25, 50, 10, 0);
			txtView.setTextSize(24);
		}
	}
}
