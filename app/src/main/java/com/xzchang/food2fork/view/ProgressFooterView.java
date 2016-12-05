package com.xzchang.food2fork.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.xzchang.food2fork.R;
import com.xzchang.food2fork.util.heteroadapter.BindableView;
import com.xzchang.food2fork.util.heteroadapter.ViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiangzhc on 05/12/2016.
 */

public class ProgressFooterView extends FrameLayout implements BindableView<ProgressFooterView.ProgressFooterViewModel>{
    @BindView(R.id.loading_indicator)
    ProgressBar progressBar;

    public ProgressFooterView(Context context) {
        super(context);
        initView(context, null, -1);
    }

    public ProgressFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs, -1);
    }

    public ProgressFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    public void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.footer_view_loading0, this, true);
        ButterKnife.bind(this);
    }

    @Override
    public void bind(ProgressFooterViewModel viewModel) {
        progressBar.setVisibility(View.VISIBLE);
    }


    public static class ProgressFooterViewModel extends ViewModel<Void> {

        public ProgressFooterViewModel(Void model) {
            super(model);
        }

        @Override
        public int getLayout() {
            return R.layout.footer_view_loading;
        }
    }

}
