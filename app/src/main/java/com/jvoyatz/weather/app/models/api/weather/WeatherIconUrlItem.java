package com.jvoyatz.weather.app.models.api.weather;

import androidx.annotation.NonNull;

public class WeatherIconUrlItem{
	private String value;

	public String getValue(){
		return value;
	}

	@NonNull
	@Override
 	public String toString(){
		return 
			"WeatherIconUrlItem{" + 
			"value = '" + value + '\'' + 
			"}";
		}
}
