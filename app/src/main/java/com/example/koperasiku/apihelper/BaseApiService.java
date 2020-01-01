package com.example.koperasiku.apihelper;

import com.example.koperasiku.karyawan.modelHitungBunga.HitungResponse;
import com.example.koperasiku.karyawan.modelKelolaNasabah.HapusNasabahResponse;
import com.example.koperasiku.karyawan.modelKelolaNasabah.NasabahResponse;
import com.example.koperasiku.karyawan.modelReport.ReportResponse;
import com.example.koperasiku.karyawan.modelVerif.AccResponse;
import com.example.koperasiku.karyawan.modelVerif.VerifResponse;
import com.example.koperasiku.model.NotifPenarikanResponse;
import com.example.koperasiku.model.NotifResponse;
import com.example.koperasiku.model.karyawanAPIModel.DetailResponse;
import com.example.koperasiku.model.karyawanAPIModel.EditResponse;
import com.example.koperasiku.model.karyawanAPIModel.LoginResponse;
import com.example.koperasiku.model.karyawanAPIModel.RegisterResponse;
import com.example.koperasiku.model.nasabahAPIModel.TransaksiResponse;
import com.example.koperasiku.nasabah.RiwayatPenarikan.PenarikanResponse;
import com.example.koperasiku.nasabah.RiwayatSimpanan.SimpananResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface BaseApiService {

    // Fungsi ini untuk memanggil API http://10.0.2.2/mahasiswa/login.php
    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> loginRequest(@Field("email") String email,
                                     @Field("password") String password, @Field("fcm_token") String token);

    // Fungsi ini untuk memanggil API http://10.0.2.2/mahasiswa/register.php
    @FormUrlEncoded
    @POST("register")
    Call<RegisterResponse> registerRequest(@Field("name") String name,
                                           @Field("email") String email,
                                           @Field("no_telp") String no_telp,
                                           @Field("password") String password,
                                           @Field("c_password") String c_password);

    @POST("verify/{pegawai}/{id}")
    Call<AccResponse> accSimpanan(@Path("pegawai") int pegawai, @Path("id") int id);

    @GET("nasabah")
    Call<NasabahResponse> listNasabah();

    @GET("notif/setoran/{id}")
    Call<NotifResponse> notifikasi(@Path("id") int id);

    @GET("notif/tarik/{id}")
    Call<NotifPenarikanResponse> notifTarik(@Path("id") int id);

    @POST("hapusnasabah/{id}")
    Call<HapusNasabahResponse> hapusNasabah(@Path("id") int id);

    @POST("detail")
    Call<DetailResponse> detailProfile();

    @POST("not_verify")
    Call<VerifResponse> getVerif();

    @POST("hitung_bunga/{pegawai} ")
    Call<HitungResponse> getHitung(@Path("pegawai") int id);

    @GET("report")
    Call<ReportResponse> reportTransaksi();

    @GET("report/nasabah/setor/{id}")
    Call<SimpananResponse> getSimpanItem(@Path("id") int id);

    @GET("report/nasabah/tarik/{id}")
    Call<PenarikanResponse> getTarikItem(@Path("id") int id);

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

//    @FormUrlEncoded
//    @POST("simpanan")
//    Call<TransaksiResponse> transaksiProses(@Path("tanggal") String tanggal,
//                                    @Field("jenis_transaksi") String jenis_transaksi,
//                                    @Field("nominal_transaksi") String nominal_transaksi,
//                                    @Field("id_user_nasabah") int id_user_nasabah,
//                                    @Field("buktiUpload") String buktiUpload);

    @Multipart
    @POST("simpanan")
    Call<TransaksiResponse> transaksiProses(@Part("jenis_transaksi") RequestBody jenis_transaksi,
                                            @Part("nominal_transaksi") RequestBody nominal_transaksi,
                                            @Part("id_user_nasabah") RequestBody id_user_nasabah,
                                            @Part MultipartBody.Part buktiUpload);
//                                            @Part("filename") RequestBody filename);




    @FormUrlEncoded
    @POST("detail")
    Call<ResponseBody> detailUser(@Field("id") Integer id);

}
