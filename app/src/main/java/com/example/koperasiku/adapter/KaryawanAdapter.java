package com.example.koperasiku.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.koperasiku.MainActivity;
import com.example.koperasiku.R;
import com.example.koperasiku.karyawan.KaryawanActivity;
import com.example.koperasiku.model.KaryawanModel;

import java.util.ArrayList;

public class KaryawanAdapter extends BaseAdapter {
    private ArrayList<KaryawanModel> listKaryawan;
    private Context context;
    private LayoutInflater inflater;

    public KaryawanAdapter(Context context, ArrayList<KaryawanModel> list){
        this.context = context;
        this.listKaryawan = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listKaryawan.size();
    }

    @Override
    public Object getItem(int i) {
        return listKaryawan.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        TextView tvNamaKaryawan = null;
        ImageView ivUbah = null, ivDelete = null;

        if (view == null) {
            view = inflater.inflate(R.layout.list_karyawan, null);
            tvNamaKaryawan = (TextView) view.findViewById(R.id.tvNamaKaryawan);
            ivUbah = (ImageView) view.findViewById(R.id.ivUbahNama);
            ivDelete = (ImageView) view.findViewById(R.id.ivHapusKaryawan);

        } else {

            tvNamaKaryawan = (TextView) view.findViewById(R.id.tvNamaKaryawan);
            ivUbah = (ImageView) view.findViewById(R.id.ivUbahNama);
            ivDelete = (ImageView) view.findViewById(R.id.ivHapusKaryawan);

            Log.d("DATA", listKaryawan.get(i).toString());
            tvNamaKaryawan = (TextView) view.findViewById(R.id.tvNamaKaryawan);
            tvNamaKaryawan.setText(listKaryawan.get(i).getName().toString());
        }

        tvNamaKaryawan.setText(listKaryawan.get(i).getName().toString());
        ivUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KaryawanModel ubahKaryawan = KaryawanActivity.getInstance().searchKaryawan(listKaryawan.get(i).getId());
                KaryawanActivity.getInstance().TambahKaryawanDialog(ubahKaryawan,i);
            }
        });

        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                konfirmasiDialog(context, listKaryawan.get(i).getId(),i);
            }
        });

        return view;
    }

    public static void konfirmasiDialog(Context context,final int dataId,final int position)
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder
                .setMessage("Yakin datanya mau dihapus ?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        KaryawanActivity.getInstance().deleteDataKaryawan(dataId, position);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
