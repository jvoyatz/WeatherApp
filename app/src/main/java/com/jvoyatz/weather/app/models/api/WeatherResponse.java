package com.jvoyatz.weather.app.models.api;

import androidx.annotation.NonNull;

import com.jvoyatz.weather.app.models.api.weather.WeatherData;

public class WeatherResponse {
	private WeatherData data;

	public WeatherData getData(){
		return data;
	}

	@NonNull
	@Override
 	public String toString(){
		return 
			"Response{" + 
			"data = '" + data + '\'' + 
			"}";
		}
}
