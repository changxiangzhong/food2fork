package com.xzchang.food2fork.util.heteroadapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * Created by xiangzhc on 05/12/2016.
 */

abstract public class HeterogenousAdapter<T extends BindableViewHolder> extends RecyclerView.Adapter<T> {
    private static final String TAG = HeterogenousAdapter.class.getSimpleName();
    protected final ArrayList<ViewModel> viewModels;
    private Constructor<T> constructor;

    public HeterogenousAdapter(Class<T> clazz) {
        viewModels = new ArrayList<>();
        try {
            constructor = clazz.getConstructor(View.class);
        } catch (NoSuchMethodException e) {
            Log.v(TAG, "Cannot find a proper constructor. Is the " + clazz.getSimpleName() + " standalone class? Non-static inner class is not acceptable ");
            e.printStackTrace();
        }
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
    final public T onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        try {
            return constructor.newInstance(v);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    final public int getItemCount() {
        return viewModels.size();
    }
}
