package com.example.koperasiku.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.koperasiku.karyawan.modelKelolaNasabah.NasabahItem;
import com.example.koperasiku.karyawan.modelReport.DataItem;
import com.example.koperasiku.nasabah.RiwayatSimpanan.HistoriItem;
import com.example.koperasiku.room.SimpananDAO;

@Database(entities = {HistoriItem.class,com.example.koperasiku.nasabah.RiwayatPenarikan.HistoriItem.class, DataItem.class, NasabahItem.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {

    public abstract SimpananDAO simpananDAO();

    public abstract NasabahDAO nasabahDAO();

}
