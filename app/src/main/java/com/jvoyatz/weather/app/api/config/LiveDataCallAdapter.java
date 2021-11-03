/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jvoyatz.weather.app.api.config;


import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.gson.Gson;
import com.jvoyatz.weather.app.models.api.config.ApiResponse;

import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A Retrofit adapter that converts the Call into a LiveData holding
 * an ApiResponse instance.
 *
 * Modified so as to be able to deserialize JSON error response into a Java Object.
 *
 * @param <R> the type wrapped inside ApiResponse
 */
public class LiveDataCallAdapter<R> implements CallAdapter<R, LiveData<ApiResponse<R>>> {
    private final Type responseType;
    private final Gson gson;

    public LiveDataCallAdapter(Type responseType, Gson gson) {
        this.responseType = responseType;
        this.gson = gson;
    }

    @NonNull
    @Override
    public Type responseType() {
        return responseType;
    }

    @NonNull
    @Override
    public LiveData<ApiResponse<R>> adapt(@NonNull Call<R> call) {
        return new LiveData<ApiResponse<R>>() {
            final AtomicBoolean started = new AtomicBoolean(false);
            @Override
            protected void onActive() {
                super.onActive();
                if (started.compareAndSet(false, true)) {
                    call.enqueue(new Callback<R>() {
                        @Override
                        public void onResponse(@NonNull Call<R> call,@NonNull Response<R> response) {
                            postValue(ApiResponse.create(gson, response));
                        }

                        @Override
                        public void onFailure(@NonNull Call<R> call, @NonNull Throwable throwable) {
                            postValue(ApiResponse.create(throwable));
                        }
                    });
                }
            }
        };
    }
}