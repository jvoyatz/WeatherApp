package com.jvoyatz.weather.app.models;

import androidx.annotation.NonNull;

import com.jvoyatz.weather.app.models.api.error.ApiError;

import java.util.List;

/**
 * Custom class for handling exceptions caught either while executing
 * a network request etc.
 *
 * Contains an errorcode indicating the error received during network requests.
 */
public class WeatherAppException extends Exception{
    private Integer errorCode;
    private List<ApiError> errorItems;

    public WeatherAppException(String message) {
        super(message);
    }

    public WeatherAppException(String message, Integer errorCode, List<ApiError> errorItems) {
        super(message);
        this.errorCode = errorCode;
        this.errorItems = errorItems;
    }

    public WeatherAppException(String message, Throwable cause, Integer errorCode, List<ApiError> errorItems) {
        super(message, cause);
        this.errorCode = errorCode;
        this.errorItems = errorItems;
    }

    public List<ApiError> getErrorItems() {
        return errorItems;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public static WeatherAppException create(@NonNull String message){
        return new WeatherAppException(message);
    }

    public static WeatherAppException create(@NonNull String message, Integer errorCode, List<ApiError> errorItems){
        return new WeatherAppException(message, errorCode, errorItems);
    }

    public static WeatherAppException create(@NonNull String message, Throwable cause, Integer errorCode, List<ApiError> errorItems){
        return new WeatherAppException(message, cause, errorCode, errorItems);
    }

    @NonNull
    @Override
    public String toString() {
        return "WeatherAppException{" +
                "errorCode=" + errorCode +
                ", errorItems=" + errorItems +
                '}';
    }
}
