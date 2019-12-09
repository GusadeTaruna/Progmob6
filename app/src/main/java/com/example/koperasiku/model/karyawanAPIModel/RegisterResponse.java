package com.example.koperasiku.model.karyawanAPIModel;

import com.google.gson.annotations.SerializedName;

public class RegisterResponse{

	@SerializedName("success")
	private Success success;

	@SerializedName("error")
	private boolean error;

	public void setSuccess(Success success){
		this.success = success;
	}

	public Success getSuccess(){
		return success;
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
			"RegisterResponse{" + 
			"success = '" + success + '\'' + 
			",error = '" + error + '\'' + 
			"}";
		}
}