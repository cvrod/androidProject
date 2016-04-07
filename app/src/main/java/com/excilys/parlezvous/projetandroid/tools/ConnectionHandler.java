package com.excilys.parlezvous.projetandroid.tools;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
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
            url = new URL(CONNECTION_URL + "/messages?&limit=" + limit + "&offset=" + offset);
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
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
            System.out.println(jsonObject.toString());
            wr.write(jsonObject.toString());
            wr.flush();
            wr.close();

            urlConnection.connect();
            InputStream is = new BufferedInputStream(urlConnection.getErrorStream());

            String result = InputStreamToString.convert(is);
            System.out.println("REPONSE DEBUGG : " + result);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
    }

    public static int register(String user, String password) {

        HttpURLConnection conn = null;
        try {

            String urlText = "https://training.loicortola.com/chat-rest/2.0/register";
            URL url = new URL(urlText);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type", "application/json");

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("login", user);
                jsonObject.put("password", password);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String json = jsonObject.toString();
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
            out.write(json);
            out.close();

            conn.connect();
            int result = conn.getResponseCode();
            System.out.println("Response : " + result);
            return result;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return 400;
    }
}
