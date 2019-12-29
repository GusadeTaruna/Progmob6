package com.example.koperasiku.Fragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.koperasiku.R;
import com.example.koperasiku.UserSessionManager;
import com.example.koperasiku.nasabah.RiwayatSimpanan.RetreiveDataActivity;
import com.example.koperasiku.nasabah.TransaksiPenarikan.TransaksiPenarikanActivity;
import com.example.koperasiku.nasabah.TransaksiSetoran.TransaksiSetoranActivity;

public class Home extends Fragment {

    UserSessionManager session;
    FrameLayout rootView;
    CardView karyawan;
    CardView nasabah;
    CardView setor;
    CardView tarik;
    CardView report;
    CardView hitung_bunga;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = (FrameLayout) inflater.inflate(R.layout.fragment_home, container, false);

        session = new UserSessionManager(getActivity().getApplicationContext());

        if(session.checkLogin()){
            getActivity().finish();
        }

//        karyawan = (CardView) rootView.findViewById(R.id.menu_karyawan);
//        nasabah = (CardView) rootView.findViewById(R.id.menu_nasabah);
        setor = (CardView) rootView.findViewById(R.id.menu_setor);
        tarik = (CardView) rootView.findViewById(R.id.menu_tarik);
        report = (CardView) rootView.findViewById(R.id.menu_report);
        hitung_bunga = (CardView) rootView.findViewById(R.id.menu_hitung_bunga);

//        karyawan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Toast.makeText(getActivity(), "Karyawan terklik", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(getActivity(), KaryawanActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        nasabah.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getActivity(), "Nasabah terklik", Toast.LENGTH_SHORT).show();
//            }
//        });

        setor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getActivity(), "Setor terklik", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), TransaksiSetoranActivity.class);
                startActivity(intent);
            }
        });

        tarik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getActivity(), "Tarik terklik", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), TransaksiPenarikanActivity.class);
                startActivity(intent);
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getActivity(), "Report terklik", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), RetreiveDataActivity.class);
                startActivity(intent);
            }
        });

        hitung_bunga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Hitung Bunga terklik", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(getActivity(), RiwayatTransaksiPenarikanActivity.class);
//                startActivity(intent);
            }
        });

        return rootView;
    }


}
