package com.excilys.parlezvous.projetandroid.tasks;


import com.excilys.parlezvous.projetandroid.activities.MainActivity;
import com.excilys.parlezvous.projetandroid.tools.ConnectionHandler;

public class RegisterTask extends android.os.AsyncTask{
    MainActivity associateActivity;
    String user;
    String password;

    public RegisterTask(MainActivity act){
        this.associateActivity = act;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        this.user = associateActivity.getUsername();
        this.password = associateActivity.getPassword();

        return ConnectionHandler.register(user,password);
    }
}
