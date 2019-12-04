package com.example.koperasiku;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.koperasiku.Fragment.Home;
import com.example.koperasiku.apihelper.BaseApiService;
import com.example.koperasiku.apihelper.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    EditText nameField;
    EditText emailField, passwordField, confirmPass;
    EditText etEmail, etPass, etConfirmPass;
    EditText etname;
    EditText etCpassword;
    Button btnUpdate;
    String id;
    String nama;
    String email;
    ProgressDialog loading;
    UserSessionManager session;
    SharedPreferences.Editor editor;

    Context mContext;
    BaseApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        session = new UserSessionManager(this.getApplicationContext());
        nameField = (EditText) findViewById(R.id.nameField);
        emailField = (EditText) findViewById(R.id.emailField);
        passwordField = (EditText) findViewById(R.id.passwordField);
        confirmPass = (EditText) findViewById(R.id.confirmPass);

        mApiService = UtilsApi.getAPIService(); // meng-init yang ada di package apihelper
        initComponents();

        if(session.checkLogin()){
            finish();
        }
        HashMap<String, String> user = session.getUserDetails();
        id = user.get(UserSessionManager.ID);
        nama = user.get(UserSessionManager.KEY_NAME);
        email = user.get(UserSessionManager.KEY_EMAIL);
        nameField.setText(Html.fromHtml(nama));
        emailField.setText(Html.fromHtml(email));

    }

    private void initComponents() {
        etEmail = (EditText) findViewById(R.id.emailField);
        etname = (EditText) findViewById(R.id.nameField);
        etPass = (EditText) findViewById(R.id.passwordField);
        etConfirmPass = (EditText) findViewById(R.id.confirmPass);

        btnUpdate = (Button) findViewById(R.id.btnUpdate);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etname.getText().toString().matches("") ||
                        etEmail.getText().toString().matches("") ||
                        etPass.getText().toString().matches("") ||
                        confirmPass.getText().toString().matches("")) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(EditProfileActivity.this);
                    alertDialogBuilder.setTitle("Gagal");
                    alertDialogBuilder.setMessage("Inputan tidak boleh kosong");
                    alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                }else if (!etPass.getText().toString().equals(confirmPass.getText().toString())){
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(EditProfileActivity.this);
                    alertDialogBuilder.setTitle("Gagal");
                    alertDialogBuilder.setMessage("Password dan Konfirmasi password tidak cocok");
                    alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                }else{
                    loading = ProgressDialog.show(EditProfileActivity.this, null, "Harap Tunggu...", true, false);
                    editprofile();
                }

            }
        });

    }

    private void editprofile(){
        mApiService.editProfil(id,etname.getText().toString(), etEmail.getText().toString(), etPass.getText().toString(), confirmPass.getText().toString())
        .enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    loading.dismiss();
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        if (jsonRESULTS.getString("success").equals("true")) {
                            final String nama = etname.getText().toString();
                            final String email = etEmail.getText().toString();
                            session.updateUser(nama, email);
                            Toast.makeText(EditProfileActivity.this, "Berhasil Update", Toast.LENGTH_SHORT).show();

                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(EditProfileActivity.this);
                            alertDialogBuilder.setTitle("Sukses");
                            alertDialogBuilder.setMessage("Edit Profil Sukses!");
                            alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    startActivity(new Intent(EditProfileActivity.this, MainActivity.class));
                                    finish();
                                }
                            });
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();

                        }else if (jsonRESULTS.getString("success").equals("false")) {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(EditProfileActivity.this);
                            alertDialogBuilder.setTitle("Gagal");
                            alertDialogBuilder.setMessage("Password tidak benar, Coba lagi");
                            alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();

                        } else {
                            String error_message = jsonRESULTS.getString("error_msg");
                            Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.i("debug", "onResponse: Gagal Update");
                    Toast.makeText(mContext, "Update Gagal", Toast.LENGTH_SHORT).show();
                    loading.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.toString());
                loading.dismiss();
            }
        });
    }

}