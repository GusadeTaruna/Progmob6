package com.example.koperasiku.karyawan;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.koperasiku.R;
import com.example.koperasiku.apihelper.BaseApiService;
import com.example.koperasiku.apihelper.UtilsApi;
import com.example.koperasiku.karyawan.modelKelolaNasabah.NasabahItem;
import com.example.koperasiku.karyawan.modelKelolaNasabah.NasabahResponse;
import com.example.koperasiku.karyawan.modelReport.DataItem;
import com.example.koperasiku.karyawan.modelReport.ReportResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportTransaksiActivity extends AppCompatActivity {

    private RecyclerView mRecycler;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    private SharedPreferences profile;
    private List<DataItem> mItems = new ArrayList<>();
    BaseApiService mApiService = UtilsApi.getAPIService();
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_transaksi);

        pd = new ProgressDialog(this);
        mRecycler = (RecyclerView) findViewById(R.id.recycleview);
        mManager = new LinearLayoutManager(ReportTransaksiActivity.this, LinearLayoutManager.VERTICAL, false);
        mRecycler.setLayoutManager(mManager);
//
        pd.setMessage("Harap Tunggu...");
        pd.setCancelable(false);
        pd.show();
//
        mApiService.reportTransaksi().enqueue(new Callback<ReportResponse>() {
            @Override
            public void onResponse(Call<ReportResponse> call, Response<ReportResponse> response) {
                if (response.isSuccessful()) {
                    pd.dismiss();
                    Log.d("retro", "RESPONSE : "+response.body().getData() );
                    mItems = response.body().getData();

                    mAdapter = new AdapterDataReport(ReportTransaksiActivity.this,mItems);
                    mRecycler.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();

                }else{
                    Log.d("retro", "RESPONSE : "+response.body().getData() );

                }

            }
            @Override
            public void onFailure(Call<ReportResponse> call, Throwable t) {
                Log.d("debug","GAGAL");
                Toast.makeText(ReportTransaksiActivity.this, "GAGAL", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });
    }
}
