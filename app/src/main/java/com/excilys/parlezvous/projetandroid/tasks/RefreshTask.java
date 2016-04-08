package com.excilys.parlezvous.projetandroid.tasks;

import android.view.View;
import android.widget.ProgressBar;

import com.excilys.parlezvous.projetandroid.activities.MessageListActivity;
import com.excilys.parlezvous.projetandroid.tools.RefreshTimerTask;

import java.util.Timer;
import java.util.TimerTask;

public class RefreshTask extends android.os.AsyncTask {
    MessageListActivity associateActivity;
    Timer timer;

    public RefreshTask(MessageListActivity act) {
        this.associateActivity = act;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        timer = new Timer();
        RefreshTimerTask tt = new RefreshTimerTask(this.associateActivity);
        timer.schedule(tt, 0, 1000);
        return null;
    }

    public Timer getTimer(){
        return this.timer;
    }
}
