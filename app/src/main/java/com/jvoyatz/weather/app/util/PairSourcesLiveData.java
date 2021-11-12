package com.jvoyatz.weather.app.util;

import android.util.Pair;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

/**
 * @param <L> left source,
 * @param <R> right source
 * @author jvoyatz
 *
 *
 * A base livedata class which extends MediatorLiveData, and gets as arguments two livedata sources,
 * which they are added as sources and the updates in their values are being listened
 * by this MediatorLiveData and get posted to the invoking observer
 */
public class PairSourcesLiveData<L, R> extends MediatorLiveData<Pair<L, R>> {
    private static final String TAG = "PairSourcesLiveData";
    private final LiveData<L> leftLiveData;
    private final LiveData<R> rightLiveData;

    final Observer<R> rightObserver = new Observer<R>() {
        public void onChanged(@Nullable R value) {
            setValue(Pair.create(leftLiveData.getValue(), value));
        }
    };

    final Observer<L> leftObserver = new Observer<L>() {
        @Override
        public void onChanged(L value) {
            setValue(Pair.create(value, rightLiveData.getValue()));
        }
    };

    public PairSourcesLiveData(LiveData<L> leftLiveData, LiveData<R> rightLiveData) {
        this.leftLiveData = leftLiveData;
        this.rightLiveData = rightLiveData;

        addSource(leftLiveData, leftObserver);
        addSource(rightLiveData, rightObserver);
    }
}
