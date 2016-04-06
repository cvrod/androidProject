package com.excilys.parlezvous.projetandroid.tools;


import java.util.ArrayList;
import java.util.HashMap;

public class messagesToList {

    /**
     * Convert messages str to ArrayList who can be used to set an adapter for the listView
     *
     * @param messages str who contain messages separated by ';'
     * @return ArrayList containing messages under Hashmap form : (keys : name, message)
     */
    public static ArrayList<HashMap<String, String>> toList(String messages) {
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

        for (String str : messages.split(";")) {
            String[] elements = str.split(":");
            if (elements.length == 2) {
                HashMap<String, String> tmp = new HashMap<String, String>();
                tmp.put("name", elements[0]);
                tmp.put("message", elements[1]);
                list.add(0, tmp);
            }
        }
        return list;
    }
}
