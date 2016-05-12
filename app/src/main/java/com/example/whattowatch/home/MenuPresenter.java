package com.example.whattowatch.home;


/**
 * Created by Lolo on 08/05/2016.
 */
public class MenuPresenter {
    private MenuActivity activity;

    public MenuPresenter(MenuActivity menuActivity) {
        this.activity = menuActivity;

    }

    public void sendRequest() {
        new MenuInteractor().execute(this);
        this.activity.setText("connarddetarem");
    }



}