package com.jvoyatz.weather.app.models.api.weather;

import androidx.annotation.NonNull;

public class WeatherRequestItem {
	private String query;
	private String type;

	public String getQuery(){
		return query;
	}

	public String getType(){
		return type;
	}

	@NonNull
	@Override
 	public String toString(){
		return 
			"RequestItem{" + 
			"query = '" + query + '\'' + 
			",type = '" + type + '\'' + 
			"}";
		}
}
