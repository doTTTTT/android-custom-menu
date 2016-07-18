package com.dot.custommenu.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

public class AnimationUtils {
    public static void fadeIn(View view){
        fadeIn(view, 500);
    }

    public static void fadeIn(View view, int time){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
        objectAnimator.setDuration(time);
        objectAnimator.setInterpolator(new DecelerateInterpolator());

        view.setVisibility(View.VISIBLE);

        objectAnimator.start();
    }

    public static void fadeOut(View view){
        fadeOut(view, 500);
    }

    public static void fadeOut(final View view, int time){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);
        objectAnimator.setDuration(time);
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
