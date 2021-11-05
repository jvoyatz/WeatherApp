package com.jvoyatz.weather.app.models.api.city;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.PrimaryKey;

import java.util.List;

public class CityItem {

	private List<CountryItem> country;
	private List<AreaNameItem> areaName;
	private Timezone timezone;
	private String latitude;
	private List<RegionItem> region;
	private String longitude;
	private String population;
	private List<WeatherUrlItem> weatherUrl;

	public void setCountry(List<CountryItem> country) {
		this.country = country;
	}

	public void setAreaName(List<AreaNameItem> areaName) {
		this.areaName = areaName;
	}

	public void setTimezone(Timezone timezone) {
		this.timezone = timezone;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public void setRegion(List<RegionItem> region) {
		this.region = region;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public void setPopulation(String population) {
		this.population = population;
	}

	public void setWeatherUrl(List<WeatherUrlItem> weatherUrl) {
		this.weatherUrl = weatherUrl;
	}

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