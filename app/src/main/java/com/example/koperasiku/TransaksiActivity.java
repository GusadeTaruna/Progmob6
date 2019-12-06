package com.example.koperasiku;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TransaksiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        TransaksiPagerAdapter transaksiPagerAdapter = new TransaksiPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(transaksiPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
    }
}
