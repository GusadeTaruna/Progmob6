package com.example.koperasiku.apihelper;

import com.example.koperasiku.model.karyawanAPIModel.DetailResponse;
import com.example.koperasiku.model.karyawanAPIModel.EditResponse;
import com.example.koperasiku.model.karyawanAPIModel.LoginResponse;
import com.example.koperasiku.model.karyawanAPIModel.RegisterResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BaseApiService {

    // Fungsi ini untuk memanggil API http://10.0.2.2/mahasiswa/login.php
    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> loginRequest(@Field("email") String email,
                                     @Field("password") String password);

    // Fungsi ini untuk memanggil API http://10.0.2.2/mahasiswa/register.php
    @FormUrlEncoded
    @POST("register")
    Call<RegisterResponse> registerRequest(@Field("name") String name,
                                           @Field("email") String email,
                                           @Field("no_telp") String no_telp,
                                           @Field("password") String password,
                                           @Field("c_password") String c_password);

    @POST("detail")
    Call<DetailResponse> detailProfile();

    @FormUrlEncoded
    @POST("edit/{id}")
    Call<EditResponse> editProfil(@Path("id") int id,
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
