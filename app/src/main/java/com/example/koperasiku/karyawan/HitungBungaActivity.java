package com.example.koperasiku.karyawan;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.koperasiku.R;
import com.example.koperasiku.apihelper.BaseApiService;
import com.example.koperasiku.apihelper.UtilsApi;
import com.example.koperasiku.karyawan.modelHitungBunga.HitungResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HitungBungaActivity extends AppCompatActivity {
    Button btnHitung;
    ProgressDialog loading;
    private SharedPreferences profile;
    BaseApiService mApiService = UtilsApi.getAPIService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hitung_bunga);

        btnHitung = (Button) findViewById(R.id.btnHitung);

        btnHitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading = ProgressDialog.show(HitungBungaActivity.this, null, "Harap Tunggu...", true, false);
//                requestLogin();
                requestHitung();
            }
        });
    }

    public void requestHitung(){
        profile = getSharedPreferences("AndroidExamplePref", Context.MODE_PRIVATE);
        final Integer pegawai = profile.getInt("id",0);
        mApiService.getHitung(pegawai).enqueue(new Callback<HitungResponse>() {

                    @Override
                    public void onResponse(Call<HitungResponse> call, Response<HitungResponse> response) {
                        if (response.isSuccessful()) {
                            loading.dismiss();
                            Toast.makeText(HitungBungaActivity.this, "Berhasil Hitung Bunga", Toast.LENGTH_SHORT).show();

                        }else{
                            loading.dismiss();
                            Toast.makeText(HitungBungaActivity.this, "Gagal Login", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<HitungResponse> call, Throwable t) {
                        loading.dismiss();
                        Toast.makeText(HitungBungaActivity.this, "Gagal Login", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}