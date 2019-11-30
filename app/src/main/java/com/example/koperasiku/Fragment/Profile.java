package com.example.koperasiku.Fragment;

import android.content.Context;
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

import com.example.koperasiku.EditProfileActivity;
import com.example.koperasiku.MainActivity;
import com.example.koperasiku.R;
import com.example.koperasiku.UserSessionManager;

import java.util.HashMap;

public class Profile extends Fragment {

    UserSessionManager session;
    FrameLayout rootView;
    Button btnLogout;
    Button btnEdit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = (FrameLayout) inflater.inflate(R.layout.fragment_profile, container, false);

        session = new UserSessionManager(getActivity().getApplicationContext());

        TextView lblnama = (TextView) rootView.findViewById(R.id.home_nama);
        btnLogout = (Button) rootView.findViewById(R.id.btnLogout);
        btnEdit = (Button) rootView.findViewById(R.id.btnEdit);

//        Toast.makeText(getActivity().getApplicationContext(),"User Login Status : " + session.isUserLoggedIn(),
//                Toast.LENGTH_LONG).show();


        if(session.checkLogin()){
            getActivity().finish();
        }
        HashMap<String, String> user = session.getUserDetails();
        final String id = user.get(UserSessionManager.ID);
        final String nama = user.get(UserSessionManager.KEY_NAME);
        final String email = user.get(UserSessionManager.KEY_EMAIL);
        lblnama.setText(Html.fromHtml(nama));

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.logoutUser();
                Toast.makeText(getActivity(), "Logout Berhasil", Toast.LENGTH_SHORT).show();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
}