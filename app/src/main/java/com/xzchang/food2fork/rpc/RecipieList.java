package com.xzchang.food2fork.rpc;

import com.xzchang.food2fork.model.Recipie;

/**
 * Created by xiangzhc on 02/12/2016.
 */

public class RecipieList {
    private int count;
    private Recipie[] recipes;

    public int getCount() {
        return count;
    }

    public Recipie[] getRecipes() {
        return recipes;
    }
}
