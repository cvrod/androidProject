package com.excilys.parlezvous.projetandroid;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by excilys on 05/04/16.
 */
public class ConnectionTask extends android.os.AsyncTask {
    MainActivity activity;
    String user;
    String password;
    boolean identification = false;

    public ConnectionTask(MainActivity act){
        this.activity = act;
    }

    protected void onPreExecute(){
        super.onPreExecute();
        ProgressBar bar = activity.getProgressBar();
        bar.setVisibility(View.VISIBLE);
    }

    @Override
    protected Object doInBackground(Object[] params) {
        user = activity.getUsername();
        password = activity.getPassword();

        System.out.println("User : "+user);
        System.out.println("Password : "+password);

        URL url = null;
        HttpURLConnection urlConnection = null;
        try {
            //url = new URL("http://[host]:[port]/connect/"+user+"/"+password);
            url = new URL("http://www.google.com");
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String response = InputStreamToString.convert(in);
            System.out.println(response);
            //if(response.compareTo("true") == 0){
            //TODO
            if(!response.isEmpty()){
                activity.setIdentificationSuccess(true);
                return true;
            }
            else{
                Toast.makeText(activity, "Utilisateur inconnu !", Toast.LENGTH_SHORT).show();
                activity.setIdentificationSuccess(false);
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
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