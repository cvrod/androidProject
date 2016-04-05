package com.excilys.parlezvous.projetandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void sendMessageMethod(View view){
        Intent intent = new Intent(WelcomeActivity.this, SendMessageActivity.class);
        startActivity(intent);
    }

    public void messageListMethod(View view){
        Intent intent = new Intent(WelcomeActivity.this, MessageListActivity.class);
        startActivity(intent);
    }
}
