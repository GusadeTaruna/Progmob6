package com.example.koperasiku.nasabah.TransaksiSetoran;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
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
import com.example.koperasiku.UserSessionManager;
import com.example.koperasiku.apihelper.BaseApiService;
import com.example.koperasiku.apihelper.RetrofitClient;
import com.example.koperasiku.apihelper.UtilsApi;
import com.example.koperasiku.model.karyawanAPIModel.DetailResponse;
import com.example.koperasiku.model.nasabahAPIModel.TransaksiResponse;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransaksiSetoranActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_transaksi_setoran);

        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnUpload = (Button) findViewById(R.id.btnUpload);
        textBukti = (TextView) findViewById(R.id.textBukti);
        nominal = (EditText) findViewById(R.id.nominal);


        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
                openGalleryIntent.setType("image/*");
                startActivityForResult(openGalleryIntent, REQUEST_GALLERY_CODE);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading = ProgressDialog.show(TransaksiSetoranActivity.this, null, "Harap Tunggu...", true, false);
                prosesTransaksi();
            }
        });

        dateFormatter = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
        dateBayar = (TextView) findViewById(R.id.tanggal);
        dateBayar.setText(dateFormatter);


//        findViewsById();
//        setDateTimeField();

    }

//    private void findViewsById() {
//        dateBayar = (EditText) findViewById(R.id.tanggal);
//        dateBayar.setInputType(InputType.TYPE_NULL);
//        dateBayar.requestFocus();
//    }

//    private void setDateTimeField() {
//        dateBayar.setOnClickListener(this);
//
//        Calendar newCalendar = Calendar.getInstance();
//        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
//
//            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                Calendar newDate = Calendar.getInstance();
//                newDate.set(year, monthOfYear, dayOfMonth);
//                dateBayar.setText(dateFormatter.format(newDate.getTime()));
//            }
//
//        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
//
//    }
//
//    @Override
//    public void onClick(View view) {
//        fromDatePickerDialog.show();
//    }

    public void prosesTransaksi(){
        //REGISTER KE API
        profile = getSharedPreferences("AndroidExamplePref", Context.MODE_PRIVATE);
        String jenisTransaksi = "1";
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
                            Toast.makeText(TransaksiSetoranActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            loading.dismiss();
                            Toast.makeText(TransaksiSetoranActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                    @Override
                    public void onFailure(Call<TransaksiResponse> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                        loading.dismiss();
                        Toast.makeText(TransaksiSetoranActivity.this, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_GALLERY_CODE && resultCode == RESULT_OK){
            gambarUri = data.getData();
            Log.d("URI :", String.valueOf(gambarUri));
            textBukti.setText("");
//            btnUpload.setText(String.valueOf(gambarUri));
            String filePath = getRealPathFromURIPath(gambarUri, TransaksiSetoranActivity.this);
            File file = new File(filePath);
            Log.e("tag", "Filename " + file.getName());
            RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
            fileToUpload = MultipartBody.Part.createFormData("buktiUpload", file.getName(), mFile);
            filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
            Log.e("debug","file : "+fileToUpload+"name : "+filename);
        }
    }

    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

}