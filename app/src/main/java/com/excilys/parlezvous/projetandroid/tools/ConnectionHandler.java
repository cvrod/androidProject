package com.excilys.parlezvous.projetandroid.tools;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;

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

    public static String getMessages(final String user, final String password) {
        URL url = null;
        HttpURLConnection urlConnection = null;

        Authenticator.setDefault(new BasicAuthenticator(user, password));

        try {
            url = new URL(CONNECTION_URL + "/messages?&limit=30&offset=0");
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            return InputStreamToString.convert(in);
        } catch (IOException e) {
            return "{\"status\":401}";
        } finally {
            urlConnection.disconnect();
        }
    }
}
