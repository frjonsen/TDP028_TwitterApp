package com.example.vakz.twitterapp;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by vakz on 10/28/15.
 */
public final class APIHandler {

    private static String makeGetRequest(URL url) {
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            if (conn.getResponseCode() == 200) {
                StringBuilder sb = new StringBuilder();
                InputStream is = conn.getInputStream();
                BufferedReader r = new BufferedReader(new InputStreamReader(is));
                for (String line = r.readLine(); line != null; line = r.readLine()) {
                    sb.append(line);
                }
                is.close();
                return sb.toString();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static ArrayList<Message> jsonToArray(JSONArray jsonArray) {
        ArrayList<Message> messages = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); ++i) {
                JSONObject c = jsonArray.getJSONObject(i);
                messages.add(new Message(
                        c.getString("author"),
                        c.getString("body"),
                        c.getString("tag"),
                        c.getString("title"),
                        c.getInt("id")
                ));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return messages;
    }

    public static ArrayList<Message> getMessages() {

        try {
            URL url = new URL("http://tdp028-test.openshift.ida.liu.se/messages");

            return jsonToArray(new JSONObject(makeGetRequest(url)).getJSONArray("result"));
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Message> search(String tag) {
        try {
            URL url = new URL("http://tdp028-test.openshift.ida.liu.se/search/" + tag);
            System.out.println(url.toString());
            return jsonToArray(new JSONObject(makeGetRequest(url)).getJSONArray("result"));
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Message>();
        }

    }

    public static void sendMessage(Map<String, String> params) throws java.io.IOException {
        HttpURLConnection url = (HttpURLConnection)(new URL("http://tdp028-test.openshift.ida.liu.se/add")).openConnection();
        url.setRequestProperty("Content-Type", "application/json");
        url.connect();
        JSONObject p = new JSONObject(params);
        DataOutputStream d = new DataOutputStream(url.getOutputStream());
        d.writeUTF(URLEncoder.encode(p.toString(), "UTF-8"));
        d.flush();
        d.close();
    }

}
