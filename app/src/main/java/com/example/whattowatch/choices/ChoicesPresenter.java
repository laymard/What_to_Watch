package com.example.whattowatch.choices;

import android.graphics.drawable.GradientDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.whattowatch.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Lolo on 09/05/2016.
 */
public class ChoicesPresenter {

    private ChoicesActivity activity;
    private HashMap<String,Integer> genreToId;
    private ArrayList<GenreItem> genreToPut;

    public ChoicesPresenter(ChoicesActivity act){
        this.activity = act;
        this.genreToId = new HashMap<String,Integer>();
        this.genreToPut = new ArrayList<>();

        GenreItem g1 = new GenreItem("Action",28);
        GenreItem g2 = new GenreItem("Adventure",12);
        GenreItem g3 = new GenreItem("Animation",16);
        GenreItem g4 = new GenreItem("Comedy",35);

        genreToPut.add(g1);
        genreToPut.add(g2);
        genreToPut.add(g3);
        genreToPut.add(g4);

        genreToId.put("action",28);
        genreToId.put("adventure",12);
        genreToId.put("animation",16);
        genreToId.put("comedy",35);

    }

    public int getId(String genre){

        for (GenreItem i:genreToPut
             ) {
            if(i.getName().equals(genre)){
                return i.getId();
            }

        }
        return 0;
    }

    public void setAllLayerPicBlack(){
        LinearLayout genres = this.activity.getLayout_genre_pictured();
        for (int i=0;i< genres.getChildCount();i++){
            LinearLayout genre = (LinearLayout) genres.getChildAt(i);
            ImageView toChange = (ImageView) genre.getChildAt(0);
            String pictureName = this.genreToPut.get(i).getBlackPictureName();
            int idDrawable = toChange.getContext().getResources().getIdentifier(pictureName,"drawable",toChange.getContext().getPackageName());
            toChange.setImageResource(idDrawable);
        }
    }

    public int getPostionByName(String name){
        int i=0;
        for (i=0;i<this.genreToPut.size();i++){
            if(name.equals(genreToPut.get(i).getName())){
                return i;
            }
        }
        return 0;
    }

    public void setLayerWhite(LinearLayout genreSelected){
        ImageView toChange = (ImageView) genreSelected.getChildAt(0);
        String tag = (String) genreSelected.getTag();
        String pictureName = this.genreToPut.get(getPostionByName(tag)).getWhitePictureName();
        int idDrawable = toChange.getContext().getResources().getIdentifier(pictureName,"drawable",toChange.getContext().getPackageName());
        toChange.setImageResource(idDrawable);
    }

    public LinearLayout generateGenreLayout(GenreItem genreItem, LinearLayout picturedGenreLayout){
        LinearLayout genre = new LinearLayout(activity);
        ImageView genreIcon = new ImageView(activity);
        TextView genreLabel = new TextView(activity);

        genre.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        genre.setOrientation(LinearLayout.VERTICAL);
        genre.setGravity(Gravity.CENTER_VERTICAL);
        genre.setWeightSum(4);
        genre.setTag(genreItem.getName());
        genre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAllLayerPicBlack();
                setLayerWhite((LinearLayout)v);
                activity.setSelectedGenre((LinearLayout)v);


            }
        });

        genreIcon.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT,1f));
        int idDrawable = genreIcon.getContext().getResources().getIdentifier(genreItem.getBlackPictureName(),"drawable",genreIcon.getContext().getPackageName());
        genreIcon.setImageResource(idDrawable);
        genreIcon.setTag(genreItem.getName()+"_pic");

        genreLabel.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT,3f));
        genreLabel.setText(genreItem.getName());
        genreLabel.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);
        genreLabel.setTextColor(genreLabel.getContext().getResources().getColor(R.color.colorGray));
        genreLabel.setGravity(Gravity.CENTER);

        genre.addView(genreIcon);
        genre.addView(genreLabel);
        return genre;

    }

    public void addGenres(LinearLayout picturedGenreLayout) {

        for (int i=0;i<genreToPut.size();i++){
            picturedGenreLayout.addView(generateGenreLayout(genreToPut.get(i),picturedGenreLayout));
        }


    }

    private class GenreItem {
        private String name;
        private int id;
        private String fileBaseName;

        public GenreItem(String name, int id){
            this.name=name;
            this.id=id;
            this.fileBaseName=name.toLowerCase()+"_";
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getBlackPictureName(){
            return this.fileBaseName+"b";
        }


        public String getWhitePictureName(){
            return this.fileBaseName+"w";
        }

    }
}
