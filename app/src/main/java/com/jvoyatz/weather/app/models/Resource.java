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
package com.jvoyatz.weather.app.models;

import static com.jvoyatz.weather.app.models.Status.ERROR;
import static com.jvoyatz.weather.app.models.Status.LOADING;
import static com.jvoyatz.weather.app.models.Status.SUCCESS;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jvoyatz.weather.app.models.exceptions.WeatherAppException;

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
public class Resource<T> {

    @NonNull
    public final Status status;

    @Nullable
    public final String message;

    @Nullable
    public final T data;

    @Nullable
    public final WeatherAppException weatherAppException;

    public Resource(@NonNull Status status, @Nullable T data, @Nullable String message, @Nullable WeatherAppException weatherAppException) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.weatherAppException = weatherAppException;
    }

    public static <T> Resource<T> success(@Nullable T data) {
        return new Resource<>(SUCCESS, data, null, null);
    }

    public static <T> Resource<T> error(String msg, @Nullable T data, WeatherAppException weatherAppException) {
        return new Resource<>(ERROR, data, msg, weatherAppException);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(LOADING, data, null, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Resource<?> resource = (Resource<?>) o;

        if (status != resource.status) {
            return false;
        }
        if (message != null ? !message.equals(resource.message) : resource.message != null) {
            return false;
        }
        return data != null ? data.equals(resource.data) : resource.data == null;
    }

    @Override
    public int hashCode() {
        int result = status.hashCode();
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}