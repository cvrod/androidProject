package com.excilys.parlezvous.projetandroid;



import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MessageTask extends android.os.AsyncTask {
    public static final String PREFS_NAME = "PrefsFile";
    private String user;
    private String password;

    public MessageTask(String user, String password){
        this.user = user;
        this.password = password;
    }

    protected Object doInBackground(Object[] params) {

        URL url = null;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL("http://formation-android-esaip.herokuapp.com/messages/"+user+"/"+password);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String response = InputStreamToString.convert(in);
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            urlConnection.disconnect();
        }
        return null;
    }
}
