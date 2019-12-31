package com.example.koperasiku.nasabah.RiwayatSimpanan;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.koperasiku.MainActivity;
import com.example.koperasiku.MainActivity2;
import com.example.koperasiku.R;
import com.example.koperasiku.apihelper.BaseApiService;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterData extends RecyclerView.Adapter<AdapterData.HolderData> {
    private List<HistoriItem> mList;
    private Context ctx;


    public AdapterData (Context ctx, List<HistoriItem> mList){
        this.ctx = ctx;
        this.mList = mList;
    }

    @Override
    public HolderData onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_riwayat_simpanan,parent,false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(HolderData holder, int position) {
        HistoriItem dm = mList.get(position);

        String date = String.valueOf(dm.getTanggal());
        holder.tanggal.setText(date);
        String nominalTransaksi = String.valueOf(dm.getNominalTransaksi());
        holder.nominal.setText(nominalTransaksi);
        holder.status.setText(dm.getStatus());
        Log.e("debug","STATUS : "+dm.getStatus());
//        Picasso.get().load(dm.getBuktiPembayaran()).fit().centerCrop().into(holder.bukti);
        Picasso.get().load(dm.getBuktiPembayaran()).placeholder(R.drawable.loading).fit().centerCrop().into(holder.bukti);


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HolderData extends RecyclerView.ViewHolder{
        TextView tanggal,nominal,status;
        ImageView bukti;
        Button verif;

        public HolderData (View v){
            super(v);

            bukti = (ImageView) v.findViewById(R.id.bukti);
            tanggal = (TextView) v.findViewById(R.id.tanggal);
            nominal = (TextView) v.findViewById(R.id.nominal);
            status = (TextView) v.findViewById(R.id.status);
            verif = (Button) v.findViewById(R.id.btnVerif);
        }
    }

}
