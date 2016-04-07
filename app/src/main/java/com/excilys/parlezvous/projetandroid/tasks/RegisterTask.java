package com.excilys.parlezvous.projetandroid.tasks;


import com.excilys.parlezvous.projetandroid.activities.MainActivity;
import com.excilys.parlezvous.projetandroid.tools.ConnectionHandler;

/**
 * Register task used to register a user on server
 */

public class RegisterTask extends android.os.AsyncTask {
    MainActivity associateActivity;
    String user;
    String password;

    /**
     * Construction
     *
     * @param act associate activity used to get user/password parameters
     */
    public RegisterTask(MainActivity act) {
        this.associateActivity = act;
    }

    /**
     * Calling register method from ConnectionHandler with user/password informations
     *
     * @param params
     * @return response code : 200 if registration success, 400 else
     */
    @Override
    protected Object doInBackground(Object[] params) {
        this.user = associateActivity.getUsername();
        this.password = associateActivity.getPassword();

        return ConnectionHandler.register(user, password);
    }
}
