package com.jvoyatz.weather.app.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;

import com.jvoyatz.weather.app.models.entities.weather.WeatherDayHourEntity;
import com.jvoyatz.weather.app.ui.home.details.pager.WeatherHourItemsAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import timber.log.Timber;

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

    public static String get24TimeFrom12(String timeStr){
        final Date date = parseAmStr(timeStr);
        return  date == null ? "": formatHHmmTime(date);
    }
    public static String getDay(String dateStr){
        final Date date = parseYYYYMMddStr(dateStr);
        return date != null ? getDayFormatter().format(date):"";
    }

    public static String getDayNumMonth(String dateStr){
        final Date date = parseYYYYMMddStr(dateStr);
        return date != null ? getDayMonth3Formatter().format(date):"";
    }

    /**
     * Given something like this
     *      EEE MMM dd HH:mm:ss zzz yyyy
     * formats it into
     *      HH:mm
     */
    public static String getTimeFromFull(@NonNull String dateStr) {
        if(!TextUtils.isEmpty(dateStr)){
            try {
                final Date date = getFullDateFormatter().parse(dateStr);
                if(date != null){
                    return  getHHmmFormatter().format(date);
                }
            } catch (ParseException e) {
                Timber.e(e);
            }
        }
        return "";
    }

    /**
     * input: 2021-11-10 15:28
     *
     * Returns the given in a format
     *         Day, 01 November
     */
    public static String getFormalDateFromStandard(String dateStr){
        final Date date = parseStandardStr(dateStr);
        return date != null ? formatFormalDate(date) : null;
    }


    //merge dates
    public static Date mergeAmDate(Date date1, String date2){
        if (date1 != null && !TextUtils.isEmpty(date2)){
            return mergeDates(date1, parseAmStr(date2));
        }
        return null;
    }

    public static Date mergeHHmmDates(Date date1, String date2){
        if (date1 != null && !TextUtils.isEmpty(date2)){
            return mergeDates(date1, parseHHmmStr(date2));
        }
        return null;
    }

    public static Date mergeDates(Date date1, Date date2){
        if (date1 != null && date2 != null){
            Calendar date1Calendar = Calendar.getInstance();
            date1Calendar.setTime(date1);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date2);

            calendar.set(Calendar.YEAR, date1Calendar.get(Calendar.YEAR));
            calendar.set(Calendar.DATE, date1Calendar.get(Calendar.DATE));
            calendar.set(Calendar.MONTH, date1Calendar.get(Calendar.MONTH));

            return calendar.getTime();
        }
        return null;
    }

    //parse
    @Nullable
    public static Date parseFullStr(String dateTimeStr){
        try {
            return TextUtils.isEmpty(dateTimeStr) ? null: getFullDateFormatter().parse(dateTimeStr);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date parseStandardStr(String dateTimeStr){
        try {
            return TextUtils.isEmpty(dateTimeStr) ? null: getStandardDateFormat().parse(dateTimeStr);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date parseHHmmStr(String date){
        try {
            return TextUtils.isEmpty(date) ? null : getHHmmFormatter().parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
    public static Date parseYYYYMMddStr(String date){
        try {
            return TextUtils.isEmpty(date) ? null : getYYYYMMddFormatter().parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
    public static Date parseAmStr(String date) {
        try {
            return TextUtils.isEmpty(date) ? null : getAmTimeFormatter().parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String formatStandardDate(Date date){
        return date == null ? "" : getStandardDateFormat().format(date);
    }
    public static String formatFullDate(Date date){
        return date == null ? "" : getFullDateFormatter().format(date);
    }
    public static String formatFormalDate(Date date){
        return date == null ? "" : getFormalDateFormatter().format(date);
    }
    public static String formatHHmmTime(Date date){
        return date == null? "": getHHmmFormatter().format(date);
    }

//formatters

    @NonNull
    public static SimpleDateFormat getStandardDateFormat(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);
    }
    @NonNull
    public static SimpleDateFormat getFormalDateFormatter(){
        return new SimpleDateFormat("EEEE, d MMMM yyyy", Locale.ENGLISH);
    }
    @NonNull
    public static SimpleDateFormat getYYYYMMddSlashFormatter(){
        return new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
    }
    @NonNull
    public static SimpleDateFormat getYYYYMMddFormatter(){
        return new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    }
    @NonNull
    public static SimpleDateFormat getHHmmFormatter(){
        return new SimpleDateFormat("HH:mm", Locale.ENGLISH);
    }
    @NonNull static SimpleDateFormat getDayFormatter(){
        return new SimpleDateFormat("EEE", Locale.ENGLISH);
    }
    @NonNull
    public static SimpleDateFormat getFullDateFormatter() {
        //return new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.ENGLISH);
    }
    @NonNull
    public static SimpleDateFormat getDayMonth3Formatter(){
        return new SimpleDateFormat("dd MMM", Locale.ENGLISH);
    }
    @NonNull
    public static SimpleDateFormat getAmTimeFormatter(){
        return new SimpleDateFormat("h:mm a", Locale.ENGLISH);
    }


    /**
     * Finds a drawable and rotates it using the degrees argument provided
     */
    public static BitmapDrawable rotateDrawable(@NonNull Context context, int drawableId, String degrees){
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableId);

        try {
            if(bitmap != null && !TextUtils.isEmpty(degrees)) {
                Matrix matrix = new Matrix();
                matrix.postRotate(Integer.parseInt(degrees));

                return new BitmapDrawable(context.getResources(), Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true));
            }
        } catch (NumberFormatException e) {
            Timber.e(e);
        }

        return null;
    }

    /**
     * Gets as an argument a list of HourEntities and traverses it
     * and finds the closest day from the specified time (long) given as a second arg
     * and returns it
     */
    @WorkerThread
    public static WeatherDayHourEntity getClosestWeatherDayHourEntity(@NonNull List<WeatherDayHourEntity> hours, long time) {
        Map<Date, WeatherDayHourEntity> hoursMap = new HashMap<>(hours.size());

        for (WeatherDayHourEntity hour : hours) {
            if (TextUtils.isEmpty(hour.getTime()))
                continue;
            try {
                if(TextUtils.equals(hour.getWeatherCode(), WeatherHourItemsAdapter.DAY_SUNRISE) ||
                        TextUtils.equals(hour.getWeatherCode(), WeatherHourItemsAdapter.DAY_SUNSET)
                || TextUtils.equals(hour.getWeatherCode(), WeatherHourItemsAdapter.DAY_STR))
                    continue;

                final Date date = Utils.getFullDateFormatter().parse(hour.getTime());
                if (date != null) {
                    hoursMap.put(date, hour);
                }
            } catch (ParseException e) { Timber.e(e); }
        }
        Date closestDate = Collections.min(hoursMap.keySet(), (d1, d2) -> {
            return Long.compare(Math.abs(d1.getTime() - time), Math.abs(d2.getTime() - time));
        });
        return hoursMap.get(closestDate);
    }

}
