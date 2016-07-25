package com.dot.custommenu.animation;

import android.animation.AnimatorSet;
import com.dot.custommenu.model.ListItemModel;

import java.util.List;

public class AnimationMenu {
    public enum MenuAnimationType {
        SLIDE_UP,
        FADE
    }

    private static int TIME_INCREMENTATION = 0;

    private List<ListItemModel> list = null;
    private long time = 0;
    private AnimatedMenu animatedMenu = null;
    private float y = 0;

    public AnimationMenu setList(List<ListItemModel> list){
        this.list = list;

        return this;
    }

    public AnimationMenu setY(float y) {
        this.y = y;

        return this;
    }

    public AnimationMenu setTime(int time) {
        this.time = time;

        return this;
    }

    public AnimationMenu setTimeIncrementation(int timeIncrementation) {
        TIME_INCREMENTATION = timeIncrementation;

        return this;
    }

    public AnimationMenu setAnimatedMenu(AnimatedMenu animatedMenu){
        this.animatedMenu = animatedMenu;

        return this;
    }

    public AnimatorSet loadFirstAnimation(){
        AnimatorSet start = new AnimatorSet();
        for (ListItemModel tmp :  list){
            start.play(animatedMenu.initAnimationStartFadeLinear(tmp))
                    .with(animatedMenu.initAnimationFirstLinear(tmp))
                    .before(animatedMenu.initAnimationStartText(tmp));
            time += TIME_INCREMENTATION;
        }
        return start;
    }

    public AnimatorSet loadStartAnimation(){
        AnimatorSet start = new AnimatorSet();
        for (ListItemModel tmp :  list){
            start.play(animatedMenu.initAnimationStartFadeLinear(tmp))
                .with(animatedMenu.initAnimationStartLinear(tmp))
                    .before(animatedMenu.initAnimationStartText(tmp));
            time += TIME_INCREMENTATION;
        }
        return start;
    }

    public AnimatorSet loadEndAnimation(){
        AnimatorSet end = new AnimatorSet();
        for (ListItemModel tmp : list){
            end.play(animatedMenu.initAnimationEndLinear(tmp))
                    .with(animatedMenu.initAnimationEndFadeLinear(tmp))
                    .after(animatedMenu.initAnimationEndText(tmp))
                    .after(time);
            time += TIME_INCREMENTATION;
        }
        return end;
    }

    public float getY() {
        return y;
    }
}
