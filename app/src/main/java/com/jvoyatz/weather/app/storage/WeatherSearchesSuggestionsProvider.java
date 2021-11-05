package com.jvoyatz.weather.app.storage;

import android.content.SearchRecentSuggestionsProvider;

import com.jvoyatz.weather.app.BuildConfig;

/**
 * Creates a search suggestions provider for SearchView Dialog
 *
 * see {@link android.content.SearchRecentSuggestionsProvider}.
 */
public class WeatherSearchesSuggestionsProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = BuildConfig.APPLICATION_ID + ".SuggestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public WeatherSearchesSuggestionsProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
