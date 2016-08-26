package com.amsen.par.govideoyoself.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.amsen.par.govideoyoself.R;
import com.amsen.par.govideoyoself.view.base.rx.event.Event;
import com.amsen.par.govideoyoself.view.base.rx.event.EventStream;
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
        videoSource.init();

        Observable<Event> videoEventStream = eventStream.historyStream()
                .filter(e -> e instanceof VideoEvent);

        videoEventStream
                .filter(e -> e.type)
    }
}
