package com.xzchang.food2fork.network;

import android.app.Application;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xzchang.food2fork.BuildConfig;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xiangzhc on 16/11/2016.
 */

@Module
public class RetrofitModule {

    @Singleton
    @Provides
    Cache provideOkHttpCache(Application application) {
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(application.getCacheDir(), cacheSize);
        return cache;
    }

    @Singleton
    @Provides
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

    @Singleton
    @Provides
    Executor provideCallbackExecutor() {
        int cpuCnt = Runtime.getRuntime().availableProcessors();

        return new ThreadPoolExecutor(
                cpuCnt/2,
                cpuCnt,
                10,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>()
        );
    }

    @Singleton
    @Provides
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }

    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient(
            Cache cache,
            AuthInterceptor authInterceptor,
            HttpLoggingInterceptor loggingInterceptor
    ) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.cache(cache);
        builder.addInterceptor(authInterceptor);
        builder.addInterceptor(loggingInterceptor);
        return builder.build();
    }


    @Singleton
    @Provides
    Retrofit providePostRetrofit(Gson gson, OkHttpClient okHttpClient, Executor executor) {
        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BuildConfig.BASE_URL)
                .callbackExecutor(executor)
                .client(okHttpClient)
                .build();
        return retrofit;
    }
}
