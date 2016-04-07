package com.excilys.parlezvous.projetandroid.tools;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MessagesToList {

    /**
     * Convert messages str to ArrayList who can be used to set an adapter for the listView
     *
     * @param messages str who contain messages separated by ';'
     * @return ArrayList containing messages under Hashmap form : (keys : name, message)
     */
    public static ArrayList<HashMap<String, String>> toList(String messages) {
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            JSONArray jsonResponse = new JSONArray(messages);
            int jsonResponseLength = jsonResponse.length();

            String userTmp;
            String messageTmp;

            for (int cpt = 0; cpt < jsonResponseLength; cpt++) {
                JSONObject tmp = jsonResponse.getJSONObject(cpt);
                userTmp = tmp.getString("login");
                messageTmp = tmp.getString("message");
                HashMap<String, String> tmpHash = new HashMap<>(2);
                tmpHash.put("name", userTmp);
                tmpHash.put("message", messageTmp);
                list.add(tmpHash);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
