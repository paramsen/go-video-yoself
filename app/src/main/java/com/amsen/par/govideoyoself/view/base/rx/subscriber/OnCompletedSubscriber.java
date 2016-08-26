package com.amsen.par.govideoyoself.view.base.rx.subscriber;

import rx.Subscriber;
import rx.functions.Action0;

/**
 * Subscriber that ignores onError and invokes the supplied Action0 on onCompleted.
 * @author Pär Amsen 2016
 */
public class OnCompletedSubscriber<T> extends Subscriber<T> {
    private final Action0 onCompleted;

    public OnCompletedSubscriber(Action0 onCompleted) {
        this.onCompleted = onCompleted;
    }

    @Override
    public void onCompleted() {
        onCompleted.call();
    }

    @Override
    public void onError(Throwable e) {
    }

    @Override
    public void onNext(T t) {
    }
}