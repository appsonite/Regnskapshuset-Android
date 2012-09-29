package com.src.appsonite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ContactActivity extends Activity implements OnClickListener {
	
	EditText txtMsg;
	EditText txtName;
	EditText txtCompany;
	EditText txtAddress;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_layout);
		
		initRes();
	}
	
	private void initRes() {
		Button sendMail = (Button)findViewById(R.id.contact_mail_id);
		
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		
		int textSize = 12;
		if (metrics.widthPixels > 600) {
			textSize = 28;
		}
				
		txtMsg = (EditText)findViewById(R.id.contact_msg_id);
		txtName = (EditText)findViewById(R.id.contact_name_id);
		txtCompany = (EditText)findViewById(R.id.contact_company_id);
		txtAddress = (EditText)findViewById(R.id.contact_address_id);
		
		ImageView logoImg = (ImageView)findViewById(R.id.contact_logo_id);
		RelativeLayout.LayoutParams rParam = new RelativeLayout.LayoutParams(metrics.widthPixels * 420 / 768, (metrics.heightPixels) * 85 / 1024);
		rParam.leftMargin = metrics.widthPixels * 168 / 768;
		rParam.topMargin = (metrics.heightPixels - 50) * 90 / 1024;
		logoImg.setLayoutParams(rParam);
		
		ImageView contentLayout = (ImageView)findViewById(R.id.contact_border_id);
		rParam = new RelativeLayout.LayoutParams(metrics.widthPixels * 666 / 768, (metrics.heightPixels) * 914 / 1024);
		rParam.leftMargin = metrics.widthPixels * 95 / 768;
		rParam.topMargin = (metrics.heightPixels) * 40 / 1024;
		contentLayout.setLayoutParams(rParam);
		
		LinearLayout layout1 = (LinearLayout)findViewById(R.id.contact_layout_1);
		rParam = new RelativeLayout.LayoutParams(metrics.widthPixels * 610 / 768, (metrics.heightPixels) * 51 / 1024);
		rParam.leftMargin = metrics.widthPixels * 80 / 768;
		rParam.topMargin = (metrics.heightPixels) * 94 / 1024;
		layout1.setLayoutParams(rParam);
		
		LinearLayout layout2 = (LinearLayout)findViewById(R.id.contact_layout_2);
		rParam = new RelativeLayout.LayoutParams(metrics.widthPixels * 610 / 768, (metrics.heightPixels) * 51 / 1024);
		rParam.leftMargin = metrics.widthPixels * 80 / 768;
		rParam.topMargin = (metrics.heightPixels) * 184 / 1024;
		layout2.setLayoutParams(rParam);
		
		LinearLayout layout3 = (LinearLayout)findViewById(R.id.contact_layout_3);
		rParam = new RelativeLayout.LayoutParams(metrics.widthPixels * 610 / 768, (metrics.heightPixels) * 51 / 1024);
		rParam.leftMargin = metrics.widthPixels * 80 / 768;
		rParam.topMargin = (metrics.heightPixels) * 284 / 1024;
		layout3.setLayoutParams(rParam);
		
		LinearLayout layout4 = (LinearLayout)findViewById(R.id.contact_layout_4);
		rParam = new RelativeLayout.LayoutParams(metrics.widthPixels * 610 / 768, (metrics.heightPixels) * 443 / 1024);
		rParam.leftMargin = metrics.widthPixels * 80 / 768;
		rParam.topMargin = (metrics.heightPixels) * 364 / 1024; 
		layout4.setLayoutParams(rParam);
		
		rParam = new RelativeLayout.LayoutParams(metrics.widthPixels * 164 / 768, (metrics.heightPixels) * 60 / 1024);
		rParam.leftMargin = metrics.widthPixels * 520 / 768;
		rParam.topMargin = (metrics.heightPixels) * 810 / 1024;
		sendMail.setLayoutParams(rParam);
		
		
		((TextView)findViewById(R.id.contact_txtview_1)).setTextSize(textSize);
		((TextView)findViewById(R.id.contact_txtview_2)).setTextSize(textSize);
		((TextView)findViewById(R.id.contact_txtview_3)).setTextSize(textSize);
		((TextView)findViewById(R.id.contact_txtview_4)).setTextSize(textSize);
		
		sendMail.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.contact_mail_id:
			String strMsg = "";
			strMsg += "Name : " + txtName.getText().toString() + "\n";
			txtName.setText("");
			strMsg += "Company : " + txtCompany.getText().toString() + "\n";
			txtCompany.setText("");
			strMsg += "Email Address : " + txtAddress.getText().toString() + "\n";
			txtAddress.setText("");
			strMsg += "Message:\n" + txtMsg.getText().toString();
			txtMsg.setText("");
			strMsg += "\n\n Sent from my Android";
			Intent mEmailIntent = new Intent(android.content.Intent.ACTION_SEND);
			
			mEmailIntent.setType("application/octet-stream"); 
			
			mEmailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { "trym.olsen@regnskapshuset.no" });//Global.admin_email });
			mEmailIntent.putExtra(android.content.Intent.EXTRA_CC, "");
			mEmailIntent.putExtra(android.content.Intent.EXTRA_BCC,	"");
			mEmailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,	"Report");
			mEmailIntent.putExtra(android.content.Intent.EXTRA_TEXT, strMsg);
			
			
			startActivity(Intent.createChooser(mEmailIntent, "send message.."));
			break;
		}
	}
}
