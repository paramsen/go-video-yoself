package com.amsen.par.govideoyoself.view.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amsen.par.govideoyoself.R;
import com.amsen.par.govideoyoself.base.dependency.ApplicationGraph;
import com.amsen.par.govideoyoself.base.dependency.ViewGraph;
import com.amsen.par.govideoyoself.base.rx.event.EventStream;
import com.amsen.par.govideoyoself.view.activity.BaseActivity;

import butterknife.ButterKnife;

/**
 * @author Pär Amsen 2016
 */
public abstract class BaseFragment extends Fragment {
    private EventStream eventStream;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayout(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        eventStream = getGraph().eventStream;
        ButterKnife.bind(this, view);
    }

    public BaseActivity getBaseActivity() {
        return ((BaseActivity) getActivity());
    }

    public ViewGraph getGraph() {
        return getBaseActivity().getViewGraph();
    }

    public EventStream getEventStream() {
        return eventStream;
    }

    @LayoutRes
    protected abstract int getLayout();
}
