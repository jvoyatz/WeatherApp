package com.jvoyatz.weather.app.models.api.weather;

import androidx.annotation.NonNull;

import java.util.List;

public class WeatherItem{
	private String date;
	private String sunHour;
	private String mintempF;
	private String avgtempF;
	private String mintempC;
	private String totalSnowCm;
	private String maxtempF;
	private List<HourlyItem> hourly;
	private String avgtempC;
	private List<AstronomyItem> astronomy;
	private String uvIndex;
	private String maxtempC;

	public String getDate(){
		return date;
	}

	public String getSunHour(){
		return sunHour;
	}

	public String getMintempF(){
		return mintempF;
	}

	public String getAvgtempF(){
		return avgtempF;
	}

	public String getMintempC(){
		return mintempC;
	}

	public String getTotalSnowCm(){
		return totalSnowCm;
	}

	public String getMaxtempF(){
		return maxtempF;
	}

	public List<HourlyItem> getHourly(){
		return hourly;
	}

	public String getAvgtempC(){
		return avgtempC;
	}

	public List<AstronomyItem> getAstronomy(){
		return astronomy;
	}

	public String getUvIndex(){
		return uvIndex;
	}

	public String getMaxtempC(){
		return maxtempC;
	}

	@NonNull
	@Override
 	public String toString(){
		return 
			"WeatherItem{" + 
			"date = '" + date + '\'' + 
			",sunHour = '" + sunHour + '\'' + 
			",mintempF = '" + mintempF + '\'' + 
			",avgtempF = '" + avgtempF + '\'' + 
			",mintempC = '" + mintempC + '\'' + 
			",totalSnow_cm = '" + totalSnowCm + '\'' + 
			",maxtempF = '" + maxtempF + '\'' + 
			",hourly = '" + hourly + '\'' + 
			",avgtempC = '" + avgtempC + '\'' + 
			",astronomy = '" + astronomy + '\'' + 
			",uvIndex = '" + uvIndex + '\'' + 
			",maxtempC = '" + maxtempC + '\'' + 
			"}";
		}
}