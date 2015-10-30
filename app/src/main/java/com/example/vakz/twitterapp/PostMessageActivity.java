package com.example.vakz.twitterapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

public class PostMessageActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_message);
        Button submit = (Button)findViewById(R.id.submitMessage);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sendNewMessage();
                    }
                });
                t.start();
            }
        });
    }

    private void sendNewMessage() {
        Map<String, String> params = new HashMap<>();
        findViewById(R.id.submitMessage).setClickable(false);
        params.put("author", ((EditText) findViewById(R.id.author)).getText().toString());
        params.put("title", ((EditText)findViewById(R.id.title)).getText().toString());
        params.put("tag", ((EditText)findViewById(R.id.tag)).getText().toString());
        params.put("body", ((EditText)findViewById(R.id.body)).getText().toString());
        for(String s : params.values()) {
            if (s.isEmpty()) {
                return;
            }
        }
        try {
            APIHandler.sendMessage(params);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        findViewById(R.id.submitMessage).setClickable(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_post_message, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
