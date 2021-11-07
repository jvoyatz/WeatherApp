package com.jvoyatz.weather.app.util;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Utils {

    /**
     * Returns the date of the given timezone
     */
    public static Date getDateTimeByZone(String zone){
        if(TextUtils.isEmpty(zone)){
            return new Date();
        }

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(zone));
        return calendar.getTime();
    }

    /**
     * Gets date and converts it to dd/MM/yyyy format
     *
     */
    public static String formatDate(Date date) {
        if (date == null)
            return null;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return formatter.format(date);
    }

    /**
     * Gets date and converts it to HH:mm format
     */
    public static String formatTime(@NonNull Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return dateFormat.format(date);
    }
}
