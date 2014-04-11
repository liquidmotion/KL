package com.kreer;

import java.util.ArrayList;
import java.util.List;

import com.kreer.datatypes.Url;

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
import android.widget.SimpleCursorAdapter;


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

        ArrayList<String> valueList = new ArrayList<String>();
        
        if(type == 2){
	        
	        for (Url u : MainActivity.data.urls){
	        	valueList.add(u.name);
	        }
        
        }
        
        ListAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, valueList);
        
        final ListView lv = (ListView)getActivity().findViewById(R.id.listfragment);

        lv.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				if(type == 2){

					Intent intent = new Intent(Intent.ACTION_VIEW);
			        intent.setData(Uri.parse(MainActivity.data.urls.get(position).url));
			        startActivity(intent);
					
				}
				
				
				
			}
        });
        
        lv.setAdapter(adapter);
        
    }
	
}