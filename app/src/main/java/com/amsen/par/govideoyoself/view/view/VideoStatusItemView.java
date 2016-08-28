package com.amsen.par.govideoyoself.view.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.amsen.par.govideoyoself.R;
import com.amsen.par.govideoyoself.base.rx.event.EventStream;
import com.amsen.par.govideoyoself.base.rx.subscriber.OnNextSubscriber;
import com.amsen.par.govideoyoself.behavior.VideoBehavior;
import com.amsen.par.govideoyoself.model.VideoStatus;
import com.amsen.par.govideoyoself.source.event.VideoEvent;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Pär Amsen 2016
 */
public class VideoStatusItemView extends FrameLayout {
    @BindView(R.id.incompleteContainer)
    protected View incompleteContainer;
    @BindView(R.id.title)
    protected TextView title;
    @BindView(R.id.progressContainer)
    protected View progressContainer;
    @BindView(R.id.progress)
    protected TextView progress;
    @BindView(R.id.completeContainer)
    protected View completeContainer;
    @BindView(R.id.complete)
    protected TextView complete;

    private VideoStatus videoStatus;
    private EventStream eventStream;
    private VideoBehavior behavior;

    public VideoStatusItemView(Activity activity, EventStream eventStream, VideoBehavior behavior) {
        super(activity);
        this.eventStream = eventStream;
        this.behavior = behavior;

        View view = LayoutInflater.from(activity).inflate(R.layout.view_video_item, this, true);
        ButterKnife.bind(view, this);
    }

    private void initBehavior() {
        Observable<VideoEvent> videoEvents = eventStream.stream()
                .filter(e -> e instanceof VideoEvent && ((VideoEvent) e).id == videoStatus.getId())
                .cast(VideoEvent.class)
                .share();

        videoEvents.filter(e -> e.type == VideoEvent.Type.PROGRESS)
                .throttleFirst(300, TimeUnit.MILLISECONDS, Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new OnNextSubscriber<>(this::onProgress));

        videoEvents.filter(e -> e.type == VideoEvent.Type.COMPLETE)
                .subscribe(new OnNextSubscriber<>(this::onComplete));
    }

    private void onProgress(VideoEvent event) {
        double progressValue = (double) event.value;

        if(progressContainer.getVisibility() == GONE) {
            progressContainer.setVisibility(VISIBLE);
        }

        progress.setText(String.format(Locale.ENGLISH, "%.0f%%", progressValue));
    }

    private void onComplete(VideoEvent e) {
        if(completeContainer.getVisibility() == GONE) {
            completeContainer.setVisibility(VISIBLE);
        }
    }

    @OnClick(R.id.video_item)
    protected void onClick() {
        if(videoStatus.isCompleted()) {
            Snackbar.make(this, R.string.This_challenge_has_been_completed, Snackbar.LENGTH_SHORT).show();
        } else {
            behavior.start(videoStatus);
        }
    }

    public void apply(VideoStatus videoStatus) {
        this.videoStatus = videoStatus;
        title.setText(videoStatus.getDisplayName());

        if(videoStatus.isCompleted()) {
            onComplete(null);
        } else {
            initBehavior();
        }
    }

}
