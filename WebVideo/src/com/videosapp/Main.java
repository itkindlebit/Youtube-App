package com.videosapp;

import java.io.InputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

import com.testflightapp.lib.TestFlight;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.RenderPriority;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main extends Activity {	

	WebView         mWebView ;  
	WebChromeClient mWebChromeClient ;
	Button          playvideo ;
	EditText        start_time, End_time, video_et ;
	String          starttime, endtime, video_id, result,str ;   
	ProgressDialog  dialog ;
	static char     first;
	static String   manufacturer,model;
	public static final String USER_MOBILE = "0";
	public static final String USER_DESKTOP = "1";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video_activity);	    
		mWebView    = (WebView ) findViewById(R.id.webview);
		playvideo   = (Button  ) findViewById(R.id.videoPlay_BT);
		video_et    = (EditText) findViewById(R.id.videoID_ET);
		start_time  = (EditText) findViewById(R.id.videoStartTime_ET); 
		End_time    = (EditText) findViewById(R.id.videoEndTime_ET); 
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		//TestFlight.takeOff(this, "e9d76d7d5cbe8efb045f59216e35a656_MzI0MDg3MjAxNC0wMS0xNCAwMTo1OTo0NS4xOTY2OTg");
		getDeviceName();

		playvideo.setOnClickListener(new View.OnClickListener() {			
			@SuppressLint("ShowToast") @Override 
			public void onClick(View v) {	 
				video_id  = video_et.getText().toString();
				starttime = start_time.getText().toString();
				endtime   = End_time.getText().toString(); 
				if(!video_id.equals("")&&!starttime.equals("")&&!endtime.equals("")){
					//new VideoTime().execute();
					mWebView.setWebChromeClient(new WebChromeClient() {
					});
					initwebview();
				}
				else{
					Toast.makeText(getApplicationContext(), "All fields are mandatory to be filled", 8000).show();
				}
			}
		});

		mWebChromeClient = new WebChromeClient() {

			@Override
			public void onShowCustomView(View view, WebChromeClient.CustomViewCallback callback)
			{
				callback.onCustomViewHidden();
				return;
			}
		};
	}

	private class HelloWebViewClient extends WebViewClient  {

		@Override
		public boolean shouldOverrideUrlLoading(WebView webview, String url)
		{
			webview.setWebChromeClient(mWebChromeClient);   
			webview.loadUrl(url);
			return true;
		}
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack())
		{
			mWebView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event); 

	}   
	@SuppressLint("SetJavaScriptEnabled") private void initwebview(){
		//String url2="http://web1.kindlebit.com/php-team/dadmomapp/test/videotest.php?videoid="+video_id+"&st="+starttime+"&en="+endtime+"&auto=1&ctrl=1";
		final String mimeType = "text/html";
		final String encoding = "UTF-8";
		
		String url2 =
				"<iframe class=\"youtube-player\" "
						+ "style=\"border: 0; width: 100%; height: 95%;"
						+ "padding:0px; margin:0px\" "
						+ "id=\"ytplayer\" type=\"text/html\" "
						+ "src=\"http://www.youtube.com/embed/" + video_id +"?autoplay=1&version=5&type=video/mp4&start="+starttime+"&end="+endtime+"&yt:format=5"
						+ " frameborder=\"0\" " + "allowfullscreen " + "</iframe>";
		WebSettings webSettings = mWebView.getSettings();
		webSettings.setPluginState(WebSettings.PluginState.ON);
		webSettings.setJavaScriptEnabled(true);
		mWebView.getSettings().setRenderPriority(RenderPriority.HIGH);
		mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		str = mWebView.getSettings().getUserAgentString();
		Log.i("UserAgent "," user " +str);
		String userAgent = new WebView(this).getSettings().getUserAgentString();
		String ua = "Mozilla/5.0 (X11; Linux i686) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.63 Safari/537.31";
		//String au = "Mozilla/5.0 (X11; Linux i686) AppleWebKit/535.2 (KHTML, like Gecko) Ubuntu/11.10 Chrome/15.0.874.121 Safari/535.2";
		//webSettings.setWebContentsDebuggingEnabled(true);
		
		
		if(manufacturer.equalsIgnoreCase("HTC")){
			mWebView.getSettings().setUserAgentString(ua);
			Log.v("", "HTC");
		}
		
		if(manufacturer.equalsIgnoreCase("Lava")){
		//webSettings.setJavaScriptEnabled(true);
		mWebView.getSettings().setUserAgentString(userAgent);
		//("Mozilla/5.0 (Windows ; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0");
		//("Mozilla/5.0 (Linux; U; Android 2.3.5; en-in; iris 401e Build/LAVA-iris401e-S111) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1");
		//("Mozilla/5.0  (X11; Linux x86_64) AppleWebKit/537.71 (KHTML, like Gecko) Version/7.0 Safari/537.71");
		//("Opera/9.80 (X11; Linux x86_64; U; in) Presto/2.9.168 Version/11.50");
		Log.v("", "Lava");
		}
		
		if(manufacturer.equalsIgnoreCase("Samsung")){
			mWebView.getSettings().setUserAgentString(USER_MOBILE);
			//("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/534.24 (KHTML, like Gecko) Chrome/11.0.696.34 Safari/534.24");
			//("Mozilla/5.0 (Linux; Android 4.0.4; GT-S7562 Build/IMM761.S7562XXBMJ1) Chrome/32.0.1700.9 Mobile");
			//GT-S7562
			//("Mozilla/5.0 (Linux; U; Android 4.2; sl-si; GT-I9500 Build/JDQ39) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30");
			//("Mozilla/5.0 (Windows; U; Windows NT 5.2; en-GB; rv:1.8.1.18) Gecko/20081029 Firefox/2.0.0.18");			
			//("Mozilla/5.0 (Linux; Android 4.2.2; nl-nl; SAMSUNG GT-I9505 Build/JDQ39) AppleWebKit/535.19 (KHTML, like Gecko) Version/1.0 Chrome/18.0.1025.308 Mobile Safari/535.19");
			Log.v("", "Samsung");
		}
		
		if(manufacturer.equalsIgnoreCase("Sony")){
			//("Mozilla/5.0 (Linux; U; Android 4.0.4; pl-pl; Sony Xperia Neo V Build/IMM76D) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30");
			mWebView.getSettings().setUserAgentString("Mozilla/5.0 (Linux; U; Android 4.0.4; pl-pl; Sony Xperia Neo V Build/IMM76D) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30");
			Log.v("", "Sony");
		}
		
		//mWebView.loadUrl(url2);
		mWebView.loadDataWithBaseURL("", url2, mimeType, encoding, "");
		mWebView.setWebViewClient(new HelloWebViewClient()); 
	}
