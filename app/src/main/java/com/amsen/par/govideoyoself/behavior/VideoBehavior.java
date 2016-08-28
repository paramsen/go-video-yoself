package com.amsen.par.govideoyoself.behavior;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Pair;

import com.amsen.par.govideoyoself.base.rx.event.EventStream;
import com.amsen.par.govideoyoself.base.rx.subscriber.OnNextSubscriber;
import com.amsen.par.govideoyoself.model.VideoStatus;
import com.amsen.par.govideoyoself.source.VideoSource;
import com.amsen.par.govideoyoself.source.event.VideoEvent;
import com.amsen.par.govideoyoself.view.activity.BaseActivity;

import rx.Observable;

/**
 * A Behavior is the concept of a component that wraps a whole behavior.
 * The recording, uploading & saving of a video spans at least two Activity lifecycles, several fragment lifecycles & so forth. To expose
 * a unified API this component uses the EventStream concept to encapsulate the complex behavior.
 *
 * (Concept introduced by me as a way to avoid headaches and try to encapsulate complex behaviors in one sequence of Rx commands...)
 *
 * @author Pär Amsen 2016
 */
public class VideoBehavior {
    private BaseActivity activity;
    private EventStream eventStream;
    private VideoSource videoSource;

    public VideoBehavior(BaseActivity activity, EventStream eventStream, VideoSource videoSource) {
        this.activity = activity;
        this.eventStream = eventStream;
        this.videoSource = videoSource;
    }

    public void start(VideoStatus videoStatus) {
        eventStream.stream()
                .filter(e -> e instanceof VideoEvent && ((VideoEvent) e).type == VideoEvent.Type.RECORDED)
                .cast(VideoEvent.class)
                .map(e -> new Pair<>(e.id, (Uri) e.value))
                .first()
                .flatMap(this::onRecordedVideo)
                .subscribe(new OnNextSubscriber<>(e -> {
                    //One would return an observable, but that requires more architectural design. Lets just autosub here and cause mem leaks
                }));

        launchCamera(videoStatus);
    }

    private Observable<Boolean> onRecordedVideo(Pair<Integer, Uri> pair) {
        return videoSource.put(pair.first, pair.second);
    }

    private void launchCamera(VideoStatus videoStatus) {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        if (takeVideoIntent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivityForResult(takeVideoIntent, videoStatus.getId());
        }
    }
}
