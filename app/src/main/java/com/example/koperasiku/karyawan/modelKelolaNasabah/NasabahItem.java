package com.example.koperasiku.karyawan.modelKelolaNasabah;

import com.google.gson.annotations.SerializedName;

public class NasabahItem{

	@SerializedName("user_role")
	private String userRole;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("name")
	private String name;

	@SerializedName("fcm_token")
	private Object fcmToken;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("email_verified_at")
	private Object emailVerifiedAt;

	@SerializedName("id")
	private int id;

	@SerializedName("no_telp")
	private Object noTelp;

	@SerializedName("email")
	private String email;

	public void setUserRole(String userRole){
		this.userRole = userRole;
	}

	public String getUserRole(){
		return userRole;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setFcmToken(Object fcmToken){
		this.fcmToken = fcmToken;
	}

	public Object getFcmToken(){
		return fcmToken;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setEmailVerifiedAt(Object emailVerifiedAt){
		this.emailVerifiedAt = emailVerifiedAt;
	}

	public Object getEmailVerifiedAt(){
		return emailVerifiedAt;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setNoTelp(Object noTelp){
		this.noTelp = noTelp;
	}

	public Object getNoTelp(){
		return noTelp;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	@Override
 	public String toString(){
		return 
			"NasabahItem{" + 
			"user_role = '" + userRole + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",name = '" + name + '\'' + 
			",fcm_token = '" + fcmToken + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",email_verified_at = '" + emailVerifiedAt + '\'' + 
			",id = '" + id + '\'' + 
			",no_telp = '" + noTelp + '\'' + 
			",email = '" + email + '\'' + 
			"}";
		}
}