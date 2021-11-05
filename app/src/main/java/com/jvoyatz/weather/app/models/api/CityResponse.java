package com.jvoyatz.weather.app.models.api;

import com.google.gson.annotations.SerializedName;
import com.jvoyatz.weather.app.models.api.city.CitySearchApi;

public class CityResponse {

	@SerializedName("search_api")
	private CitySearchApi searchApi;

	public CitySearchApi getSearchApi(){
		return searchApi;
	}
}
