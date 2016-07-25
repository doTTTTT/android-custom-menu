package com.dot.custommenu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import android.widget.ScrollView;
import android.widget.Toast;
import com.dot.custommenu.animation.AnimatedMenu;
import com.dot.custommenu.animation.AnimationMenu;
import com.dot.custommenu.animation.AnimationUtils;
import com.dot.custommenu.animation.menu.FadeAnimation;
import com.dot.custommenu.animation.menu.SlideUpAnimation;
import com.dot.custommenu.model.ListItemModel;
import com.dot.custommenu.listener.OnItemClickListener;
import com.dot.custommenu.listener.OnMenuItemClickListener;
import de.hdodenhof.circleimageview.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import static com.dot.custommenu.animation.AnimationMenu.*;

public class CustomMenu extends RelativeLayout implements View.OnClickListener, OnItemClickListener {
    private RelativeLayout root;
    private View background, parentView;
    private ListItemModel lastItem = null;
    private LinearLayout listItem;
    private Context context;
    private List<ListItemModel> list;
    private OnMenuItemClickListener onMenuItemClickListener = null;
    private AnimatorSet start = null, end = null;
    private AnimationMenu animationMenu = null;
    private CircleImageView floatingActionButton;
    private int colorBorder,
            widthBorder,
            timeBackground = 500;
    private boolean isClicked = false,
            isStarted = false,
            isEnded = false,
            isFirst = false,
            isBorder = false,
            isAnimationViewEnable = false;

    public CustomMenu(Context context) {
        super(context);
        this.context = context;

        init();
    }

    public CustomMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        init();
    }

    public CustomMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void init() {
        ScrollView scrollView = (ScrollView) LayoutInflater.from(context).inflate(R.layout.custom_menu, null);
        root = (RelativeLayout) scrollView.findViewById(R.id.root);

        this.addView(scrollView);

        floatingActionButton = (CircleImageView) findViewById(R.id.navigation_drawer);
        floatingActionButton.setOnClickListener(this);
        listItem = (LinearLayout) findViewById(R.id.listItem);
        background = (View) findViewById(R.id.background);

        list = new ArrayList<>();

        animationMenu = new AnimationMenu().setList(list)
                .setTime(0)
                .setY(floatingActionButton.getY())
                .setAnimatedMenu(new SlideUpAnimation(floatingActionButton.getY()));

        start = new AnimatorSet();
        end = new AnimatorSet();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void start(){
        if (!isStarted()){
            floatingActionButton.setEnabled(false);
            start = animationMenu.loadStartAnimation();

            if(!isFirst()){
                isFirst = true;
                start = animationMenu.loadFirstAnimation();
            }

            isStarted = true;
            start.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    isStarted = false;
                    floatingActionButton.setEnabled(true);
                }
            });
            start.start();
            AnimationUtils.fadeIn(background, 750);
            if (isAnimationViewEnable()) {
                parentView.animate().setDuration(750).rotationY(-25).translationX(500).start();
            }
        }
        isClicked = !isClicked;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void end() {
        if (!isEnded()){
            floatingActionButton.setEnabled(false);
            end = animationMenu.loadEndAnimation();

            isEnded = true;
            end.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    isEnded = false;
                    floatingActionButton.setEnabled(true);
                }
            });
            end.start();
            AnimationUtils.fadeOut(background, 750);
            if (isAnimationViewEnable()) {
                parentView.animate().setDuration(750).rotationY(0).translationX(0).start();
            }
        }
        isClicked = !isClicked;
    }

    public void addItem(String title, int resId, int position){
        list.add(position, new ListItemModel(this, title, resId, this, position));
    }

    public void addItem(String title, int resId, int position, Fragment fragment){
        list.add(position, new ListItemModel(this, title, resId, this, position, fragment));
    }

    public CustomMenu create(){
        ListItemModel empty = new ListItemModel(this, "", 0, null, 0);
        empty.getRoot().setVisibility(VISIBLE);

        listItem.addView(empty.getRoot());

        for (ListItemModel tmp : list){
            listItem.addView(tmp.getRoot());
        }

        return this;
    }

    public CustomMenu setItemSelected(int positon) {
        setItemSelected(getItem(positon));

        return this;
    }

    public CustomMenu setItemSelected(ListItemModel item) {
        if (lastItem != null){
            lastItem.getTitle().setTextColor(Color.WHITE);
        }

        item.getTitle().setTextColor(Color.GRAY);

        lastItem = item;

        return this;
    }

    public CustomMenu setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.onMenuItemClickListener = onMenuItemClickListener;

        return this;
    }

    public CustomMenu setMenu(int resId){
        Menu menu = (Menu) new MenuInflater(context);

        for (int i = 0; i < menu.size(); ++i){
            MenuItem menuItem = menu.getItem(i);
            list.add(new ListItemModel(this, menuItem.getTitle().toString(), 0, this, menuItem.getItemId()));
        }

        return this;
    }

    public CustomMenu setBorder(String color, int width){
        this.isBorder = true;
        this.colorBorder = Color.parseColor(color);
        this.widthBorder = width;

        return this;
    }

    public CustomMenu setEnableAnimationView(boolean enable){
        this.isAnimationViewEnable = enable;

        return this;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomMenu setAnimateView(View view){
        this.parentView = view;

        return setEnableAnimationView(true);
    }

    public CustomMenu setAnimatedMenu(MenuAnimationType type){
        AnimatedMenu animatedMenu = null;

        switch (type){
            case SLIDE_UP:
                animatedMenu = new SlideUpAnimation(animationMenu.getY());
                break;
            case FADE:
                animatedMenu = new FadeAnimation(animationMenu);
                break;
        }
        animationMenu.setAnimatedMenu(animatedMenu);

        return this;
    }

    public boolean isBorder() {
        return isBorder;
    }

    public boolean isAnimationViewEnable() {
        return isAnimationViewEnable;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public boolean isEnded() {
        return isEnded;
    }

    public boolean isFirst(){
        return isFirst;
    }

    public ListItemModel getItem(int positon){
        return this.list.get(positon);
    }

    public View getFloatingActionButton() {
        return floatingActionButton;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(ListItemModel item) {
        setItemSelected(item);

        if (onMenuItemClickListener != null){
            Log.d("CustomMenu", "Clicked");
            onMenuItemClickListener.onMenuItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();

        if (i == R.id.navigation_drawer) {
            AnimationUtils.onClick(context, v);
            if (!isClicked()) {
                start();
            } else {
                end();
            }
        }
    }
}