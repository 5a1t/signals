package com.salt.drme;

import com.example.drme.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class LaunchScreen extends Activity {

	  public static final long TIME = 3000;

	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    this.setContentView(R.layout.logo);

	    Thread welcomeThread = new Thread() {
	        @Override
	        public void run() {
	            try {
	                sleep(TIME);
	            } catch (Exception e) {
	                Log.e(getClass().getName(), e.toString());
	            } finally {
	                startActivity(new Intent(LaunchScreen.this,MainScreen.class));
	                finish();
	            }
	        }
	    };
	    welcomeThread.start();
	  }
	}