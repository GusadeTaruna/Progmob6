package com.example.koperasiku.karyawan.modelVerif;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class VerifResponse{

	@SerializedName("not_verified")
	private List<NotVerifiedItem> notVerified;

	public void setNotVerified(List<NotVerifiedItem> notVerified){
		this.notVerified = notVerified;
	}

	public List<NotVerifiedItem> getNotVerified(){
		return notVerified;
	}

	@Override
 	public String toString(){
		return 
			"VerifResponse{" + 
			"not_verified = '" + notVerified + '\'' + 
			"}";
		}
}