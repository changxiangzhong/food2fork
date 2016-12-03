package com.xzchang.food2fork.view;

import dagger.Module;
import dagger.Subcomponent;

/**
 * Created by xiangzhc on 22/11/2016.
 */

@FragmentScope
@Subcomponent(
        modules = {
                RecipieListComponent.RecipieListModule.class
        }
)
public interface RecipieListComponent {
    RecipieListFragment inject(RecipieListFragment fragment);

    @Module
    class RecipieListModule extends BaseFragmentModule<RecipieListFragment> {
        public RecipieListModule(RecipieListFragment fragment) {
            super(fragment);
        }
    }
}
