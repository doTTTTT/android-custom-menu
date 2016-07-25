package com.dot.custommenu.animation.menu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import com.dot.custommenu.animation.AnimatedMenu;
import com.dot.custommenu.model.ListItemModel;

public class SlideUpAnimation implements AnimatedMenu {
    private float y = 0;

    public SlideUpAnimation(float y){
        this.y = y;
    }

    @Override
    public ObjectAnimator initAnimationFirstLinear(final ListItemModel target) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(target.getRoot(), "translationY", 0);
        target.getRoot().setY(0);
        objectAnimator.setDuration(500);
        objectAnimator.setInterpolator(new DecelerateInterpolator());
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                target.getButton().setVisibility(View.VISIBLE);
            }
        });

        target.getRoot().setVisibility(View.VISIBLE);
        target.getRoot().setEnabled(true);

        return objectAnimator;
    }

    @Override
    public ObjectAnimator initAnimationStartText(final ListItemModel target) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(target.getTitle(), "alpha", 0f, 1f);
        objectAnimator.setDuration(250);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                target.getTitle().setVisibility(View.VISIBLE);
            }
        });

        return objectAnimator;
    }

    @Override
    public ObjectAnimator initAnimationStartLinear(final ListItemModel target) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(target.getRoot(), "translationY", 0);
        target.getRoot().setY(0);
        objectAnimator.setDuration(500);
        objectAnimator.setInterpolator(new DecelerateInterpolator());
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                target.getButton().setVisibility(View.VISIBLE);
            }
        });

        target.getRoot().setVisibility(View.VISIBLE);
        target.getRoot().setEnabled(true);

        return objectAnimator;
    }

    @Override
    public ObjectAnimator initAnimationEndText(final ListItemModel target) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(target.getTitle(), "alpha", 1f, 0f);
        objectAnimator.setDuration(250);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                target.getTitle().setVisibility(View.VISIBLE);
            }
        });

        return objectAnimator;
    }

    @Override
    public ObjectAnimator initAnimationEndLinear(final ListItemModel target) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(target.getRoot(), "translationY", y - target.getRoot().getTop());
        objectAnimator.setDuration(500);
        objectAnimator.setInterpolator(new DecelerateInterpolator());
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                target.getRoot().setVisibility(View.GONE);
            }
        });

        target.getRoot().setVisibility(View.VISIBLE);
        target.getRoot().setEnabled(false);

        return objectAnimator;
    }

    @Override
    public ObjectAnimator initAnimationEndFadeLinear(ListItemModel target) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(target.getRoot(), "alpha", 1f, 0f);
        objectAnimator.setDuration(250);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());

        return objectAnimator;
    }

    @Override
    public ObjectAnimator initAnimationStartFadeLinear(ListItemModel target) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(target.getRoot(), "alpha", 0f, 1f);
        objectAnimator.setDuration(250);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());

        return objectAnimator;
    }
}
