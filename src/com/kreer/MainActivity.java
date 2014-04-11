package com.kreer;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

    private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
    
    public static Data data;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        	
        data = new Data();
        
        Network nw = new Network();
        nw.loadData(this);
        
        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // For each of the sections in the app, add a tab to the action bar.
        actionBar.addTab(actionBar.newTab().setText(R.string.title_section1).setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(R.string.title_section2).setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(R.string.title_section3).setTabListener(this));
    }

    public void getResult(String res){
    	
    	res = "<data><url name='Test' url='http://www.kreer.at' /></data>";
    	
    	data.processData(res);
    	
    }
    
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
            getActionBar().setSelectedNavigationItem(savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar().getSelectedNavigationIndex());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        
    	DataFragment fragment = new DataFragment();
    	fragment.type = tab.getPosition();
        Bundle args = new Bundle();
        args.putInt(DataFragment.ARG_SECTION_NUMBER, tab.getPosition() + 1);
        fragment.setArguments(args);
        getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    
}
