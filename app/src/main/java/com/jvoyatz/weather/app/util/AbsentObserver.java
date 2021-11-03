package com.jvoyatz.weather.app.util;

import androidx.lifecycle.Observer;

/**
 * An observer class which does nothing.
 */
public class AbsentObserver<T> implements Observer<T> {
    public static <T> Observer<T> create() {
        return new AbsentObserver<>();
    }

    @Override
    public void onChanged(T o) {
    }
}
