package com.jvoyatz.weather.app.models.api.weather;

import androidx.annotation.NonNull;

public class MonthItem{
	private String absMaxTemp;
	private String avgMinTempF;
	private String name;
	private String index;
	private String absMaxTempF;
	private String avgDailyRainfall;
	private String avgMinTemp;

	public String getAbsMaxTemp(){
		return absMaxTemp;
	}

	public String getAvgMinTempF(){
		return avgMinTempF;
	}

	public String getName(){
		return name;
	}

	public String getIndex(){
		return index;
	}

	public String getAbsMaxTempF(){
		return absMaxTempF;
	}

	public String getAvgDailyRainfall(){
		return avgDailyRainfall;
	}

	public String getAvgMinTemp(){
		return avgMinTemp;
	}

	@NonNull
	@Override
 	public String toString(){
		return 
			"MonthItem{" + 
			"absMaxTemp = '" + absMaxTemp + '\'' + 
			",avgMinTemp_F = '" + avgMinTempF + '\'' + 
			",name = '" + name + '\'' + 
			",index = '" + index + '\'' + 
			",absMaxTemp_F = '" + absMaxTempF + '\'' + 
			",avgDailyRainfall = '" + avgDailyRainfall + '\'' + 
			",avgMinTemp = '" + avgMinTemp + '\'' + 
			"}";
		}
}
