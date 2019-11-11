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

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.koperasiku.Fragment.Home;
import com.example.koperasiku.Fragment.Notification;
import com.example.koperasiku.Fragment.Profile;

public class MainActivity extends AppCompatActivity {
    TextView tvResultNama;
    String resultNama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Menampilkan halaman Fragment yang pertama kali muncul
        getFragmentPage(new Home());

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