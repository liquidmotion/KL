package com.kreer;

import java.io.File;
import java.util.ArrayList;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.kreer.datatypes.DataObject;


public class DataFragment extends Fragment {

    public static final String ARG_SECTION_NUMBER = "section_number";
    
    public int type = 0;
    
    public DataFragment(){
    	
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
    	return inflater.inflate(R.layout.listviewfragment, container, false);
    	
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final ArrayList<DataObject> valueList = new ArrayList<DataObject>();
        
        ArrayList<DataObject> currentData = null;
        
        if(type == 0) currentData = MainActivity.data.pdfs;
        if(type == 1) currentData = MainActivity.data.shops;
        if(type == 2) currentData = MainActivity.data.urls;
         
        if(currentData == null) return;
        
        for (DataObject u : currentData)
        	valueList.add(u);
        
        ListAdapter adapter = new ArrayAdapter<DataObject>(getActivity(), android.R.layout.simple_list_item_2, android.R.id.text1, valueList)
        {
        	
			public View getView(int position, View convertView, ViewGroup parent) {
			    View view = super.getView(position, convertView, parent);
			    TextView text1 = (TextView) view.findViewById(android.R.id.text1);
			    TextView text2 = (TextView) view.findViewById(android.R.id.text2);
			    text1.setText(valueList.get(position).name);
			    text2.setText(valueList.get(position).subname);
			    return view;
			}
			
        };
        
        final ListView lv = (ListView)getActivity().findViewById(R.id.listfragment);
        
        lv.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				if(type == 1 || type == 2){

					Intent intent = new Intent(Intent.ACTION_VIEW);
			        intent.setData(Uri.parse(MainActivity.data.urls.get(position).url));
			        startActivity(intent);
					
				}
				
				if(type == 0){
					
					File file = new File(MainActivity.DATAPATH+"/pdf/"+MainActivity.data.pdfs.get(position).pdf);
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setDataAndType(Uri.fromFile(file), "application/pdf");
					intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
					startActivity(intent);
					
				}
				
			}
        });
        
        lv.setAdapter(adapter);
        
    }
	
}