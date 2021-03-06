package com.excilys.parlezvous.projetandroid.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.excilys.parlezvous.projetandroid.R;
import com.excilys.parlezvous.projetandroid.tasks.ConnectionTask;
import com.excilys.parlezvous.projetandroid.tasks.RegisterTask;

import java.util.concurrent.ExecutionException;

/**
 * Main Activity : display connection form
 */
public class MainActivity extends AppCompatActivity {
    //Getting class name
    private final String TAG = MainActivity.class.getSimpleName();
    public static final String PREFS_NAME = "PrefsFile";

    private EditText usernameField;
    private EditText passwordField;

    private Button flushButton;
    private Button sendButton;

    private ProgressBar progressBar;

    private boolean identificationSuccess = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate !");
        usernameField = (EditText) findViewById(R.id.user);
        passwordField = (EditText) findViewById(R.id.password);

        flushButton = (Button) findViewById(R.id.viderButton);
        sendButton = (Button) findViewById(R.id.envoyerButton);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

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

        //Setting user/password from SharedPreferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String user = settings.getString("user", "");
        String password = settings.getString("password", "");

        usernameField.setText(user);
        passwordField.setText(password);

    }

    //Getters
    public ProgressBar getProgressBar() {
        return this.progressBar;
    }

    public String getUsername() {
        return this.usernameField.getText().toString();
    }

    public String getPassword() {
        return this.passwordField.getText().toString();
    }


    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy !");
    }

    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause !");
    }

    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume !");
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState !");
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstanceState !");
    }

    /**
     * Method called to flush usernameField and passwordField
     *
     * @param view
     */
    public void flushButtonMethod(View view) {
        usernameField.setText("");
        passwordField.setText("");
    }

    /**
     * Method called when connection button is pushed
     * Calling ConnectionTask
     *
     * @param view
     * @see ConnectionTask
     */
    public void sendButtonMethod(View view) {
        //Toast.makeText(this, "Toast !", Toast.LENGTH_SHORT).show();
        ConnectionTask task = new ConnectionTask(this);
        task.execute();
        boolean result = false;
        try {
            result = (boolean) task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //if ParlezVousTask return true -> login exist
        if (result) {
            //Putting user/password in Preferences
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("user", this.getUsername());
            editor.putString("password", this.getPassword());
            editor.commit();

            Intent intent = new Intent(MainActivity.this, MessageListActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Utilisateur inconnu !", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Method called when register button is pushed
     * Called RegisterTask
     *
     * @param view
     * @see RegisterTask
     */
    public void registerButtonMethod(View view) {
        RegisterTask registerTask = new RegisterTask(this);
        registerTask.execute();
        int result = 400;
        try {
            result = (int) registerTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (result == 200) {
            Toast.makeText(this, "Inscription reussie !", Toast.LENGTH_SHORT).show();
            sendButtonMethod(view);
        } else {
            Toast.makeText(this, "Erreur d'insciption !", Toast.LENGTH_SHORT).show();
        }

    }
}