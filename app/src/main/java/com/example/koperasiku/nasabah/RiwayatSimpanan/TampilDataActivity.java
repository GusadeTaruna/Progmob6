package com.example.koperasiku.nasabah.RiwayatSimpanan;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.koperasiku.R;
import com.example.koperasiku.apihelper.BaseApiService;
import com.example.koperasiku.apihelper.RetrofitClient;
import com.example.koperasiku.apihelper.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TampilDataActivity extends AppCompatActivity {

    private RecyclerView mRecycler;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    private List<SimpanItem> mItems = new ArrayList<>();
    BaseApiService mApiService;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retreive_data);

        pd = new ProgressDialog(this);
        mRecycler = (RecyclerView) findViewById(R.id.recycleview);
        mManager = new LinearLayoutManager(TampilDataActivity.this, LinearLayoutManager.VERTICAL, false);
        mRecycler.setLayoutManager(mManager);

        pd.setMessage("Harap Tunggu...");
        pd.setCancelable(false);
        pd.show();

        mApiService.getSimpanItem().enqueue(new Callback<SimpananResponse>() {
            @Override
            public void onResponse(Call<SimpananResponse> call, Response<SimpananResponse> response) {
                if (response.isSuccessful()) {
                    pd.dismiss();
                    Log.d("retro", "RESPONSE : "+response.body().getSimpan() );
                    mItems = response.body().getSimpan();

                    mAdapter = new AdapterData(TampilDataActivity.this,mItems);
                    mRecycler.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();

                }else{

                }

            }

            @Override
            public void onFailure(Call<SimpananResponse> call, Throwable t) {
                Log.d("debug","GAGAL");
                Toast.makeText(TampilDataActivity.this, "Tidak bisa edit dalam Mode Offline", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });
    }
}
