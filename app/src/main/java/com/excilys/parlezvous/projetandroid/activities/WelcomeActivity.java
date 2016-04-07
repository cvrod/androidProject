package com.excilys.parlezvous.projetandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.excilys.parlezvous.projetandroid.R;

/**
 * Welcome Page Activity
 */
public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

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
    }

    /**
     * Method called when sendMessage button is pushed
     * Launching SendMessageActivity
     *
     * @param view
     */
    public void sendMessageMethod(View view) {
        Intent intent = new Intent(WelcomeActivity.this, SendMessageActivity.class);
        startActivity(intent);
    }

    /**
     * Method called when messageList button is pushed
     * Launching messageListActivity
     *
     * @param view
     */
    public void messageListMethod(View view) {
        Intent intent = new Intent(WelcomeActivity.this, MessageListActivity.class);
        startActivity(intent);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);//Menu Resource, Menu
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.deconnexion:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
