package com.example.koperasiku.karyawan;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.example.koperasiku.MainActivity2;
import com.example.koperasiku.R;
import com.example.koperasiku.apihelper.BaseApiService;
import com.example.koperasiku.apihelper.UtilsApi;
import com.example.koperasiku.karyawan.modelVerif.AccResponse;
import com.example.koperasiku.karyawan.modelVerif.NotVerifiedItem;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterDataVerif extends RecyclerView.Adapter<AdapterDataVerif.HolderData> {
    SharedPreferences profile;
    BaseApiService mApiService = UtilsApi.getAPIService();
    ProgressDialog loading;
    private List<NotVerifiedItem> mList;
    private Context ctx;

    public AdapterDataVerif (Context ctx, List<NotVerifiedItem> mList){
        this.ctx = ctx;
        this.mList = mList;
    }

    @Override
    public AdapterDataVerif.HolderData onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_verif,parent,false);
        AdapterDataVerif.HolderData holder = new AdapterDataVerif.HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(AdapterDataVerif.HolderData holder, int position) {
        NotVerifiedItem dm = mList.get(position);

        String date = String.valueOf(dm.getTanggal());
        holder.tanggal.setText(date);
        String nominalTransaksi = String.valueOf(dm.getNominalTransaksi());
        holder.nominal.setText(nominalTransaksi);
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
        holder.verif.setVisibility(View.VISIBLE);

        Picasso.get().load(dm.getBuktiPembayaran()).placeholder(R.drawable.loading).fit().centerCrop().into(holder.bukti);

        profile = ctx.getSharedPreferences("AndroidExamplePref", Context.MODE_PRIVATE);
        final int idKaryawan = profile.getInt("id",0);
        final int idSimpanan = dm.getId();


        holder.verif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading = ProgressDialog.show(ctx, null, "Harap Tunggu...", true, false);
                mApiService.accSimpanan(idKaryawan,idSimpanan).enqueue(new Callback<AccResponse>() {
                    @Override
                    public void onResponse(Call<AccResponse> call, Response<AccResponse> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(ctx, "Transaksi Berhasil di Verifikasi", Toast.LENGTH_SHORT).show();
                            ((verifikasiTransaksiActivity)ctx).finish();
                            Intent intent = new Intent(ctx, verifikasiTransaksiActivity.class);
                            ctx.startActivity(intent);
                            loading.dismiss();
                        }else{
                            Log.d("debug","GAGAL");
                            Toast.makeText(ctx, "Gagal Verif", Toast.LENGTH_SHORT).show();
                            loading.dismiss();
                        }

                    }

                    @Override
                    public void onFailure(Call<AccResponse> call, Throwable t) {
                        Log.d("debug","GAGAL");
                        Toast.makeText(ctx, "Gagal Verif", Toast.LENGTH_SHORT).show();
                        loading.dismiss();

                    }
                });
            }
        });


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HolderData extends RecyclerView.ViewHolder{
        ImageView bukti;
        TextView tanggal,nominal,status,jenis;
        Button verif;

        public HolderData (View v){
            super(v);

            tanggal = (TextView) v.findViewById(R.id.tanggal);
            nominal = (TextView) v.findViewById(R.id.nominal);
            status = (TextView) v.findViewById(R.id.status);
            verif = (Button) v.findViewById(R.id.btnVerif);
            jenis = (TextView) v.findViewById(R.id.jenis);
            bukti = (ImageView) v.findViewById(R.id.bukti);


            verif.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("debug","Terklik");
                }
            });

        }


    }
}
