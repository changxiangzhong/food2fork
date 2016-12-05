package com.xzchang.food2fork.util.heteroadapter;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by xiangzhc on 05/12/2016.
 */

abstract public class HeterogenousAdapter<T extends BindableViewHolder> extends RecyclerView.Adapter<T>{
    protected ArrayList<ViewModel> viewModels;

    protected HeterogenousAdapter() {
        viewModels = new ArrayList<>();
    }

    @Override
    final public int getItemViewType(int position) {
        return viewModels.get(position).getLayout();
    }

    @Override
    final public void onBindViewHolder(T holder, int position) {
        holder.bind(viewModels.get(position));
    }

    @Override
    final public int getItemCount() {
        return viewModels.size();
    }
}
