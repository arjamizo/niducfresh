package net.netii.niducproject;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

public class DataSenderThread extends Thread implements Runnable {
	private HttpClient httpclient;
	private HttpPost httppost;
	private List<NameValuePair> nameValuePairs;
	private final String[] keys;
	private final String[] data;     
	public boolean sent=false;
	public boolean finished=true;
	private final DBHelper db;
	
	
	public DataSenderThread(String []keys, String []data, DBHelper db){
		this.keys = keys;
		this.data = data;
		this.db = db;
		start();
	}
	
	@Override
	public void run() {		
        httpclient = new DefaultHttpClient();
    	
        
        httppost = new HttpPost("http://arzoxadi.tk/niduc/bazadanych.php");
        //wymagane w AndroindManifest.xml <uses-permission android:name="android.permission.INTERNET"></uses-permission> 

        nameValuePairs = new ArrayList<NameValuePair>(2);
        
       // String string="";
        for(int i=0; i<data.length; i++)
        {
        	//string+=keys[i];
        	//string+="=";
        	//string+=data[i];
        	//string+=";";
        	nameValuePairs.add(new BasicNameValuePair(keys[i],data[i]));
        }
        //Log.i("http", string);
        
		try {
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			//Log.i("http", httppost.)
			HttpResponse execute = httpclient.execute(httppost);
			Log.i("http",execute.getEntity().getContent().toString());
			if(execute.getStatusLine().getStatusCode()==200)
			{
				Log.i("http", "wyslano pomyslnie ".concat(httppost.toString()));
			}
			else 
			{
				Log.i("http", "blad przy wysylaniu");
			}
		}catch(Exception e){
        	Log.i("http", e.getMessage());
		}
	}

}

