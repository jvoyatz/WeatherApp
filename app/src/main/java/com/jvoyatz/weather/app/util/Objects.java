package com.jvoyatz.weather.app.util;

import java.util.Collection;
import java.util.Collections;

/**
 * Util methods for Java Objects, Collections etc
 */
public class Objects{

    /**
     * Checks whether a given Collections is null or empty.
     * @param collection collection to be checked
     * @return Boolean, true or false
     */
    public static boolean isEmpty(Collection<?> collection){
        return collection == null || collection.isEmpty();
    }
}
