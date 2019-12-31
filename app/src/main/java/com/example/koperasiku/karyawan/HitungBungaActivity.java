package com.example.koperasiku.karyawan;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.koperasiku.R;
import com.example.koperasiku.apihelper.BaseApiService;
import com.example.koperasiku.apihelper.UtilsApi;
import com.example.koperasiku.karyawan.modelHitungBunga.HitungResponse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HitungBungaActivity extends AppCompatActivity {
    Button btnHitung;
    TextView tanggal;
    ProgressDialog loading;
    String dateFormatter;
    private SharedPreferences profile;
    BaseApiService mApiService = UtilsApi.getAPIService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hitung_bunga);

        btnHitung = (Button) findViewById(R.id.btnHitung);
        tanggal = (TextView) findViewById(R.id.tanggal);

        dateFormatter = new SimpleDateFormat("MMM dd, yyyy", Locale.US).format(new Date());
        tanggal.setText(dateFormatter);

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
                            String pesan = response.body().getMsg();
                            if(pesan.equals("Perhitungan bunga bulan ini dan tahun ini sudah pernah dilakukan!")){
                                loading.dismiss();
                                Toast.makeText(HitungBungaActivity.this, "Perhitungan bunga bulan ini dan tahun ini sudah pernah dilakukan!", Toast.LENGTH_SHORT).show();
                            }else{
                                loading.dismiss();
                                Toast.makeText(HitungBungaActivity.this, "Berhasil Hitung Bunga", Toast.LENGTH_SHORT).show();
                            }


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
