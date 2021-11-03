package com.jvoyatz.weather.app.models.api.error;

/**
 * Wrapper class for json error responses from WorldWeatherApi
 */
public class ApiErrorWrapper {
	private APiErrorData data;

	public APiErrorData getData(){
		return data;
	}

	@Override
 	public String toString(){
		return 
			"Response{" + 
			"data = '" + data + '\'' + 
			"}";
		}
}
