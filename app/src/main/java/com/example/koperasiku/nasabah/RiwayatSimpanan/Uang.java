package com.example.koperasiku.nasabah.RiwayatSimpanan;

import com.google.gson.annotations.SerializedName;

public class Uang{

	@SerializedName("saldo")
	private int saldo;

	public void setSaldo(int saldo){
		this.saldo = saldo;
	}

	public int getSaldo(){
		return saldo;
	}

	@Override
 	public String toString(){
		return 
			"Uang{" + 
			"saldo = '" + saldo + '\'' + 
			"}";
		}
}