package com.xzchang.food2fork.util.heteroadapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by xiangzhc on 05/12/2016.
 */

public class BindableViewHolder<T extends ViewModel> extends RecyclerView.ViewHolder {
    protected BindableView<T> bindableView;

    public BindableViewHolder(View itemView) {
        super(itemView);
        bindableView = (BindableView<T>) itemView;
    }

    public void bind(T viewModel) {
        bindableView.bind(viewModel);
    }

    public View getView() {
        return (View) bindableView;
    }
}
