package com.dot.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.dot.custommenu.CustomMenu;
import com.dot.custommenu.listener.OnMenuItemClickListener;
import com.dot.custommenu.model.ListItemModel;

public class MainActivity extends AppCompatActivity implements OnMenuItemClickListener {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.social);
        RelativeLayout rootView = (RelativeLayout) findViewById(R.id.root);

        CustomMenu customMenu = new CustomMenu(this);

        customMenu.addItem("Facebook", R.drawable.ic_facebook, 0);
        customMenu.addItem("Twitter", R.drawable.ic_twitter, 1);
        customMenu.addItem("Linkedin", R.drawable.ic_linkedin, 2);
        customMenu.addItem("Instagramm", R.drawable.ic_instagram, 3);
        customMenu.addItem("GitHub", R.drawable.ic_github, 4);
        customMenu.addItem("Google Plus", R.drawable.ic_google_plus, 5);
        customMenu.addItem("Youtube", R.drawable.ic_youtube, 6);

        customMenu.setOnMenuItemClickListener(this);

        customMenu.setBorder("#FFFFFF", 5);

        customMenu.setParentView(textView);

        rootView.addView(customMenu.create());
    }

    @Override
    public void onMenuItemSelected(ListItemModel item) {
        textView.setText(item.getTitle().getText().toString());
    }
}
