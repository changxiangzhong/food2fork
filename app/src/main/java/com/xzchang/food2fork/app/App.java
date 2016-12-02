package com.xzchang.food2fork.app;

import android.app.Application;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by xiangzhc on 16/11/2016.
 */

public class App extends Application {
    private static App singleton;

    public static App getInstance() {
        return singleton;
    }

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
