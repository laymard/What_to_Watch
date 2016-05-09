package com.example.whattowatch.choices;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.example.whattowatch.R;

public class ChoicesActivity extends AppCompatActivity {
    private LinearLayout genre_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choices);
        this.genre_layout = (LinearLayout) findViewById(R.id.layout_genre);
    }


    public void action(View view) {
        Button  button = (Button)view;
        int id = button.getId();
        Log.i("Choices","ID: "+id);
        System.out.println("Id of button touch: "+id);
        int children = genre_layout.getChildCount();
        for (int i =0;i<children;i++){
            Button child = (Button)genre_layout.getChildAt(i);
            if(id == child.getId()){
                button.setBackgroundColor(getResources().getColor(R.color.colorBlueNeon));
            }else{
                child.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            }
        }

    }
}
