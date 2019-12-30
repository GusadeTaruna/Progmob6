//package com.example.koperasiku;
//
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.widget.TextView;
//
//public class MainActivity extends AppCompatActivity {
//    TextView tvResultNama;
//    String resultNama;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        initComponents();
//
//        // untuk mendapatkan data dari activity sebelumnya, yaitu activity login.
//        Bundle extras = getIntent().getExtras();
//        if (extras != null)
//            resultNama = extras.getString("result_nama");
//        tvResultNama.setText(resultNama);
//    }
//
//    private void initComponents(){
//        tvResultNama = (TextView) findViewById(R.id.tvResultNama);
//    }
//}


package com.example.koperasiku;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.koperasiku.Fragment.Home;
import com.example.koperasiku.Fragment.Notification;
import com.example.koperasiku.Fragment.Profile;
import com.example.koperasiku.apihelper.BaseApiService;
import com.example.koperasiku.apihelper.UtilsApi;
import com.example.koperasiku.nasabah.RiwayatPenarikan.PenarikanResponse;
import com.example.koperasiku.nasabah.RiwayatSimpanan.HistoriItem;
import com.example.koperasiku.nasabah.RiwayatSimpanan.SimpananResponse;
import com.example.koperasiku.room.AppDatabase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    TextView tvResultNama;
    String resultNama;
    private SharedPreferences profile;
    BaseApiService mApiService = UtilsApi.getAPIService();
    private List<HistoriItem> mItems = new ArrayList<>();
    private List<com.example.koperasiku.nasabah.RiwayatPenarikan.HistoriItem> nItems = new ArrayList<>();
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Menampilkan halaman Fragment yang pertama kali muncul
        getFragmentPage(new Home());
        Bundle bundle = new Bundle();
        Home myObj = new Home();
        myObj.setArguments(bundle);


        /*Inisialisasi BottomNavigationView beserta listenernya untuk
         *menangkap setiap kejadian saat salah satu menu item diklik
         */
        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigationView);
        profile = getSharedPreferences("AndroidExamplePref", Context.MODE_PRIVATE);
        Integer id = profile.getInt("id",0);

        mApiService.getSimpanItem(id).enqueue(new Callback<SimpananResponse>() {
            @Override
            public void onResponse(Call<SimpananResponse> call, Response<SimpananResponse> response) {
                if (response.isSuccessful()) {
                    mItems = response.body().getHistori();
                    db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class, "db_koperasi").allowMainThreadQueries().build();
                    HistoriItem simpanan = new HistoriItem();
                    for(int i = 0; i < mItems.size(); i++){
                        simpanan.setId(mItems.get(i).getId());
                        simpanan.setTanggal(mItems.get(i).getTanggal());
                        simpanan.setJenisTransaksi(mItems.get(i).getJenisTransaksi());
                        simpanan.setNominalTransaksi(mItems.get(i).getNominalTransaksi());
                        simpanan.setIdUserNasabah(mItems.get(i).getIdUserNasabah());
                        simpanan.setStatus(mItems.get(i).getStatus());
                        simpanan.setIdUserKaryawan(mItems.get(i).getIdUserKaryawan());
                        simpanan.setBuktiPembayaran(mItems.get(i).getBuktiPembayaran());
                        db.simpananDAO().insertSimpanan(mItems);
                        Log.d("retro", "berhasil insert ke sqllite");
                    }


                }else{
                    Log.d("retro", "RESPONSE : "+response.body().getHistori() );

                }

            }

            @Override
            public void onFailure(Call<SimpananResponse> call, Throwable t) {
                Log.d("debug","GAGAL");
                Toast.makeText(MainActivity.this, "GAGAL", Toast.LENGTH_SHORT).show();
            }
        });
        mApiService.getTarikItem(id).enqueue(new Callback<PenarikanResponse>() {
            @Override
            public void onResponse(Call<PenarikanResponse> call, Response<PenarikanResponse> response) {
                if (response.isSuccessful()) {
                    nItems = response.body().getHistori();
                    db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class, "db_koperasi").allowMainThreadQueries().build();
                    HistoriItem simpanan = new HistoriItem();
                    for(int i = 0; i < nItems.size(); i++){
                        simpanan.setId(nItems.get(i).getId());
                        simpanan.setTanggal(nItems.get(i).getTanggal());
                        simpanan.setJenisTransaksi(nItems.get(i).getJenisTransaksi());
                        simpanan.setNominalTransaksi(nItems.get(i).getNominalTransaksi());
                        simpanan.setIdUserNasabah(nItems.get(i).getIdUserNasabah());
                        simpanan.setStatus(nItems.get(i).getStatus());
                        simpanan.setIdUserKaryawan(nItems.get(i).getIdUserKaryawan());
                        simpanan.setBuktiPembayaran(nItems.get(i).getBuktiPembayaran());
                        db.simpananDAO().insertTarikan(nItems);
                        Log.d("retro", "berhasil insert ke sqllite");
                    }


                }else{
                    Log.d("retro", "RESPONSE : "+response.body().getHistori() );

                }

            }

            @Override
            public void onFailure(Call<PenarikanResponse> call, Throwable t) {
                Log.d("debug","GAGAL");
                Toast.makeText(MainActivity.this, "GAGAL", Toast.LENGTH_SHORT).show();
            }
        });
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment = null;

                //Menantukan halaman Fragment yang akan tampil
                switch (item.getItemId()){
                    case R.id.home:
                        fragment = new Home();
                        break;

                    case R.id.person:
                        fragment = new Profile();
                        break;

                    case R.id.notidications:
                        fragment = new Notification();
                        break;
                }
                return getFragmentPage(fragment);
            }
        });
    }
    //Menampilkan halaman Fragment
    private boolean getFragmentPage(Fragment fragment){
        if (fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.page_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

}