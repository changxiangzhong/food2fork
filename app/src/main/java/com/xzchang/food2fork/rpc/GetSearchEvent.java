package com.xzchang.food2fork.rpc;

import com.xzchang.food2fork.model.Recipie;

/**
 * Created by xiangzhc on 02/12/2016.
 */

public class GetSearchEvent {
    public final Recipie[] recipies;
    public GetSearchEvent(RecipieList body) {
        recipies = body.getRecipes();
    }
}
