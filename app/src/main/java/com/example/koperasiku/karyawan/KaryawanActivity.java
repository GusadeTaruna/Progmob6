package com.example.koperasiku.karyawan;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.koperasiku.R;
import com.example.koperasiku.adapter.KaryawanAdapter;
import com.example.koperasiku.apihelper.BaseApiService;
import com.example.koperasiku.apihelper.UtilsApi;
import com.example.koperasiku.model.KaryawanModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KaryawanActivity extends AppCompatActivity {

    private static KaryawanActivity mInstance = null;
    private FloatingActionButton fabAddKaryawan;
    private ListView lvDaftarKaryawan;
    private EditText inputSearch,nama_karyawan,email_karyawan,pass_karyawan,konfirmasi;
    private AlertDialog.Builder dialogHandler;
    private Realm dbRealm;
    private ArrayList<KaryawanModel> KaryawanArrayList = new ArrayList<KaryawanModel>();
    private KaryawanAdapter listKaryawanAdapter;
    ProgressDialog loading;
    Context mContext;
    BaseApiService mApiService;


    public static KaryawanActivity getInstance() {
        return mInstance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nasabah);

        mInstance = this;
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        lvDaftarKaryawan = (ListView) findViewById(R.id.lvKaryawan);
        inputSearch = (EditText) findViewById(R.id.inputSearch);

        mContext = this;
        mApiService = UtilsApi.getAPIService();

        initComponents();

        RealmConfiguration config = new RealmConfiguration.Builder().name("myrealm.realm").build();
        dbRealm = Realm.getInstance(config);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TambahKaryawanDialog(null, -1);
            }
        });

        lvDaftarKaryawan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(KaryawanActivity.this,DetailKaryawanActivity.class);
                intent.putExtra("KaryawanID", KaryawanArrayList.get(i).getId());
                startActivity(intent);
            }
        });

        listKaryawanAdapter = new KaryawanAdapter(this,KaryawanArrayList);
        lvDaftarKaryawan.setAdapter(listKaryawanAdapter);

        readDataKaryawan();

    }

    private void initComponents() {
        nama_karyawan = (EditText) findViewById(R.id.etNamaKaryawan);
        email_karyawan = (EditText) findViewById(R.id.etEmail);
        pass_karyawan = (EditText) findViewById(R.id.etPass);
        konfirmasi = (EditText) findViewById(R.id.etConfirmPass);

    }


    public void TambahKaryawanDialog(final KaryawanModel model, final int posisiIndex) {

        LayoutInflater li = LayoutInflater.from(KaryawanActivity.this);
        View promptsView = li.inflate(R.layout.karyawan_dialog, null);
        AlertDialog.Builder mainDialog = new AlertDialog.Builder(KaryawanActivity.this);
        mainDialog.setTitle("Isi data diri dibawah");
        mainDialog.setView(promptsView);

        final EditText etNamaKaryawan = (EditText) promptsView.findViewById(R.id.etNamaKaryawan);
        final EditText etEmail = (EditText) promptsView.findViewById(R.id.etEmail);
        final EditText etPass = (EditText) promptsView.findViewById(R.id.etPass);

        if (model != null) {
            etNamaKaryawan.setText(model.getName());
            etEmail.setText(model.getEmail());
        }

        mainDialog.setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!etNamaKaryawan.getText().toString().trim().equals("") && !etEmail.getText().toString().trim().equals("") && !etPass.getText().toString().trim().equals("")) {

                            KaryawanModel karyawanModel = new KaryawanModel();

                            karyawanModel.setName(etNamaKaryawan.getText().toString());
                            karyawanModel.setEmail(etEmail.getText().toString());
                            karyawanModel.setPassword(etPass.getText().toString());

                            if (model == null) {
                                loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
                                regisKaryawanDB(karyawanModel);
//                                createDataKaryawan(karyawanModel);

                            } else {
                                updateDataKaryawan(karyawanModel, posisiIndex, model.getId());
                            }
                            dialog.cancel();
                        } else {
                            dialogHandler = new AlertDialog.Builder(KaryawanActivity.this)
                                    .setMessage("Silahkan isi datanya!")
                                    .setCancelable(false)
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                            dialogHandler.show();
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        final AlertDialog dialog = mainDialog.create();
        dialog.show();
    }

