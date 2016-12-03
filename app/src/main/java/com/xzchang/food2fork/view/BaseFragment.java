package com.xzchang.food2fork.view;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xzchang.food2fork.R;
import com.xzchang.food2fork.app.App;
import com.xzchang.food2fork.app.AppComponent;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Optional;

/**
 * Created by xiangzhc on 02/12/2016.
 */

public abstract class BaseFragment extends Fragment {
    @Inject
    EventBus bus;

    @Nullable
    @BindView(R.id.empty_indicator)
    protected TextView emptyIndicator;

    @Nullable
    @BindView(R.id.loading_indicator)
    protected ProgressBar loadingIndicator;

    protected void onEndLoading() {
        if (loadingIndicator == null || emptyIndicator == null) return;

        loadingIndicator.setVisibility(View.GONE);
        emptyIndicator.setVisibility(hasContent()? View.GONE: View.VISIBLE);
    }

    protected void onStartLoading() {
        if (loadingIndicator == null || emptyIndicator == null) return;

        loadingIndicator.setVisibility(hasContent()? View.GONE: View.VISIBLE);
        emptyIndicator.setVisibility(View.GONE);
    }

    abstract protected boolean hasContent();

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentRoot = inflater.inflate(getLayout(), container, false);
        ButterKnife.bind(this, fragmentRoot);
        return fragmentRoot;
    }

    @LayoutRes
    protected abstract int getLayout();


}
