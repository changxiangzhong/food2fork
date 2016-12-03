package com.xzchang.food2fork.rpc;

import android.support.annotation.StringDef;

import com.google.gson.annotations.SerializedName;
import com.xzchang.food2fork.model.Recipie;
import com.xzchang.food2fork.model.RecipieDetail;

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
    Call<RecipieWrap> getRecipieDetail(
            @Query("rId") String recipieId
    );


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

    /**
     * Created by xiangzhc on 02/12/2016.
     */

    class RecipieList {
        private int count;
        private Recipie[] recipes;

        public int getCount() {
            return count;
        }

        public Recipie[] getRecipes() {
            return recipes;
        }
    }

    class RecipieWrap {
        @SerializedName(value = "recipe")
        private RecipieDetail recipie;

        public RecipieDetail getRecipie() {
            return recipie;
        }
    }
}
