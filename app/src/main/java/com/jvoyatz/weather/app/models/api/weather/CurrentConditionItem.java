package com.jvoyatz.weather.app.models.api.weather;

import androidx.annotation.NonNull;

import java.util.List;

public class CurrentConditionItem{
	private String precipMM;
	private String observationTime;
	private List<WeatherDescItem> weatherDesc;
	private String visibility;
	private String weatherCode;
	private String feelsLikeF;
	private String pressure;
	private String tempC;
	private String tempF;
	private String cloudcover;
	private String windspeedMiles;
	private String winddirDegree;
	private String feelsLikeC;
	private String windspeedKmph;
	private String humidity;
	private String visibilityMiles;
	private String precipInches;
	private String uvIndex;
	private String winddir16Point;
	private List<WeatherIconUrlItem> weatherIconUrl;
	private String pressureInches;

	public String getPrecipMM(){
		return precipMM;
	}

	public String getObservationTime(){
		return observationTime;
	}

	public List<WeatherDescItem> getWeatherDesc(){
		return weatherDesc;
	}

	public String getVisibility(){
		return visibility;
	}

	public String getWeatherCode(){
		return weatherCode;
	}

	public String getFeelsLikeF(){
		return feelsLikeF;
	}

	public String getPressure(){
		return pressure;
	}

	public String getTempC(){
		return tempC;
	}

	public String getTempF(){
		return tempF;
	}

	public String getCloudcover(){
		return cloudcover;
	}

	public String getWindspeedMiles(){
		return windspeedMiles;
	}

	public String getWinddirDegree(){
		return winddirDegree;
	}

	public String getFeelsLikeC(){
		return feelsLikeC;
	}

	public String getWindspeedKmph(){
		return windspeedKmph;
	}

	public String getHumidity(){
		return humidity;
	}

	public String getVisibilityMiles(){
		return visibilityMiles;
	}

	public String getPrecipInches(){
		return precipInches;
	}

	public String getUvIndex(){
		return uvIndex;
	}

	public String getWinddir16Point(){
		return winddir16Point;
	}

	public List<WeatherIconUrlItem> getWeatherIconUrl(){
		return weatherIconUrl;
	}

	public String getPressureInches(){
		return pressureInches;
	}

	@NonNull
	@Override
 	public String toString(){
		return 
			"CurrentConditionItem{" + 
			"precipMM = '" + precipMM + '\'' + 
			",observation_time = '" + observationTime + '\'' + 
			",weatherDesc = '" + weatherDesc + '\'' + 
			",visibility = '" + visibility + '\'' + 
			",weatherCode = '" + weatherCode + '\'' + 
			",feelsLikeF = '" + feelsLikeF + '\'' + 
			",pressure = '" + pressure + '\'' + 
			",temp_C = '" + tempC + '\'' + 
			",temp_F = '" + tempF + '\'' + 
			",cloudcover = '" + cloudcover + '\'' + 
			",windspeedMiles = '" + windspeedMiles + '\'' + 
			",winddirDegree = '" + winddirDegree + '\'' + 
			",feelsLikeC = '" + feelsLikeC + '\'' + 
			",windspeedKmph = '" + windspeedKmph + '\'' + 
			",humidity = '" + humidity + '\'' + 
			",visibilityMiles = '" + visibilityMiles + '\'' + 
			",precipInches = '" + precipInches + '\'' + 
			",uvIndex = '" + uvIndex + '\'' + 
			",winddir16Point = '" + winddir16Point + '\'' + 
			",weatherIconUrl = '" + weatherIconUrl + '\'' + 
			",pressureInches = '" + pressureInches + '\'' + 
			"}";
		}
}