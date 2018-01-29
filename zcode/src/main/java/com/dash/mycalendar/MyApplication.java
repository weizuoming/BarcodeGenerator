package com.dash.mycalendar;

import android.app.Application;

import com.dash.zxinglibrary.activity.ZXingLibrary;

/**
 * Created by Dash on 2018/1/29.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化zxing
        ZXingLibrary.initDisplayOpinion(this);

    }
}
