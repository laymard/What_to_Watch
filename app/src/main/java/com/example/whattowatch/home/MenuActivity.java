package com.example.whattowatch.home;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.whattowatch.R;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.MovieDb;

public class MenuActivity extends AppCompatActivity {
    private TextView textView;
    private TextView title;
    private MenuPresenter menuPresenter;

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
        this.textView = (TextView) findViewById(R.id.result);
        this.menuPresenter = new MenuPresenter(this);
        this.title = (TextView) findViewById(R.id.title);
        Typeface type = Typeface.createFromAsset(getAssets(), "Park Lane NF.ttf");
        title.setTypeface(type);

    }

    public void sendRequest(View view) {
        new myTask().execute();
    }

    public void setText(String string){
        this.textView.setText(string);
    }
}
