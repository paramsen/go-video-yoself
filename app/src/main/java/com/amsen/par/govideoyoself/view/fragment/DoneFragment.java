package com.amsen.par.govideoyoself.view.fragment;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.Transition;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;

import com.amsen.par.govideoyoself.R;

/**
 * @author Pär Amsen 2016
 */
public class DoneFragment extends BaseFragment {
    @Override
    protected int getLayout() {
        return R.layout.fragment_done;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupIntro();
    }

    private void setupIntro() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    reveal();

                    getView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            });
        }
    }

    /**
     * If < 21 just fade in.
     */
    @TargetApi(21)
    void reveal(){
        int finalRadius = Math.max(getView().getWidth(), getView().getHeight());

        Animator animator = ViewAnimationUtils.createCircularReveal(getView(), getView().getWidth()/2, getView().getHeight()/2, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(getResources().getInteger(android.R.integer.config_longAnimTime));
        animator.start();
    }
}
