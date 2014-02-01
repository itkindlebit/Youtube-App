package com.videosapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Refrence {
	
	 private static Context con;
     
	    @SuppressWarnings("static-access")
		public Refrence(Context context){
	        this.con = context;
	    }
	    
	public static boolean isConnectingToInternet() {
		ConnectivityManager connec = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
		android.net.NetworkInfo wifi = connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		android.net.NetworkInfo mobile = connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		android.net.NetworkInfo mobileWiMAx = connec.getNetworkInfo(ConnectivityManager.TYPE_WIMAX);

		if (connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED
				|| connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED) {
			return true;
		} else if (wifi != null && wifi.isConnected() && wifi.isAvailable()) {
			return wifi.isConnectedOrConnecting();
		} else if (mobile != null && mobile.isConnected()
				&& mobile.isAvailable()) {
			return mobile.isConnectedOrConnecting();
		} else if (mobileWiMAx != null && mobileWiMAx.isAvailable()
				&& mobileWiMAx.isConnected()) {
			return mobileWiMAx.isConnectedOrConnecting();
		} 
		return false;
	}

	public static void showstartup(String msg, Context context) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Status");
		builder.setMessage(msg).setCancelable(false)
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(final DialogInterface dialog,
							final int id) {
						dialog.cancel();
					}
				});
		final AlertDialog alert = builder.create();
		alert.show();
	}

	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}
