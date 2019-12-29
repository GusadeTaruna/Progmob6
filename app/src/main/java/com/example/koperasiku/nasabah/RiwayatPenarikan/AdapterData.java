package com.example.koperasiku.nasabah.RiwayatPenarikan;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.TextView;

import com.example.koperasiku.R;

import java.util.List;

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
        if(dm.getStatus()=="Not Verified"){
            holder.verif.setVisibility(View.VISIBLE);
        }else{
            holder.verif.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HolderData extends RecyclerView.ViewHolder{
        TextView tanggal,nominal,status;
        Button verif;

        public HolderData (View v){
            super(v);

            tanggal = (TextView) v.findViewById(R.id.tanggal);
            nominal = (TextView) v.findViewById(R.id.nominal);
            status = (TextView) v.findViewById(R.id.status);
            verif = (Button) v.findViewById(R.id.btnVerif);
        }
    }
}
