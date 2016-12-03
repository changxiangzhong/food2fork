package com.xzchang.food2fork.view;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.xzchang.food2fork.R;
import com.xzchang.food2fork.app.AppComponent;
import com.xzchang.food2fork.model.Recipie;
import com.xzchang.food2fork.rpc.GetIngredient;
import com.xzchang.food2fork.rpc.RecipieService;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;

import javax.inject.Inject;

/**
 * Created by xiangzhc on 03/12/2016.
 */

public class RecipieDetailFragment extends BaseFragment {
    public static final String PARAM_RECIPIE_STUB = "PARAM_RECIPIE_STUB";
    private Recipie stub;
    private boolean hasContent;

    @Inject
    RecipieService recipieService;

    public static RecipieDetailFragment newInstance(Serializable serializable) {
        Bundle args = new Bundle();
        args.putSerializable(PARAM_RECIPIE_STUB, serializable);
        RecipieDetailFragment f = new RecipieDetailFragment();
        f.setArguments(args);
        return f;
    }

    @Override
    protected boolean hasContent() {
        return hasContent;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        appComponent.plus(new RecipieDetailComponent.RecipieDetailModule(this)).inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_recipie_detail;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stub = (Recipie) getArguments().getSerializable(PARAM_RECIPIE_STUB);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recipieService.fetchRecipieDetail(stub.getRecipieId());
        onStartLoading();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetRecipieDetail(GetIngredient event) {
        hasContent = true;
    }
}
