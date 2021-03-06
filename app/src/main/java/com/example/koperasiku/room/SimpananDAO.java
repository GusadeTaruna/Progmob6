package com.example.koperasiku.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.koperasiku.karyawan.modelReport.DataItem;
import com.example.koperasiku.nasabah.RiwayatSimpanan.HistoriItem;

import java.util.List;

@Dao
public interface SimpananDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSimpanan(List<HistoriItem> simpananList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTarikan(List<com.example.koperasiku.nasabah.RiwayatPenarikan.HistoriItem> simpananList);

    @Query("SELECT * FROM tb_simpanan WHERE idUserNasabah = :id AND jenisTransaksi = 1 ORDER BY tanggal ASC")
    List<HistoriItem> selectAll(int id);

    @Query("SELECT * FROM tb_penarikan WHERE idUserNasabah = :id AND jenisTransaksi = 2 ORDER BY tanggal ASC")
    List<com.example.koperasiku.nasabah.RiwayatPenarikan.HistoriItem> selectAllTarik(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertReports(List<DataItem> simpananList);

    @Query("SELECT * FROM tb_report")
    List<DataItem> selectReports();
}
