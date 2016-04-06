package com.excilys.parlezvous.projetandroid.tasks;


import com.excilys.parlezvous.projetandroid.tools.InputStreamToString;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Message Task used to get messages list from server
 */

public class MessageTask extends android.os.AsyncTask {
    public static final String PREFS_NAME = "PrefsFile";
    private String user;
    private String password;

    /**
     * Constructor
     * @param user user login
     * @param password user password
     */
    public MessageTask(String user, String password) {
        this.user = user;
        this.password = password;
    }

    /**
     * getting str response from server
     * @param params
     * @return str containing messages list separated by ';'
     */
    protected Object doInBackground(Object[] params) {

        URL url = null;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL("http://formation-android-esaip.herokuapp.com/messages/" + user + "/" + password);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String response = InputStreamToString.convert(in);
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
        return null;
    }
}
