package com.xzchang.food2fork.view;

import dagger.Module;
import dagger.Subcomponent;

/**
 * Created by xiangzhc on 22/11/2016.
 */

@FragmentScope
@Subcomponent(
        modules = {
                RecipieDetailComponent.RecipieDetailModule.class
        }
)
public interface RecipieDetailComponent {
    RecipieDetailFragment inject(RecipieDetailFragment fragment);

    @Module
    class RecipieDetailModule extends BaseFragmentModule<RecipieDetailFragment> {
        public RecipieDetailModule(RecipieDetailFragment fragment) {
            super(fragment);
        }
    }
}
