package com.example.koperasiku.nasabah.RiwayatSimpanan;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.koperasiku.R;

import java.util.List;

public class AdapterData extends RecyclerView.Adapter<AdapterData.HolderData> {

    private List<SimpanItem> mList;
    private Context ctx;

    public AdapterData (Context ctx, List<SimpanItem> mList){
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
        SimpanItem dm = mList.get(position);

        holder.tanggal.setText(dm.getTanggal());
        holder.nominal.setText(dm.getNominalTransaksi());
        holder.status.setText(dm.getStatus());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HolderData extends RecyclerView.ViewHolder{
        TextView tanggal,nominal,status;

        public HolderData (View v){
            super(v);

            tanggal = (TextView) v.findViewById(R.id.tanggal);
            nominal = (TextView) v.findViewById(R.id.nominal);
            status = (TextView) v.findViewById(R.id.status);
        }
    }

}