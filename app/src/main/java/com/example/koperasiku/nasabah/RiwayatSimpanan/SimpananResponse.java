package com.example.koperasiku.nasabah.RiwayatSimpanan;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SimpananResponse{

	@SerializedName("simpan")
	private List<SimpanItem> simpan;

	public void setSimpan(List<SimpanItem> simpan){
		this.simpan = simpan;
	}

	public List<SimpanItem> getSimpan(){
		return simpan;
	}

	@Override
 	public String toString(){
		return 
			"SimpananResponse{" + 
			"simpan = '" + simpan + '\'' + 
			"}";
		}
}