package com.amsen.par.govideoyoself.base.rx.subscriber;

import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Action2;

/**
 * Subscriber that ignores onComplete/onError and invokes the supplied Action1 on onNext.
 *
 * @author Pär Amsen 2016
 */
public class OnNextSubscriber<T> extends Subscriber<T> {
    private Action1<T> action;
    private Action2<T, Subscriber<T>> action2;

    public OnNextSubscriber(Action1<T> action) {
        this.action = action;
    }

    public OnNextSubscriber(Action2<T, Subscriber<T>> action2) {
        this.action2 = action2;
    }

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }

    @Override
    public void onNext(T t) {
        try {
            if(action != null) {
                action.call(t);
            } else {
                action2.call(t, this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}