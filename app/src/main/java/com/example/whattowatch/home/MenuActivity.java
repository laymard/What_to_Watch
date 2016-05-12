package com.example.whattowatch.home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;


import com.example.whattowatch.R;
import com.example.whattowatch.choices.ChoicesActivity;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.MovieDb;

public class MenuActivity extends AppCompatActivity {
    private TextView textView;
    private TextView title;
    private MenuPresenter menuPresenter;

    public void navigateToChoices(View view) {
        startActivity(new Intent(this, ChoicesActivity.class));
    }

    private class myTask extends AsyncTask<Void, Void, String> {

        //initiate vars
        public myTask() {
            super();
            //my params here
        }

        protected String doInBackground(Void... params) {
            TmdbApi api = new TmdbApi("7929cf9ce016b589b2d5bc43b4295f30");
            MovieDb movie = api.getMovies().getMovie(5353, "en");

            return movie.getTitle();
        }

        @Override
        protected void onPostExecute(String result) {
            setText(result);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        this.menuPresenter = new MenuPresenter(this);
        this.title = (TextView) findViewById(R.id.title);
        Typeface type = Typeface.createFromAsset(getAssets(), "Park Lane NF.ttf");
        title.setTypeface(type);

    }


    public void setText(String string){
        this.textView.setText(string);
    }
}
