package com.example.koperasiku.karyawan.modelKelolaNasabah;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class NasabahResponse{

	@SerializedName("nasabah")
	private List<NasabahItem> nasabah;

	public void setNasabah(List<NasabahItem> nasabah){
		this.nasabah = nasabah;
	}

	public List<NasabahItem> getNasabah(){
		return nasabah;
	}

	@Override
 	public String toString(){
		return 
			"NasabahResponse{" + 
			"nasabah = '" + nasabah + '\'' + 
			"}";
		}
}