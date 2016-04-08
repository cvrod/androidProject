package com.excilys.parlezvous.projetandroid.tools;

import com.excilys.parlezvous.projetandroid.activities.MessageListActivity;

import java.util.TimerTask;

public class RefreshTimerTask extends TimerTask {

    MessageListActivity associateActivity;

    public RefreshTimerTask(MessageListActivity act) {
        this.associateActivity = act;
    }

    @Override
    public void run() {
        this.associateActivity.refresh();
    }
}
