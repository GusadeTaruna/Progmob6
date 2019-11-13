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
    String mParam1;
    FrameLayout rootView;
    Button btnLogout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = (FrameLayout) inflater.inflate(R.layout.fragment_home, container, false);

        session = new UserSessionManager(getActivity().getApplicationContext());

        TextView lblnama = (TextView) rootView.findViewById(R.id.home_nama);
        btnLogout = (Button) rootView.findViewById(R.id.btnLogout);

        Toast.makeText(getActivity().getApplicationContext(),"User Login Status : " + session.isUserLoggedIn(),
                        Toast.LENGTH_LONG).show();


        if(session.checkLogin()){
            getActivity().finish();
        }
        HashMap<String, String> user = session.getUserDetails();
        String name = user.get(UserSessionManager.KEY_NAME);
        lblnama.setText(Html.fromHtml("Selamat Datang " +  name));

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.logoutUser();
            }
        });

        return rootView;
    }


}