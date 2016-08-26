package com.amsen.par.govideoyoself.view.base.rx.event;

import com.amsen.par.govideoyoself.view.base.rx.subscriber.OnCompletedSubscriber;
import com.amsen.par.govideoyoself.view.base.rx.subscriber.OnNextOnCompletedSubscriber;
import com.amsen.par.govideoyoself.view.base.rx.subscriber.OnNextSubscriber;

import rx.Observable;
import rx.observers.SafeSubscriber;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

/**
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
