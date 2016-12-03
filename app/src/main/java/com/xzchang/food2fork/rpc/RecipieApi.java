package com.xzchang.food2fork.rpc;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by xiangzhc on 01/12/2016.
 */

public interface RecipieApi {
    @GET("/api/get")
    Call<Void> getRecipieDetail();


    @GET("/api/search")
    Call<RecipieList> searchRecipies(
            @Query("q") String searchQuery,
            @Query("sort") @SortOption String sort,
            @Query("page") Integer page
            );

    @Retention(SOURCE)
    @StringDef({
            ORDER_BY_TRENDING,
            ORDER_BY_RATING
    })
    public @interface SortOption{}

    public static final String ORDER_BY_RATING = "r";
    public static final String ORDER_BY_TRENDING = "t";

}
