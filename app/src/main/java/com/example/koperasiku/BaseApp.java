package com.example.koperasiku;

import android.app.Application;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

public class BaseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //konfigurasi Realm
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("myrealm.realm")
                .schemaVersion(0) // versi schema database yg digunakan
                .migration(new DataMigration())
                .build();

        Realm.setDefaultConfiguration(config);

    }

    private class DataMigration implements RealmMigration {
        @Override
        public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

            //mengakses schema database untuk tujuan membuat, mengubah atau menghapus kelas dan kolom.
            RealmSchema schema = realm.getSchema();

            //jika versi 0 maka buat schema baru
            if (oldVersion == 0) {
                // buat kelas buku
                schema.create("karyawan")
                        .addField("id", int.class)
                        .addField("name", String.class)
                        .addField("email", String.class)
                        .addField("password", String.class);
                oldVersion++;
            }
        }
    }
}
