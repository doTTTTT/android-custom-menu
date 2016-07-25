package com.dot.custommenu.animation;

import android.animation.*;
import android.content.Context;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import com.dot.custommenu.R;

public class AnimationUtils {
    public static void onClick(Context context, View view){
        AnimatorSet animY = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.bounce_button);
        animY.setTarget(view);
        animY.start();
    }

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
