package com.xzchang.food2fork.rpc;

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
        Call<RecipieList> call = api.searchRecipies(keyword, null, null);
        call.enqueue(new Callback<RecipieList>() {
            @Override
            public void onResponse(Call<RecipieList> call, Response<RecipieList> response) {
                bus.post(new GetSearchEvent(response.body()));
            }

            @Override
            public void onFailure(Call<RecipieList> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