//		@Override
//		protected void onPause() {
//		    super.onPause();
//		    ((AudioManager)getSystemService(
//		            Context.AUDIO_SERVICE)).requestAudioFocus(
//		                    new OnAudioFocusChangeListener() {
//		                        @Override
//		                        public void onAudioFocusChange(int focusChange) {}
//		                    }, AudioManager.STREAM_MUSIC, 
//		                    AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
//		}

	private class VideoTime extends AsyncTask<Void, Void, Boolean> { 
		protected void onPreExecute() {
			dialog = ProgressDialog.show(Main.this, "","Loading, please wait...", true); 
		}
		@SuppressLint("SetJavaScriptEnabled") protected Boolean doInBackground(Void... unused) {
			InputStream     instream;
			HttpClient      httpclient;
			HttpPost        httppost;
			HttpResponse    response;
			HttpEntity      entity;
			try {
				httpclient = new DefaultHttpClient();
				httppost = new HttpPost("http://web1.kindlebit.com/php-team/dadmomapp/test/getvideoSeconds.php?id="+ video_id ); 
				MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);	 			
				reqEntity.addPart("starttime", new StringBody(starttime));
				reqEntity.addPart("endtime", new StringBody(endtime));				
				httppost.setEntity(reqEntity);
				response = httpclient.execute(httppost);
				entity   = response.getEntity();
				if (entity != null) {
					instream = entity.getContent();
					result   = Refrence.convertStreamToString(instream);
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(Boolean unused) {
			super.onPostExecute(unused);			
			initwebview();
			//webviewfunc();
			dialog.dismiss();
		}
	}
	public static String getDeviceName() {
		manufacturer = Build.MANUFACTURER;
		model = Build.MODEL;
		Log.v("","manufacturer :"+ manufacturer +": model :"+model);
		if (model.startsWith(manufacturer)) {
			return capitalize(model);
		} else {
			return capitalize(manufacturer) + " " + model; 
		}
	}


	private static String capitalize(String s) {
		if (s == null || s.length() == 0) {
			return "";
		}
		first = s.charAt(0);
		Log.v("","first :"+first);
		if (Character.isUpperCase(first)) {
			return s;
		} else {
			return Character.toUpperCase(first) + s.substring(1); 
		}
	}
	private void webviewfunc(){
		mWebView.setKeepScreenOn(true);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setDomStorageEnabled(true);
		mWebView.getSettings().setBuiltInZoomControls(true);
		mWebView.setInitialScale(100);
		mWebView.getSettings().setUseWideViewPort(true);
		mWebView.setWebViewClient(new MyWebViewClient());
		mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		mWebView.loadUrl("http://web1.kindlebit.com/php-team/dadmomapp/test/videotest.php?videoid="+video_id+"&st="+starttime+"&en="+endtime+"&auto=1&ctrl=1");
	}
	private class MyWebViewClient extends WebViewClient {
		@Override
		  public boolean shouldOverrideUrlLoading(WebView view, String url) {
		   view.loadUrl(url);
		   return true;
		  }
		  
		 }
}