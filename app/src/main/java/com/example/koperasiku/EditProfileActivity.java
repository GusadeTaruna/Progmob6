package com.example.koperasiku;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
    EditText emailField;
    EditText etEmail;
    EditText etname;
    EditText etCpassword;
    Button btnUpdate;
    String id;
    String nama;
    String email;
    ProgressDialog loading;
    UserSessionManager session;

    Context mContext;
    BaseApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        session = new UserSessionManager(this.getApplicationContext());
        nameField = (EditText) findViewById(R.id.nameField);
        emailField = (EditText) findViewById(R.id.emailField);

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
        btnUpdate = (Button) findViewById(R.id.btnUpdate);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading = ProgressDialog.show(EditProfileActivity.this, null, "Harap Tunggu...", true, false);
                editprofile();
            }
        });

    }

    private void editprofile(){
        mApiService.editProfil(id,etname.getText().toString(), etEmail.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            loading.dismiss();
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                if (jsonRESULTS.getString("error").equals("false")){
                                    // Jika login berhasil maka data nama yang ada di response API
                                    // akan diparsing ke activity selanjutnya.
                                    Toast.makeText(EditProfileActivity.this, "Berhasil Update", Toast.LENGTH_SHORT).show();
                                    finish();
                                    startActivity(new Intent(EditProfileActivity.this, MainActivity.class));
//                                    final String nama = jsonRESULTS.getJSONObject("user").getString("name");
//                                    final String email = jsonRESULTS.getJSONObject("user").getString("email");
//                                    final String id = jsonRESULTS.getJSONObject("user").getString("id");
//                                    session.createUserLoginSession(id,nama,email);
////                                    int id = jsonRESULTS.getJSONObject("user").getInt("id");
////                                    Log.d("debug","id : "+id);
////                                    Intent intent = new Intent(mContext, MainActivity.class);
//                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
//                                    alertDialogBuilder.setTitle("Login Sukses!");
//                                    alertDialogBuilder.setMessage("Nama : "+nama+ "\nRole : Admin");
//                                    alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialogInterface, int i) {
//                                            Intent intent = new Intent(mContext, MainActivity.class);
//                                            intent.putExtra("result_nama", nama);
//                                            startActivity(intent);
//                                            finish();
//                                        }
//                                    });
//                                    AlertDialog alertDialog = alertDialogBuilder.create();
//                                    alertDialog.show();
                                } else {
                                    // Jika login gagal
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