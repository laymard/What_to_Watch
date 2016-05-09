package com.example.whattowatch.choices;

import java.util.HashMap;

/**
 * Created by Lolo on 09/05/2016.
 */
public class ChoicesPresenter {

    private ChoicesActivity activity;
    private HashMap<String,Integer> genreToId;

    public ChoicesPresenter(ChoicesActivity act){
        this.activity = act;
        this.genreToId = new HashMap<String,Integer>();

        genreToId.put("Action",28);
        genreToId.put("Adventure",12);
        genreToId.put("Animation",16);

    }

    public int getId(String genre){
        return genreToId.get(genre);
    }
}
