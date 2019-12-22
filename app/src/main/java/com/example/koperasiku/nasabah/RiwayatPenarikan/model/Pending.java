package com.example.koperasiku.nasabah.RiwayatPenarikan.model;

public class Pending {

    private String Tanggal;
    private String Nominal;

    public Pending(){

    }

    public  Pending(String tanggal, String nominal){
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
