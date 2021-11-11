package com.jvoyatz.weather.app.util;

/**
 * Interface used in {@link EventObserver} class
 */
public interface EventHandler<V>{
    void onEventUnHandled(V object);
}
