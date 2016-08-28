package com.amsen.par.govideoyoself.source.event;

import com.amsen.par.govideoyoself.base.rx.event.Event;

/**
 * @author Pär Amsen 2016
 */
public class VideoEvent<T> implements Event {
    public VideoEvent(int id, Type type, T value) {
        this.id = id;
        this.type = type;
        this.value = value;
    }

    public final int id;
    public final Type type;
    public final T value;

    public enum Type {
        INCOMPLETE, PROGRESS, COMPLETE, RECORDED
    }
}
