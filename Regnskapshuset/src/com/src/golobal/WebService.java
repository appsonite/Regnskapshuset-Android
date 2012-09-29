package com.src.golobal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class WebService {
	DefaultHttpClient httpClient;
	HttpContext localContext;
	
	HttpResponse response = null;
	HttpPost httpPost = null;
	HttpGet httpGet = null;
	String webServiceUrl;     
	
	//The serviceName should be the name of the Service you are going to be using.    
	public WebService(String serviceName) {
		HttpParams myParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(myParams, 10000);        
		HttpConnectionParams.setSoTimeout(myParams, 10000);        
		
		httpClient = new DefaultHttpClient(myParams);
		localContext = new BasicHttpContext();        
		webServiceUrl = serviceName;     
	}   
	
	public JSONObject getInfoFromPost(List<NameValuePair> nameValuePairs) {
		httpPost = new HttpPost(webServiceUrl + "delEventPart.php");//webServiceUrl);    
		try {       
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));        
			// Execute HTTP Post Request       
			response = httpClient.execute(httpPost);            
		} catch (ClientProtocolException e) {        
		} catch (IOException e) {        
		}
		
		JSONObject ret = null;
		// we assume that the response body contains the error message       
		try {            
			HttpEntity entity = response.getEntity();
			InputStream is = entity.getContent();
			try{ 
	            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8); 
	            StringBuilder sb = new StringBuilder(); 
	            String line = null; 
	 
	            while ((line = reader.readLine()) != null) { 
	                    sb.append(line + "\n"); 
	            }
	                        
	            String result = sb.toString();
	            ret = new JSONObject(result);
	            reader.close();
			} catch(Exception e) {
				Log.e("JARRAY", e.toString());
			}
			is.close();
		} catch (IOException e) {           
			Log.e("Groshie:", e.getMessage());        
		}
		
		return ret;
	}
	
	public JSONArray getArrayFromPost(List<NameValuePair> nameValuePairs) {
		httpPost = new HttpPost(webServiceUrl);    
		try {       
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));        
			// Execute HTTP Post Request       
			response = httpClient.execute(httpPost);            
		} catch (ClientProtocolException e) {        
		} catch (IOException e) {        
		}
		
		JSONArray ret = null;
		
		// we assume that the response body contains the error message       
		try {
			HttpEntity entity = response.getEntity();
			InputStream is = entity.getContent();
			try{
	            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8); 
	            StringBuilder sb = new StringBuilder(); 
	            String line = null; 
	 
	            while ((line = reader.readLine()) != null) {
	            	sb.append(line + "\n"); 
	            }
	                        
	            String result=sb.toString();
	            if (result.compareTo("[]") == 0) {
	            	return null;
	            }
	            ret = new JSONArray(result);
	            reader.close();
			} catch(Exception e) {
				Log.e("JARRAY", e.toString());
			}
			is.close();
		} catch (Exception e) {
			
		}
		return ret;
	}
	
	public JSONObject getObjFromServer(String methodName, Map<String, String> params) {
		String getUrl = webServiceUrl + methodName;         
		int i = 0;        
		
		for (Map.Entry<String, String> param : params.entrySet()) {            
			if(i == 0){                
				getUrl += "?";            
			}            
			else{                
				getUrl += "&";            
			}             
			try {                
				getUrl += param.getKey() + "=" + URLEncoder.encode(param.getValue(),"UTF-8");            
			} catch (UnsupportedEncodingException e) { 
				// TODO Auto-generated catch block                
				e.printStackTrace();            
			}             
			i++;      
		}         
		httpGet = new HttpGet(getUrl);        
		try {            
			response = httpClient.execute(httpGet);        
		} catch (Exception e) {       
			Log.e("Groshie:", e.getMessage());
			return null;
		}       
		
		JSONObject ret = null;
		
		// we assume that the response body contains the error message       
		try {            
			HttpEntity entity = response.getEntity();
			InputStream is = entity.getContent();
			try{ 
	            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8); 
	            StringBuilder sb = new StringBuilder(); 
	            String line = null; 
	 
	            while ((line = reader.readLine()) != null) { 
	                    sb.append(line + "\n"); 
	            }
	                        
	            String result=sb.toString();
	            ret = new JSONObject(result);
	            reader.close();
			} catch(Exception e) {
				Log.e("JARRAY", e.toString());
			}
			is.close();
		} catch (IOException e) {           
			Log.e("Groshie:", e.getMessage());        
		}         
		return ret;
	}
	
	public Vector<JSONObject> getInfoFromServer(String methodName, Map<String, String> params) {
		JSONArray jArray = webGet(methodName, params);
		
		Vector<JSONObject> ret = null; 
		if (jArray == null || jArray.length() == 0) {
			return null;
		} else {
			ret = new Vector<JSONObject>();
			int nCount = jArray.length();
			for (int i=0; i<nCount; i++) {
				JSONObject json_data = null;
				try {
					json_data = jArray.getJSONObject(i);
					
					ret.add(json_data);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return ret;
	}
	
	//Use this method to do a HttpGet/WebGet on the web service    
	public JSONArray webGet(String methodName, Map<String, String> params) {
		String getUrl = webServiceUrl + methodName;         
		int i = 0;        
		
		for (Map.Entry<String, String> param : params.entrySet()) {            
			if(i == 0){                
				getUrl += "?";            
			}            
			else{                
				getUrl += "&";            
			}             
			try {                
				getUrl += param.getKey() + "=" + URLEncoder.encode(param.getValue(),"UTF-8");            
			} catch (UnsupportedEncodingException e) { 
				// TODO Auto-generated catch block                
				e.printStackTrace();            
			}             
			i++;      
		}         
		httpGet = new HttpGet(getUrl);        
		try {            
			response = httpClient.execute(httpGet);        
		} catch (Exception e) {           
			Log.e("Groshie:", e.getMessage());     
			return null;
		}       
		
		JSONArray jArray = null;
		
		// we assume that the response body contains the error message       
		try {            
			HttpEntity entity = response.getEntity();
			InputStream is = entity.getContent();
			try{ 
	            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8); 
	            StringBuilder sb = new StringBuilder(); 
	            String line = null; 
	 
	            while ((line = reader.readLine()) != null) { 
	                    sb.append(line + "\n"); 
	            }
	                        
	            String result=sb.toString();
	            jArray = new JSONArray(result);
	            reader.close();
			} catch(Exception e) {
				Log.e("JARRAY", e.toString());
			}
			is.close();
		} catch (IOException e) {           
			Log.e("Groshie:", e.getMessage());        
		}         
		return jArray;
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
	
	public void clearCookies() {  
		httpClient.getCookieStore().clear();    
	}    
	
	public void abort() {      
		try {           
			if (httpClient != null) {        
				System.out.println("Abort.");     
				httpPost.abort();         
			}      
		} catch (Exception e) {     
			System.out.println("Your App Name Here" + e);     
		}
	}
}

