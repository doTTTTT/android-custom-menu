package com.dot.example.view.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dot.example.R;
import com.dot.networkloading.NetworkLoading;
import com.dot.networkloading.listeners.OnLoadingListener;

import java.util.Timer;
import java.util.TimerTask;

public class FacebookFragment extends Fragment implements OnLoadingListener {
    private NetworkLoading networkLoading;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_facebook, container, false);

        networkLoading = (NetworkLoading) rootView.findViewById(R.id.network_loading);
        networkLoading.setOnLoadingListener(this);

        return rootView;
    }

    @Override
    public void onLoading() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FacebookFragment.this.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        networkLoading.finsihLoading();
                    }
                });
            }
        }, 5000);
    }
}
