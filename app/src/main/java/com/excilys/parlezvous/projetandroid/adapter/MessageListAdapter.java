package com.excilys.parlezvous.projetandroid.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.excilys.parlezvous.projetandroid.R;
import com.excilys.parlezvous.projetandroid.activities.MessageListActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class MessageListAdapter extends BaseAdapter {

    private ArrayList<HashMap<String, String>> messages;
    private String username;
    private MessageListActivity associateActivity;

    public MessageListAdapter(MessageListActivity act, ArrayList<HashMap<String, String>> messages, String username) {
        this.messages = messages;
        this.username = username;
        this.associateActivity = act;
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int ressource;

        String name = messages.get(position).get("name");
        String messageStr = messages.get(position).get("message");

        if (username.equals(name)) {
            ressource = R.layout.row_list2;
        } else {
            ressource = R.layout.row_list;
        }

        LayoutInflater inflater = associateActivity.getLayoutInflater();

        View row = inflater.inflate(ressource, parent, false);
        TextView login, message;
        login = (TextView) row.findViewById(R.id.pseudo);
        message = (TextView) row.findViewById(R.id.textMessage);

        login.setText(name);
        message.setText(messageStr);

        return (row);
    }
}
