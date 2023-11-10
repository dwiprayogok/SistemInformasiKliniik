package com.example.sisteminformasikliniik.View.Login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.sisteminformasikliniik.Adapter.BaseMenuAdapter;
import com.example.sisteminformasikliniik.Model.BaseMenu;
import com.example.sisteminformasikliniik.R;
import com.example.sisteminformasikliniik.View.Admin.DataMaster.DataMasterActivity;
import com.example.sisteminformasikliniik.View.Admin.KonfirmasiPasien.KonfirmasiPasienActivity;
import com.example.sisteminformasikliniik.View.Admin.Transaksi.MenuTransaksiActivity;
import com.example.sisteminformasikliniik.View.Dokter.ProfileDokterActivity;
import com.example.sisteminformasikliniik.View.Dokter.RekamMedisActivity;
import com.example.sisteminformasikliniik.View.Owner.LaporanActivity;
import com.example.sisteminformasikliniik.View.Owner.ProfileOwnerActivity;
import com.example.sisteminformasikliniik.View.User.DaftarBerobatActivity;
import com.example.sisteminformasikliniik.View.User.KartuBerobatActivity;
import com.example.sisteminformasikliniik.View.User.ProfileActivity;
import com.example.sisteminformasikliniik.shared.Utility.PrefManager;
import com.example.sisteminformasikliniik.shared.Utility.ToastUtility;
import com.example.sisteminformasikliniik.transfer.messaging.EventbusProfileDokter;
import com.example.sisteminformasikliniik.transfer.messaging.EventbusProfileOwner;
import com.example.sisteminformasikliniik.transfer.messaging.EventbusProfileUser;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class HomeActivity extends AppCompatActivity implements BaseMenuAdapter.BaseMenuClicked {

    private GridView gridView ;
    private BaseMenuAdapter baseMenuAdapter;
    private List<BaseMenu> itemsList = new ArrayList<>();
    private String nama, userName,roleUser,nikUser;
    private TextView tvWelcome;
    private ImageView imgLogOut;
    private PrefManager prefManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        Bundle bundle = getIntent().getExtras();
        nama = bundle.getString("nama", "");
        userName = bundle.getString("userName", "");
        roleUser = bundle.getString("roleUser", "");
        nikUser = bundle.getString("nikUser","");
        registerEventBus();
        initView();
        checkLogin();
    }

    private void registerEventBus() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    private void initView(){
        prefManager = new PrefManager(getApplicationContext());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tvWelcome = findViewById(R.id.tvWelcome);
        imgLogOut = findViewById(R.id.imgLogout);
        gridView = findViewById(R.id.gvMenu);
        tvWelcome.setText("Selamat Datang , " + nama);

        imgLogOut.setOnClickListener(v -> {
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setContentText("Apakah Anda yakin ingin Keluar?")
                    .setConfirmText("Ya")
                    .setCancelText("Tidak")
                    .setConfirmClickListener(sweetAlertDialog -> {
                        sweetAlertDialog.dismissWithAnimation();
                        prefManager.removeDate();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        EventBus.getDefault().unregister(this);
                        finish();
                    })
                    .setCancelClickListener(sweetAlertDialog -> sweetAlertDialog.dismissWithAnimation())
                    .show();
        });

        if (userName.contains("Admin") || roleUser.contains("administrator")) {
            itemsList.add(new BaseMenu("Data Master",R.drawable.data_master));
            itemsList.add(new BaseMenu("Konfirmasi Pasien",R.drawable.schedule));
            itemsList.add(new BaseMenu("Transaksi",R.drawable.ic_transacation_2));
        } else if(roleUser.contains("Dokter")){
            itemsList.add(new BaseMenu("Profile",R.drawable.account));
            itemsList.add(new BaseMenu("Rekam Medis",R.drawable.medical_card));
        } else if(roleUser.contains("Owner")){
            itemsList.add(new BaseMenu("Profile",R.drawable.account));
            itemsList.add(new BaseMenu("Laporan",R.drawable.ic_report_2));
        } else {
            itemsList.add(new BaseMenu("Profile",R.drawable.account));
            itemsList.add(new BaseMenu("Pendaftaran Berobat",R.drawable.daftar_berobat));
            itemsList.add(new BaseMenu("Kartu Berobat",R.drawable.medical_card));
        }
        baseMenuAdapter = new BaseMenuAdapter(this, R.layout.item_menu, itemsList);
        gridView.setAdapter(baseMenuAdapter);
        baseMenuAdapter.setBaseMenuClicked(this);
    }

    private void checkLogin(){
        if (prefManager.isLogin() == false) {
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onMenuClicked(BaseMenu baseMenu) {
        switch (baseMenu.getNameMenu()) {
            case "Data Master":
                Intent intent = new Intent(getApplicationContext(), DataMasterActivity.class);
                startActivity(intent);
                break;
            case "Pendaftaran Berobat":
                Intent intent2 = new Intent(getApplicationContext(), DaftarBerobatActivity.class);
                Bundle bundle2 = new Bundle();
                bundle2.putString("userName",userName);
                bundle2.putString("nikUser",nikUser);
                intent2.putExtras(bundle2);
                startActivity(intent2);
                break;
            case "Transaksi":
                Intent intent22 = new Intent(getApplicationContext(), MenuTransaksiActivity.class);
                startActivity(intent22);
                break;
            case "Profile":
                if (roleUser.equals("Dokter")) {
                    Intent intentDokter = new Intent(getApplicationContext(), ProfileDokterActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("userName",userName);
                    bundle.putString("nikUser",nikUser);
                    intentDokter.putExtras(bundle);
                    startActivity(intentDokter);
                } else if (roleUser.equals("Owner")) {
                    Intent intentOwner = new Intent(getApplicationContext(), ProfileOwnerActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("userName",userName);
                    bundle.putString("nikUser",nikUser);
                    intentOwner.putExtras(bundle);
                    startActivity(intentOwner);
                } else {
                    Intent intent3 = new Intent(getApplicationContext(), ProfileActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("userName",userName);
                    bundle.putString("nikUser",nikUser);
                    intent3.putExtras(bundle);
                    startActivity(intent3);
                }

                break;
            case "Konfirmasi Pasien":
                Intent intent221 = new Intent(getApplicationContext(), KonfirmasiPasienActivity.class);
                startActivity(intent221);
                break;
            case "Kartu Berobat":
                Intent kartu = new Intent(getApplicationContext(), KartuBerobatActivity.class);
                Bundle bundleKartu = new Bundle();
                bundleKartu.putString("userName",userName);
                bundleKartu.putString("nikUser",nikUser);
                kartu.putExtras(bundleKartu);
                startActivity(kartu);
                break;
            case "Rekam Medis":
                Intent Medis = new Intent(getApplicationContext(), RekamMedisActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userName",userName);
                bundle.putString("nikDokter",nikUser);
                Medis.putExtras(bundle);
                startActivity(Medis);
                break;
            case "Laporan":
                Intent laporan = new Intent(getApplicationContext(), LaporanActivity.class);
                startActivity(laporan);
                break;
        }
    }

    @Override
    public void onBackPressed() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onProfileUserUpdate(EventbusProfileUser profileUser) {
        try {
            tvWelcome.setText("Selamat Datang , " + profileUser.getUserEntityList().get(0).getNama());
        } catch (Exception e) {
            ToastUtility.showToast(this,e.getMessage());
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onProfileDokterUpdate(EventbusProfileDokter profileDokter) {
        try {
            tvWelcome.setText("Selamat Datang , " + profileDokter.getDokterEntityList().get(0).getNamaDokter());
        } catch (Exception e) {
            ToastUtility.showToast(this,e.getMessage());
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onProfileOwnerUpdate(EventbusProfileOwner profileOwner) {
        try {
            tvWelcome.setText("Selamat Datang , " + profileOwner.getDataPemilikEntityList().get(0).getNamaPemilik());
        } catch (Exception e) {
            ToastUtility.showToast(this,e.getMessage());
        }
    }



    @Override
    protected void onStart() {
        super.onStart();
        registerEventBus();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
