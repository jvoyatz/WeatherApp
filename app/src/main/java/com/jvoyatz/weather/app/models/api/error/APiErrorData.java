package com.jvoyatz.weather.app.models.api.error;

import java.util.List;

/**
 * Contains a list of {@link ApiError} instances. Each item describes the errors returned.
 */
public class APiErrorData {
	private List<ApiError> error;

	public List<ApiError> getError(){
		return error;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"error = '" + error + '\'' + 
			"}";
		}
}