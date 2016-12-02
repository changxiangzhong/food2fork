package com.xzchang.food2fork.view;

import dagger.Module;
import dagger.Provides;

/**
 * Created by xiangzhc on 22/11/2016.
 */

@Module
public abstract class BaseFragmentModule<T extends BaseFragment> {
    protected final T fragment;

    public BaseFragmentModule(T activity) {
        this.fragment = activity;
    }

    @Provides
    @FragmentScope
    public T provideActivity() {
        return fragment;
    }
}
