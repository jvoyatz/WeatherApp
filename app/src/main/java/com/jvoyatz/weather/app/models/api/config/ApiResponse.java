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

package com.jvoyatz.weather.app.models.api.config;

import android.util.ArrayMap;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.jvoyatz.weather.app.Constants;
import com.jvoyatz.weather.app.models.api.error.ApiErrorWrapper;
import com.jvoyatz.weather.app.models.exceptions.WeatherAppException;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Response;
import timber.log.Timber;

/**
 * Common class used by API responses.
 * @param <T> the type of the provided data
 */
public class ApiResponse<T> {
    private static final String LINK_HEADER = "LINK";
    private static final String REGEX_LINK = "\"<([^>]*)>[\\\\s]*;[\\\\s]*rel=\"([a-zA-Z0-9]+)\"\"";
    private static final String REGEX_PAGE = "page=(\\d)+";
    private static final Pattern LINK_PATTERN = Pattern.compile(REGEX_LINK);
    private static final Pattern PAGE_PATTERN = Pattern.compile(REGEX_PAGE);
    private static final String NEXT_LINK = "next";
    @NonNull
    private Map<String, String> links = Collections.emptyMap();

    private ApiResponse(){}

    //factory methods

    /**
     * Factory method for creating {@link ApiErrorResponse} subclass instance.
     *
     * @param error The throwable given from network engine
     * @return ApiErrorResponse
     */
    public static <T> ApiResponse<T> create(Throwable error) {
        return new ApiErrorResponse<>(error.getMessage() != null ? error.getMessage() : Constants.ERROR_GENERIC_MESSAGE);
    }

    public static <T> ApiResponse<T> create(WeatherAppException exception) {
        return new ApiErrorResponse<>(Constants.ERROR_GENERIC_MESSAGE, exception);
    }

    /**
     * Checks the main response if it is successful or not and creates the appropriate
     * instance of {@link ApiResponse } subclasses.
     *
     * @param response see Retrofit {@link Response} for more details.
     */
    public static <T> ApiResponse<T> create(@NonNull Gson gson, @NonNull Response<T> response){
        ApiResponse<T> apiResponse;

        if(!response.isSuccessful()){
            try {
                ApiErrorWrapper apiError = gson.fromJson(response.errorBody().string(), ApiErrorWrapper.class);
                WeatherAppException weatherAppException = null;
                if(apiError != null && apiError.getData() != null){
                    int code = response.code();
                    weatherAppException = WeatherAppException.create(Integer.toString(code), code, apiError.getData().getError());
                }else{
                    weatherAppException = WeatherAppException.create(response.errorBody().string());
                }
                apiResponse = create(weatherAppException);
            } catch (Exception e) {
                Timber.e(e);
                apiResponse = create(e);
            }
        }else{
            T body = response.body();

            if(body == null || response.code() == 204){ // 204 is empty response
                apiResponse = new ApiEmptyResponse<>();
            } else{
                apiResponse = new ApiSuccessResponse<>(body);
            }
        }

        //checkLinksHeader(response.headers().get(LINK_HEADER));

        return apiResponse;
    }

    //helpers
    public boolean isSuccessful() {
        return this instanceof ApiSuccessResponse || this instanceof ApiEmptyResponse;
    }

    @SuppressWarnings("unused")
    public void checkLinksHeader(String linkHeader) {
        if (linkHeader == null) {
            links = Collections.emptyMap();
        } else {
            links = new ArrayMap<>();
            Matcher matcher = LINK_PATTERN.matcher(linkHeader);

            while (matcher.find()) {
                int count = matcher.groupCount();
                if (count == 2) {
                    links.put(matcher.group(2), matcher.group(1));
                }
            }
        }
    }

    @SuppressWarnings("unused")
    public Integer getNextPage() {
        String next = links.get(NEXT_LINK);
        if (next == null) {
            return null;
        }
        Matcher matcher = PAGE_PATTERN.matcher(next);
        if (!matcher.find() || matcher.groupCount() != 1) {
            return null;
        }
        try {
            return Integer.parseInt(Objects.requireNonNull(matcher.group(1)));
        } catch (NumberFormatException ex) {
            Timber.e(ex.getCause(), "getNextPage: ");
            return null;
        }
    }

    //SUBCLASSES

    /**
     * Generic success response from api
     * @param <T> the type of the response object
     */
    public static class ApiSuccessResponse<T> extends ApiResponse<T> {
        private final T body;

        ApiSuccessResponse(@NonNull T body) {
            this.body = body;
        }

        public T getBody() {
            return body;
        }
    }

    /**
     * Generic Error response from API
     */
    public static class ApiErrorResponse<T> extends ApiResponse<T> {
        private final String errorMessage;
        private WeatherAppException exception;

        public ApiErrorResponse(String errorMessage, @NonNull WeatherAppException exception) {
            this.errorMessage = errorMessage;
            this.exception = exception;
        }
        ApiErrorResponse(@NonNull String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public WeatherAppException getException() {
            return exception;
        }
    }

    /**
     * Separate class for HTTP 204 responses so that we can make ApiSuccessResponse's body non-null.
     */
    public static class ApiEmptyResponse<T> extends ApiResponse<T> { }
}