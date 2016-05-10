package com.example.whattowatch.results;

import android.app.ActionBar;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.whattowatch.R;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import info.movito.themoviedbapi.model.MovieDb;

public class ResultsActivity extends AppCompatActivity {
    private ArrayList<MovieDb> movieDbs;
    private LinearLayout results;
    public static String posterBasePath = "https://image.tmdb.org/t/p/w185";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        this.results = (LinearLayout) findViewById(R.id.posters);
        TextView title = (TextView) findViewById(R.id.TITLE_Result);
        Typeface type = Typeface.createFromAsset(getAssets(), "Atlantic_Cruise-Demo.ttf");
        title.setTypeface(type);

        ArrayList<String> moviesJson = getIntent().getExtras().getStringArrayList("movies");
        this.movieDbs = new ArrayList<MovieDb>();
        ObjectMapper mapper = new ObjectMapper();
        try {

            for (int i=0; i<moviesJson.size();i++){
                MovieDb movie = mapper.readValue(moviesJson.get(i), MovieDb.class);
                this.movieDbs.add(movie);
                generatePosterLayout(movie);
                Log.i("Title added : ", movie.getTitle());
                Log.i("Poster URl:", movie.getPosterPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addMovie(MovieDb movie){
        TextView textView = new TextView(this.getApplicationContext());
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        Log.i("Title added : ", movie.getTitle());
        Log.i("Poster URl:", movie.getPosterPath());
        textView.setText(movie.getTitle());
        textView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        LinearLayout layout = (LinearLayout) findViewById(R.id.result_page);


        layout.addView(textView);

    }
    public LinearLayout generatePosterLayout(MovieDb movieDb){
        LinearLayout poster = new LinearLayout(this);
        poster.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
        poster.setOrientation(LinearLayout.VERTICAL);
        poster.setGravity(Gravity.CENTER_VERTICAL);

        TextView textView = new TextView(this);
        textView.setTextAppearance(this, R.style.movie_title);
        textView.setText(movieDb.getTitle());
        textView.setTypeface(Typeface.createFromAsset(getAssets(), "Atlantic_Cruise-Demo.ttf"));
        poster.addView(textView);

        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setAdjustViewBounds(true);
        poster.addView(imageView);

        PairMovieDbImageView pair = new PairMovieDbImageView(movieDb,imageView);
        new downloadPic().execute(pair);
        this.results.addView(poster);
        return poster;

    }


    private class downloadPic extends AsyncTask<PairMovieDbImageView,Void,Drawable>{

        private ImageView imageView;
        private MovieDb movieDb;

        @Override
        protected Drawable doInBackground(PairMovieDbImageView... params) {
            this.imageView = params[0].getImageView();
            this.movieDb=params[0].getMovieDb();
            try {
                InputStream is = (InputStream) new URL(ResultsActivity.posterBasePath+this.movieDb.getPosterPath()).getContent();
                Drawable d = Drawable.createFromStream(is, "src name");
                return d;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(Drawable result) {
            Log.i("OnPostExecute","downloaded pic");
            imageView.setImageDrawable(result);
        }


    }
}
