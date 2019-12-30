package com.example.koperasiku;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.koperasiku.Fragment.Profile;
import com.example.koperasiku.MainActivity;
import com.example.koperasiku.R;
import com.example.koperasiku.RegisterActivity;
import com.example.koperasiku.apihelper.BaseApiService;
import com.example.koperasiku.apihelper.UtilsApi;
import com.example.koperasiku.model.karyawanAPIModel.LoginResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText etEmail;
    EditText etPassword;
    Button btnLogin;
    TextView tvRegister;
    ProgressDialog loading;
    UserSessionManager session;


    Context mContext;
    BaseApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        session = new UserSessionManager(getApplicationContext());
        mContext = this;
        mApiService = UtilsApi.getAPIService(); // meng-init yang ada di package apihelper
        initComponents();
        SharedPreferences profile = getSharedPreferences("profile", Context.MODE_PRIVATE);
        final Intent toMain = new Intent(LoginActivity.this, MainActivity.class);
        if(profile.contains("id")){
            startActivity(toMain);
            finish();
        }

    }

    private void initComponents() {
        etEmail = (EditText) findViewById(R.id.login_email);
        etPassword = (EditText) findViewById(R.id.login_password);
        btnLogin = (Button) findViewById(R.id.login_button);
        tvRegister = (TextView) findViewById(R.id.signup_button);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w("TESTAA", "tesa");
                loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
                boolean anyError = false;
                if (etEmail.getText().toString().equals("")){
                    etEmail.setError("Email tidak boleh kosong");
                    anyError = true;
                }

                if (etPassword.getText().toString().equals("")){
                    etPassword.setError("Password tidak boleh kosong");
                    anyError = true;
                }
                Log.w("TESTAA", "tesa");


                if(!anyError){
                    Log.w("TESTAA", "tesa");
                    loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
                    FirebaseInstanceId.getInstance().getInstanceId()
                        .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                            @Override
                            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                if (!task.isSuccessful()) {
                                    Log.w("TESTAA", "getInstanceId failed", task.getException());
                                    return;
                                }

                                // Get new Instance ID token
                                String token = task.getResult().getToken();

                                // Log and toast
                                String msg = token;
                                Log.d("TESTAA", msg);
                                requestLogin(token);
                            }
                        });
                }

            }
        });


        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, RegisterActivity.class));
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


    public void requestLogin(String token){
        mApiService.loginRequest(etEmail.getText().toString(), etPassword.getText().toString(), token)
                .enqueue(new Callback<LoginResponse>() {

                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()) {
                            loading.dismiss();
                            Toast.makeText(mContext, "Berhasil Login", Toast.LENGTH_SHORT).show();
                            int id = response.body().getUser().getId();
                            String token = response.body().getUser().getToken();
                            final String nama = response.body().getUser().getName();
                            final String email = response.body().getUser().getEmail();
                            final String user_role = response.body().getUser().getUserRole();
                            final String telp = response.body().getUser().getNoTelp();
                            session.createUserLoginSession(id,token,nama,email,user_role,telp);
                            session.editor.putInt("id",id);
                            session.editor.putString("access_token",token);
                            session.editor.putString("role",user_role);
                            session.editor.apply();
                            Log.d("debug","token : "+token+"id : "+id);
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                            alertDialogBuilder.setTitle("Login Sukses!");
                            alertDialogBuilder.setMessage("Nama : "+nama+ "\nRole : "+user_role);
                            alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    Log.d("debug","ROLEKU : "+user_role);
                                    if(user_role.equals("Admin")){
                                        Intent intent = new Intent(mContext, MainActivity2.class);
                                        intent.putExtra("result_nama", nama);
                                        startActivity(intent);
                                        finish();
                                    }else if(user_role.equals("Nasabah")){
                                        Intent intent = new Intent(mContext, MainActivity.class);
                                        intent.putExtra("result_nama", nama);
                                        startActivity(intent);
                                        finish();
                                    }

                                }
                            });
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        }else{
                            loading.dismiss();
                            Toast.makeText(mContext, "Gagal Login", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        loading.dismiss();
                        Toast.makeText(mContext, "Gagal Login", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
