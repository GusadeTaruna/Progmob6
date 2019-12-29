package com.example.koperasiku.nasabah.RiwayatPenarikan;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class PenarikanResponse{

	@SerializedName("uang")
	private Uang uang;

	@SerializedName("histori")
	private List<HistoriItem> histori;

	@SerializedName("error")
	private boolean error;

	@SerializedName("user")
	private User user;

	public void setUang(Uang uang){
		this.uang = uang;
	}

	public Uang getUang(){
		return uang;
	}

	public void setHistori(List<HistoriItem> histori){
		this.histori = histori;
	}

	public List<HistoriItem> getHistori(){
		return histori;
	}

	public void setError(boolean error){
		this.error = error;
	}

	public boolean isError(){
		return error;
	}

	public void setUser(User user){
		this.user = user;
	}

	public User getUser(){
		return user;
	}

	@Override
 	public String toString(){
		return 
			"PenarikanResponse{" + 
			"uang = '" + uang + '\'' + 
			",histori = '" + histori + '\'' + 
			",error = '" + error + '\'' + 
			",user = '" + user + '\'' + 
			"}";
		}
}