package com.amsen.par.govideoyoself.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.amsen.par.govideoyoself.R;
import com.amsen.par.govideoyoself.base.BaseApplication;
import com.amsen.par.govideoyoself.base.dependency.ApplicationGraph;
import com.amsen.par.govideoyoself.base.dependency.ViewGraph;
import com.amsen.par.govideoyoself.base.rx.subscriber.OnNextSubscriber;
import com.amsen.par.govideoyoself.source.event.VideoEvent;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * @author Pär Amsen 2016
 */
public class BaseActivity extends AppCompatActivity {
    private ViewGraph viewGraph;
    private ApplicationGraph applicationGraph;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        applicationGraph = getApplicationGraph(this);
        viewGraph = new ViewGraph(this, applicationGraph.eventStream, applicationGraph.source);
    }

    public ApplicationGraph getApplicationGraph(Context context) {
        return getBaseApplication(context).getGraph();
    }

    public ViewGraph getViewGraph() {
        return viewGraph;
    }

    public BaseApplication getBaseApplication(Context context) {
        return (BaseApplication) context.getApplicationContext();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null && data.getData() != null) { //video result returned -> post it
            runOnMain(e -> applicationGraph.eventStream.post(new VideoEvent<>(requestCode, VideoEvent.Type.RECORDED, data.getData())));
        }
    }

    public void runOnMain(Action1<Boolean> func) {
        Observable.just(true)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new OnNextSubscriber<>(func));
    }

    public void showFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .replace(R.id.fragments, fragment)
                .commit();
    }
}
