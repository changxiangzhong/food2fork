package com.xzchang.food2fork.view;

import dagger.Module;
import dagger.Provides;

/**
 * Created by xiangzhc on 22/11/2016.
 */

@Module
public abstract class BaseActivityModule<T extends BaseActivity> {
    protected final T activity;

    public BaseActivityModule(T activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    public T provideActivity() {
        return activity;
    }
}
