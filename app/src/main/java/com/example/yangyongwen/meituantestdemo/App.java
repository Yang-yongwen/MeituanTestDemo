package com.example.yangyongwen.meituantestdemo;

import android.app.Application;

/**
 * Created by samsung on 2016/6/30.
 */
public class App extends Application {


    private static Application instance;


    @Override
    public void onCreate(){
        super.onCreate();
        instance=this;
    }

    public static Application getInstance(){
        return instance;
    }

}
