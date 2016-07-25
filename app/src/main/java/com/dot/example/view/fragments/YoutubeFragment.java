package com.dot.example.view.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dot.example.R;
import com.dot.networkloading.NetworkLoading;
import com.dot.networkloading.listeners.OnLoadingListener;

public class YoutubeFragment extends Fragment implements OnLoadingListener {
    private NetworkLoading networkLoading;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_youtube, container, false);

        networkLoading = (NetworkLoading) rootView.findViewById(R.id.network_loading);
        networkLoading.setOnLoadingListener(this);

        return rootView;
    }

    @Override
    public void onLoading() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                YoutubeFragment.this.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        networkLoading.finsihLoading();
                    }
                });
            }
        }, 5000);
    }
}