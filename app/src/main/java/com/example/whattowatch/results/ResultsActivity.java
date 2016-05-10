package com.example.whattowatch.results;

import android.app.ActionBar;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    private TextView result;
    private ArrayList<MovieDb> movieDbs;
    public static String posterBasePath = "https://image.tmdb.org/t/p/w185";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        ArrayList<String> moviesJson = getIntent().getExtras().getStringArrayList("movies");
        this.movieDbs = new ArrayList<MovieDb>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            new downloadPic().execute(mapper.readValue(moviesJson.get(0), MovieDb.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {

            for (int i=0; i<moviesJson.size();i++){
                MovieDb movie = mapper.readValue(moviesJson.get(i), MovieDb.class);
                this.movieDbs.add(movie);
                addMovie(movie);
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

    public void setFirstPoster(Drawable d){
        ImageView firstPoster= (ImageView) findViewById(R.id.p1);
        firstPoster.setImageDrawable(d);
    }

    private class downloadPic extends AsyncTask<MovieDb,Void,Drawable>{

        @Override
        protected Drawable doInBackground(MovieDb... params) {
            try {
                InputStream is = (InputStream) new URL(ResultsActivity.posterBasePath+params[0].getPosterPath()).getContent();
                Drawable d = Drawable.createFromStream(is, "src name");
                return d;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(Drawable result) {
            setFirstPoster(result);
        }


    }
}
