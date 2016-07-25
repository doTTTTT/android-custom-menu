package com.dot.custommenu.animation;

import android.animation.ObjectAnimator;
import com.dot.custommenu.model.ListItemModel;

public interface AnimatedMenu {
    public ObjectAnimator initAnimationFirstLinear(final ListItemModel target);

    public ObjectAnimator initAnimationStartText(final ListItemModel target);
    public ObjectAnimator initAnimationStartLinear(final ListItemModel target);

    public ObjectAnimator initAnimationEndText(final ListItemModel target);
    public ObjectAnimator initAnimationEndLinear(final ListItemModel target);

    public ObjectAnimator initAnimationEndFadeLinear(final ListItemModel target);
    public ObjectAnimator initAnimationStartFadeLinear(final ListItemModel target);
}
