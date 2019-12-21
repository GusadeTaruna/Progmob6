package com.example.koperasiku.nasabah.RiwayatSetoran;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.koperasiku.R;
import com.example.koperasiku.nasabah.RiwayatSetoran.model.Berhasil;
import com.example.koperasiku.nasabah.RiwayatSetoran.model.Pending;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    Context mContext;
    List<Berhasil> mData;

    public RecyclerViewAdapter(Context mContext, List<Berhasil> mData){
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.berhasil_item,parent,false);
        MyViewHolder vHolder = new MyViewHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.tv_Tanggal.setText(mData.get(position).getTanggal());
        holder.tv_Nominal.setText(mData.get(position).getNominal());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_Tanggal;
        private TextView tv_Nominal;


        public MyViewHolder(View itemView){
            super(itemView);

            tv_Tanggal = (TextView) itemView.findViewById(R.id.tanggal_berhasil);
            tv_Nominal = (TextView) itemView.findViewById(R.id.nominal_berhasil);

        }
    }

}
