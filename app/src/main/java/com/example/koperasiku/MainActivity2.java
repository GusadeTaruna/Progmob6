package com.example.koperasiku;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.koperasiku.Fragment.Home;
import com.example.koperasiku.Fragment.HomeAdmin;
import com.example.koperasiku.Fragment.Notification;
import com.example.koperasiku.Fragment.NotificationAdmin;
import com.example.koperasiku.Fragment.Profile;
import com.example.koperasiku.Fragment.ProfileAdmin;
import com.example.koperasiku.apihelper.BaseApiService;
import com.example.koperasiku.apihelper.UtilsApi;
import com.example.koperasiku.karyawan.modelKelolaNasabah.NasabahItem;
import com.example.koperasiku.karyawan.modelKelolaNasabah.NasabahResponse;
import com.example.koperasiku.karyawan.modelReport.DataItem;
import com.example.koperasiku.karyawan.modelReport.ReportResponse;
import com.example.koperasiku.room.AppDatabase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity2 extends AppCompatActivity {
    TextView tvResultNama;
    String resultNama;
    BaseApiService mApiService = UtilsApi.getAPIService();
    private List<DataItem> reports = new ArrayList<>();
    private List<NasabahItem> nasabahs = new ArrayList<>();
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //Menampilkan halaman Fragment yang pertama kali muncul
        getFragmentPage(new HomeAdmin());
        Bundle bundle = new Bundle();
        HomeAdmin myObj = new HomeAdmin();
        myObj.setArguments(bundle);

        /*Inisialisasi BottomNavigationView beserta listenernya untuk
         *menangkap setiap kejadian saat salah satu menu item diklik
         */
        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigationView);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment = null;

                //Menantukan halaman Fragment yang akan tampil
                switch (item.getItemId()){
                    case R.id.home:
                        fragment = new HomeAdmin();
                        break;

                    case R.id.person:
                        fragment = new ProfileAdmin();
                        break;

                    case R.id.notidications:
                        fragment = new NotificationAdmin();
                        break;
                }
                return getFragmentPage(fragment);
            }
        });

        mApiService.reportTransaksi().enqueue(new Callback<ReportResponse>() {
            @Override
            public void onResponse(Call<ReportResponse> call, Response<ReportResponse> response) {
                if (response.isSuccessful()) {
                    reports = response.body().getData();
                    db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class, "db_koperasi").allowMainThreadQueries().build();
                    DataItem allReports = new DataItem();
                    for(int i = 0; i < reports.size(); i++){
                        allReports.setId(reports.get(i).getId());
                        allReports.setTanggal(reports.get(i).getTanggal());
                        allReports.setJenisTransaksi(reports.get(i).getJenisTransaksi());
                        allReports.setNominalTransaksi(reports.get(i).getNominalTransaksi());
                        allReports.setIdUserNasabah(reports.get(i).getIdUserNasabah());
                        allReports.setStatus(reports.get(i).getStatus());
                        allReports.setIdUserKaryawan(reports.get(i).getIdUserKaryawan());
                        allReports.setBuktiPembayaran(reports.get(i).getBuktiPembayaran());
                        db.simpananDAO().insertReports(reports);
                        Log.d("retro", "berhasil insert ke sqllite");
                    }


                }else{
                    Log.d("retro", "RESPONSE : "+response.body().getData());

                }

            }

            @Override
            public void onFailure(Call<ReportResponse> call, Throwable t) {
                Log.d("debug","GAGAL");
                Toast.makeText(MainActivity2.this, "GAGAL", Toast.LENGTH_SHORT).show();
            }
        });

        mApiService.listNasabah().enqueue(new Callback<NasabahResponse>() {
            @Override
            public void onResponse(Call<NasabahResponse> call, Response<NasabahResponse> response) {
                if (response.isSuccessful()) {
                    nasabahs = response.body().getNasabah();
                    db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class, "db_koperasi").allowMainThreadQueries().build();
                    NasabahItem allNasabahs = new NasabahItem();
                    for(int i = 0; i < nasabahs.size(); i++){
                        allNasabahs.setId(nasabahs.get(i).getId());
                        allNasabahs.setName(nasabahs.get(i).getName());
                        allNasabahs.setEmail(nasabahs.get(i).getEmail());
                        allNasabahs.setEmailVerifiedAt(nasabahs.get(i).getEmailVerifiedAt());
                        allNasabahs.setCreatedAt(nasabahs.get(i).getCreatedAt());
                        allNasabahs.setUpdatedAt(nasabahs.get(i).getUpdatedAt());
                        allNasabahs.setUserRole(nasabahs.get(i).getUserRole());
                        allNasabahs.setNoTelp(nasabahs.get(i).getNoTelp());
                        allNasabahs.setFcmToken(nasabahs.get(i).getFcmToken());
                        db.nasabahDAO().insertNasabah(nasabahs);
                        Log.d("retro", "berhasil insert ke sqllite");
                    }


                }else{
                    Log.d("retro", "RESPONSE : "+response.body().getNasabah());

                }

            }

            @Override
            public void onFailure(Call<NasabahResponse> call, Throwable t) {
                Log.d("debug","GAGAL");
                Toast.makeText(MainActivity2.this, "GAGAL", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
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
