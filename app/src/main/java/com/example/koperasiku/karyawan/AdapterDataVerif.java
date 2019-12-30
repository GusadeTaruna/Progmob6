package com.example.koperasiku.karyawan;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.koperasiku.R;
import com.example.koperasiku.karyawan.modelVerif.NotVerifiedItem;

import java.util.List;

public class AdapterDataVerif extends RecyclerView.Adapter<AdapterDataVerif.HolderData> {

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
        }
        if(dm.getJenisTransaksi()==2){
            holder.jenis.setText("Penarikan");
        }
        Log.e("debug","STATUS : "+dm.getStatus());
        holder.verif.setVisibility(View.VISIBLE);


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HolderData extends RecyclerView.ViewHolder{
        TextView tanggal,nominal,status,jenis;
        Button verif;

        public HolderData (View v){
            super(v);

            tanggal = (TextView) v.findViewById(R.id.tanggal);
            nominal = (TextView) v.findViewById(R.id.nominal);
            status = (TextView) v.findViewById(R.id.status);
            verif = (Button) v.findViewById(R.id.btnVerif);
            jenis = (TextView) v.findViewById(R.id.jenis);


            verif.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("debug","Terklik");
                }
            });

        }


    }
}
