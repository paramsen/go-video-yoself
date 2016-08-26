package com.amsen.par.govideoyoself.view.activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.amsen.par.govideoyoself.R;
import com.amsen.par.govideoyoself.view.base.rx.event.Event;
import com.amsen.par.govideoyoself.view.base.rx.event.EventStream;
import com.amsen.par.govideoyoself.view.base.rx.subscriber.OnNextSubscriber;
import com.amsen.par.govideoyoself.view.fragment.DoneFragment;
import com.amsen.par.govideoyoself.view.fragment.ListFragment;
import com.amsen.par.govideoyoself.view.model.VideoStatus;
import com.amsen.par.govideoyoself.view.source.VideoSource;
import com.amsen.par.govideoyoself.view.source.event.VideoEvent;

import rx.Observable;

/**
 * @author Pär Amsen 2016
 */
public class HomeActivity extends AppCompatActivity {
    private VideoSource videoSource;
    private EventStream eventStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDependencies();

        setContentView(R.layout.activity_home);

        initialState();
    }

    private void initDependencies() {
        eventStream = new EventStream();
        videoSource = new VideoSource();
    }

    private void initialState() {
        videoSource
                .getVideos()
                .map(videoStatuses -> {
                    int complete = 0;

                    for(VideoStatus v : videoStatuses) {
                        complete += v.isCompleted() ? 1 : 0;
                    }

                    return complete == videoStatuses.size();
                })
                .subscribe(new OnNextSubscriber<>(completedVideos -> {
                    if(completedVideos) {
                        showFragment(new DoneFragment());
                    } else {
                        showFragment(new ListFragment());
                    }
                }));
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragments, fragment)
                .commit();
    }
}
