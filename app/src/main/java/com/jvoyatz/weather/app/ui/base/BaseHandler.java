package com.jvoyatz.weather.app.ui.base;

import android.view.View;

/**
 * An interface implemented by Activities or Fragment
 * It is used inside layout xmls, in onClick tags
 * to execute certain actions
 */
public interface BaseHandler {
    void onViewClicked(View view);
}
