package com.example.koperasiku.nasabah.RiwayatSetoran;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.koperasiku.R;
import com.example.koperasiku.nasabah.RiwayatSetoran.model.Berhasil;

import java.util.ArrayList;
import java.util.List;

public class FragmentBerhasil extends Fragment {

    View v;
    private RecyclerView rcv;
    private List<Berhasil> lstBerhasil;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lstBerhasil = new ArrayList<>();
        lstBerhasil.add(new Berhasil("11/12/2019","Rp.1.000.000"));
        lstBerhasil.add(new Berhasil("12/12/2019","Rp.2.000.000"));
        lstBerhasil.add(new Berhasil("10/12/2019","Rp.1.000.000"));
    }

    public FragmentBerhasil() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.berhasil_fragment,container,false);
        rcv = (RecyclerView) v.findViewById(R.id.berhasil_recycleview);
        RecyclerViewAdapter rcvAdapter = new RecyclerViewAdapter(getContext(),lstBerhasil);
        rcv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcv.setAdapter(rcvAdapter);
        return v;
    }
}
