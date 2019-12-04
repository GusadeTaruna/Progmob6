package com.example.koperasiku.karyawan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.koperasiku.R;
import com.example.koperasiku.model.KaryawanModel;

public class DetailKaryawanActivity extends AppCompatActivity {
    private TextView tvNamaPenulis, tvJudul;
    private KaryawanModel karyawanModel =  new KaryawanModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_karyawan);

        tvNamaPenulis = (TextView) findViewById(R.id.tvNamaKaryawan);
        tvJudul = (TextView) findViewById(R.id.tvEmail);

        int karyawanID = getIntent().getIntExtra("KaryawanID", -1);
        karyawanModel= KaryawanActivity.getInstance().searchKaryawan(karyawanID);

        tvNamaPenulis.setText(getString(R.string.karyawan_nama, karyawanModel.getName()));
        tvJudul.setText(getString(R.string.karyawan_email,karyawanModel.getEmail()));
    }
}
