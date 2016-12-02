package com.xzchang.food2fork.rpc;

import com.xzchang.food2fork.model.Recipie;

/**
 * Created by xiangzhc on 02/12/2016.
 */

public class RecipieList {
    private int count;
    private Recipie[] recipe;

    public int getCount() {
        return count;
    }

    public Recipie[] getRecipe() {
        return recipe;
    }
}
