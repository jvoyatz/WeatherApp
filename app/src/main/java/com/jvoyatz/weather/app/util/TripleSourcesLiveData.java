package com.jvoyatz.weather.app.util;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import kotlin.Triple;

/**
 * @param <L> left source,
 * @param <M> middle,
 * @param <R> right
 * @author jvoyatz
 * <p>
 * Same as {@link PairSourcesLiveData} but this class is being
 * used to observe 3 different sources
 */
public class TripleSourcesLiveData<L, M, R> extends MediatorLiveData<Triple<L, M, R>> {

    private final LiveData<L> leftLiveData;
    private final LiveData<M> middleLiveData;
    final Observer<R> rightObserver = new Observer<R>() {
        public void onChanged(@Nullable R r) {
            setValue(new Triple<>(leftLiveData.getValue(), middleLiveData.getValue(), r));
        }
    };
    private final LiveData<R> rightLiveData;
    final Observer<L> leftObserver = new Observer<L>() {
        @Override
        public void onChanged(L l) {
            setValue(new Triple<>(l, middleLiveData.getValue(), rightLiveData.getValue()));
        }
    };
    final Observer<M> middleObserver = new Observer<M>() {
        @Override
        public void onChanged(M m) {
            setValue(new Triple<>(leftLiveData.getValue(), m, rightLiveData.getValue()));
        }
    };


    public TripleSourcesLiveData(LiveData<L> leftLiveData, LiveData<M> middleLiveData, LiveData<R> rightLiveData) {
        this.leftLiveData = leftLiveData;
        this.middleLiveData = middleLiveData;
        this.rightLiveData = rightLiveData;

        addSource(leftLiveData, leftObserver);
        addSource(middleLiveData, middleObserver);
        addSource(rightLiveData, rightObserver);
    }
}
