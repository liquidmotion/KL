package com.kreer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import android.app.ProgressDialog;
import android.os.AsyncTask;

public class Network {

	private MainActivity ctx;
	private ProgressDialog pdia;
	
	private class NetworkTask extends AsyncTask<Void, Void, Boolean> {
		
		protected void onPreExecute()
		{
			
			pdia = new ProgressDialog(ctx);
			pdia.setCancelable(false);
			pdia.setCanceledOnTouchOutside(false);
			pdia.setIndeterminate(true);
			pdia.setTitle("Synchronisation");
			pdia.setMessage("Daten werden geladen...");
			
			pdia.show();
			
		}
		
		@Override
		protected Boolean doInBackground(Void... params) {
			
			Boolean result = false;
			
			File localFilePath = new File(MainActivity.DATAPATH);
			localFilePath.mkdir();
			
			NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("", "tablet", "jD881G9F");
			
	        try {
	        	
	        	SmbFile dataFile = new SmbFile("smb://KREERSERVER/TabletSoftware/daten.txt", auth);
	        	
	        	downloadFile(dataFile, MainActivity.DATAPATH, "");
	        	
	        	SmbFile dataFiles = new SmbFile("smb://KREERSERVER/TabletSoftware/pdf/", auth);
	        	
	        	for(SmbFile f : dataFiles.listFiles() ){
	        		
	        		downloadFile(f, MainActivity.DATAPATH, "pdf/");
	        		
	        	}
	        	
	        	result = true;
	        	
	        } catch (SmbException e) {
	            e.printStackTrace();
	        } catch (MalformedURLException e) {
	            e.printStackTrace();
	        }catch (Exception e) {
	            e.printStackTrace();
	        }

			return result;
			
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			
			pdia.hide();
			
			if(result){
	    		
				MainActivity.data.loadData();
				
	    	}else{
	    		ctx.showMessageDialog("Fehler!", "Datentransfer Fehlgeschlagen");
	    	}
	    	
	    }
		
		protected void downloadFile(SmbFile f, String sdCardPath, String subfolder ) throws IOException{
			
			String smbFileName = f.getName();
			BufferedInputStream inputStream = new BufferedInputStream(new SmbFileInputStream(f));

    		File folder = new File(sdCardPath+ "/" + subfolder);
    		if(!folder.exists()){
    			folder.mkdir();
    		}
    		
    		File localFilePath = new File(sdCardPath+ "/" + subfolder + smbFileName);

            OutputStream out = new FileOutputStream(localFilePath);
            byte buf[] = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0) 
            {
                out.write(buf, 0, len);
            }
            out.flush();
            out.close();
            inputStream.close();
			
		}

	}
	
	public Network(MainActivity ctx){
		
		this.ctx = ctx;
		
	}
	
	public void loadData(){
		
		new NetworkTask().execute();
		
	}
	
}
