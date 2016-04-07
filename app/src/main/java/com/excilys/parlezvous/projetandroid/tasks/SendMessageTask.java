package com.excilys.parlezvous.projetandroid.tasks;

import android.support.v7.app.AppCompatActivity;

import com.excilys.parlezvous.projetandroid.tools.ConnectionHandler;

/**
 * Task who send a formated message to the server
 */
public class SendMessageTask extends android.os.AsyncTask {
    public static final String PREFS_NAME = "PrefsFile";
    private String user;
    private String password;
    private String message;
    private AppCompatActivity activity;

    /**
     * Constructor
     *
     * @param user     user login needed for server request
     * @param password user password
     * @param message  str who contain message
     * @param act      activity associate to the task
     */
    public SendMessageTask(String user, String password, String message, AppCompatActivity act) {
        this.user = user;
        this.password = password;
        this.message = message;
        this.activity = act;
    }

    /**
     * Send message to server
     *
     * @param params
     * @return
     */
    protected Object doInBackground(Object[] params) {

        ConnectionHandler.sendMessage(user, password, message);
        return null;
    }

    protected void onPostExecute(Object result) {
        super.onPostExecute(result);
        activity.finish();
    }
}
