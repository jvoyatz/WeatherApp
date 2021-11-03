package com.jvoyatz.weather.app.models.api.weather;

import androidx.annotation.NonNull;

import java.util.List;

public class ClimateAveragesItem{
	private List<MonthItem> month;

	public List<MonthItem> getMonth(){
		return month;
	}

	@NonNull
	@Override
 	public String toString(){
		return 
			"ClimateAveragesItem{" + 
			"month = '" + month + '\'' + 
			"}";
		}
}