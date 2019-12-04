package com.example.koperasiku.Fragment;

import android.app.Activity;
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
import android.widget.EditText;
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
    TextView tvResultNama;
    String resultNama;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = (FrameLayout) inflater.inflate(R.layout.fragment_profile, container, false);

        session = new UserSessionManager(getActivity().getApplicationContext());

        TextView lblnama = (TextView) rootView.findViewById(R.id.home_nama);
        TextView lblemail = (TextView) rootView.findViewById(R.id.email_text);
        TextView lblrole = (TextView) rootView.findViewById(R.id.home_jabatan);
        TextView lbltelepon = (TextView) rootView.findViewById(R.id.notelp);

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
        final String user_role = user.get(UserSessionManager.KEY_ROLE);
        final String no_telp = user.get(UserSessionManager.KEY_PHONE);

        //        Bundle extras = getIntent().getExtras();
//        if (extras != null)
//            resultNama = extras.getString("result_nama");
//        tvResultNama.setText(resultNama);
        lblnama.setText(nama);
        lblemail.setText(email);
        lblrole.setText(user_role);
        lbltelepon.setText(no_telp);




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