package com.amsen.par.govideoyoself.base.dependency;

import com.amsen.par.govideoyoself.base.rx.event.EventStream;
import com.amsen.par.govideoyoself.behavior.VideoBehavior;
import com.amsen.par.govideoyoself.source.VideoSource;
import com.amsen.par.govideoyoself.view.activity.BaseActivity;

/**
 * Dependency graph. ApplicationGraph holds refs to instances owned by the Application, i.e. Singletons per se.
 * @author Pär Amsen 2016
 */
public class ApplicationGraph {
    public final VideoSource source;
    public final EventStream eventStream;

    public ApplicationGraph() {
        eventStream = new EventStream();
        source = new VideoSource(eventStream);
    }
}
