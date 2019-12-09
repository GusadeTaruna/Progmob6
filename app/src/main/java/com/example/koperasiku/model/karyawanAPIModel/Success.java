package com.example.koperasiku.model.karyawanAPIModel;

import com.google.gson.annotations.SerializedName;

public class Success{

	@SerializedName("name")
	private String name;

	@SerializedName("token")
	private String token;

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setToken(String token){
		this.token = token;
	}

	public String getToken(){
		return token;
	}

	@Override
 	public String toString(){
		return 
			"Success{" + 
			"name = '" + name + '\'' + 
			",token = '" + token + '\'' + 
			"}";
		}
}