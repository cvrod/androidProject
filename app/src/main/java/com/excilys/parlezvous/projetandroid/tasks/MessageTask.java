package com.excilys.parlezvous.projetandroid.tasks;


import com.excilys.parlezvous.projetandroid.tools.ConnectionHandler;


/**
 * Message Task used to get messages list from server
 */

public class MessageTask extends android.os.AsyncTask {
    public static final String PREFS_NAME = "PrefsFile";
    public static final int LIMIT = 1000;
    public static final int OFFSET = 0;
    private String user;
    private String password;

    /**
     * Constructor
     *
     * @param user     user login
     * @param password user password
     */
    public MessageTask(String user, String password) {
        this.user = user;
        this.password = password;
    }

    /**
     * getting str response from server
     *
     * @param params
     * @return str containing messages list separated by ';'
     */
    protected Object doInBackground(Object[] params) {
        String response = ConnectionHandler.getMessages(this.user, this.password, LIMIT, OFFSET);
        return response;
    }
}
