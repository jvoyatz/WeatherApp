package com.jvoyatz.weather.app.api.config;
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

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.gson.Gson;
import com.jvoyatz.weather.app.models.api.config.ApiResponse;
import com.jvoyatz.weather.app.di.NetworkModule;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.inject.Inject;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;


/**
 * Since we own this class we can inject our GSON instance using the @Inject
 * annotation in constructor. Remember that this GSON instance has been added
 * to the graph in the NetworkModule.
 * See {@link NetworkModule#provideGson()}
 */
public class LiveDataCallAdapterFactory extends CallAdapter.Factory {

    @NonNull
    private final Gson gson;

    @Inject
    public LiveDataCallAdapterFactory(@NonNull Gson gson) {
        this.gson = gson;
    }

    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        if (getRawType(returnType) != LiveData.class) {
            return null;
        }
        Type observableType = getParameterUpperBound(0, (ParameterizedType) returnType);
        Class<?> rawObservableType = getRawType(observableType);
        if (rawObservableType != ApiResponse.class) {
            throw new IllegalArgumentException("type must be a resource");
        }
        if (! (observableType instanceof ParameterizedType)) {
            throw new IllegalArgumentException("resource must be parameterized");
        }
        Type bodyType = getParameterUpperBound(0, (ParameterizedType) observableType);
        return new LiveDataCallAdapter<>(bodyType, gson);
    }
}