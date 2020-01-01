package com.example.koperasiku.karyawan.modelReport;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("nominal_transaksi")
	private int nominalTransaksi;

	@SerializedName("id_user_karyawan")
	private int idUserKaryawan;

	@SerializedName("id")
	private int id;

	@SerializedName("tanggal")
	private String tanggal;

	@SerializedName("jenis_transaksi")
	private int jenisTransaksi;

	@SerializedName("id_user_nasabah")
	private int idUserNasabah;

	@SerializedName("bukti_pembayaran")
	private String buktiPembayaran;

	@SerializedName("status")
	private String status;

	public void setNominalTransaksi(int nominalTransaksi){
		this.nominalTransaksi = nominalTransaksi;
	}

	public int getNominalTransaksi(){
		return nominalTransaksi;
	}

	public void setIdUserKaryawan(int idUserKaryawan){
		this.idUserKaryawan = idUserKaryawan;
	}

	public int getIdUserKaryawan(){
		return idUserKaryawan;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setTanggal(String tanggal){
		this.tanggal = tanggal;
	}

	public String getTanggal(){
		return tanggal;
	}

	public void setJenisTransaksi(int jenisTransaksi){
		this.jenisTransaksi = jenisTransaksi;
	}

	public int getJenisTransaksi(){
		return jenisTransaksi;
	}

	public void setIdUserNasabah(int idUserNasabah){
		this.idUserNasabah = idUserNasabah;
	}

	public int getIdUserNasabah(){
		return idUserNasabah;
	}

	public void setBuktiPembayaran(String buktiPembayaran){
		this.buktiPembayaran = buktiPembayaran;
	}

	public String getBuktiPembayaran(){
		return buktiPembayaran;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"nominal_transaksi = '" + nominalTransaksi + '\'' + 
			",id_user_karyawan = '" + idUserKaryawan + '\'' + 
			",id = '" + id + '\'' + 
			",tanggal = '" + tanggal + '\'' + 
			",jenis_transaksi = '" + jenisTransaksi + '\'' + 
			",id_user_nasabah = '" + idUserNasabah + '\'' + 
			",bukti_pembayaran = '" + buktiPembayaran + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}