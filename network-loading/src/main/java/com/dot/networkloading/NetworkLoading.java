package com.dot.networkloading;

import android.animation.*;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Keep;
import android.util.AttributeSet;
import android.util.Log;
import android.view.*;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.*;
import com.dot.networkloading.listeners.OnLoadingListener;

import java.util.ArrayList;
import java.util.List;

import static com.dot.networkloading.Keeper.*;
import static com.dot.networkloading.Keeper.STATE.*;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class NetworkLoading extends RelativeLayout implements View.OnClickListener, View.OnAttachStateChangeListener {
    private int uniqueIdentifier = 0, reavealViewId = R.id.revealView;
    private TypedArray attributes;
    private View rootView, finishView;
    private LinearLayout container;
    private TextView title;
    private ImageView image, imageBackground;
    private ObjectAnimator fadeB = null,
            scaleYb = null,
            scaleXb = null,
            scaleYLoadingEnd = null,
            scaleXLoadingEnd = null,
            scaleYLoadingStart = null,
            scaleXLoadingStart = null;
    private Animator revealView = null;
    private OnLoadingListener loadingListener = null;


    public NetworkLoading(Context context) {
        super(context);

        init();
    }

    public NetworkLoading(Context context, AttributeSet attrs) {
        super(context, attrs);

        attributes = this.getContext().obtainStyledAttributes(attrs, R.styleable.network_loading, 0, 0);

        init();
    }

    public NetworkLoading(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        attributes = this.getContext().obtainStyledAttributes(attrs, R.styleable.network_loading, 0, 0);

        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NetworkLoading(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        attributes = this.getContext().obtainStyledAttributes(attrs, R.styleable.network_loading, 0, 0);

        init();
    }

    public void setImage(int resId){
        this.image.setImageResource(resId);
    }

    public void setImage(Bitmap bitmap) {
        this.image.setImageBitmap(bitmap);
    }

    public void setImageBackground(int resId){
        this.imageBackground.setImageResource(resId);
    }

    public void setImageBackground(Bitmap bitmap) {
        this.imageBackground.setImageBitmap(bitmap);
    }

    public void setText(int resId){
        this.title.setText(resId);
    }

    public void setText(String text){
        this.title.setText(text);
    }

    public void setRevealView(View revealView) {
        this.finishView = revealView;
    }

    public void setOnLoadingListener(OnLoadingListener loadingListener){
        this.loadingListener = loadingListener;
    }

    public void finsihLoading(){
        if (Keeper.getInstance().isAttached(uniqueIdentifier)){
            Keeper.getInstance().set(uniqueIdentifier, FINISHED);

            loadFinish().start();
        } else {
            Keeper.getInstance().set(uniqueIdentifier, REVEAL);
        }
    }

    private void init(){
        int imageId, imageBackgroundId;

        if (attributes != null) {
            rootView = View.inflate(getContext(), R.layout.network_loading, this);

            container = (LinearLayout) findViewById(R.id.container);
            title = (TextView) findViewById(R.id.title);
            image = (ImageView) findViewById(R.id.image);
            imageBackground = (ImageView) findViewById(R.id.imageBackground);
            finishView = findViewById(R.id.revealView);

            String text = attributes.getString(R.styleable.network_loading_text);
            imageId = attributes.getResourceId(R.styleable.network_loading_image, 0);
            imageBackgroundId = attributes.getResourceId(R.styleable.network_loading_imageBackground, 0);
            uniqueIdentifier = attributes.getInteger(R.styleable.network_loading_uniqueIdentifier, 0);
            reavealViewId = attributes.getResourceId(R.styleable.network_loading_revealView, R.id.revealView);

            title.setText(text);
            image.setImageResource(imageId);
            imageBackground.setImageResource(imageBackgroundId);

            attributes.recycle();

            Keeper.getInstance().add(uniqueIdentifier);

            addOnAttachStateChangeListener(this);

            switch (Keeper.getInstance().get(uniqueIdentifier)) {
                case STARTED:
                    container.setOnClickListener(this);
                    break;
                case CLICKED:
                    loadLoading().start();
                    break;
                case REVEAL:
                    if (Keeper.getInstance().isAttached(uniqueIdentifier)){
                        Keeper.getInstance().set(uniqueIdentifier, FINISHED);

                        loadFinish().start();
                    }
                    break;
                case FINISHED:
                    finishView.setVisibility(View.VISIBLE);
                    break;
            }

            Keeper.getInstance().setAttached(uniqueIdentifier, true);
        }
    }

    private AnimatorSet loadLoading(){
        AnimatorSet set = new AnimatorSet();

        image.setScaleX(1.5f);
        image.setScaleY(1.5f);

        scaleYb = ObjectAnimator.ofFloat(imageBackground, "scaleY", 0, 2);
        scaleYb.setDuration(2000);
        scaleYb.setRepeatMode(ValueAnimator.INFINITE);
        scaleYb.setRepeatCount(ValueAnimator.INFINITE);
        scaleYb.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                imageBackground.setVisibility(View.VISIBLE);
            }
        });

        scaleXb = ObjectAnimator.ofFloat(imageBackground, "scaleX", 0, 2);
        scaleXb.setDuration(2000);
        scaleXb.setRepeatMode(ValueAnimator.INFINITE);
        scaleXb.setRepeatCount(ValueAnimator.INFINITE);

        fadeB = ObjectAnimator.ofFloat(imageBackground, "alpha", 0.3f, 0);
        fadeB.setDuration(2000);
        fadeB.setRepeatCount(ValueAnimator.INFINITE);
        fadeB.setInterpolator(new AccelerateDecelerateInterpolator());

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                title.setVisibility(GONE);
                container.setBackgroundColor(Color.TRANSPARENT);
            }
        });

        set.play(scaleXb)
                .with(scaleYb)
                .with(fadeB);

        return set;
    }

    private AnimatorSet loadTransition(){
        AnimatorSet set = new AnimatorSet();

        Keeper.getInstance().set(uniqueIdentifier, CLICKED);

        ValueAnimator valueAnimator = ValueAnimator.ofInt(container.getWidth(), image.getWidth() + 30);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) container.getLayoutParams();
                params.width = (int) valueAnimator.getAnimatedValue();
                container.setLayoutParams(params);
            }
        });

        ValueAnimator weightTitle = ValueAnimator.ofInt(title.getWidth(), 0);
        weightTitle.setDuration(500);
        weightTitle.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                title.setWidth((Integer) valueAnimator.getAnimatedValue());
            }
        });

        ObjectAnimator fadeOutTitle = ObjectAnimator.ofFloat(title, "alpha", 1f, 0f);
        fadeOutTitle.setDuration(250);
        fadeOutTitle.setInterpolator(new AccelerateDecelerateInterpolator());

        scaleYLoadingStart = ObjectAnimator.ofFloat(image, "scaleY", image.getScaleY(), 1.5f);
        scaleYLoadingStart.setDuration(500);

        scaleXLoadingStart = ObjectAnimator.ofFloat(image, "scaleX", image.getScaleX(), 1.5f);
        scaleXLoadingStart.setDuration(500);

        scaleYLoadingStart.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //scaleYLoadingEnd.start();
                container.setBackgroundColor(Color.TRANSPARENT);
            }
        });

        set.play(weightTitle)
                .with(valueAnimator)
                .with(fadeOutTitle);

        set.play(scaleXLoadingStart)
                .with(scaleYLoadingStart)
                .after(weightTitle);

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                loadLoading().start();
            }
        });

        return set;
    }

    private AnimatorSet loadReveal(){
        AnimatorSet set = new AnimatorSet();

        int cx = finishView.getWidth() / 2;
        int cy = finishView.getHeight() / 2;

        float finalRadius = (float) Math.hypot(cx, cy);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            revealView = ViewAnimationUtils.createCircularReveal(finishView, cx, cy, 0, finalRadius);
        }

        set.play(revealView);

        return set;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        init();
    }

    private AnimatorSet loadFinish(){
        AnimatorSet set = new AnimatorSet();

        image.setScaleX(1.5f);
        image.setScaleY(1.5f);

        container.setBackgroundColor(Color.TRANSPARENT);
        title.setVisibility(GONE);

        if (Keeper.getInstance().isAttached(uniqueIdentifier)) {
            scaleXb.cancel();
            scaleYb.cancel();
        }

        ObjectAnimator scaleXStopLoadingStart = ObjectAnimator.ofFloat(image, "scaleX", image.getScaleX(), 2f);
        scaleXStopLoadingStart.setDuration(250);

        ObjectAnimator scaleYStopLoadingStart = ObjectAnimator.ofFloat(image, "scaleY", image.getScaleY(), 2f);
        scaleYStopLoadingStart.setDuration(250);

        final ObjectAnimator scaleXStopLoadingEnd = ObjectAnimator.ofFloat(image, "scaleX", 2f, 0f);
        scaleXStopLoadingEnd.setDuration(250);

        final ObjectAnimator scaleYStopLoadingEnd = ObjectAnimator.ofFloat(image, "scaleY", 2f, 0f);
        scaleYStopLoadingEnd.setDuration(250);

        scaleXStopLoadingEnd.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                finishView.setVisibility(View.VISIBLE);

                loadReveal().start();
            }
        });

        scaleXStopLoadingStart.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                scaleXStopLoadingEnd.start();
            }
        });

        scaleYStopLoadingStart.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                scaleYStopLoadingEnd.start();
            }
        });

        ObjectAnimator scaleXBackground = ObjectAnimator.ofFloat(imageBackground, "scaleX", imageBackground.getScaleX(), 2f);
        scaleXBackground.setDuration(500);

        ObjectAnimator scaleYBackground = ObjectAnimator.ofFloat(imageBackground, "scaleY", imageBackground.getScaleY(), 2f);
        scaleYBackground.setDuration(500);

        ObjectAnimator fadeBackground = ObjectAnimator.ofFloat(imageBackground, "alpha", imageBackground.getAlpha(), 0f);
        fadeBackground.setDuration(500);

        set.play(scaleXBackground)
                .with(scaleYBackground)
                .with(fadeBackground);

        set.play(scaleXStopLoadingStart)
                .with(scaleYStopLoadingStart)
                .after(scaleXBackground);

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (Keeper.getInstance().isAttached(uniqueIdentifier)) {
                    fadeB.cancel();
                }
            }
        });

        return set;
    }

    @Override
    public void onClick(View view) {
        if (getInstance().get(uniqueIdentifier) == STARTED) {
            getInstance().set(uniqueIdentifier, CLICKED);

            loadTransition().start();

            if (loadingListener != null){
                loadingListener.onLoading();
            }
        }
    }

    @Override
    public void onViewAttachedToWindow(View view) {
        Keeper.getInstance().setAttached(uniqueIdentifier, true);
    }

    @Override
    public void onViewDetachedFromWindow(View view) {
        Keeper.getInstance().setAttached(uniqueIdentifier, false);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        finishView = ((View) getParent()).findViewById(reavealViewId);
    }
}
