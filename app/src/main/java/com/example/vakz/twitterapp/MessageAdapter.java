package com.example.vakz.twitterapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by vakz on 10/28/15.
 */
public class MessageAdapter extends ArrayAdapter<Message> {
    ArrayList<Message> messages;

    public MessageAdapter(Context context, ArrayList<Message> messages) {
        super(context, 0, messages);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Message message = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.message_layout, parent, false);
        }
        ((TextView)convertView.findViewById(R.id.Title)).setText(message.title);
        ((TextView)convertView.findViewById(R.id.Author)).setText(message.author);
        ((TextView)convertView.findViewById(R.id.Body)).setText(message.body);
        ((TextView)convertView.findViewById(R.id.Tag)).setText(message.tag);
        return convertView;
    }

    public void replaceList(ArrayList<Message> m) {
        clear();
        messages = m;
        addAll(messages);
        notifyDataSetChanged();
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }
}
