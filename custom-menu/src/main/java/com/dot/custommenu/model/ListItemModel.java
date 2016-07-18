package com.dot.custommenu.model;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dot.custommenu.CustomMenu;
import com.dot.custommenu.R;
import com.dot.custommenu.animation.AnimationMenu;
import com.dot.custommenu.listener.OnItemClickListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListItemModel implements View.OnClickListener {
    private CustomMenu customMenu;
    private LinearLayout root;
    private CircleImageView button;
    private TextView title;
    private Fragment fragment = null;
    private int idPosition;
    private OnItemClickListener onItemClickListener;

    public ListItemModel(CustomMenu menu, String title, int resId, OnItemClickListener onItemClickListener, int idPosition) {
        this.customMenu = menu;

        init(title, idPosition, onItemClickListener, resId);
    }

    public ListItemModel(CustomMenu menu, String title, int resId, OnItemClickListener onItemClickListener, int idPosition, Fragment fragment) {
        this.customMenu = menu;
        this.fragment = fragment;

        init(title, idPosition, onItemClickListener, resId);
    }

    private void init(String title, int idPosition, OnItemClickListener onItemClickListener, int resId){
        LayoutInflater inflater = (LayoutInflater) customMenu.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        root = (LinearLayout) inflater.inflate(R.layout.custom_menu_item, null);

        this.title = (TextView) root.findViewById(R.id.title);
        this.button = (CircleImageView) root.findViewById(R.id.button);

        this.title.setText(title);
        this.button.setImageResource(resId);

        if (customMenu.isBorder()) {
            this.button.setBorderColor(Color.WHITE);
            this.button.setBorderWidth(10);
            this.button.setBorderOverlay(true);
        }

        this.idPosition = idPosition;
        this.onItemClickListener = onItemClickListener;

        this.root.setOnClickListener(this);
        this.root.setEnabled(false);
    }

    public LinearLayout getRoot() {
        return root;
    }

    public CircleImageView getButton() {
        return button;
    }

    public TextView getTitle() {
        return title;
    }

    public int getPosition() {
        return idPosition;
    }

    public Fragment getFragment() {
        return fragment;
    }

    @Override
    public void onClick(View v) {
        AnimationMenu.getInstance(customMenu.getContext()).onClick(button);
        if (onItemClickListener != null){
            onItemClickListener.onClick(this);
        }

        customMenu.end();
    }
}
