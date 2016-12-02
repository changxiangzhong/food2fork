package com.xzchang.food2fork.app;

import android.app.Application;

import com.xzchang.food2fork.network.RetrofitModule;
import com.xzchang.food2fork.view.RecipieListComponent;

import javax.inject.Singleton;

import dagger.Component;


/**
 * Created by xiangzhc on 16/11/2016.
 */

@Singleton
@Component(modules = {
        AppModule.class,
        RetrofitModule.class
})

public interface AppComponent {
    Application app();
    RecipieListComponent plus(RecipieListComponent.RecipieListModule module);
}
