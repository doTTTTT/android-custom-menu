package com.dot.custommenu.model;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dot.custommenu.CustomMenu;
import com.dot.custommenu.R;
import com.dot.custommenu.animation.AnimationUtils;
import com.dot.custommenu.listener.OnItemClickListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListItemModel implements View.OnClickListener {
    private CustomMenu customMenu;
    private LinearLayout root;
    private CircleImageView button;
    private TextView title;
    private int y, position, idPosition;
    private OnItemClickListener onItemClickListener;

    public ListItemModel(CustomMenu menu, String title, int resId, int y, int position, OnItemClickListener onItemClickListener, int idPosition) {
        this.customMenu = menu;

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

        this.y = y;
        this.position = position;
        this.idPosition = idPosition;
        this.onItemClickListener = onItemClickListener;

        this.button.setOnClickListener(this);
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

    public int getY() {
        return y;
    }

    public int getPosition() {
        return position;
    }

    public int getIdPosition() {
        return idPosition;
    }

    @Override
    public void onClick(View v) {
        AnimationUtils.getInstance(customMenu.getContext()).onClick(v);
        onItemClickListener.onClick(this);
    }
}
