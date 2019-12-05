package com.example.koperasiku.apihelper;

import com.example.koperasiku.model.LoginResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface BaseApiService {

    // Fungsi ini untuk memanggil API http://10.0.2.2/mahasiswa/login.php
    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody> loginRequest(@Field("email") String email,
                                     @Field("password") String password);

    // Fungsi ini untuk memanggil API http://10.0.2.2/mahasiswa/register.php
    @FormUrlEncoded
    @POST("register")
    Call<ResponseBody> registerRequest(@Field("name") String name,
                                       @Field("email") String email,
                                       @Field("password") String password,
                                       @Field("confirm_password") String confirm_password);

    @FormUrlEncoded
    @POST("edit/{id}")
    Call<ResponseBody> editProfil(@Path("id") String id,
                                  @Field("name") String name,
                                  @Field("email") String email,
                                  @Field("password") String password,
                                  @Field("confirm_password") String confirm_password);


    @FormUrlEncoded
    @POST("registerKaryawan")
    Call<ResponseBody> registerKaryawan(@Field("name") String name,
                                       @Field("email") String email,
                                       @Field("password") String password,
                                       @Field("confirm_password") String confirm_password);

    @FormUrlEncoded
    @POST("editKaryawan/{id}")
    Call<ResponseBody> editKaryawan(@Path("id") int id,
                                  @Field("name") String name,
                                  @Field("email") String email,
                                  @Field("password") String password,
                                  @Field("confirm_password") String confirm_password);




    @FormUrlEncoded
    @POST("detail")
    Call<ResponseBody> detailUser(@Field("id") Integer id);

}
