package com.growcn;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.growcn.R;
import com.growcn.R.id;
import com.growcn.R.layout;


import android.content.Context;
import android.media.*;
import android.net.Uri;



import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;




public class GrowcnApplication  extends Activity {

	WebView mWebView;
Context context;
	static String TAG = "News";
	MediaPlayer mp;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		context = this;

		mWebView = (WebView) findViewById(R.id.webview);
		if (null == mWebView) {
			Log.e(TAG, "mWebView is null!");
		}
		mWebView.setWebViewClient(new NewsClient());
		mWebView.getSettings().setJavaScriptEnabled(true);
		//mWebView.getSettings().setDomStorageEnabled(true);
		//mWebView.loadUrl("file:///android_asset/www/index.html");
		//mWebView.loadUrl("http://www.growcn.com/mystory/index.htm");
		//mWebView.loadUrl("http://172.18.19.231:3000/webui/index?ddddddddddddddfs");
		//mWebView.loadUrl("http://172.18.19.231:3000/zh/login");
		//mWebView.loadUrl("http://zhy.m.the9.com");
	
		//mWebView.loadUrl("file:///android_asset/wapphp/index.htm");
		mWebView.loadUrl("http://172.18.19.168:3001/query_word/query");

		Log.d(TAG, "onCreate Method done.");
//		mp =MediaPlayer.create(GrowcnApplication.this,R.raw.word);ß
		
		
		
		mWebView.addJavascriptInterface(new Object(){
        	public void the9start(){
        		String pro_path = "/growcn/";
        		String dir=Environment.getExternalStorageDirectory().getPath();
        		File f= new File(dir+pro_path);
        		f.mkdir();
//        		String urlPath="http://www.baidu.com/img/baidu_sylogo1.gif";
        		
        		String urlPath="http://mp3.dict.cn/mp3.php?q=4DTxW";
        		String name ="miss.mp3";
        		downloadFile(urlPath,dir+pro_path+name,name);
        		Log.d("cgg: "+dir+pro_path+name,name);
                try{
        		Uri load_file = Uri.parse(dir+pro_path+name);
//        		String load_file = R.raw.word;
        	  
        		MediaPlayer	mp =MediaPlayer.create(GrowcnApplication.this,load_file);
        		mp.setLooping(false);
        		mp.start();
	 		   } catch (Exception e) {
	        	Log.d("not found: "+dir+pro_path+name,name);
			   }
        	}
        }, "the9city");
		
		
		mWebView.addJavascriptInterface(new Object(){
			
        	public void replay_word(String wordurl,String name){
        		
        		String pro_path = "/growcn/";
        		
        		String dir=Environment.getExternalStorageDirectory().getPath();
        		File f= new File(dir+pro_path);
        		f.mkdir();
        		
//        		String urlPath="http://www.baidu.com/img/baidu_sylogo1.gif";
//        		String urlPath="http://mp3.dict.cn/mp3.php?q=4DTxW";
//        		String name ="miss.mp3";        		
        		
        		String urlPath= wordurl;

        		downloadFile(urlPath,dir+pro_path+name,name);
        		Log.d("cgg: "+dir+pro_path+name,name);
        		
			 try{
					Uri load_file = Uri.parse(dir+pro_path+name);        	  
					MediaPlayer	mp =MediaPlayer.create(GrowcnApplication.this,load_file);
					mp.setLooping(false);
					mp.start();
			   } catch (Exception e) {
				   Log.d("not found: "+dir+pro_path+name,name);
			   }
        	}
        }, "GN");		
		
		
		
		
		
		
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
			mWebView.goBack();
			Log.d(TAG, "onKeyDown method done.");
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private class NewsClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			Log.d(TAG, "URL: " + url);
			view.loadUrl("javascript:changeLocation(" + url + ")");
			Log.d(TAG, "Back from loading url");
			return true;
		}
	}
	
	
	
	   /**
	    * 下载
	    *@param url 下载路径
	    *@param string 创建本地保存流的文件
	    *@return
	    * @return 下载失败返回1（比如没有网络等情况）下载成功返回0
	    */	
	   public static int downloadFile(String urlPsth, String string,String filename) {
		   int result=0;
		   try {
		       URL url = new URL(urlPsth);
		       HttpURLConnection conn =(HttpURLConnection) url.openConnection();
		       conn.setDoInput(true);
		       conn.connect();
		     if(  conn.getResponseCode() == HttpURLConnection.HTTP_OK)
		     {
		         InputStream is = conn.getInputStream();
		        FileOutputStream fos = new FileOutputStream(string);
		        byte[] bt = new byte[1024];
		        int i = 0;
		        while ((i = is.read(bt)) > 0) {
		     fos.write(bt, 0, i);
		        }
		        fos.flush();
		        fos.close();
		        is.close();
		       
		     }else {
		       result=1;
		   }
		     
		   } catch (FileNotFoundException e) {
		       result=1;
		   } catch (IOException e) {
		       result=1;
		   }
		   return result;
	   }	
	

}