package com.src.appsonite;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.src.golobal.Global;
import com.src.golobal.WebService;
import com.src.group.NavigationActivity;

public class LoginActivity extends NavigationActivity implements OnClickListener {

	boolean initFlag = false;
	DisplayMetrics metrics;
	
	EditText username;
	EditText password;
	
	Activity instance;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
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
		
		ImageView logoImg = (ImageView) findViewById(R.id.login_logoimg_id);

		RelativeLayout.LayoutParams rParam = new RelativeLayout.LayoutParams(
				metrics.widthPixels * 425 / 768,
				(metrics.heightPixels) * 105 / 1024);
		rParam.leftMargin = metrics.widthPixels * 209 / 768;
		rParam.topMargin = (metrics.heightPixels) * 100 / 1024;
		logoImg.setLayoutParams(rParam);
		
		TextView username_view = (TextView) findViewById(R.id.textview1);
		rParam = new RelativeLayout.LayoutParams(
				metrics.widthPixels * 200 / 768,
				(metrics.heightPixels) * 40 / 1024);
		rParam.leftMargin = metrics.widthPixels * 209 / 768;
		rParam.topMargin = (metrics.heightPixels) * 235 / 1024;
		username_view.setLayoutParams(rParam);
		username_view.setTextSize(textSize);
		
		username = (EditText) findViewById(R.id.login_username_id);
		rParam = new RelativeLayout.LayoutParams(
				metrics.widthPixels * 406 / 768,
				(metrics.heightPixels) * 75 / 1024);
		rParam.leftMargin = metrics.widthPixels * 209 / 768;
		rParam.topMargin = (metrics.heightPixels) * 283 / 1024;
		username.setLayoutParams(rParam);
		username.setTextSize(textSize + 4);
		//username.setText("Robin");
		
		TextView password_view = (TextView) findViewById(R.id.textview2);
		rParam = new RelativeLayout.LayoutParams(
				metrics.widthPixels * 200 / 768,
				(metrics.heightPixels) * 40 / 1024);
		rParam.leftMargin = metrics.widthPixels * 209 / 768;
		rParam.topMargin = (metrics.heightPixels) * 386 / 1024;
		password_view.setLayoutParams(rParam);
		password_view.setTextSize(textSize);
		
		password = (EditText) findViewById(R.id.login_password_id);
		rParam = new RelativeLayout.LayoutParams(
				metrics.widthPixels * 406 / 768,
				(metrics.heightPixels) * 75 / 1024);
		rParam.leftMargin = metrics.widthPixels * 209 / 768;
		rParam.topMargin = (metrics.heightPixels) * 428 / 1024;
		password.setLayoutParams(rParam);
		password.setTextSize(textSize + 4);
		//password.setText("fredag11");
		
		Button loginButton = (Button) findViewById(R.id.login_logbtn_id);
		rParam = new RelativeLayout.LayoutParams(
				metrics.widthPixels * 300 / 768,
				(metrics.heightPixels) * 105 / 1024);
		rParam.leftMargin = metrics.widthPixels * 266 / 768;
		rParam.topMargin = (metrics.heightPixels) * 555 / 1024;
		loginButton.setLayoutParams(rParam);
		loginButton.setTextSize(textSize);
		
		loginButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_logbtn_id:
			if (username.getText().toString().compareTo("") == 0 || password.getText().toString().compareTo("") == 0) {
				Toast.makeText(getApplicationContext(), "Please enter user and password", Toast.LENGTH_LONG).show();
			} else {
				LoginCheck task = new LoginCheck();
				task.execute();
			}
			break;
		}
	}
	
	private class LoginCheck extends AsyncTask<String, Void, String>{
		private ProgressDialog _mDialog = null;
		private JSONObject result = null;
		
		@Override
	    public void onPreExecute() {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			_mDialog = new ProgressDialog(instance.getParent());
			_mDialog.setMessage("Loading...");
			_mDialog.show();
	    }

		@Override
		protected String doInBackground(String...strings) {
			WebService service = new WebService("http://www.regnskapshuset.info/");
			Map<String, String> params = new HashMap<String, String>();
			params.put("user_name", username.getText().toString());
			params.put("user_password", password.getText().toString());
			
			result = service.getObjFromServer("login_service.php", params);
			if (result == null) {
				return "Please confirm the network status!";
			} else {
				try {
					if (result.getString("status").compareTo("success") == 0) {
							Global.user_id = Integer.parseInt(result.getString("user_id"));
							Global.admin_email = result.getString("admin_email_address");
					} else {
						return "Login Fail!";
					}
				} catch (JSONException e) {
					Log.v("JError: ", "login result is null");
				}
			}
			return "success";
		}
		
		@Override
		protected void onPostExecute(String result) {
			_mDialog.dismiss();
			
			if (result.compareTo("success") == 0) {
				goNextHistory("LoginActivity", new Intent(LoginActivity.this, HomeActivity.class));
			} else {
				Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
			}
		}
	}
}
