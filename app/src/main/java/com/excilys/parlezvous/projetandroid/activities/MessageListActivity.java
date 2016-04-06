package com.excilys.parlezvous.projetandroid.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.excilys.parlezvous.projetandroid.R;
import com.excilys.parlezvous.projetandroid.tasks.MessageTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class MessageListActivity extends AppCompatActivity {
    public static final String PREFS_NAME = "PrefsFile";
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);
        //Getting ListView from Layout
        listView = (ListView) findViewById(R.id.listView);
        refresh();
    }

    public void refreshButtonMethod(View view){
        refresh();
    }

    //Getting fresh list from server
    public void refresh(){
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String user = settings.getString("user", "");
        String password = settings.getString("password", "");

        MessageTask task = new MessageTask(user, password);
        task.execute();
        String result = "";
        try {
            result = (String) task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //List used by the Adapter
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();

        for (String str : result.split(";")) {
            String[] elements = str.split(":");
            if (elements.length == 2) {
                HashMap<String,String> tmp = new HashMap<String, String>();
                tmp.put("name", elements[0]);
                tmp.put("message", elements[1]);
                list.add(0, tmp);
            }
        }

        //creating listAdapter with ArrayList of Hashmap.
        ListAdapter adapter = new SimpleAdapter(MessageListActivity.this, list, R.layout.row_list, new String[] {"name", "message"}, new int[] { R.id.pseudo, R.id.textMessage });

        //Setting list Adapter
        listView.setAdapter(adapter);
    }
}
