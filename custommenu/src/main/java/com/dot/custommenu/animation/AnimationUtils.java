package com.dot.custommenu.animation;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.dot.custommenu.R;
import com.dot.custommenu.model.ListItemModel;

import java.util.List;

public class AnimationUtils {
    private static int TIME_INCREMENTATION = 0;

    private Context context;
    private List<ListItemModel> list = null;
    private float y = 0;
    private long time = 0;
    private static AnimationUtils animationUtils = null;

    public static AnimationUtils getInstance(Context context){
        if (animationUtils == null){
            animationUtils = new AnimationUtils(context);
        }
        return animationUtils;
    }

    public AnimationUtils(Context context) {
        this.context = context;
    }

    public void setList(List<ListItemModel> list){
        this.list = list;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setTimeIncrementation(int timeIncrementation) {
        this.TIME_INCREMENTATION = timeIncrementation;
    }

    public void onClick(View view){
        AnimatorSet animY = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.bounce_button);
        animY.setTarget(view);
        animY.start();
    }

    public AnimatorSet loadFirstAnimation(AnimatorSet start){
        start = new AnimatorSet();
        for (ListItemModel tmp :  list){
            start.play(initAnimationStartText(tmp)).after(initAnimationFirstLinear(tmp)).after(time);
            time += TIME_INCREMENTATION;
        }
        return start;
    }

    public AnimatorSet loadStartAnimation(AnimatorSet start){
        start = new AnimatorSet();
        for (ListItemModel tmp :  list){
            start.play(initAnimationStartText(tmp)).after(initAnimationStartLinear(tmp)).after(time);
            time += TIME_INCREMENTATION;
        }
        return start;
    }

    public AnimatorSet loadEndAnimation(AnimatorSet end){
        end = new AnimatorSet();
        for (ListItemModel tmp : list){
            end.play(initAnimationEndLinear(tmp)).after(initAnimationEndText(tmp)).after(time);
            time += TIME_INCREMENTATION;
        }
        return end;
    }

    public ObjectAnimator initAnimationFirstLinear(final ListItemModel target){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(target.getRoot(), "translationY", 0);
        target.getRoot().setY(0);
        objectAnimator.setDuration(500);
        objectAnimator.setInterpolator(new DecelerateInterpolator());
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                target.getRoot().getChildAt(0).setVisibility(View.VISIBLE);
            }
        });

        target.getRoot().setVisibility(View.VISIBLE);

        return objectAnimator;
    }

    public ObjectAnimator initAnimationStartText(final ListItemModel target){
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

    public ObjectAnimator initAnimationStartLinear(final ListItemModel target){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(target.getRoot(), "translationY", 0);
        target.getRoot().setY(target.getRoot().getY());
        objectAnimator.setDuration(500);
        objectAnimator.setInterpolator(new DecelerateInterpolator());
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                target.getRoot().getChildAt(0).setVisibility(View.VISIBLE);
            }
        });

        target.getRoot().setVisibility(View.VISIBLE);

        return objectAnimator;
    }

    public ObjectAnimator initAnimationEndText(final ListItemModel target){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(target.getTitle(), "alpha", 1f, 0f);
        objectAnimator.setDuration(250);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                target.getTitle().setVisibility(View.INVISIBLE);
            }
        });

        return objectAnimator;
    }

    public ObjectAnimator initAnimationEndLinear(ListItemModel target){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(target.getRoot(), "translationY", y - target.getRoot().getY());
        objectAnimator.setDuration(500);
        objectAnimator.setInterpolator(new DecelerateInterpolator());

        target.getRoot().setVisibility(View.VISIBLE);

        return objectAnimator;
    }

    public void fadeIn(View view){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
        objectAnimator.setDuration(500);
        objectAnimator.setInterpolator(new DecelerateInterpolator());

        view.setVisibility(View.VISIBLE);

        objectAnimator.start();
    }

    public void fadeOut(final View view){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);
        objectAnimator.setDuration(500);
        objectAnimator.setInterpolator(new DecelerateInterpolator());

        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.INVISIBLE);
            }
        });

        objectAnimator.start();
    }
}
