package com.xzchang.food2fork.app;

import android.app.Application;
import android.content.Context;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by xiangzhc on 16/11/2016.
 */

@Module
class AppModule {
    private final Application application;
    private final EventBus eventBus;

    AppModule(Application application) {
        this.application = application;
        eventBus = EventBus.builder().installDefaultEventBus();
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    EventBus provideEventBus() {
        return eventBus;
    }
}
