package com.example.whattowatch.home;
import android.os.AsyncTask;

import com.example.whattowatch.R;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;

/**
 * Created by Lolo on 08/05/2016.
 */
public class MenuInteractor extends AsyncTask<MenuPresenter, Void, String> {

    public MenuInteractor(){

    }

    @Override
    protected String doInBackground(MenuPresenter... params) {
        MenuPresenter mp = params[0];
        return "merde";
    }

    protected void onPostExecute (String result){

    }

    public void showMovie(MenuPresenter menuPresenter) {
        doInBackground(menuPresenter);
    }
}


