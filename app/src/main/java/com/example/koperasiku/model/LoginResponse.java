package com.example.koperasiku.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse{

	@SerializedName("error")
	private boolean error;

	@SerializedName("user")
	private User user;

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
			"LoginResponse{" + 
			"error = '" + error + '\'' + 
			",user = '" + user + '\'' + 
			"}";
		}
}