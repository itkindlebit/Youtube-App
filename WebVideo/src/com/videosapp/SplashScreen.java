package com.videosapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class SplashScreen extends Activity {

	int splashTime = 3000;
	Boolean isInternetPresent = false;
	Refrence ref;
	Thread splashTread;

	@SuppressWarnings("static-access")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		WindowManager.LayoutParams.FLAG_FULLSCREEN); 
		setContentView(R.layout.splash);
	//	ref = new Refrence(getApplicationContext());
	//	isInternetPresent = ref.isConnectingToInternet(); 
//		 if (isInternetPresent) {			 
//             splashTread = new Thread() {
//     			@Override
//     			public void run() {
//     				try {
//     					synchronized (this) {
//     						wait(splashTime);
//     					}
//     				} catch (InterruptedException e) {
//     					e.printStackTrace();
//     				} finally {   					
//     						Intent i = new Intent(SplashScreen.this,Main.class);
//     						startActivity(i);
//     						finish();
//     				}
//     			}
//     		};
//     		splashTread.start();
//         } 
		 //else {
             //showAlertDialog(SplashScreen.this, "No Internet Connection","You don't have internet connection.", false);                        
        // }
		
		
		Intent i = new Intent(SplashScreen.this,Main.class);
			startActivity(i);
			finish();
	}
	
	 @SuppressWarnings("deprecation")
	public void showAlertDialog(Context context, String title, String message, Boolean status) {
	        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
	        alertDialog.setTitle(title);
	        alertDialog.setMessage(message);
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {
	            	SplashScreen.this.finish();
	            }
	        });
	        alertDialog.show();
	    }
}