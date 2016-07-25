package com.dot.custommenu.animation.menu;

import android.animation.ObjectAnimator;
import com.dot.custommenu.animation.AnimatedMenu;
import com.dot.custommenu.animation.AnimationMenu;
import com.dot.custommenu.model.ListItemModel;

public class FadeAnimation implements AnimatedMenu {
    private AnimationMenu animationMenu = null;

    public FadeAnimation(AnimationMenu animationMenu) {
        this.animationMenu = animationMenu;
    }

    @Override
    public ObjectAnimator initAnimationFirstLinear(ListItemModel target) {
        return null;
    }

    @Override
    public ObjectAnimator initAnimationStartText(ListItemModel target) {
        return null;
    }

    @Override
    public ObjectAnimator initAnimationStartLinear(ListItemModel target) {
        return null;
    }

    @Override
    public ObjectAnimator initAnimationEndText(ListItemModel target) {
        return null;
    }

    @Override
    public ObjectAnimator initAnimationEndLinear(ListItemModel target) {
        return null;
    }

    @Override
    public ObjectAnimator initAnimationEndFadeLinear(ListItemModel target) {
        return null;
    }

    @Override
    public ObjectAnimator initAnimationStartFadeLinear(ListItemModel target) {
        return null;
    }
}
