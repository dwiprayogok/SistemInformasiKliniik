package com.example.sisteminformasikliniik.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.sisteminformasikliniik.DB.DataDokter.DokterDao;
import com.example.sisteminformasikliniik.DB.DataDokter.DokterEntity;
import com.example.sisteminformasikliniik.DB.DataLogin.DataLoginDao;
import com.example.sisteminformasikliniik.DB.DataLogin.DataLoginEntitiy;
import com.example.sisteminformasikliniik.DB.DataObat.DataObatEntity;
import com.example.sisteminformasikliniik.DB.DataObat.ObatDao;
import com.example.sisteminformasikliniik.DB.DataPelayanan.DataPelayananEntity;
import com.example.sisteminformasikliniik.DB.DataPelayanan.PelayananDao;
import com.example.sisteminformasikliniik.DB.DataPemilik.DataPemilikEntity;
import com.example.sisteminformasikliniik.DB.DataPemilik.PemilikDao;
import com.example.sisteminformasikliniik.DB.DataPendaftaran.DataPendaftaranEntity;
import com.example.sisteminformasikliniik.DB.DataPendaftaran.PendaftaranDao;
import com.example.sisteminformasikliniik.DB.Pembayaran.PembayaranDao;
import com.example.sisteminformasikliniik.DB.Pembayaran.PembayaranEntity;
import com.example.sisteminformasikliniik.DB.RekamMedis.RekamMedisDao;
import com.example.sisteminformasikliniik.DB.RekamMedis.RekamMedisEntity;
import com.example.sisteminformasikliniik.DB.DataPasien.UserDao;
import com.example.sisteminformasikliniik.DB.DataPasien.UserEntity;

@Database(entities = {
        UserEntity.class,
        DokterEntity.class,
        DataObatEntity.class,
        DataPelayananEntity.class,
        DataPendaftaranEntity.class,
        RekamMedisEntity.class,
        PembayaranEntity.class,
        DataPemilikEntity.class,
        DataLoginEntitiy.class
},version = 14, exportSchema = false)
public abstract class KlinikDatabase extends RoomDatabase {
    private static final String dbName = "klinik";
    private static KlinikDatabase klinikDatabase;

    public static synchronized  KlinikDatabase getKlinikDatabase(Context context){
        if (klinikDatabase == null) {
            klinikDatabase = Room.databaseBuilder(context,KlinikDatabase.class,dbName)
                    .allowMainThreadQueries()
                    .build();
        }
        return klinikDatabase;
    }


    public abstract UserDao userDao();

    public abstract DokterDao dokterDao();

    public abstract ObatDao obatDao();

    public abstract PelayananDao pelayananDao();

    public abstract PendaftaranDao pendaftaranDao();

    public abstract RekamMedisDao rekamMedisDao();

    public abstract PembayaranDao pembayaranDao();

    public abstract PemilikDao pemilikDao();

    public abstract DataLoginDao dataLoginDao();
}
