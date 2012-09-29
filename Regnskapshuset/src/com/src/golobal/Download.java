package com.src.golobal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import com.artifex.mupdf.MuPDFActivity;

public class Download extends AsyncTask<String, Integer, String> {
	private Activity curActivity;
	private Activity parentActivity;
	private String curURL;
	private ProgressDialog m_dialog = null;

	public Download(Activity cur, Activity parent, String url) {
		curActivity = cur;
		parentActivity = parent;
		curURL = url;
		m_dialog = new ProgressDialog(parentActivity);
		m_dialog.setMessage("Downloading...");
		m_dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		m_dialog.setMax(100);
	}

	@Override
	public void onPreExecute() {
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (m_dialog != null)
			m_dialog.dismiss();

		m_dialog.show();
	}

	@Override
	protected String doInBackground(String... strings) {
		try {
			int fileLen[] = new int[1];
			InputStream in = getHttpStream(curURL, fileLen);
			
			if (in == null) return null;

			// File file = new File(strPath);
			String sdPath = Environment.getExternalStorageDirectory()
					.getAbsolutePath();
			File dir = new File(sdPath, "appsonite");
			if (!dir.exists())
				dir.mkdir();

			String filename = curURL.substring(curURL.lastIndexOf('/') + 1);
			filename = "temp";
			File downdir = new File(dir.getPath(), "PDF");
			if (!downdir.exists())
				downdir.mkdir();

			File file = new File(downdir, filename + ".pdf");
			if (file.exists()) {
				file.delete();
				// return null;
			}

			OutputStream os;
			try {
				os = new FileOutputStream(file);
				long total = 0;

				try {
					int c = 0;
					byte[] buffer = new byte[1024];
					while ((c = in.read(buffer)) != -1) {
						total += c;
						publishProgress((int) (total * 100 / fileLen[0]));
						os.write(buffer, 0, c);
					}

					os.flush();
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "success";
	}
	
	protected void onProgressUpdate(Integer... progress) { 
        super.onProgressUpdate(progress); 
        m_dialog.setProgress(progress[0]); 
    } 
	
	@Override
	protected void onPostExecute(String result) {
		if (m_dialog != null && m_dialog.isShowing())
			m_dialog.dismiss();
		
		if (result == null) {
			Toast.makeText(curActivity, "Rapport ikke funnet. Prøv annen periode.", Toast.LENGTH_SHORT).show();
		} else {
			readMagazine();
		}
	}
	
	public InputStream getHttpStream(String urlString, int[] len) throws IOException {
		InputStream in = null;        
		int response = -1;      
		URL url = new URL(urlString);      
		URLConnection conn = url.openConnection();     
		if (!(conn instanceof HttpURLConnection))      
			throw new IOException("Not an HTTP connection");         
		
		try{            
			HttpURLConnection httpConn = (HttpURLConnection) conn;     
			httpConn.setAllowUserInteraction(false);       
			httpConn.setInstanceFollowRedirects(true);       
			httpConn.setRequestMethod("GET");        
			httpConn.connect();            
			response = httpConn.getResponseCode();        
			if (response == HttpURLConnection.HTTP_OK) {
				len[0] = httpConn.getContentLength();
				in = httpConn.getInputStream(); 
			}       
		} catch (Exception e) {    
			throw new IOException("Error connecting");        
		} // end try-catch        
		return in;    
	}
	
	private void readMagazine() {
		String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/appsonite/PDF/";

		String filename = "temp";
		sdPath += filename + ".pdf";
		
		File file = new File(sdPath);
		if (file.exists()) {
			Uri uri = Uri.parse(file.getAbsolutePath());
			Intent intent = new Intent(curActivity, MuPDFActivity.class);
			intent.setAction(Intent.ACTION_VIEW);
			intent.setData(uri);
			curActivity.startActivity(intent);
		} else {
			Toast.makeText(curActivity, "Rapport ikke funnet. Prøv annen periode.", Toast.LENGTH_LONG).show();
		}
	}
}
