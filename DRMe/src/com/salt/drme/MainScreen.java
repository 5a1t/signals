package com.salt.drme;

import com.example.drme.R;

import android.animation.Animator;
import android.app.Activity;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.VideoView;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

public class MainScreen extends Activity{
 
    MediaPlayer mediaPlayer;
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    boolean pausing = false;
    
     
     
@Override
public void onCreate(Bundle savedInstanceState) {
	   
    	requestWindowFeature(Window.FEATURE_NO_TITLE); 
        super.onCreate(savedInstanceState);
        //setMainScreen();

     }




     
public void playVideo(String path, String fileName, boolean autoplay){
	    getWindow().setFormat(PixelFormat.TRANSLUCENT);
	    VideoView videoHolder = new VideoView(this);
	    videoHolder.setMediaController(null);
	    //videoHolder.setVideoURI(Uri.parse("android.resource://com.salt.drme/" + R.raw.test));
	    videoHolder.requestFocus();
	    if(autoplay){
	        videoHolder.start();
	    }
	 
	 }

}
