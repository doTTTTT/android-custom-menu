package com.dot.custommenu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.dot.custommenu.animation.AnimationUtils;
import com.dot.custommenu.model.ListItemModel;
import com.dot.custommenu.listener.OnItemClickListener;
import com.dot.custommenu.listener.OnMenuItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class CustomMenu extends RelativeLayout implements View.OnClickListener, OnItemClickListener {
    private static final int SIZE_ITEM = 100;

    private RelativeLayout root;
    private View background;
    private ListItemModel lastItem = null;
    private LinearLayout listItem;
    private Context context;
    private List<ListItemModel> list;
    private OnMenuItemClickListener onMenuItemClickListener = null;
    private AnimatorSet start, end;
    private FloatingActionButton floatingActionButton;
    private int colorBorder, widthBorder;
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

    private void init() {
        root = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.custom_menu, null);
        this.addView(root);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.navigation_drawer);
        floatingActionButton.setOnClickListener(this);
        listItem = (LinearLayout) findViewById(R.id.listItem);
        background = (View) findViewById(R.id.background);

        list = new ArrayList<>();

        AnimationUtils.getInstance(context).setList(list);
        AnimationUtils.getInstance(context).setTime(0);
        AnimationUtils.getInstance(context).setY(floatingActionButton.getY());

        start = new AnimatorSet();
        end = new AnimatorSet();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();

        if (i == R.id.navigation_drawer) {
            AnimationUtils.getInstance(context).onClick(v);
            if (!isClicked) {
                start = AnimationUtils.getInstance(context).loadStartAnimation(start);
                start();
            } else {
                end = AnimationUtils.getInstance(context).loadEndAnimation(end);
                end();
            }
            isClicked = !isClicked;
        }
    }

    private void start(){
        if (!isStarted){
            if(!isFirst){
                isFirst = true;
                start = AnimationUtils.getInstance(context).loadFirstAnimation(start);
            }
            isStarted = true;
            start.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    isStarted = false;
                }
            });
            start.start();
            AnimationUtils.getInstance(context).fadeIn(background);
        }
    }

    private void end() {
        if (!isEnded){
            isEnded = true;
            end.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    isEnded = false;
                }
            });
            end.start();
            AnimationUtils.getInstance(context).fadeOut(background);
        }
    }

    public void addItem(String title, int resId, int idPosition){
        list.add(new ListItemModel(this, title, resId, (list.size() * SIZE_ITEM) + SIZE_ITEM, list.size(), this, idPosition));
    }

    public CustomMenu create(){
        listItem.addView(new ListItemModel(this, "", 0, SIZE_ITEM, list.size(), null, 0).getRoot());
        for (ListItemModel tmp : list){
            listItem.addView(tmp.getRoot());
            tmp.getRoot().setY(0);
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

    @Override
    public void onClick(ListItemModel item) {
        if (lastItem != null){
            //lastItem.getButton().setBackgroundTintList(ColorStateList.valueOf(Color.TRANSPARENT));
        }

        //item.getButton().setBackgroundTintList(ColorStateList.valueOf(Color.RED));

        lastItem = item;

        if (onMenuItemClickListener != null){
            onMenuItemClickListener.onMenuItemSelected(item.getIdPosition());
        }
    }
}