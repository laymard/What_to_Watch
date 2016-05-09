package com.example.whattowatch.results;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.whattowatch.R;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import info.movito.themoviedbapi.model.MovieDb;

public class ResultsActivity extends AppCompatActivity {
    private TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        this.result = (TextView)  findViewById(R.id.movieResult);
        String movieJson = getIntent().getStringExtra("movie");
        ObjectMapper mapper = new ObjectMapper();
        try {
            MovieDb movie = mapper.readValue(movieJson, MovieDb.class);
            String title = movie.getTitle();
            Log.i("title=",title);
            this.result.setText(title);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
