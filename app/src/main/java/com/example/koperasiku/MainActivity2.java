package com.example.koperasiku;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.koperasiku.Fragment.Home;
import com.example.koperasiku.Fragment.HomeAdmin;
import com.example.koperasiku.Fragment.Notification;
import com.example.koperasiku.Fragment.NotificationAdmin;
import com.example.koperasiku.Fragment.Profile;
import com.example.koperasiku.Fragment.ProfileAdmin;

public class MainActivity2 extends AppCompatActivity {
    TextView tvResultNama;
    String resultNama;

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
