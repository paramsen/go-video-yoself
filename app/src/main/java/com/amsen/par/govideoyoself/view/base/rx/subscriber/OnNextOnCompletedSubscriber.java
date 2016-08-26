package com.amsen.par.govideoyoself.view.base.rx.subscriber;

import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Subscriber that ignores onError and invokes the supplied Action*:s on onNext/onCompleted.
 *
 * @author Pär Amsen 2016
 */
public class OnNextOnCompletedSubscriber<T> extends Subscriber<T> {
    private final Action1<T> onNext;
    private final Action0 onCompleted;

    public OnNextOnCompletedSubscriber(Action1<T> onNext, Action0 onCompleted) {
        this.onNext = onNext;
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
        try {
            onNext.call(t);
        } catch (Exception e) {
        }
    }
}
