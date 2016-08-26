package com.amsen.par.govideoyoself.view.base.rx.subscriber;

import rx.Subscriber;
import rx.functions.Action1;

/**
 * Subscriber that ignores onComplete/onError and invokes the supplied Action1 on onNext.
 *
 * @author Pär Amsen 2016
 */
public class OnNextSubscriber<T> extends Subscriber<T> {
    private final Action1<T> action;

    public OnNextSubscriber(Action1<T> action) {
        this.action = action;
    }

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
    }

    @Override
    public void onNext(T t) {
        try {
            action.call(t);
        } catch (Exception e) {
        }
    }
}