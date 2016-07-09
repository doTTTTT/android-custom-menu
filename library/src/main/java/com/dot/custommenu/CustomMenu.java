package com.dot.custommenu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.dot.custommenu.animation.AnimationMenu;
import com.dot.custommenu.animation.AnimationUtils;
import com.dot.custommenu.model.ListItemModel;
import com.dot.custommenu.listener.OnItemClickListener;
import com.dot.custommenu.listener.OnMenuItemClickListener;
import de.hdodenhof.circleimageview.CircleImageView;

import java.util.ArrayList;
import java.util.List;

public class CustomMenu extends RelativeLayout implements View.OnClickListener, OnItemClickListener {
    public static final int SIZE_ITEM = 100;

    private RelativeLayout root;
    private View background, parentView;
    private ListItemModel lastItem = null;
    private LinearLayout listItem;
    private Context context;
    private List<ListItemModel> list;
    private OnMenuItemClickListener onMenuItemClickListener = null;
    private AnimatorSet start, end;
    private CircleImageView floatingActionButton;
    private int colorBorder, widthBorder, timeBackground = 500;
    private boolean isClicked = false, isStarted = false, isEnded = false, isFirst = false, isBorder = false;

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
        root = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.custom_menu, null);
        this.addView(root);

        floatingActionButton = (CircleImageView) findViewById(R.id.navigation_drawer);
        floatingActionButton.setOnClickListener(this);
        listItem = (LinearLayout) findViewById(R.id.listItem);
        background = (View) findViewById(R.id.background);

        list = new ArrayList<>();

        AnimationMenu.getInstance(context).setList(list);
        AnimationMenu.getInstance(context).setTime(0);
        AnimationMenu.getInstance(context).setY(floatingActionButton.getY());

        start = new AnimatorSet();
        end = new AnimatorSet();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();

        if (i == R.id.navigation_drawer) {
            AnimationMenu.getInstance(context).onClick(v);
            if (!isClicked) {
                start();
            } else {
                end();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setParentView(View view){
        this.parentView = view;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void start(){
        if (!isStarted){
            floatingActionButton.setEnabled(false);
            Log.d("Start", "Click");
            start = AnimationMenu.getInstance(context).loadStartAnimation();

            if(!isFirst){
                isFirst = true;
                start = AnimationMenu.getInstance(context).loadFirstAnimation();
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
            parentView.animate().setDuration(750).rotationY(-25).translationX(500).start();
        }
        isClicked = !isClicked;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void end() {
        if (!isEnded){
            Log.d("End", "Click");
            floatingActionButton.setEnabled(false);
            end = AnimationMenu.getInstance(context).loadEndAnimation();

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
            parentView.animate().setDuration(750).rotationY(0).translationX(0).start();
        }
        isClicked = !isClicked;
    }

    public void addItem(String title, int resId, int idPosition){
        list.add(new ListItemModel(this, title, resId, (list.size() * SIZE_ITEM) + SIZE_ITEM, list.size(), this, idPosition));
    }

    public CustomMenu create(){
        listItem.addView(new ListItemModel(this, "", 0, SIZE_ITEM, list.size(), null, 0).getRoot());

        for (ListItemModel tmp : list){
            listItem.addView(tmp.getRoot());
        }

        return this;
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.onMenuItemClickListener = onMenuItemClickListener;
    }

    public void setMenu(int resId){
        Menu menu = (Menu) new MenuInflater(context);

        for (int i = 0; i < menu.size(); ++i){
            MenuItem menuItem = menu.getItem(i);
            list.add(new ListItemModel(this, menuItem.getTitle().toString(), 0, (list.size() * SIZE_ITEM) + SIZE_ITEM, list.size(), this, menuItem.getItemId()));
        }
    }

    public void setBorder(String color, int width){
        isBorder = true;
        colorBorder = Color.parseColor(color);
        widthBorder = width;
    }

    public int getWidthBorder() {
        return widthBorder;
    }

    public int getColorBorder() {
        return colorBorder;
    }

    public boolean isBorder() {
        return isBorder;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(ListItemModel item) {
        if (lastItem != null){
            //lastItem.getRoot().setBackgroundColor(Color.TRANSPARENT);
            lastItem.getTitle().setTextColor(Color.WHITE);
        }

        item.getTitle().setTextColor(Color.GRAY);
        //item.getRoot().setBackgroundColor(Color.parseColor("#55000000"));

        lastItem = item;

        if (onMenuItemClickListener != null){
            onMenuItemClickListener.onMenuItemSelected(item);
        }
    }
}