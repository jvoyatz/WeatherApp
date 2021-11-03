package com.jvoyatz.weather.app.models.api.error;

/**
 * Contains a String message field, describing the error
 */
public class ApiError {
	private String msg;

	public String getMsg(){
		return msg;
	}

	@Override
 	public String toString(){
		return 
			"ErrorItem{" + 
			"msg = '" + msg + '\'' + 
			"}";
		}
}
