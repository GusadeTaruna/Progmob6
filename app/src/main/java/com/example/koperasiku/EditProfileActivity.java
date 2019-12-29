package com.example.koperasiku;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.example.koperasiku.Fragment.Profile;
import com.example.koperasiku.apihelper.BaseApiService;
import com.example.koperasiku.apihelper.UtilsApi;
import com.example.koperasiku.model.karyawanAPIModel.DetailResponse;
import com.example.koperasiku.model.karyawanAPIModel.EditResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
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
    private SharedPreferences profile;

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
        loading = ProgressDialog.show(EditProfileActivity.this, null, "Harap Tunggu...", true, false);
        initComponents();

        if(session.checkLogin()){
            finish();
        }
//        HashMap<String, String> user = session.getUserDetails();
//        id = user.get(UserSessionManager.ID);
//        nama = user.get(UserSessionManager.KEY_NAME);
//        email = user.get(UserSessionManager.KEY_EMAIL);
//        nameField.setText(Html.fromHtml(nama));
//        emailField.setText(Html.fromHtml(email));


    }

    private void initComponents() {
        etname = (EditText) findViewById(R.id.nameField);
        etEmail = (EditText) findViewById(R.id.emailField);
        etPass = (EditText) findViewById(R.id.passwordField);
        etConfirmPass = (EditText) findViewById(R.id.confirmPass);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        profile = getSharedPreferences("AndroidExamplePref", Context.MODE_PRIVATE);
        String token = "Bearer " + profile.getString("access_token", null);
        mApiService = UtilsApi.getAPIServiceWithToken(token);
        mApiService.detailProfile()
                .enqueue(new Callback<DetailResponse>() {
                    @Override
                    public void onResponse(Call<DetailResponse> call, Response<DetailResponse> response) {
                        if (response.isSuccessful()) {
                            loading.dismiss();
                            String nama = response.body().getName();
                            String email = response.body().getEmail();
//                            String notelp = response.body().getNoTelp();

                            etname.setText(nama);
                            etEmail.setText(email);
//                            etNoTelp.setText(notelp);


                        } else {
                            loading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<DetailResponse> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                        loading.dismiss();
                    }
                });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean anyError = false;
                if (etname.getText().toString().equals("")){
                    etname.setError("Field tidak boleh kosong");
                    anyError = true;
                }

                if (etEmail.getText().toString().equals("")){
                    etEmail.setError("Field tidak boleh kosong");
                    anyError = true;
                }

//                if (etNoTelp.getText().toString().equals("")){
//                    etNoTelp.setError("Field tidak boleh kosong");
//                    anyError = true;
//                }

                if(!anyError){
                    loading = ProgressDialog.show(EditProfileActivity.this, null, "Harap Tunggu...", true, false);
                    editprofile();
                }

            }
        });
    }

    private void editprofile(){
        int id = profile.getInt("id",0);
//        RequestBody formid = RequestBody.create(MediaType.parse("text/plain"), id+"");
//        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), etname.getText().toString());
//        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), etEmail.getText().toString());
        profile = getSharedPreferences("AndroidExamplePref", Context.MODE_PRIVATE);
        final String user_role = profile.getString("role",null);
        mApiService.editProfil(id,etname.getText().toString(), etEmail.getText().toString(), etPass.getText().toString(), confirmPass.getText().toString())
        .enqueue(new Callback<EditResponse>() {
            @Override
            public void onResponse(Call<EditResponse> call, Response<EditResponse> response) {
                if (response.isSuccessful()){
                    loading.dismiss();
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
                            if(user_role.equals("Nasabah")){
                                startActivity(new Intent(EditProfileActivity.this, MainActivity.class));
                                finish();
                            }else if(user_role.equals("Admin")){
                                startActivity(new Intent(EditProfileActivity.this, MainActivity2.class));
                                finish();
                            }

//                                    Fragment fragment= new Profile();
//                                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                                    transaction.replace(R.id.page_container, fragment); // fragment container id in first parameter is the  container(Main layout id) of Activity
//                                    transaction.addToBackStack(null);  // this will manage backstack
//                                    transaction.commit();
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();


                } else {
                    loading.dismiss();
                    Toast.makeText(EditProfileActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EditResponse> call, Throwable t) {
                Log.i("debug", "onResponse: Gagal Update");
                Toast.makeText(EditProfileActivity.this, "Update Gagal", Toast.LENGTH_SHORT).show();
                loading.dismiss();
            }
        });
    }

}