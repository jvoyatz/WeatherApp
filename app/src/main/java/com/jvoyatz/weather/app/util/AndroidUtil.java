package com.jvoyatz.weather.app.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;

public class AndroidUtil {

    /**
     * Finds the color resource id given the attribute id as
     * declared in application's theme
     *
     */
    @ColorInt
    public static int getColorByAttributeId(Context context, @AttrRes int attrIdForColor) {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(attrIdForColor, typedValue, true);
        return typedValue.data;
    }
}
