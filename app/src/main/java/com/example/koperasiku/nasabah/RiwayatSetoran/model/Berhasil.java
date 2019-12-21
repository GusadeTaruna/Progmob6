package com.example.koperasiku.nasabah.RiwayatSetoran.model;

public class Berhasil {

    private String Tanggal;
    private String Nominal;

    public Berhasil(){

    }

    public  Berhasil(String tanggal, String nominal){
        Tanggal = tanggal;
        Nominal = nominal;
    }

    public String getTanggal() {
        return Tanggal;
    }

    public String getNominal() {
        return Nominal;
    }

    public void setTanggal(String tanggal) {
        Tanggal = tanggal;
    }

    public void setNominal(String  nominal) {
        Nominal = nominal;
    }
}
