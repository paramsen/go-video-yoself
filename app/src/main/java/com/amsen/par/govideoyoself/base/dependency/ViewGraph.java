package com.amsen.par.govideoyoself.base.dependency;

import com.amsen.par.govideoyoself.base.rx.event.EventStream;
import com.amsen.par.govideoyoself.behavior.VideoBehavior;
import com.amsen.par.govideoyoself.source.VideoSource;
import com.amsen.par.govideoyoself.view.activity.BaseActivity;

/**
 * Dependency graph. ViewGraph owns refs to instances that are dependent on the current Activity.
 * Acts as a sub graph to the ApplicationGraph and thus has references to the ApplicationGraph Singletons.
 * @author Pär Amsen 2016
 */
public class ViewGraph {
    public final VideoSource source;
    public final EventStream eventStream;

    public final VideoBehavior videoBehavior;

    public ViewGraph(BaseActivity baseActivity, EventStream eventStream, VideoSource source) {
        this.source = source;
        this.eventStream = eventStream;
        videoBehavior = new VideoBehavior(baseActivity, this.eventStream, this.source);
    }
}
