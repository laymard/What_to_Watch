package com.example.whattowatch.movie;

import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.whattowatch.R;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.people.PersonCast;
import info.movito.themoviedbapi.model.people.PersonCrew;

public class Movie2Activity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    public static MovieDb movieDb;
    private ArrayList<String> actors;
    private ArrayList<String> characters;
    private String director;
    private float userRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie2);


        this.actors = new ArrayList<>();
        this.characters = new ArrayList<>();
        setResults();




        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    private void setResults() {
        setCasting();
        setDirector();
        setUserRating();

    }

    private void setUserRating() {
        this.userRating=this.movieDb.getVoteAverage();
    }

    private void setDirector() {
        List<PersonCrew> crew = this.movieDb.getCrew();
        for (int i=0;i<crew.size();i++){
            PersonCrew person = crew.get(i);

            if(person.getJob().equals("Director")){
                this.director=person.getName();
            }
        }
    }

    private void setCasting() {
        List<PersonCast> casting = new ArrayList<PersonCast>();
        casting = this.movieDb.getCast();
        int size = casting.size();
        for (int i=0;i<Math.min(5,size);i++){
            String character = casting.get(i).getCharacter();
            String actor = casting.get(i).getName();

            this.characters.add(character);
            this.actors.add(actor);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public MovieDb getMovieDb(){
        return movieDb;
    }

    public ArrayList<String> getActors() {
        return actors;
    }

    public String[] getActorsTab(){
        int actorsize = getActors().size();
        String[] actorsTab = new String[actorsize];
        for (int i=0;i<actorsize;i++){
            actorsTab[i] = getActors().get(i);
        }
        return actorsTab;

    }

    public ArrayList<String> getCharacters() {
        return characters;
    }

    public String[] getCharactersTab(){
        int charactersSize = getCharacters().size();
        String[] charactersTab = new String[charactersSize];
        for (int i=0;i<charactersSize;i++){
            charactersTab[i] = getCharacters().get(i);
        }
        return charactersTab;


    }

    public String getDirector() {
        return director;
    }

    public float getUserRating() {
        return userRating;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";


        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_movie2, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    public static class OverviewFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";


        public OverviewFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static OverviewFragment newInstance(int sectionNumber,MovieDb movieDb) {
            OverviewFragment fragment = new OverviewFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putString("synopsis",movieDb.getOverview());
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_movie_overview, container, false);
            TextView title = (TextView) rootView.findViewById(R.id.overview_title);
            Typeface type = Typeface.createFromAsset(getContext().getAssets(), "Atlantic_Cruise-Demo.ttf");
            title.setTypeface(type);
            title.setText("Overview");

            TextView synopsis = (TextView) rootView.findViewById(R.id.overview_synopsis);
            synopsis.setText(getArguments().getString("synopsis","Overview"));
            return rootView;
        }
    }

    public static class CastingFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";


        public CastingFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static CastingFragment newInstance(int sectionNumber,String[]actors, String[] characters, String director) {
            CastingFragment fragment = new CastingFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putStringArray("actors", actors);
            args.putStringArray("characters", characters);
            args.putString("director",director);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_movie_casting, container, false);
            TextView title = (TextView) rootView.findViewById(R.id.casting_title);
            TextView directorTitle = (TextView) rootView.findViewById(R.id.casting_director_title);
            TextView actorsTitle = (TextView) rootView.findViewById(R.id.casting_actors_title);
            Typeface type = Typeface.createFromAsset(getContext().getAssets(), "Atlantic_Cruise-Demo.ttf");
            title.setTypeface(type);
            directorTitle.setTypeface(type);
            actorsTitle.setTypeface(type);

            TextView director = (TextView) rootView.findViewById(R.id.casting_director_name);
            director.setText(getArguments().getString("director","Director name"));

            LinearLayout actors_layout = (LinearLayout) rootView.findViewById(R.id.casting_actors_names);

            putActors(actors_layout,getArguments().getStringArray("actors"),getArguments().getStringArray("characters"));
            return rootView;
        }

        private void putActors(LinearLayout actors_layout, String[] actors, String[] characters) {

            for (int i=0;i<actors.length;i++){
                TextView actor = new TextView(getContext());
                actor.setTextSize(TypedValue.COMPLEX_UNIT_DIP,18f);
                actor.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
                actor.setText(getString(R.string.actor_format,actors[i],characters[i]));
                actors_layout.addView(actor);
            }

        }
    }

    public static class RatingFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";


        public RatingFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static RatingFragment newInstance(int sectionNumber,float rate) {
            RatingFragment fragment = new RatingFragment();
            Bundle args = new Bundle();
            args.putFloat("rate", rate);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_movie_rating, container, false);
            TextView title = (TextView) rootView.findViewById(R.id.rating_title);
            TextView rate = (TextView) rootView.findViewById(R.id.rating_rate);
            Typeface type = Typeface.createFromAsset(getContext().getAssets(), "Atlantic_Cruise-Demo.ttf");
            title.setTypeface(type);

            String rate_2decimal=String.format("%.1f",getArguments().getFloat("rate"));

            rate.setText(getString(R.string.rating_format,rate_2decimal));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            switch (position){
                case 0:
                    return OverviewFragment.newInstance(0,getMovieDb());

                case 1:
                    return CastingFragment.newInstance(1,getActorsTab(),getCharactersTab(),getDirector());

                case 2:
                    return RatingFragment.newInstance(2, getUserRating());
            }

            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Overview";
                case 1:
                    return "Casting";
                case 2:
                    return "Rating";
            }
            return null;
        }
    }
}
