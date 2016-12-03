package com.xzchang.food2fork.rpc;

import com.xzchang.food2fork.model.RecipieDetail;

/**
 * Created by xiangzhc on 03/12/2016.
 */

public class GetIngredient {
    public final RecipieDetail detail;

    public GetIngredient(RecipieDetail detail) {
        this.detail = detail;
    }
}
