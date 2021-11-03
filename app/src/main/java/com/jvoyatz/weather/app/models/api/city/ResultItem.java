package com.jvoyatz.weather.app.models.api.city;

import java.util.List;

public class ResultItem{
	private List<CountryItem> country;
	private List<AreaNameItem> areaName;
	private Timezone timezone;
	private String latitude;
	private List<RegionItem> region;
	private String longitude;
	private String population;
	private List<WeatherUrlItem> weatherUrl;

	public List<CountryItem> getCountry(){
		return country;
	}

	public List<AreaNameItem> getAreaName(){
		return areaName;
	}

	public Timezone getTimezone(){
		return timezone;
	}

	public String getLatitude(){
		return latitude;
	}

	public List<RegionItem> getRegion(){
		return region;
	}

	public String getLongitude(){
		return longitude;
	}

	public String getPopulation(){
		return population;
	}

	public List<WeatherUrlItem> getWeatherUrl(){
		return weatherUrl;
	}
}