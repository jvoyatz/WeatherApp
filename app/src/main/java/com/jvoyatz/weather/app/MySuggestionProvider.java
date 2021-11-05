package com.jvoyatz.weather.app;

import android.content.SearchRecentSuggestionsProvider;

public class MySuggestionProvider  extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = BuildConfig.APPLICATION_ID + ".SuggestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public MySuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }

}
