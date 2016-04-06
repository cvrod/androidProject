package com.excilys.parlezvous.projetandroid.tasks;

import android.util.Base64;
import android.view.View;
import android.widget.ProgressBar;

import com.excilys.parlezvous.projetandroid.tools.ConnectionHandler;
import com.excilys.parlezvous.projetandroid.tools.InputStreamToString;
import com.excilys.parlezvous.projetandroid.activities.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
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

        String response = ConnectionHandler.authentification(user, password);
        System.out.println(response);

        //Getting JSON from str
        JSONObject jsonResponse;
        try {
            jsonResponse = new JSONObject(response);
            int status = jsonResponse.getInt("status");
            if(status == 200){
                return true;
            }
            else{
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    protected void onPostExecute(Object result) {
        super.onPostExecute(result);
        ProgressBar bar = activity.getProgressBar();
        bar.setVisibility(View.INVISIBLE);
    }
}