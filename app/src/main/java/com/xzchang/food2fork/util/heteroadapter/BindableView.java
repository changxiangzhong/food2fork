package com.xzchang.food2fork.util.heteroadapter;

/**
 * Created by xiangzhc on 05/12/2016.
 */

public interface BindableView<T extends ViewModel> {
    public void bind(T viewModel);
}
