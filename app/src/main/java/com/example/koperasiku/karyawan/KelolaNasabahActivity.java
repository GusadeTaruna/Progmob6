package com.example.koperasiku.karyawan;

import android.app.ProgressDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.example.koperasiku.room.AppDatabase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelolaNasabahActivity extends AppCompatActivity {

    private RecyclerView mRecycler;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    private SharedPreferences profile;
    private List<NasabahItem> mItems = new ArrayList<>();
    BaseApiService mApiService = UtilsApi.getAPIService();
    ProgressDialog pd;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_nasabah);

        pd = new ProgressDialog(this);
        mRecycler = (RecyclerView) findViewById(R.id.recycleview);
        mManager = new LinearLayoutManager(KelolaNasabahActivity.this, LinearLayoutManager.VERTICAL, false);
        mRecycler.setLayoutManager(mManager);
//
        pd.setMessage("Harap Tunggu...");
        pd.setCancelable(false);
        pd.show();
//
        if (this.isNetworkAvailable()){
            mApiService.listNasabah().enqueue(new Callback<NasabahResponse>() {
                @Override
                public void onResponse(Call<NasabahResponse> call, Response<NasabahResponse> response) {
                    if (response.isSuccessful()) {
                        pd.dismiss();
                        Log.d("retro", "RESPONSE : "+response.body().getNasabah() );
                        mItems = response.body().getNasabah();

                        mAdapter = new AdapterDataNasabah(KelolaNasabahActivity.this,mItems);
                        mRecycler.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();

                    }else{
                        Log.d("retro", "RESPONSE : "+response.body().getNasabah() );

                    }

                }
                @Override
                public void onFailure(Call<NasabahResponse> call, Throwable t) {
                    Log.d("debug","GAGAL");
                    Toast.makeText(KelolaNasabahActivity.this, "GAGAL", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        }
        else{
            pd.dismiss();
            Toast.makeText(KelolaNasabahActivity.this, "Menggunakan data local", Toast.LENGTH_SHORT).show();
            loadFromLocal();
        }
    }

    private boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void loadFromLocal(){
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "db_koperasi").allowMainThreadQueries().build();
        mItems = db.nasabahDAO().selectNasabah();
        mAdapter = new AdapterDataNasabah(KelolaNasabahActivity.this,mItems);
        mRecycler.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}
