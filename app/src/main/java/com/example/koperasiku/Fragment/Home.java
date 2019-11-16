package com.example.koperasiku.Fragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.koperasiku.R;
import com.example.koperasiku.RegisterActivity;
import com.example.koperasiku.UserSessionManager;

import java.util.HashMap;

public class Home extends Fragment {

    UserSessionManager session;
    FrameLayout rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = (FrameLayout) inflater.inflate(R.layout.fragment_home, container, false);

        session = new UserSessionManager(getActivity().getApplicationContext());

        if(session.checkLogin()){
            getActivity().finish();
        }

        return rootView;
    }


}