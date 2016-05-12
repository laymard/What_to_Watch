package com.example.whattowatch.choices;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.example.whattowatch.R;
import com.example.whattowatch.results.ResultsActivity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;

public class ChoicesActivity extends AppCompatActivity {
    private LinearLayout layout_genre_pictured;
    private LinearLayout selectedGenre;
    private ChoicesPresenter presenter;
    public static List<MovieDb> movieToSend = new ArrayList<MovieDb>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choices);
        this.presenter = new ChoicesPresenter(this);
        this.layout_genre_pictured = (LinearLayout) findViewById(R.id.layout_genre_pictured);
        presenter.addGenres(this.layout_genre_pictured);

    }

    public LinearLayout getLayout_genre_pictured() {
        return layout_genre_pictured;
    }

    public void setLayout_genre_pictured(LinearLayout layout_genre_pictured) {
        this.layout_genre_pictured = layout_genre_pictured;
    }

    public LinearLayout getSelectedGenre() {
        return selectedGenre;
    }

    public void setSelectedGenre(LinearLayout selectedGenre) {
        this.selectedGenre = selectedGenre;
    }


   /* public void showResults(List<MovieDb> movies){
        Intent intent = new Intent(this,ResultsActivity.class);
        ChoicesActivity.movieJsonToSend.clear();
        ArrayList<String> movieJson = new ArrayList<String>();
        Bundle myBundle = new Bundle();
        ObjectMapper mapper = new ObjectMapper();
        try {
            for (int i=0; i<Math.min(10,movies.size());i++){
                MovieDb movie = movies.get(i);
                String movieJsonString = mapper.writeValueAsString(movie);
                ChoicesActivity.movieJsonToSend.add(movieJsonString);
                movieJson.add(movieJsonString);
            }
           // myBundle.putStringArrayList("movies",movieJson);
            //intent.putExtras(myBundle);
            startActivity(intent);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }*/


    public void launchRequest(View view) {
        int id = getGenreId();
        new searchByGenre().execute(id);
    }

    public int getGenreId() {
        String tag = this.selectedGenre.getTag().toString();
        Log.i("tag=",tag);
        return this.presenter.getId(tag);
    }


    private class searchByGenre extends AsyncTask<Integer, Void, List<MovieDb>> {

        //initiate vars
        public searchByGenre() {
            super();
            //my params here
        }

        protected List<MovieDb> doInBackground(Integer... params) {
            TmdbApi api = new TmdbApi("7929cf9ce016b589b2d5bc43b4295f30");
            ChoicesActivity.movieToSend.clear();
            TmdbMovies movies = api.getMovies();
            List<MovieDb> res =  api.getGenre().getGenreMovies(params[0],"en",2,false).getResults();
            for (int i=0;i<10;i++){
                res.set(i,movies.getMovie(res.get(i).getId(),"en", TmdbMovies.MovieMethod.credits));
                ChoicesActivity.movieToSend.add(res.get(i));
                Log.i("Getting movie:",""+i);
            }

            return res;
        }

        @Override
        protected void onPostExecute(List<MovieDb> result) {
            showResults();

        }
    }

    private void showResults() {
        Intent intent = new Intent(this,ResultsActivity.class);
        startActivity(intent);
    }

    private class searchMovies extends AsyncTask<Void, Void, TmdbMovies> {

        //initiate vars
        public searchMovies() {
            super();
            //my params here
        }

        protected TmdbMovies doInBackground(Void... params) {
            TmdbApi api = new TmdbApi("7929cf9ce016b589b2d5bc43b4295f30");
            TmdbMovies res = api.getMovies();
            return res;
        }

        @Override
        protected void onPostExecute(TmdbMovies result) {
            //showResults(result);

        }
    }


}
