package com.example.koperasiku.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.koperasiku.EditProfileActivity;
import com.example.koperasiku.R;
import com.example.koperasiku.UserSessionManager;
import com.example.koperasiku.apihelper.BaseApiService;
import com.example.koperasiku.apihelper.UtilsApi;
import com.example.koperasiku.model.karyawanAPIModel.DetailResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends Fragment {

    UserSessionManager session;
    FrameLayout rootView;
    Button btnLogout;
    Button btnEdit;
    TextView tvResultNama,lblnama,lblemail,lblrole,lbltelepon;
    String resultNama;
    BaseApiService mApiService;
    ProgressDialog loading;
    private SharedPreferences profile;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = (FrameLayout) inflater.inflate(R.layout.fragment_profile, container, false);

        session = new UserSessionManager(getActivity().getApplicationContext());

        lblnama = (TextView) rootView.findViewById(R.id.home_nama);
        lblemail = (TextView) rootView.findViewById(R.id.email_text);
        lblrole = (TextView) rootView.findViewById(R.id.home_jabatan);
        lbltelepon = (TextView) rootView.findViewById(R.id.notelp);

        btnLogout = (Button) rootView.findViewById(R.id.btnLogout);
        btnEdit = (Button) rootView.findViewById(R.id.btnEdit);



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

                loading = ProgressDialog.show(getActivity(), null, "Harap Tunggu...", true, false);

                profile = getActivity().getSharedPreferences("AndroidExamplePref", Context.MODE_PRIVATE);
                final SharedPreferences.Editor edit = profile.edit();
                final String token = "Bearer "+profile.getString("access_token",null);
                Log.d("debug","token : "+token);
                mApiService = UtilsApi.getAPIServiceWithToken(token);
                mApiService.detailProfile().enqueue(new Callback<DetailResponse>() {
                    @Override
                    public void onResponse(Call<DetailResponse> call, Response<DetailResponse> response) {
                        if (response.isSuccessful()) {
                            int id = response.body().getId();
                            String nama = response.body().getName();
                            String email = response.body().getEmail();
                            String jabatan = response.body().getUserRole();
                            String telp = response.body().getNoTelp();

                            edit.putInt("id",id);
                            edit.putString("token_edit",token);
                            edit.putString("nama",nama);
                            edit.putString("email",email);
                            edit.putString("role",jabatan);
                            edit.putString("telepon",telp);
                            edit.apply();

                            Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                            startActivity(intent);

                            loading.dismiss();
                        }else{

                        }

                    }

                    @Override
                    public void onFailure(Call<DetailResponse> call, Throwable t) {
                        Log.d("debug","GAGAL");
                        Toast.makeText(getActivity(), "Tidak bisa edit dalam Mode Offline", Toast.LENGTH_SHORT).show();
                        loading.dismiss();

                    }
                });


            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        loading = ProgressDialog.show(getActivity(), null, "Harap Tunggu...", true, false);

        profile = getActivity().getSharedPreferences("AndroidExamplePref", Context.MODE_PRIVATE);
        String token = "Bearer "+profile.getString("access_token",null);
        Log.d("debug","token : "+token);
        mApiService = UtilsApi.getAPIServiceWithToken(token);
        mApiService.detailProfile().enqueue(new Callback<DetailResponse>() {
            @Override
            public void onResponse(Call<DetailResponse> call, Response<DetailResponse> response) {
                if (response.isSuccessful()) {
                    String nama = response.body().getName();
                    String email = response.body().getEmail();
                    String jabatan = response.body().getUserRole();
                    String telp = response.body().getNoTelp();

                    lblnama.setText(nama);
                    lblemail.setText(email);
                    lblrole.setText(jabatan);
                    lbltelepon.setText(telp);

//                Glide.with(getActivity())
//                        .load(UtilsApi.BASE_URL_API + "/profileimages/" + image)
//                        .into(ivUser);

                    loading.dismiss();
                }else{

                }

            }

            @Override
            public void onFailure(Call<DetailResponse> call, Throwable t) {
                Log.d("debug","GAGAL");
                Toast.makeText(getActivity(), "Mode Offline", Toast.LENGTH_SHORT).show();
                loading.dismiss();
                HashMap<String, String> user = session.getUserDetails();
                final String id = user.get(UserSessionManager.ID);
                final String nama = user.get(UserSessionManager.KEY_NAME);
                final String email = user.get(UserSessionManager.KEY_EMAIL);
                final String user_role = user.get(UserSessionManager.KEY_ROLE);
                final String no_telp = user.get(UserSessionManager.KEY_PHONE);

                lblnama.setText(nama);
                lblemail.setText(email);
                lblrole.setText(user_role);
                lbltelepon.setText(no_telp);

            }
        });
    }
}