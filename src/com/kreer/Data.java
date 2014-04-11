package com.kreer;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.kreer.datatypes.Url;

public class Data {

	private String xmlData = "";
	private XmlPullParser xmlParser;
	
	public ArrayList<Url> urls = new ArrayList<Url>();
	
	public Data(){
		
		XmlPullParserFactory factory;
		
		try {
			factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			xmlParser = factory.newPullParser();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
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
		
        int eventType = xmlParser.getEventType();
        Url currentUrl = null;
        
        while (eventType != XmlPullParser.END_DOCUMENT) {
        	
        	String name = xmlParser.getName();
        	switch (eventType){
    	      	case XmlPullParser.START_TAG:
    	      		break;
    	      	case XmlPullParser.END_TAG:
    	      		if(name.equals("url")){
    	      			currentUrl = new Url();
    	      			currentUrl.name = xmlParser.getAttributeValue(null,"name");
    	      			currentUrl.url = xmlParser.getAttributeValue(null,"url");
    	      			urls.add(currentUrl);
    	      		}
    	      		break;
        	}
         
        	eventType = xmlParser.next();
         
        }
		
	}
		
}
