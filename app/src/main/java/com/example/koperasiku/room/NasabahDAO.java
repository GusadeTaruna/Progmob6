package com.example.koperasiku.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.koperasiku.karyawan.modelKelolaNasabah.NasabahItem;

import java.util.List;

@Dao
public interface NasabahDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNasabah(List<NasabahItem> simpananList);

    @Query("SELECT * FROM tb_nasabah")
    List<NasabahItem> selectNasabah();
}