//    private void createDataKaryawan(KaryawanModel model) {
//
//        dbRealm.beginTransaction();
//        Number currentIdNum = dbRealm.where(KaryawanModel.class).max("id");
//        int nextId;
//        if (currentIdNum == null){
//            nextId = 1;
//        }else {
//            nextId = currentIdNum.intValue() + 1;
//        }
//
//        KaryawanModel karyawanModel = dbRealm.createObject(KaryawanModel.class,nextId);
////        int id = dbRealm.where(KaryawanModel.class).max("id").intValue() + 1;
////        karyawanModel.setId(id);
//        karyawanModel.setName(model.getName());
//        karyawanModel.setEmail(model.getEmail());
//        karyawanModel.setPassword(model.getPassword());
//        dbRealm.commitTransaction();
//        KaryawanArrayList.add(karyawanModel);
//        listKaryawanAdapter.notifyDataSetChanged();
//    }

    private void readDataKaryawan() {
        RealmResults<KaryawanModel> hasil = dbRealm.where(KaryawanModel.class).findAll();
        dbRealm.beginTransaction();
        if(!hasil.isEmpty()) {
            KaryawanArrayList.addAll(hasil);
            listKaryawanAdapter.notifyDataSetChanged();
        }
        dbRealm.commitTransaction();

    }

    private void updateDataKaryawan(KaryawanModel model, int posisiIndex, int id) {
        KaryawanModel editKaryawanModel = dbRealm.where(KaryawanModel.class).equalTo("id", id).findFirst();
        dbRealm.beginTransaction();
        editKaryawanModel.setName(model.getName());
        editKaryawanModel.setEmail(model.getEmail());
        dbRealm.commitTransaction();
        KaryawanArrayList.set(posisiIndex,editKaryawanModel);
        listKaryawanAdapter.notifyDataSetChanged();

    }

    public void deleteDataKaryawan(int dataID, int posisiIndex) {
        RealmResults<KaryawanModel> hasil = dbRealm.where(KaryawanModel.class).equalTo("id", dataID).findAll();
        dbRealm.beginTransaction();
        hasil.deleteFromRealm(0);
        dbRealm.commitTransaction();
        KaryawanArrayList.remove(posisiIndex);
        listKaryawanAdapter.notifyDataSetChanged();
    }

    public KaryawanModel searchKaryawan(int id) {
        RealmResults<KaryawanModel> hasil = dbRealm.where(KaryawanModel.class).equalTo("id", id).findAll();
        dbRealm.beginTransaction();
        dbRealm.commitTransaction();
        return hasil.first();
    }

    public void regisKaryawanDB(final KaryawanModel model){
        //REGISTER KE API
        mApiService.registerKaryawan(model.getName(),
        model.getEmail(),
        model.getPassword(),
        model.getPassword())
        .enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    Log.i("debug", "onResponse: BERHASIL");
                    loading.dismiss();
                    try {
                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                        if (jsonRESULTS.getString("error").equals("false")){
                            Toast.makeText(mContext, "Registrasi Karyawan Berhasil", Toast.LENGTH_SHORT).show();

                            dbRealm.beginTransaction();
                            Number currentIdNum = dbRealm.where(KaryawanModel.class).max("id");
                            int nextId;
                            if (currentIdNum == null){
                                nextId = 1;
                            }else {
                                nextId = currentIdNum.intValue() + 1;
                            }

                            KaryawanModel karyawanModel = dbRealm.createObject(KaryawanModel.class,nextId);
                            //        int id = dbRealm.where(KaryawanModel.class).max("id").intValue() + 1;
                            //        karyawanModel.setId(id);
                            karyawanModel.setName(model.getName());
                            karyawanModel.setEmail(model.getEmail());
                            karyawanModel.setPassword(model.getPassword());
                            dbRealm.commitTransaction();
                            KaryawanArrayList.add(karyawanModel);
                            listKaryawanAdapter.notifyDataSetChanged();
                        } else {
                            String error_message = jsonRESULTS.getString("error_msg");
                            Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.i("debug", "onResponse: GA BERHASIL");
                    Toast.makeText(mContext, "Tambah Karyawan Gagal", Toast.LENGTH_SHORT).show();
                    loading.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                loading.dismiss();
                Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbRealm.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
