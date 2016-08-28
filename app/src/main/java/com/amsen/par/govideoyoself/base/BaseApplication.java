package com.amsen.par.govideoyoself.base;

import android.app.Application;

import com.amsen.par.govideoyoself.base.dependency.ApplicationGraph;

/**
 * @author Pär Amsen 2016
 */
public class BaseApplication extends Application {
    private ApplicationGraph graph;

    @Override
    public void onCreate() {
        super.onCreate();

        graph = new ApplicationGraph();
    }

    public ApplicationGraph getGraph() {
        return graph;
    }
}
