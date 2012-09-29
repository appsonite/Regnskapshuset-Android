package com.src.appsonite;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.src.group.NavigationActivity;

public class PDFViewActivity extends NavigationActivity implements OnClickListener {

	Activity instance;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pdfview_layout);

		instance = this;

		WebView mweb = (WebView) findViewById(R.id.pdfview_webview_id);
		WebSettings set = mweb.getSettings();
		set.setJavaScriptEnabled(true);
		set.setBuiltInZoomControls(true);
		mweb.loadUrl("http://docs.google.com/gview?embedded=true&url=http://www.regnskapshuset.info/pdfs/10004/2012/8/accounting/balance.pdf");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
}
