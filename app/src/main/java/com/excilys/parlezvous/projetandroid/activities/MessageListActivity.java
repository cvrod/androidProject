package com.excilys.parlezvous.projetandroid.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.excilys.parlezvous.projetandroid.R;
import com.excilys.parlezvous.projetandroid.tasks.MessageTask;
import com.excilys.parlezvous.projetandroid.tools.MessagesToList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

/**
 * Activity for message list display
 */
public class MessageListActivity extends AppCompatActivity {
    public static final String PREFS_NAME = "PrefsFile";
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);

        //ToolBar intialisation
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Sets the toolbar title
        if(toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Chip Chat");
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //Getting ListView from Layout
        listView = (ListView) findViewById(R.id.listView);
        refresh();

    }

    /**
     * Refresh menu Creation
     * @param menu
     * @return
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_refresh, menu);//Menu Resource, Menu
        return true;
    }

    /**
     * Menu Listener
     * @param item
     * @return
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.refresh:
                refresh();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Refreshing message list
     * launching MessageTask to perform request on server
     *
     * @see MessageTask
     */
    public void refresh() {
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
        ArrayList<HashMap<String, String>> list = MessagesToList.toList(result);

        //creating listAdapter with ArrayList of Hashmap.
        ListAdapter adapter = new SimpleAdapter(MessageListActivity.this, list, R.layout.row_list, new String[]{"name", "message"}, new int[]{R.id.pseudo, R.id.textMessage});

        //Setting list Adapter
        listView.setAdapter(adapter);
    }

}
