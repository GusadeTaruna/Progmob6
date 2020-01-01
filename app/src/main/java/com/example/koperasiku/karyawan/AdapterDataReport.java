package com.example.koperasiku.karyawan;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.koperasiku.R;
import com.example.koperasiku.apihelper.BaseApiService;
import com.example.koperasiku.apihelper.UtilsApi;
import com.example.koperasiku.karyawan.modelKelolaNasabah.HapusNasabahResponse;
import com.example.koperasiku.karyawan.modelKelolaNasabah.NasabahItem;
import com.example.koperasiku.karyawan.modelReport.DataItem;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterDataReport extends RecyclerView.Adapter<AdapterDataReport.HolderData> {

    SharedPreferences profile;
    BaseApiService mApiService = UtilsApi.getAPIService();
    ProgressDialog loading;
    private List<DataItem> mList;
    private Context ctx;

    public AdapterDataReport (Context ctx, List<DataItem> mList){
        this.ctx = ctx;
        this.mList = mList;
    }

    @Override
    public AdapterDataReport.HolderData onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_report,parent,false);
        AdapterDataReport.HolderData holder = new AdapterDataReport.HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(AdapterDataReport.HolderData holder, int position) {
        DataItem dm = mList.get(position);

        String date = String.valueOf(dm.getTanggal());
        holder.tanggal.setText(date);
        String nominalTransaksi = String.valueOf(dm.getNominalTransaksi());
        holder.nominal.setText(nominalTransaksi);
        Picasso.get().load(dm.getBuktiPembayaran()).placeholder(R.drawable.loading).fit().centerCrop().into(holder.bukti);
        holder.status.setText(dm.getStatus());
        if(dm.getJenisTransaksi()==1){
            holder.jenis.setText("Simpanan");
            holder.bukti.setVisibility(View.VISIBLE);
        }
        if(dm.getJenisTransaksi()==2){
            holder.jenis.setText("Penarikan");
            holder.bukti.setVisibility(View.GONE);
        }
        Log.e("debug","STATUS : "+dm.getStatus());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HolderData extends RecyclerView.ViewHolder{
        ImageView bukti;
        TextView tanggal,nominal,status,jenis;

        public HolderData (View v){
            super(v);

            tanggal = (TextView) v.findViewById(R.id.tanggal);
            nominal = (TextView) v.findViewById(R.id.nominal);
            status = (TextView) v.findViewById(R.id.status);
            jenis = (TextView) v.findViewById(R.id.jenis);
            bukti = (ImageView) v.findViewById(R.id.bukti);

        }


    }
}
