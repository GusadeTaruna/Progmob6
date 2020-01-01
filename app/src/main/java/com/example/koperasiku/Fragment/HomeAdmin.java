package com.example.koperasiku.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.koperasiku.R;
import com.example.koperasiku.UserSessionManager;
import com.example.koperasiku.karyawan.HitungBungaActivity;
import com.example.koperasiku.karyawan.KelolaNasabahActivity;
import com.example.koperasiku.karyawan.ReportTransaksiActivity;
import com.example.koperasiku.karyawan.verifikasiTransaksiActivity;

public class HomeAdmin extends Fragment {
    UserSessionManager session;
    FrameLayout rootView;
    CardView verifTransaksi;
    CardView kelolaNasabah;
    CardView report;
    CardView hitungBunga;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = (FrameLayout) inflater.inflate(R.layout.fragment_home_admin, container, false);

        session = new UserSessionManager(getActivity().getApplicationContext());

        if(session.checkLogin()){
            getActivity().finish();
        }

//        karyawan = (CardView) rootView.findViewById(R.id.menu_karyawan);
//        nasabah = (CardView) rootView.findViewById(R.id.menu_nasabah);
        verifTransaksi = (CardView) rootView.findViewById(R.id.verifTransaksi);
        kelolaNasabah = (CardView) rootView.findViewById(R.id.kelolaNasabah);
        report = (CardView) rootView.findViewById(R.id.reportTransaksi);
        hitungBunga = (CardView) rootView.findViewById(R.id.hitungBunga);

//        karyawan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Toast.makeText(getActivity(), "Karyawan terklik", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(getActivity(), KaryawanActivity.class);
//                startActivity(intent);
//            }
//        });

//        nasabah.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getActivity(), "Nasabah terklik", Toast.LENGTH_SHORT).show();
//            }
//        });

        verifTransaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getActivity(), "Setor terklik", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), verifikasiTransaksiActivity.class);
                startActivity(intent);
            }
        });

        kelolaNasabah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getActivity(), "Tarik terklik", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), KelolaNasabahActivity.class);
                startActivity(intent);
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getActivity(), "Report terklik", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ReportTransaksiActivity.class);
                startActivity(intent);
            }
        });

        hitungBunga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getActivity(), "Hitung Bunga terklik", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), HitungBungaActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

}
