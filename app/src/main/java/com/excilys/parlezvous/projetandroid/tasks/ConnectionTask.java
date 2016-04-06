package com.excilys.parlezvous.projetandroid.tasks;

import android.view.View;
import android.widget.ProgressBar;

import com.excilys.parlezvous.projetandroid.tools.InputStreamToString;
import com.excilys.parlezvous.projetandroid.activities.MainActivity;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Connection Task who verify if a given user exist on server
 */

public class ConnectionTask extends android.os.AsyncTask {
    MainActivity activity;
    String user;
    String password;
    boolean identification = false;

    /**
     * Constructor
     *
     * @param act activity associate to the task
     */
    public ConnectionTask(MainActivity act) {
        this.activity = act;
    }

    
    protected void onPreExecute() {
        super.onPreExecute();
        ProgressBar bar = activity.getProgressBar();
        bar.setVisibility(View.VISIBLE);
    }

    /**
     * Main method who try to connect to the server and verify is the given
     *
     * @param params
     * @return a boolean : true if verification pass, false else
     */
    @Override
    protected Object doInBackground(Object[] params) {
        user = activity.getUsername();
        password = activity.getPassword();

        URL url = null;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL("http://formation-android-esaip.herokuapp.com/connect/" + user + "/" + password);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String response = InputStreamToString.convert(in);
            if (response.compareTo("true") == 0) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
        return null;
    }

    protected void onPostExecute(Object result) {
        super.onPostExecute(result);
        ProgressBar bar = activity.getProgressBar();
        bar.setVisibility(View.INVISIBLE);
    }
}