package com.salt.drme;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
class ImageUploadUtility {

	
	

class SubmitImageTask extends AsyncTask<String, Void, Void> {
	
	   private Exception exception;

	    protected Void doInBackground(String... urls) {
	        try {
	        	uploadFileNew(urls[0]);
	        	return null;
	            
	        } catch (Exception e) {
	            this.exception = e;
	            return null;
	        }
	    }

	    protected void onPostExecute(Void Void) {

	    }
	    
}


public void submit(String s){
	new ImageUploadUtility.SubmitImageTask().execute(s);
}


private int uploadFileNew(String source){
	
    HttpClient httpclient = new DefaultHttpClient();
    httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

    HttpPost httppost = new HttpPost("http://10.1.16.16:8080/upload");
    File file = new File(source);
    

    HttpEntity httpEntity = MultipartEntityBuilder.create()
    .addBinaryBody("file", file, ContentType.create("image/jpeg"), file.getName())
    .build();

    //MultipartEntity mpEntity = new MultipartEntity();
    //ContentBody cbFile = new FileBody(file, "image/jpeg");
    //httpEntity.addPart("userfile", cbFile);

try{
    httppost.setEntity(httpEntity);
    System.out.println("executing request " + httppost.getRequestLine());
    HttpResponse response = httpclient.execute(httppost);
    HttpEntity resEntity = response.getEntity();

   // Log.i("drme", response.getStatusLine());
    if (resEntity != null) {
      Log.e("drme", EntityUtils.toString(resEntity));
    }
    if (resEntity != null) {
      resEntity.consumeContent();
    }

    httpclient.getConnectionManager().shutdown();
	
}
catch(Exception e){
	
	Log.e("drme", e.getMessage() + e.toString());
	
	
}

return 0;
}
	
private int uploadFile(String sourceFileUri) {
		int serverResponseCode = 0;
        String upLoadServerUri = "http://10.1.16.16:5555/upload";
        String fileName = sourceFileUri;

        HttpURLConnection conn = null;
        DataOutputStream dos = null; 
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);
        if (!sourceFile.isFile()) {
         Log.e("drme", "Source File Does not exist");
         return 0;
        }
            try { 
             FileInputStream fileInputStream = new FileInputStream(sourceFile);
             URL url = new URL(upLoadServerUri);
             conn = (HttpURLConnection) url.openConnection(); // Open a HTTP  connection to  the URL
             conn.setDoInput(true); // Allow Inputs
             conn.setDoOutput(true); // Allow Outputs
             conn.setUseCaches(false); // Don't use a Cached Copy
             conn.setRequestMethod("POST");
             conn.setRequestProperty("Connection", "Keep-Alive");
             conn.setRequestProperty("ENCTYPE", "multipart/form-data");
             conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
             conn.setRequestProperty("uploaded_file", fileName);
             dos = new DataOutputStream(conn.getOutputStream());
   
             dos.writeBytes(twoHyphens + boundary + lineEnd);
             dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""+ fileName + "\"" + lineEnd);
             dos.writeBytes(lineEnd);
   
             bytesAvailable = fileInputStream.available(); // create a buffer of  maximum size
             Log.i("drme", Integer.toString(bytesAvailable));
   
             bufferSize = Math.min(bytesAvailable, maxBufferSize);
             buffer = new byte[bufferSize];
   
             // read file and write it into form...
             bytesRead = fileInputStream.read(buffer, 0, bufferSize); 
               
             while (bytesRead > 0) {
               dos.write(buffer, 0, bufferSize);
               bytesAvailable = fileInputStream.available();
               bufferSize = Math.min(bytesAvailable, maxBufferSize);
               bytesRead = fileInputStream.read(buffer, 0, bufferSize);              
              }
   
             // send multipart form data after file data...
             dos.writeBytes(lineEnd);
             dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
   
             // Responses from the server (code and message)
             serverResponseCode = conn.getResponseCode();
             String serverResponseMessage = conn.getResponseMessage();
              
             Log.i("drme", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode );
             if(serverResponseCode == 200){
              
             }   
             
             //close the streams //
             fileInputStream.close();
             dos.flush();
             dos.close();
              
        } catch (MalformedURLException ex) { 
            ex.printStackTrace();
            Log.e("drme", "error: " + ex.getMessage(), ex); 
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("drme", "Exception : " + e.getMessage(), e); 
        }
        return serverResponseCode; 
       } 
}