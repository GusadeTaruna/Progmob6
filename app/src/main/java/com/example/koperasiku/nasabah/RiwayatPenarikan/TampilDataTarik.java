package com.example.koperasiku.nasabah.RiwayatPenarikan;

import android.app.ProgressDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.koperasiku.R;
import com.example.koperasiku.apihelper.BaseApiService;
import com.example.koperasiku.apihelper.UtilsApi;
import com.example.koperasiku.nasabah.RiwayatSimpanan.TampilDataActivity;
import com.example.koperasiku.room.AppDatabase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TampilDataTarik extends AppCompatActivity {

    private RecyclerView mRecycler;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    private List<HistoriItem> mItems = new ArrayList<>();
    private SharedPreferences profile;
    BaseApiService mApiService = UtilsApi.getAPIService();
    ProgressDialog pd;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retreive_data);

        pd = new ProgressDialog(this);
        mRecycler = (RecyclerView) findViewById(R.id.recycleview);
        mManager = new LinearLayoutManager(TampilDataTarik.this, LinearLayoutManager.VERTICAL, false);
        mRecycler.setLayoutManager(mManager);
//
        pd.setMessage("Harap Tunggu...");
        pd.setCancelable(false);
        pd.show();
//
        profile = getSharedPreferences("AndroidExamplePref", Context.MODE_PRIVATE);
        Integer id = profile.getInt("id",0);
        if (this.isNetworkAvailable()){
            mApiService.getTarikItem(id).enqueue(new Callback<PenarikanResponse>() {
                @Override
                public void onResponse(Call<PenarikanResponse> call, Response<PenarikanResponse> response) {
                    pd.dismiss();
                    mItems = response.body().getHistori();

                    mAdapter = new AdapterData(TampilDataTarik.this,mItems);
                    mRecycler.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();

                }

                @Override
                public void onFailure(Call<PenarikanResponse> call, Throwable t) {
                    Log.d("debug","GAGAL");
                    Toast.makeText(TampilDataTarik.this, "GAGAL", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        }
        else{
            pd.dismiss();
            Toast.makeText(TampilDataTarik.this, "Menggunakan data local", Toast.LENGTH_SHORT).show();
            loadFromLocal();
        }
    }
    private boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void loadFromLocal(){
        profile = getSharedPreferences("AndroidExamplePref", Context.MODE_PRIVATE);
        Integer id = profile.getInt("id",0);
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "db_koperasi").allowMainThreadQueries().build();
        mItems = db.simpananDAO().selectAllTarik(id);
        mAdapter = new com.example.koperasiku.nasabah.RiwayatPenarikan.AdapterData(TampilDataTarik.this,mItems);
        mRecycler.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();


    }
}
