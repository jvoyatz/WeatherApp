package com.jvoyatz.weather.app.models.api.weather;

import androidx.annotation.NonNull;

import java.util.List;

public class WeatherData {
	private List<WeatherRequestItem> request;
	private List<CurrentConditionItem> currentCondition;
	private List<WeatherItem> weather;
	private List<TimeZoneItem> timeZone;
	private List<ClimateAveragesItem> climateAverages;

	public List<WeatherRequestItem> getRequest(){
		return request;
	}

	public List<CurrentConditionItem> getCurrentCondition(){
		return currentCondition;
	}

	public List<WeatherItem> getWeather(){
		return weather;
	}

	public List<TimeZoneItem> getTimeZone(){
		return timeZone;
	}

	public List<ClimateAveragesItem> getClimateAverages(){
		return climateAverages;
	}

	@NonNull
	@Override
 	public String toString(){
		return 
			"Data{" + 
			"request = '" + request + '\'' + 
			",current_condition = '" + currentCondition + '\'' + 
			",weather = '" + weather + '\'' + 
			",time_zone = '" + timeZone + '\'' + 
			",climateAverages = '" + climateAverages + '\'' + 
			"}";
		}
}