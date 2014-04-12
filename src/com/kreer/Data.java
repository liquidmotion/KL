package com.kreer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.kreer.datatypes.DataObject;

public class Data {

	private String xmlData = "";
	private XmlPullParser xmlParser;
	
	public ArrayList<DataObject> urls = new ArrayList<DataObject>();
	public ArrayList<DataObject> shops = new ArrayList<DataObject>();
	public ArrayList<DataObject> pdfs = new ArrayList<DataObject>();
	
	private MainActivity ctx;
	
	public Data(MainActivity ctx){
		
		this.ctx = ctx;
		
		XmlPullParserFactory factory;
		
		try {
			factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			xmlParser = factory.newPullParser();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
        
	}
	
	public void sync(){
		
		MainActivity.network.loadData();
		
	}
	
	void deleteRecursive(File fileOrDirectory) {
	    if (fileOrDirectory.isDirectory())
	        for (File child : fileOrDirectory.listFiles())
	        	deleteRecursive(child);

	    fileOrDirectory.delete();
	}
	
	public void loadData(){
		
		String result = "";
		
		try {
			
			File dataFile = new File(MainActivity.DATAPATH+"/daten.txt");
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(dataFile)));
			
			StringBuilder sb = new StringBuilder();
			String line = null;
		
			while ((line = reader.readLine()) != null) sb.append(line + "\n");
			
			reader.close();
			
			result = sb.toString();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(!result.isEmpty()) processData(result);
		else{
			
			//TODO ERROR
			
		}
		
	}
	
	public void processData(String data){
		
		xmlData = data;
		
		try {
			parseData();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void parseData() throws XmlPullParserException, IOException{
		
		xmlParser.setInput(new StringReader (xmlData));
		
		urls.clear();
		pdfs.clear();
		shops.clear();
		
        int eventType = xmlParser.getEventType();
        
        while (eventType != XmlPullParser.END_DOCUMENT) {
        	
        	String name = xmlParser.getName();
        	switch (eventType){
    	      	case XmlPullParser.START_TAG:
    	      		break;
    	      	case XmlPullParser.END_TAG:
    	      		if(name.equals("url") || name.equals("shop")){
    	      			DataObject currentData = new DataObject();
    	      			currentData.name = xmlParser.getAttributeValue(null,"name");
    	      			currentData.url = xmlParser.getAttributeValue(null,"data");
    	      			currentData.subname = currentData.url;
    	      			if(name.equals("url")) urls.add(currentData);
    	      			if(name.equals("shop")) shops.add(currentData);
    	      		}
    	      		if(name.equals("pdf")){
    	      			DataObject currentData = new DataObject();
    	      			currentData.name = xmlParser.getAttributeValue(null,"name");
    	      			currentData.pdf = xmlParser.getAttributeValue(null,"data");
    	      			currentData.subname = xmlParser.getAttributeValue(null,"subname");
    	      			pdfs.add(currentData);
    	      		}
    	      		break;
        	}
         
        	eventType = xmlParser.next();
         
        }
        
        parseDataFinished();
		
	}
	
	private void parseDataFinished(){
		
		ctx.reloadCurrentFragment();
		
	}
		
}
