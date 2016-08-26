package com.amsen.par.govideoyoself.view.source;

import com.amsen.par.govideoyoself.view.base.rx.event.Event;
import com.amsen.par.govideoyoself.view.model.VideoStatus;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * @author Pär Amsen 2016
 */
public class VideoSource {
    public Observable<List<VideoStatus>> getVideos() {
        return Observable.just(new ArrayList<>());
    }
}
