package com.amsen.par.govideoyoself.view.view;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
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
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Encapsulation for ListItem in RecyclerListView.
 * Encapsulates all behavior around progress &
 * completion of video tasks.
 *
 * @author Pär Amsen 2016
 */
public class VideoStatusItemView extends FrameLayout {
    @BindView(R.id.icon)
    protected TextView icon;
    @BindView(R.id.incompleteContainer)
    protected View incompleteContainer;
    @BindView(R.id.title)
    protected TextView title;
    @BindView(R.id.progressContainer)
    protected View progressContainer;
    @BindView(R.id.progress)
    protected TextView progress;
    @BindView(R.id.progressBar)
    protected ProgressBar progressBar;
    @BindView(R.id.completeContainer)
    protected View completeContainer;
    @BindView(R.id.complete)
    protected TextView complete;

    private VideoStatus videoStatus;
    private EventStream eventStream;
    private VideoBehavior behavior;

    public VideoStatusItemView(Activity activity, ViewGroup parent, EventStream eventStream, VideoBehavior behavior) {
        super(activity);
        this.eventStream = eventStream;
        this.behavior = behavior;

        View view = LayoutInflater.from(activity).inflate(R.layout.view_video_item, parent, false);
        ViewGroup.LayoutParams lp = parent.getLayoutParams();
        lp.height = LayoutParams.WRAP_CONTENT;
        setLayoutParams(lp);
        addView(view);

        ButterKnife.bind(this, view);
    }

    private void initBehavior() {
        incompleteContainer.setVisibility(VISIBLE);
        progressContainer.setVisibility(GONE);
        completeContainer.setVisibility(GONE);

        Observable<VideoEvent> videoEvents = eventStream.stream()
                .filter(e -> e instanceof VideoEvent && ((VideoEvent) e).id == videoStatus.getId())
                .cast(VideoEvent.class)
                .share();

        Subscription progressSubscription = videoEvents.filter(e -> e.type == VideoEvent.Type.PROGRESS)
                .throttleFirst(300, TimeUnit.MILLISECONDS, Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new OnNextSubscriber<>(this::onProgress));

        videoEvents.filter(e -> e.type == VideoEvent.Type.COMPLETE)
                .subscribe(new OnNextSubscriber<>((e, subscriber) -> {
                    progressSubscription.unsubscribe(); //be sure to close the mem leaks :)
                    subscriber.unsubscribe();
                    onComplete();
                }));

        videoEvents.filter(e -> e.type == VideoEvent.Type.INCOMPLETE)
                .subscribe(new OnNextSubscriber<>(this::onUploadFailure));
    }

    private void onUploadFailure(VideoEvent event) {
        Snackbar.make(this, getResources().getString(R.string.Failed_to_upload_ARG1_video, videoStatus.getUnicodeIcon()), Snackbar.LENGTH_LONG)
                .setAction(R.string.Show_why, e -> {
                    Snackbar.make(VideoStatusItemView.this, "DEBUG Error message: " + ((Throwable) event.value).getMessage(), Snackbar.LENGTH_LONG).show();
                })
                .show();
    }

    private void onProgress(VideoEvent event) {
        double progressValue = (double) event.value;

        incompleteContainer.setVisibility(GONE);
        progressContainer.setVisibility(VISIBLE);

        progress.setText(String.format(Locale.ENGLISH, "%d%%", (int) progressValue));
        progressBar.setProgress((int) progressValue);
    }

    private void onComplete() {
        incompleteContainer.setVisibility(GONE);
        progressContainer.setVisibility(GONE);
        completeContainer.setVisibility(VISIBLE);

        complete.setText("✓ Done!");
    }

    @OnClick(R.id.video_item)
    protected void onClick() {
        delay(() -> {
            if (videoStatus.isCompleted()) {
                Snackbar.make(this, getResources().getString(R.string.ARG1_challenge_has_been_completed, videoStatus.getUnicodeIcon()), Snackbar.LENGTH_SHORT).show();
            } else {
                behavior.start(videoStatus);
            }
        });
    }

    public void apply(VideoStatus videoStatus) {
        this.videoStatus = videoStatus;
        icon.setText(videoStatus.getUnicodeIcon());
        title.setText(videoStatus.getActionDisplayString());

        if (videoStatus.isCompleted()) {
            onComplete();
        } else {
            initBehavior();
        }
    }

    public void delay(Action0 func) {
        Observable.just(true)
                .delay(getResources().getInteger(android.R.integer.config_shortAnimTime), TimeUnit.MILLISECONDS, Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new OnNextSubscriber<>(e -> {
                    func.call();
                }));
    }
}
