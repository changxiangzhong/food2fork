package com.xzchang.food2fork.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.xzchang.food2fork.app.App;
import com.xzchang.food2fork.app.AppComponent;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

/**
 * Created by xiangzhc on 02/12/2016.
 */

public abstract class BaseFragment extends Fragment {
    @Inject
    EventBus bus;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App app = App.getInstance();
        setupFragmentComponent(app.getAppComponent());
    }

    abstract protected void setupFragmentComponent(AppComponent app);

    @Override
    public void onPause() {
        bus.unregister(this);
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        bus.register(this);
    }


}
