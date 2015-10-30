package com.example.vakz.twitterapp;

/**
 * Created by vakz on 10/28/15.
 */
public class Message {

    public Message(String author, String body, String tag, String title) {
        this.author = author;
        this.body = body;
        this.tag = tag;
        this.title = title;
    }

    public String author;
    public String body;
    public String tag;
    public String title;
}
