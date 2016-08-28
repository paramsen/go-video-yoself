package com.amsen.par.govideoyoself.view.activity;

import android.os.Bundle;

import com.amsen.par.govideoyoself.R;
import com.amsen.par.govideoyoself.base.rx.subscriber.OnNextSubscriber;
import com.amsen.par.govideoyoself.model.VideoStatus;
import com.amsen.par.govideoyoself.source.VideoSource;
import com.amsen.par.govideoyoself.source.event.VideoEvent;
import com.amsen.par.govideoyoself.view.fragment.DoneFragment;
import com.amsen.par.govideoyoself.view.fragment.ListFragment;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.List;

/**
 * @author Pär Amsen 2016
 */
public class HomeActivity extends BaseActivity {
    private VideoSource videoSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        videoSource = getApplicationGraph(this).source;

        initialState();
        initBehavior();
    }

    private void initialState() {
        videoSource
                .get()
                .map(this::isAllVideosCompleted)
                .subscribe(new OnNextSubscriber<>(completedVideos -> {
                    if (completedVideos) {
                        showFragment(new DoneFragment());
                    } else {
                        showFragment(new ListFragment());
                    }
                }));
    }

    private void initBehavior() {
        getViewGraph().eventStream
                .stream()
                .filter(e -> e instanceof VideoEvent && ((VideoEvent) e).type == VideoEvent.Type.COMPLETE)
                .cast(VideoEvent.class)
                .flatMap(e -> videoSource.get())
                .map(this::isAllVideosCompleted)
                .subscribe(new OnNextSubscriber<>((completed, subscriber) -> {
                    if (completed) {
                        showFragment(new DoneFragment());
                        subscriber.unsubscribe();
                    }
                }));
    }

    private boolean isAllVideosCompleted(List<VideoStatus> videoStatuses) {
        int complete = 0;

        for (VideoStatus v : videoStatuses) {
            complete += v.isCompleted() ? 1 : 0;
        }

        return complete == videoStatuses.size();
    }
}
