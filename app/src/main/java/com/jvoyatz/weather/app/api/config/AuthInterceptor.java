package com.jvoyatz.weather.app.api.config;

import static com.jvoyatz.weather.app.Constants.QUERY_PARAM_TOKEN;
import static com.jvoyatz.weather.app.Constants.TOKEN;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * OK-Http interceptor which adds a 'key=[TOKEN_VALUE]' query param to each request
 */
public class AuthInterceptor implements Interceptor {

    @Inject
    public AuthInterceptor() {}

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();

        HttpUrl updatedUrl = request.url().newBuilder().addQueryParameter(QUERY_PARAM_TOKEN, TOKEN).build();
        Request updatedRequest = request.newBuilder().url(updatedUrl).build();

        return chain.proceed(updatedRequest);
    }
}
