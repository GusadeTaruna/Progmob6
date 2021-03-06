package com.example.koperasiku;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.koperasiku.apihelper.BaseApiService;
import com.example.koperasiku.apihelper.UtilsApi;
import com.example.koperasiku.model.karyawanAPIModel.RegisterResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    EditText etNama;
    EditText etEmail,etTelepon;
    EditText etPassword;
    EditText etCpassword;
    Button btnRegister;
    TextView tvLogin;
    ProgressDialog loading;

    Context mContext;
    BaseApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mContext = this;
        mApiService = UtilsApi.getAPIService();

        initComponents();
    }

    private void initComponents() {
        etNama = (EditText) findViewById(R.id.register_name);
        etEmail = (EditText) findViewById(R.id.register_email);
        etTelepon = (EditText) findViewById(R.id.register_telepon);
        etPassword = (EditText) findViewById(R.id.register_password);
        etCpassword = (EditText) findViewById(R.id.register_confirm_password);
        btnRegister = (Button) findViewById(R.id.register_button);
        tvLogin = (TextView) findViewById(R.id.login_button);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
                requestRegister();
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, LoginActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void requestRegister(){
        mApiService.registerRequest(etNama.getText().toString(),
                etEmail.getText().toString(),
                etTelepon.getText().toString(),
                etPassword.getText().toString(),
                etCpassword.getText().toString())
                .enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                        if (response.isSuccessful()){
                            Log.i("debug", "onResponse: BERHASIL");
                            loading.dismiss();
                            Toast.makeText(mContext, "Registrasi Berhasil", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(mContext, LoginActivity.class));
                            finish();
                        } else {
                            Log.i("debug", "onResponse: GA BERHASIL");
                            Toast.makeText(mContext, "Register Gagal", Toast.LENGTH_SHORT).show();
                            loading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                        loading.dismiss();
                        Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}