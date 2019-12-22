package com.example.koperasiku.nasabah.TransaksiPenarikan;

import android.app.DatePickerDialog;
import android.content.Intent;
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
import android.widget.TextView;

import com.example.koperasiku.R;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TransaksiPenarikanActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnUpload;
    final int kodeGallery = 100;
    Uri gambarUri;
    TextView textBukti;

    private EditText dateBayar;
    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi_penarikan);

        btnUpload = (Button) findViewById(R.id.btnUpload);
        textBukti = (TextView) findViewById(R.id.textBukti);

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galery,kodeGallery);
            }
        });

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        findViewsById();

        setDateTimeField();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == kodeGallery && resultCode == RESULT_OK){
            gambarUri = data.getData();
            Log.d("URI :", String.valueOf(gambarUri));
            textBukti.setText("");
            btnUpload.setText(String.valueOf(gambarUri));
        }
    }

    private void findViewsById() {
        dateBayar = (EditText) findViewById(R.id.tanggal);
        dateBayar.setInputType(InputType.TYPE_NULL);
        dateBayar.requestFocus();
    }

    private void setDateTimeField() {
        dateBayar.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateBayar.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }


    @Override
    public void onClick(View view) {
        fromDatePickerDialog.show();
    }
}
