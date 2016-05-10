package com.example.whattowatch.results;

import android.widget.ImageView;

import info.movito.themoviedbapi.model.MovieDb;

/**
 * Created by Lolo on 10/05/2016.
 */
public class PairMovieDbImageView {
    private MovieDb movieDb;
    private ImageView imageView;


    public PairMovieDbImageView(MovieDb m, ImageView i){
        movieDb=m;
        imageView=i;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public MovieDb getMovieDb() {

        return movieDb;
    }
}
