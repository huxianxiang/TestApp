package com.gozone.testapp;

import android.os.Bundle;
import android.app.Activity;
import java.util.ArrayList;
import java.util.List;
import android.view.ViewGroup;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ListView;
import android.content.Context;

public class GroupListActivity extends Activity {  
       
    private GroupListAdapter adapter = null;  
    private ListView listView = null;  
    private List<String> list = new ArrayList<String>();  
    private List<String> listTag = new ArrayList<String>();  
       
    @Override 
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.group_list_activity);  
           
        setData();  
        adapter = new GroupListAdapter(this, list, listTag);  
        listView = (ListView)findViewById(R.id.group_list);  
        listView.setAdapter(adapter);  
    }  
   
    public void setData(){
        list.add("N");
        listTag.add("N");
        list.add("Notification Test");
        list.add("NavigationBar Hide");
        list.add("NavigationBar Show");
        list.add("R");
        listTag.add("R");
        list.add("Root Request");
        list.add("O");
        listTag.add("O");
        list.add("OpenGL ES Test");
    }
    
    
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        java.lang.System.out.println("[huxx] :: position = " + position + ", id = " + id);
    }
    
    private static class GroupListAdapter extends ArrayAdapter<String>{  
           
        private List<String> listTag = null;  
        public GroupListAdapter(Context context, List<String> objects, List<String> tags) {  
            super(context, 0, objects);  
            this.listTag = tags;  
        }  
           
        @Override 
        public boolean isEnabled(int position) {  
            if(listTag.contains(getItem(position))){  
                return false;  
            }  
            return super.isEnabled(position);  
        }  
        @Override 
        public View getView(int position, View convertView, ViewGroup parent) {  
            View view = convertView;  
            if(listTag.contains(getItem(position))){  
                view = LayoutInflater.from(getContext()).inflate(R.layout.group_list_item_tag, null);  
            }else{                      
                view = LayoutInflater.from(getContext()).inflate(R.layout.group_list_item, null);  
            }  
            TextView textView = (TextView) view.findViewById(R.id.group_list_item_text);  
            textView.setText(getItem(position));  
            return view;  
        }  
    }  
}  