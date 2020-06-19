package com.com.example.goods.app;

import android.app.Application;

import com.xuexiang.xui.XUI;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化XUI
        XUI.init(this);
        XUI.debug(true);

    }
}
