package com.excilys.parlezvous.projetandroid.tools;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.UUID;

public class ConnectionHandler {
    public final static String CONNECTION_URL = "https://training.loicortola.com/chat-rest/2.0";

    static class BasicAuthenticator extends Authenticator {
        String user;
        String password;

        private BasicAuthenticator(String user, String password) {
            this.user = user;
            this.password = password;
        }

        @Override
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(user, password.toCharArray());
        }
    }

    public static String authentification(final String user, final String password) {

        URL url = null;
        HttpURLConnection urlConnection = null;

        Authenticator.setDefault(new BasicAuthenticator(user, password));

        try {
            url = new URL(CONNECTION_URL + "/connect");
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            return InputStreamToString.convert(in);
        } catch (IOException e) {
            return "{\"status\":401}";
        } finally {
            urlConnection.disconnect();
        }
    }

    public static String getMessages(final String user, final String password, int limit, int offset) {
        URL url = null;
        HttpURLConnection urlConnection = null;

        Authenticator.setDefault(new BasicAuthenticator(user, password));

        try {
            url = new URL(CONNECTION_URL + "/messages?&limit="+limit+"&offset="+offset);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            return InputStreamToString.convert(in);
        } catch (IOException e) {
            return "{\"status\":401}";
        } finally {
            urlConnection.disconnect();
        }
    }

    public static void sendMessage(final String user, final String password, String message) {

        URL url = null;
        HttpURLConnection urlConnection = null;
        UUID uuid = UUID.randomUUID();

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("uuid", uuid);
            jsonObject.put("login", user);
            jsonObject.put("message", message);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            url = new URL(CONNECTION_URL + "/messages");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(false);
            OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
            System.out.println(jsonObject.toString());
            wr.write(jsonObject.toString());
            wr.flush();

            wr.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }  finally {
            urlConnection.disconnect();
        }
    }

}
