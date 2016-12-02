package com.xzchang.food2fork.network;

import android.os.Build;

import com.xzchang.food2fork.BuildConfig;

import java.io.IOException;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by xiangzhc on 01/12/2016.
 */
@Singleton
public class AuthInterceptor implements Interceptor {
    @Inject
    public AuthInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalReq = chain.request();
        HttpUrl originalUrl = originalReq.url();
        HttpUrl newUrl = originalUrl.newBuilder().addQueryParameter("key", BuildConfig.API_KEY).build();

        /**
         * Handle token refreshing here, so that the refreshing is transparent for the APP.
         * Broadcast a token refreshing failure through EventBus and logout the user in case of refreshing failure.
         */

        Request.Builder builder = originalReq.newBuilder();
        Request newRequest = builder.url(newUrl).build();
        return chain.proceed(newRequest);
    }
}
