package com.kreer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import android.app.ProgressDialog;
import android.os.AsyncTask;

public class Network {

	private MainActivity ctx;
	private ProgressDialog pdia;
	
	private class NetworkTask extends AsyncTask<Void, Void, String> {
		
		protected void onPreExecute()
		{
			
			pdia = new ProgressDialog(ctx);
			pdia.setCancelable(false);
			pdia.setCanceledOnTouchOutside(false);
			pdia.setIndeterminate(true);
			pdia.setTitle("Bitte warten");
			pdia.setMessage("Daten werden geladen");
			
			pdia.show();
			
		}
		
		@Override
		protected String doInBackground(Void... params) {
			
			String result = "";
			
			NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("", "andi.rauchenbacher", "cNdT9Mpg");
			
	        try {
	        	
	        	SmbFile sFile = new SmbFile("smb://SERVER/clients/data.txt", auth);

	        	BufferedReader reader = new BufferedReader(new InputStreamReader(sFile.getInputStream()));
				
				StringBuilder sb = new StringBuilder();
				String line = null;
			
				while ((line = reader.readLine()) != null) sb.append(line + "\n");
			
				result = sb.toString();
	        	
	        } catch (SmbException e) {
	            e.printStackTrace();
	        } catch (MalformedURLException e) {
	            e.printStackTrace();
	        }catch (Exception e) {
	            e.printStackTrace();
	        }

			return result;
			
		}
		
		protected void onPostExecute(String result) {
			
			pdia.hide();
			
			if(result != null){
	    		
				ctx.getResult(result);
				
	    	}
	    	
	    }

	}
	
	public void loadData(MainActivity act){
		
		ctx = act;
		
		new NetworkTask().execute();
		
	}
	
}
