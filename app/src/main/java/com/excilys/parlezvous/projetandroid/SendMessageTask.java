package com.excilys.parlezvous.projetandroid;

import android.support.v7.app.AppCompatActivity;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class SendMessageTask extends android.os.AsyncTask {
    public static final String PREFS_NAME = "PrefsFile";
    private String user;
    private String password;
    private String message;
    private AppCompatActivity activity;

    public SendMessageTask(String user, String password, String message, AppCompatActivity act){
        this.user = user;
        this.password = password;
        this.message = message;
        this.activity = act;
    }

    protected Object doInBackground(Object[] params) {

        URL url = null;
        HttpURLConnection urlConnection = null;
        try {
            System.out.println(user);
            System.out.println(password);
            System.out.println(message);
            message = URLEncoder.encode(message, "UTF-8");
            message = message.replace("+", "%20");

            url = new URL("http://formation-android-esaip.herokuapp.com/message/"+user+"/"+password+"/"+message);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

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
        activity.finish();
    }
}
