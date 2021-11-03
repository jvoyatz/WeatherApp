package com.jvoyatz.weather.app.models.api.weather;

import androidx.annotation.NonNull;

import java.util.List;

public class HourlyItem{
	private String weatherCode;
	private String chanceofhightemp;
	private String feelsLikeF;
	private String cloudcover;
	private String windChillC;
	private String windspeedMiles;
	private String winddirDegree;
	private String dewPointC;
	private String chanceofthunder;
	private String chanceoffrost;
	private String dewPointF;
	private String humidity;
	private String winddir16Point;
	private String windChillF;
	private List<WeatherIconUrlItem> weatherIconUrl;
	private String tempF;
	private String precipMM;
	private List<WeatherDescItem> weatherDesc;
	private String chanceofrain;
	private String chanceofovercast;
	private String visibility;
	private String pressure;
	private String chanceofsunshine;
	private String windGustMiles;
	private String chanceofsnow;
	private String feelsLikeC;
	private String windspeedKmph;
	private String windGustKmph;
	private String chanceoffog;
	private String visibilityMiles;
	private String heatIndexC;
	private String time;
	private String precipInches;
	private String chanceofwindy;
	private String uvIndex;
	private String tempC;
	private String pressureInches;
	private String heatIndexF;
	private String chanceofremdry;

	public String getWeatherCode(){
		return weatherCode;
	}

	public String getChanceofhightemp(){
		return chanceofhightemp;
	}

	public String getFeelsLikeF(){
		return feelsLikeF;
	}

	public String getCloudcover(){
		return cloudcover;
	}

	public String getWindChillC(){
		return windChillC;
	}

	public String getWindspeedMiles(){
		return windspeedMiles;
	}

	public String getWinddirDegree(){
		return winddirDegree;
	}

	public String getDewPointC(){
		return dewPointC;
	}

	public String getChanceofthunder(){
		return chanceofthunder;
	}

	public String getChanceoffrost(){
		return chanceoffrost;
	}

	public String getDewPointF(){
		return dewPointF;
	}

	public String getHumidity(){
		return humidity;
	}

	public String getWinddir16Point(){
		return winddir16Point;
	}

	public String getWindChillF(){
		return windChillF;
	}

	public List<WeatherIconUrlItem> getWeatherIconUrl(){
		return weatherIconUrl;
	}

	public String getTempF(){
		return tempF;
	}

	public String getPrecipMM(){
		return precipMM;
	}

	public List<WeatherDescItem> getWeatherDesc(){
		return weatherDesc;
	}

	public String getChanceofrain(){
		return chanceofrain;
	}

	public String getChanceofovercast(){
		return chanceofovercast;
	}

	public String getVisibility(){
		return visibility;
	}

	public String getPressure(){
		return pressure;
	}

	public String getChanceofsunshine(){
		return chanceofsunshine;
	}

	public String getWindGustMiles(){
		return windGustMiles;
	}

	public String getChanceofsnow(){
		return chanceofsnow;
	}

	public String getFeelsLikeC(){
		return feelsLikeC;
	}

	public String getWindspeedKmph(){
		return windspeedKmph;
	}

	public String getWindGustKmph(){
		return windGustKmph;
	}

	public String getChanceoffog(){
		return chanceoffog;
	}

	public String getVisibilityMiles(){
		return visibilityMiles;
	}

	public String getHeatIndexC(){
		return heatIndexC;
	}

	public String getTime(){
		return time;
	}

	public String getPrecipInches(){
		return precipInches;
	}

	public String getChanceofwindy(){
		return chanceofwindy;
	}

	public String getUvIndex(){
		return uvIndex;
	}

	public String getTempC(){
		return tempC;
	}

	public String getPressureInches(){
		return pressureInches;
	}

	public String getHeatIndexF(){
		return heatIndexF;
	}

	public String getChanceofremdry(){
		return chanceofremdry;
	}

	@NonNull
	@Override
 	public String toString(){
		return 
			"HourlyItem{" + 
			"weatherCode = '" + weatherCode + '\'' + 
			",chanceofhightemp = '" + chanceofhightemp + '\'' + 
			",feelsLikeF = '" + feelsLikeF + '\'' + 
			",cloudcover = '" + cloudcover + '\'' + 
			",windChillC = '" + windChillC + '\'' + 
			",windspeedMiles = '" + windspeedMiles + '\'' + 
			",winddirDegree = '" + winddirDegree + '\'' + 
			",dewPointC = '" + dewPointC + '\'' + 
			",chanceofthunder = '" + chanceofthunder + '\'' + 
			",chanceoffrost = '" + chanceoffrost + '\'' + 
			",dewPointF = '" + dewPointF + '\'' + 
			",humidity = '" + humidity + '\'' + 
			",winddir16Point = '" + winddir16Point + '\'' + 
			",windChillF = '" + windChillF + '\'' + 
			",weatherIconUrl = '" + weatherIconUrl + '\'' + 
			",tempF = '" + tempF + '\'' + 
			",precipMM = '" + precipMM + '\'' + 
			",weatherDesc = '" + weatherDesc + '\'' + 
			",chanceofrain = '" + chanceofrain + '\'' + 
			",chanceofovercast = '" + chanceofovercast + '\'' + 
			",visibility = '" + visibility + '\'' + 
			",pressure = '" + pressure + '\'' + 
			",chanceofsunshine = '" + chanceofsunshine + '\'' + 
			",windGustMiles = '" + windGustMiles + '\'' + 
			",chanceofsnow = '" + chanceofsnow + '\'' + 
			",feelsLikeC = '" + feelsLikeC + '\'' + 
			",windspeedKmph = '" + windspeedKmph + '\'' + 
			",windGustKmph = '" + windGustKmph + '\'' + 
			",chanceoffog = '" + chanceoffog + '\'' + 
			",visibilityMiles = '" + visibilityMiles + '\'' + 
			",heatIndexC = '" + heatIndexC + '\'' + 
			",time = '" + time + '\'' + 
			",precipInches = '" + precipInches + '\'' + 
			",chanceofwindy = '" + chanceofwindy + '\'' + 
			",uvIndex = '" + uvIndex + '\'' + 
			",tempC = '" + tempC + '\'' + 
			",pressureInches = '" + pressureInches + '\'' + 
			",heatIndexF = '" + heatIndexF + '\'' + 
			",chanceofremdry = '" + chanceofremdry + '\'' + 
			"}";
		}
}