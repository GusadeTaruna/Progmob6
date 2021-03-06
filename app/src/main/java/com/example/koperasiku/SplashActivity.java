package com.example.koperasiku;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

public class SplashActivity extends AppCompatActivity {
    UserSessionManager session;
    private SharedPreferences profile;
    private int waktu_loading=1000;

    //4000=4 detik

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        if(session.checkLogin()){
//            finish();
//        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                Intent home = new Intent(SplashActivity.this, LoginActivity.class);
//                startActivity(home);
//                finish();

                profile = getSharedPreferences("AndroidExamplePref", Context.MODE_PRIVATE);
                final String UserRole = profile.getString("role",null);
                Log.d("debug","Role : "+UserRole);
                if (TextUtils.isEmpty(UserRole)){
                    Intent home = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(home);
                    finish();
                }
                else if(UserRole.equals("Nasabah")){
                    Intent main =new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(main);
                    finish();
                }else if(UserRole.equals("Admin")) {
                    Intent main = new Intent(SplashActivity.this, MainActivity2.class);
                    startActivity(main);
                    finish();
                }
                //setelah loading maka akan langsung berpindah ke home activity
            }
        },waktu_loading);
    }
}
