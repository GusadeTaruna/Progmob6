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
import android.widget.TextView;
import android.widget.Toast;

import com.example.koperasiku.R;
import com.example.koperasiku.apihelper.BaseApiService;
import com.example.koperasiku.apihelper.UtilsApi;
import com.example.koperasiku.karyawan.modelKelolaNasabah.HapusNasabahResponse;
import com.example.koperasiku.karyawan.modelKelolaNasabah.NasabahItem;
import com.example.koperasiku.karyawan.modelKelolaNasabah.NasabahResponse;
import com.example.koperasiku.karyawan.modelVerif.AccResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterDataNasabah extends RecyclerView.Adapter<AdapterDataNasabah.HolderData> {
    SharedPreferences profile;
    BaseApiService mApiService = UtilsApi.getAPIService();
    ProgressDialog loading;
    private List<NasabahItem> mList;
    private Context ctx;

    public AdapterDataNasabah (Context ctx, List<NasabahItem> mList){
        this.ctx = ctx;
        this.mList = mList;
    }

    @Override
    public AdapterDataNasabah.HolderData onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nasabah,parent,false);
        AdapterDataNasabah.HolderData holder = new AdapterDataNasabah.HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(AdapterDataNasabah.HolderData holder, int position) {
        NasabahItem dm = mList.get(position);

//        String date = String.valueOf(dm.getTanggal());
        holder.nama.setText(dm.getName());
        String telp = String.valueOf(dm.getNoTelp());
        holder.nomer.setText(telp);
        holder.email.setText(dm.getEmail());
        final int id = dm.getId();

        holder.hapusNasabah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading = ProgressDialog.show(ctx, null, "Harap Tunggu...", true, false);
                mApiService.hapusNasabah(id).enqueue(new Callback<HapusNasabahResponse>() {
                    @Override
                    public void onResponse(Call<HapusNasabahResponse> call, Response<HapusNasabahResponse> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(ctx, "Nasabah Berhasil di Hapus", Toast.LENGTH_SHORT).show();
                            ((KelolaNasabahActivity)ctx).finish();
                            Intent intent = new Intent(ctx, KelolaNasabahActivity.class);
                            ctx.startActivity(intent);
                            loading.dismiss();
                        }else{
                            Log.d("debug","GAGAL");
                            Toast.makeText(ctx, "Gagal Verif", Toast.LENGTH_SHORT).show();
                            loading.dismiss();
                        }

                    }

                    @Override
                    public void onFailure(Call<HapusNasabahResponse> call, Throwable t) {
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
        TextView nama,nomer,email;
        Button hapusNasabah;

        public HolderData (View v){
            super(v);

            nama = (TextView) v.findViewById(R.id.nama);
            nomer = (TextView) v.findViewById(R.id.nomer);
            email = (TextView) v.findViewById(R.id.email);
            hapusNasabah = (Button) v.findViewById(R.id.hapusNasabah);


            hapusNasabah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("debug","Terklik");
                }
            });

        }


    }
}
