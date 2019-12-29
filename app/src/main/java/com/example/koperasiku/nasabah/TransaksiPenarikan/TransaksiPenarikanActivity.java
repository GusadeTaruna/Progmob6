package com.example.koperasiku.nasabah.TransaksiPenarikan;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.koperasiku.R;
import com.example.koperasiku.apihelper.BaseApiService;
import com.example.koperasiku.apihelper.RetrofitClient;
import com.example.koperasiku.apihelper.UtilsApi;
import com.example.koperasiku.model.nasabahAPIModel.TransaksiResponse;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransaksiPenarikanActivity extends AppCompatActivity {

    Button btnUpload;
    Button btnUpdate;
    private static final int REQUEST_GALLERY_CODE = 200;
    private static final int READ_REQUEST_CODE = 300;
    Uri gambarUri;
    TextView textBukti;
    ProgressDialog loading;
    BaseApiService mApiService;
    EditText nominal;
    private ImageView ivUser;
    private String buktiUpload;
    private SharedPreferences profile;
    RequestBody filename;
    MultipartBody.Part fileToUpload;

    private TextView dateBayar;
    private DatePickerDialog fromDatePickerDialog;
    //    private SimpleDateFormat dateFormatter;
    String dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi_penarikan);

        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        textBukti = (TextView) findViewById(R.id.textBukti);
        nominal = (EditText) findViewById(R.id.nominal);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading = ProgressDialog.show(TransaksiPenarikanActivity.this, null, "Harap Tunggu...", true, false);
                prosesTransaksi();
            }
        });

        dateFormatter = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
        dateBayar = (TextView) findViewById(R.id.tanggal);
        dateBayar.setText(dateFormatter);
    }

    public void prosesTransaksi(){
        //REGISTER KE API
        profile = getSharedPreferences("AndroidExamplePref", Context.MODE_PRIVATE);
        String jenisTransaksi = "2";
        Integer id = profile.getInt("id",0);
        RequestBody nasabahId = RequestBody.create(MediaType.parse("text/plain"), id+"");
        RequestBody nominalTransaksi = RequestBody.create(MediaType.parse("text/plain"), nominal.getText().toString());
        RequestBody jenis_transaksi = RequestBody.create(MediaType.parse("text/plain"), jenisTransaksi.toString());
        Log.d("debug","file : "+fileToUpload+"name : "+filename);
        RetrofitClient.getClient(UtilsApi.BASE_URL_API)
                .create(BaseApiService.class)
                .transaksiProses(jenis_transaksi, nominalTransaksi, nasabahId, fileToUpload)
                .enqueue(new Callback<TransaksiResponse>() {
                    @Override
                    public void onResponse(Call<TransaksiResponse> call, Response<TransaksiResponse> response) {
                        if (response.isSuccessful()) {
                            loading.dismiss();
                            Toast.makeText(TransaksiPenarikanActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            loading.dismiss();
                            Toast.makeText(TransaksiPenarikanActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                    @Override
                    public void onFailure(Call<TransaksiResponse> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                        loading.dismiss();
                        Toast.makeText(TransaksiPenarikanActivity.this, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
