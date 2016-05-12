package com.example.whattowatch.movie;

import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.whattowatch.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import java.io.IOException;

import info.movito.themoviedbapi.model.MovieDb;

public class MovieActivity extends AppCompatActivity {
    private BottomBar bottomBar;
    private TextView title;
    private MovieDb movieDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        this.title = (TextView) findViewById(R.id.MOVIE_title);
        ObjectMapper mapper = new ObjectMapper();
        String movieJson = getIntent().getStringExtra("movieJSON");
        try {
            this.movieDb = mapper.readValue(movieJson, MovieDb.class);
            this.title.setText(movieDb.getTitle());
        } catch (IOException e) {
            e.printStackTrace();
        }



        bottomBar=BottomBar.attach(this, savedInstanceState);
        bottomBar.setItemsFromMenu(R.menu.bottombar_menu, new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {

            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {

            }
        });

        bottomBar.mapColorForTab(0, ContextCompat.getColor(this,R.color.colorAccent));
    }
}
