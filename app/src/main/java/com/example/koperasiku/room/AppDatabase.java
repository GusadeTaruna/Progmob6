package com.example.koperasiku.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.koperasiku.nasabah.RiwayatSimpanan.HistoriItem;
import com.example.koperasiku.room.SimpananDAO;

@Database(entities = {HistoriItem.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract SimpananDAO simpananDAO();

}
