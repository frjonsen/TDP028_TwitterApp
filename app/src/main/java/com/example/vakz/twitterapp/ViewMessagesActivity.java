package com.example.vakz.twitterapp;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.ArrayList;

public class ViewMessagesActivity extends Activity implements AllMessagesFragment.OnFragmentInteractionListener, AuthorFilteredFragment.OnFragmentInteractionListener {

    MessageAdapter messages;
    AllMessagesFragment allMessagesFragment;
    AuthorFilteredFragment authorFilteredFragment;

    @Override
    public void OnMessageClicked(int pos) {

        getFragmentManager().beginTransaction().replace(R.id.fragmentplaceholder, authorFilteredFragment).addToBackStack("AllMessages").commit();
        authorFilteredFragment.setFilter(messages.getItem(pos).author);
    }

    protected MessageAdapter getMessages() {
        return messages;
    }

    private class SearchMessagesTask extends AsyncTask<Void, Void, ArrayList<Message>> {

        String searchWord;

        @Override
        protected void onPreExecute() {
            searchWord = ((EditText)findViewById(R.id.searchField)).getText().toString();
        }

        @Override
        protected ArrayList<Message> doInBackground(Void... params) {
            if (searchWord.length() == 0) {
                return APIHandler.getMessages();
            }
            else {
                return APIHandler.search(searchWord);
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Message> m) {

            messages.replaceList(m);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_messages);
        setMessagesFragments();
        messages = new MessageAdapter(this, new ArrayList<Message>());
        (new SearchMessagesTask()).execute();
        setupOnClick();


    }

    protected void setMessagesFragments() {
        allMessagesFragment = AllMessagesFragment.newInstance();
        authorFilteredFragment = AuthorFilteredFragment.newInstance();
        getFragmentManager().beginTransaction().add(R.id.fragmentplaceholder, allMessagesFragment).commit();
    }

    void setupOnClick() {
        Button submitButton = (Button) findViewById(R.id.searchButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (new SearchMessagesTask()).execute();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_messages, menu);
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
