package com.excilys.parlezvous.projetandroid.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.excilys.parlezvous.projetandroid.R;
import com.excilys.parlezvous.projetandroid.tasks.MessageTask;
import com.excilys.parlezvous.projetandroid.tasks.RefreshTask;
import com.excilys.parlezvous.projetandroid.tasks.SendMessageTask;
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
    private RefreshTask refreshTask;
    String user;
    String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        this.user = settings.getString("user", "");
        this.password = settings.getString("password", "");

        //ToolBar intialisation
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Sets the toolbar title
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Chip Chat");
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //Getting ListView from Layout
        listView = (ListView) findViewById(R.id.listView);

        refreshTask= new RefreshTask(this);
        refreshTask.execute();

    }

    @Override
    protected void onStop() {
        super.onStop();
        refreshTask.getTimer().cancel();
    }

    /**
     * Refresh menu Creation
     *
     * @param menu
     * @return
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_refresh, menu);//Menu Resource, Menu
        return true;
    }

    /**
     * Menu Listener
     *
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
    public int refresh() {

        MessageTask task = new MessageTask(user, password);
        task.execute();
        String result = "";
        try {
            result = (String) task.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        //List used by the Adapter
        final ArrayList<HashMap<String, String>> list = MessagesToList.toList(result);

        //creating listAdapter with ArrayList of Hashmap.
        final ListAdapter adapter = new SimpleAdapter(MessageListActivity.this, list, R.layout.row_list, new String[]{"name", "message"}, new int[]{R.id.pseudo, R.id.textMessage});

        //Setting list Adapter
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listView.setAdapter(adapter);
                listView.setSelection(list.size() - 1);
            }
        });
        return list.size();
    }

    public void fragmentSendButtonMethod(View view) {
        EditText messageField = (EditText) findViewById(R.id.fragmentSendMessageLabel);
        ListView listView = (ListView) findViewById(R.id.listView);
        String message = messageField.getText().toString();

        if (!message.isEmpty()) {
            SendMessageTask task = new SendMessageTask(user, password, message, this);
            task.execute();
            int listSize = refresh();
            listView.setSelection(listSize-1);
            messageField.setText("");
        } else {
            Toast.makeText(this, "Message Vide !", Toast.LENGTH_SHORT).show();
        }

    }

}
