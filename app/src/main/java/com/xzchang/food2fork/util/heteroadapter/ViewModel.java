package com.xzchang.food2fork.util.heteroadapter;

import android.support.annotation.LayoutRes;

import java.lang.reflect.Constructor;

/**
 * Created by xiangzhc on 05/12/2016.
 */

abstract public class ViewModel<T> {
    private final T wrapped;

    public ViewModel(T model) {
        this.wrapped = model;
    }

    public T getWrapped() {
        return wrapped;
    }

    abstract public @LayoutRes int getLayout();

}
