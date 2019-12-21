package com.example.koperasiku.nasabah.RiwayatSetoran;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.koperasiku.R;
import com.example.koperasiku.nasabah.RiwayatSetoran.model.Pending;

import java.util.ArrayList;
import java.util.List;

public class FragmentPending extends Fragment {

    View v;
    private RecyclerView rcv;
    private List<Pending> lstPending;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lstPending = new ArrayList<>();
        lstPending.add(new Pending("11/12/2019","Rp.1.000.000"));
        lstPending.add(new Pending("12/12/2019","Rp.2.000.000"));
        lstPending.add(new Pending("10/12/2019","Rp.1.000.000"));
    }

    public FragmentPending() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.pending_fragment,container,false);
        rcv = (RecyclerView) v.findViewById(R.id.pending_recyclerview);
        RecyclerViewAdapterPending rcvAdapter = new RecyclerViewAdapterPending(getContext(),lstPending);
        rcv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcv.setAdapter(rcvAdapter);
        return v;
    }
}
