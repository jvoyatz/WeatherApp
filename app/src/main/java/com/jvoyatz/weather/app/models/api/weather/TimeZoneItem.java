package com.jvoyatz.weather.app.models.api.weather;

import androidx.annotation.NonNull;

public class TimeZoneItem{
	private String localtime;
	private String utcOffset;
	private String zone;

	public String getLocaltime(){
		return localtime;
	}

	public String getUtcOffset(){
		return utcOffset;
	}

	public String getZone(){
		return zone;
	}

	@NonNull
	@Override
 	public String toString(){
		return 
			"TimeZoneItem{" + 
			"localtime = '" + localtime + '\'' + 
			",utcOffset = '" + utcOffset + '\'' + 
			",zone = '" + zone + '\'' + 
			"}";
		}
}
