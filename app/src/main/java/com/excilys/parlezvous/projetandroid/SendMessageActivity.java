package com.excilys.parlezvous.projetandroid;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class SendMessageActivity extends AppCompatActivity {
    public static final String PREFS_NAME = "PrefsFile";
    private String user;
    private String password;
    private String message;
    private EditText messageField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        messageField = (EditText) findViewById(R.id.messageField);
    }

    public void sendMessage(View view){

        //getting user/password from SharedPreferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        user = settings.getString("user", "");
        password = settings.getString("password", "");

        message = messageField.getText().toString();
        //Sending info to SendMessageTask
        SendMessageTask task = new SendMessageTask(user,password,message, this);
        task.execute();
    }

}
