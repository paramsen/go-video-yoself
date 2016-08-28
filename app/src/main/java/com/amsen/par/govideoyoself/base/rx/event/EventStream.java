package com.amsen.par.govideoyoself.base.rx.event;

import com.amsen.par.govideoyoself.base.rx.subscriber.OnCompletedSubscriber;
import com.amsen.par.govideoyoself.base.rx.subscriber.OnNextOnCompletedSubscriber;

import rx.Observable;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

/**
 * Rx version of otto.EventBus [the largest EventBus lib for Android]. Streamable instead and expose a better api.
 * @author Pär Amsen 2016
 */
public class EventStream {
    private PublishSubject<Event> stream = PublishSubject.create();
    private Observable<Event> historyStream;
    private BehaviorSubject<Event> mostRecentStream;

    public EventStream() {
        stream.subscribe(new OnNextOnCompletedSubscriber<>(next -> mostRecentStream.onNext(next), () -> mostRecentStream.onCompleted()));
        historyStream = stream().replay().autoConnect();
        mostRecentStream = BehaviorSubject.create();

        historyStream().subscribe(new OnCompletedSubscriber<>(() -> {
            //just subscribe to init stream
        }));
    }

    public void post(Event t) {
        stream.onNext(t);
    }

    public Observable<Event> stream() {
        return stream;
    }

    /**
     * @return an Observable that repeats the EventStreams events (look at Observable.repeat)
     */
    public Observable<Event> historyStream() {
        return historyStream;
    }

    /**
     * @return an Observable that repeats the last emitted item and following items
     */
    public Observable<Event> mostRecentStream() {
        return mostRecentStream;
    }

    public void complete() {
        stream.onCompleted();
    }
}
