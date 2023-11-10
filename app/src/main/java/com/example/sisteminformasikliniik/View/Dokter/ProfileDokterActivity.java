package com.example.sisteminformasikliniik.View.Dokter;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.sisteminformasikliniik.Controller.UserController;
import com.example.sisteminformasikliniik.DB.DataDokter.DokterEntity;
import com.example.sisteminformasikliniik.DB.DataPasien.UserEntity;
import com.example.sisteminformasikliniik.R;
import com.example.sisteminformasikliniik.shared.Utility.LoadingDialog;
import com.example.sisteminformasikliniik.shared.Utility.ToastUtility;
import com.example.sisteminformasikliniik.shared.interfaces.profile.IProfileDokter;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class ProfileDokterActivity extends AppCompatActivity  implements IProfileDokter {

    private EditText edtUsername,edtNIK,edtNama,edtAlamat,edtNoHP;
    private Button btnUpdate;
    private TextInputEditText edtPassword;
    private List<UserEntity> userEntityList;
    private String nikUser, userName;
    private LoadingDialog loadingDialog;
    private UserController userController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_profile);
        loadingDialog = new LoadingDialog(ProfileDokterActivity.this);
        userController = new UserController(getApplicationContext());
        userController.setDialog(loadingDialog);
        userController.setiProfileDokter(this);
        Bundle bundle = getIntent().getExtras();
        nikUser = bundle.getString("nikUser", "");
        userName = bundle.getString("userName", "");
        Toolbar tbProfile = findViewById(R.id.toolbar);
        tbProfile.setTitle("My Profile");
        setSupportActionBar(tbProfile);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        edtUsername = findViewById(R.id.edtUsername);
        edtNIK = findViewById(R.id.edtNIK);
        edtPassword = findViewById(R.id.edtPassword);
        edtNama = findViewById(R.id.edtNama);
        edtAlamat = findViewById(R.id.edtAlamat);
        edtNoHP = findViewById(R.id.edtNoHp);
        btnUpdate = findViewById(R.id.btnUpdate);

        userEntityList = new ArrayList<>();
        initContent();
        initButton();
    }

    private void initButton(){
        btnUpdate.setOnClickListener(v -> {
            userController.setUpdateProfileDokter(
                    edtUsername.getText().toString(),
                    edtPassword.getText().toString(),
                    edtNIK.getText().toString(),
                    edtNama.getText().toString(),
                    edtNoHP.getText().toString(),
                    edtAlamat.getText().toString()
            );
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initContent() {
        userController.getDataDokterByUsername(userName);
    }

    private void refreshData(List<DokterEntity> dokterEntityList){
        for (DokterEntity entity: dokterEntityList) {
            edtUsername.setEnabled(false);
            edtUsername.setText(entity.getUsername());
            edtPassword.setText(entity.getPassword());
            edtNIK.setText(entity.getNikDokter());
            edtNama.setText(entity.getNamaDokter());
            edtAlamat.setText(entity.getAlamat());
            edtNoHP.setText(entity.getNoHp());
        }
    }

    @Override
    public void onFailed(String message) {
        ToastUtility.showToast(this,message);
    }

    @Override
    public void showDataDokter(List<DokterEntity> dokterEntityList) {
        refreshData(dokterEntityList);

    }

    @Override
    public void onSuccessUpdateProfileDokter(List<DokterEntity> dokterEntityList) {
        ToastUtility.showToast(this,"Data Anda Berhasil Di Ubah");
        refreshData(dokterEntityList);
    }
}
