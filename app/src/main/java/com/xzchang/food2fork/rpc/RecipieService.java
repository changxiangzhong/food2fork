package com.xzchang.food2fork.rpc;

import com.xzchang.food2fork.model.RecipieDetail;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by xiangzhc on 01/12/2016.
 */
@Singleton
public class RecipieService {
    private final RecipieApi api;
    private final EventBus bus;

    @Inject
    public RecipieService(Retrofit retrofit, EventBus bus) {
        this.api = retrofit.create(RecipieApi.class);
        this.bus = bus;
    }

    public void searchRecipie(String keyword) {
        Call<RecipieApi.RecipieList> call = api.searchRecipies(keyword, null, null);
        call.enqueue(new Callback<RecipieApi.RecipieList>() {
            @Override
            public void onResponse(Call<RecipieApi.RecipieList> call, Response<RecipieApi.RecipieList> response) {
                bus.post(new GetSearchEvent(response.body()));
            }

            @Override
            public void onFailure(Call<RecipieApi.RecipieList> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void fetchRecipieDetail(String recipieId) {
        Call<RecipieApi.RecipieWrap> call = api.getRecipieDetail(recipieId);
        call.enqueue(new Callback<RecipieApi.RecipieWrap>() {
            @Override
            public void onResponse(Call<RecipieApi.RecipieWrap> call, Response<RecipieApi.RecipieWrap> response) {
                bus.post(new GetIngredient(response.body().getRecipie()));
            }

            @Override
            public void onFailure(Call<RecipieApi.RecipieWrap> call, Throwable t) {

            }
        });
    }
}
