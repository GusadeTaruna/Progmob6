package com.example.koperasiku.karyawan.modelVerif;

import com.google.gson.annotations.SerializedName;

public class AccResponse{

	@SerializedName("msg")
	private String msg;

	@SerializedName("error")
	private boolean error;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setError(boolean error){
		this.error = error;
	}

	public boolean isError(){
		return error;
	}

	@Override
 	public String toString(){
		return 
			"AccResponse{" + 
			"msg = '" + msg + '\'' + 
			",error = '" + error + '\'' + 
			"}";
		}
}