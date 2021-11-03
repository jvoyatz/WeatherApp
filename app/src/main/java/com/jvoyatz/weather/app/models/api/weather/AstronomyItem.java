package com.jvoyatz.weather.app.models.api.weather;

import androidx.annotation.NonNull;

public class AstronomyItem{
	private String moonset;
	private String moonIllumination;
	private String sunrise;
	private String moonPhase;
	private String sunset;
	private String moonrise;

	public String getMoonset(){
		return moonset;
	}

	public String getMoonIllumination(){
		return moonIllumination;
	}

	public String getSunrise(){
		return sunrise;
	}

	public String getMoonPhase(){
		return moonPhase;
	}

	public String getSunset(){
		return sunset;
	}

	public String getMoonrise(){
		return moonrise;
	}

	@NonNull
	@Override
 	public String toString(){
		return 
			"AstronomyItem{" + 
			"moonset = '" + moonset + '\'' + 
			",moon_illumination = '" + moonIllumination + '\'' + 
			",sunrise = '" + sunrise + '\'' + 
			",moon_phase = '" + moonPhase + '\'' + 
			",sunset = '" + sunset + '\'' + 
			",moonrise = '" + moonrise + '\'' + 
			"}";
		}
}
