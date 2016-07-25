package com.dot.example.view;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.dot.custommenu.CustomMenu;
import com.dot.custommenu.listener.OnMenuItemClickListener;
import com.dot.custommenu.model.ListItemModel;
import com.dot.example.R;
import com.dot.example.view.fragments.*;

public class MainActivity extends AppCompatActivity implements OnMenuItemClickListener {

    private TextView textView;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameLayout = (FrameLayout) findViewById(R.id.container);

        RelativeLayout rootView = (RelativeLayout) findViewById(R.id.root);

        CustomMenu customMenu = new CustomMenu(this);

        customMenu.addItem("Facebook", R.drawable.ic_facebook, 0, new FacebookFragment());
        customMenu.addItem("Twitter", R.drawable.ic_twitter, 1, new TwitterFragment());
        customMenu.addItem("Linkedin", R.drawable.ic_linkedin, 2, new LinkedinFragment());
        customMenu.addItem("Instagram", R.drawable.ic_instagram, 3, new InstagramFragment());
        customMenu.addItem("GitHub", R.drawable.ic_github, 4, new GithubFragment());
        customMenu.addItem("Google Plus", R.drawable.ic_google_plus, 5, new GooglePlusFragment());
        customMenu.addItem("Youtube", R.drawable.ic_youtube, 6, new YoutubeFragment());
        customMenu.addItem("Settings", R.drawable.ic_settings, 7, new SettingsFragment());

        customMenu.setOnMenuItemClickListener(this)
                .setBorder("#FFFFFF", 5)
                .setAnimateView(frameLayout)
                .setItemSelected(0);

        rootView.addView(customMenu.create());

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.container, new FacebookFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onMenuItemSelected(ListItemModel item) {
        if (item.getFragment() != null){
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction()
                    .replace(R.id.container, item.getFragment())
                    .addToBackStack(null)
                    .commit();
        }
    }
}
