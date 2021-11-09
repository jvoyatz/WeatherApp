package com.jvoyatz.weather.app.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import timber.log.Timber;

public class Utils {
    public static SimpleDateFormat fullDateFormatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.getDefault());
    public static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    public static SimpleDateFormat dayFormatter = new SimpleDateFormat("EEE", Locale.getDefault());
    public static final SimpleDateFormat dayMonth3LettersFormatter = new SimpleDateFormat("dd MMM", Locale.getDefault());
    public static final SimpleDateFormat hourFormatter = new SimpleDateFormat("h:mm a", Locale.getDefault());
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

    public static String getDay(String dateStr){
        if(!TextUtils.isEmpty(dateStr)){
            try {

                final Date date = dateFormatter.parse(dateStr);
                return dayFormatter.format(date);

            } catch (ParseException e) {
                return "";
            }
        }

        return "";
    }

    public static String getDayNumMonth(String dateStr){
        if(!TextUtils.isEmpty(dateStr)){
            try {
                final Date date = dateFormatter.parse(dateStr);
                return dayMonth3LettersFormatter.format(date);
            } catch (ParseException e) {
                return "";
            }
        }

        return "";
    }

    public static String get(String dateStr){
        if(!TextUtils.isEmpty(dateStr)){
            try {
                final Date date = dateFormatter.parse(dateStr);
                return dayFormatter.format(date);
            } catch (ParseException e) {
                return "";
            }
        }

        return "";
    }

    public static String getHour(String dateStr){
        if(!TextUtils.isEmpty(dateStr)){
            Timber.d("getHour() called with: dateStr = [" + dateStr + "]");
            try {
           //     final Date date = hourFormatter.parse(dateStr);
                //final String format = hourFormatter.format(date);
                //Timber.d("getHour: " + format);
                //return format;
                SimpleDateFormat parseFormat = new SimpleDateFormat("h:mm a", Locale.ENGLISH);
                final String format = parseFormat.format(dateStr);
                Timber.d("getHour: " + format);
                return "";
            } catch (Exception e) {
                e.printStackTrace();
                return "error";
            }
        }

        return "empty";
    }


    public static Calendar convertHourStrToCalendar(String hour){
        try {
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            Date date = simpleDateFormat.parse(hour);

            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);//we set the date we created, however it starts from 1970
            calendar.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
            calendar.set(Calendar.DATE,Calendar.getInstance().get(Calendar.DATE));
            calendar.set(Calendar.MONTH, Calendar.getInstance().get(Calendar.MONTH));

            return calendar;
        } catch (ParseException e) {
            return null;
        }
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
}
