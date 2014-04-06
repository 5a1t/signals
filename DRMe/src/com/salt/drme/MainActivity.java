package com.salt.drme;

import java.io.File;
import java.io.IOException;

import com.example.drme.R;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.VideoView;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;





public class MainActivity extends Activity{
	
	
 
	  public static final long TIME = 2000;
	  private static int splashed = 0;
	  static final int REQUEST_IMAGE_CAPTURE = 1;
	  private String lastPath = "";


     
     
@Override
public void onCreate(Bundle savedInstanceState) {
	   
    	requestWindowFeature(Window.FEATURE_NO_TITLE); 
        super.onCreate(savedInstanceState);
        
        this.setContentView(R.layout.logo);

        if(this.getSplashed() == 0){
	    Thread welcomeThread = new Thread() {
	        @Override
	        public void run() {
	            try {
	                sleep(TIME);
	            } catch (Exception e) {
	                Log.e(getClass().getName(), e.toString());
	            } finally {
	            	MainActivity.setSplashed(1);
	                startActivity(new Intent(MainActivity.this,MainActivity.class));
	                finish();
	            }
	        }
	    };
	    welcomeThread.start();
        }
        else{
        	setMainScreen();
        }
}



public void setMainScreen(){
	
    setContentView(R.layout.activity_main);
    
    Button button = (Button) findViewById(R.id.watch_button);
    button.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			//showImage("");
			takePhoto();
		}
	});	
}

public void showImage(String src){
	setContentView(R.layout.video_view); 
    VideoView video = (VideoView)findViewById(R.id.videoview);
    //video.setVideoPath("http://techslides.com/demos/sample-videos/small.mp4");
    video.setVideoPath("http://f8f8ff.com/drme/out.mp4");
    video.setOnPreparedListener(new OnPreparedListener() {
		
		@Override
		public void onPrepared(MediaPlayer mp) {
			mp.setLooping(true);
	
		}
	});

    video.start();
}

private File createImageFile() throws IOException {
    // Create an image file name
    String imageFileName = "temp";
    File storageDir = getExternalFilesDir(
            Environment.DIRECTORY_PICTURES);
    File image = File.createTempFile(
        imageFileName,  /* prefix */
        ".jpg",         /* suffix */
        storageDir      /* directory */
    );

    // Save a file: path for use with ACTION_VIEW intents
    lastPath = image.getAbsolutePath();
    return image;
}


public void onActivityResult(int i, int j, Intent k){
	ImageUploadUtility upload = new ImageUploadUtility();
	upload.submit(lastPath);
	 Log.i("drme", "Sumbitted");
	//upload.uploadFile(lastPath);
}

public void takePhoto(){
	Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
        }
        // Continue only if the File was successfully created
        if (photoFile != null) {
        	
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(photoFile));
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            Log.i("drme", lastPath);
        }
    }
	

	
}




public static int getSplashed() {
	return splashed;
}

public static void setSplashed(int newValue) {
	splashed = newValue;
}
}