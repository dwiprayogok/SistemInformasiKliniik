package com.example.sisteminformasikliniik.View.User;

import android.os.Bundle;
import android.view.MenuItem;

import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.sisteminformasikliniik.Controller.UserController;
import com.example.sisteminformasikliniik.DB.DataPasien.UserEntity;
import com.example.sisteminformasikliniik.R;
import com.example.sisteminformasikliniik.shared.Utility.LoadingDialog;
import com.example.sisteminformasikliniik.shared.Utility.ToastUtility;
import com.example.sisteminformasikliniik.shared.interfaces.profile.IProfile;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity implements IProfile {

    private EditText edtUsername,edtNIK,edtNama,edtUsia,edtAlamat,edtStatusPekerjaan,edtNoHP;
    private RadioButton rbLaki,rbPerempuan;
    private Button btnUpdate;
    private TextInputEditText edtPassword;
    private List<UserEntity> userEntityList;
    private String nikUser, userName;
    private LoadingDialog loadingDialog;
    private UserController userController;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        loadingDialog = new LoadingDialog(ProfileActivity.this);
        userController = new UserController(getApplicationContext());
        userController.setiProfile(this);
        userController.setDialog(loadingDialog);
        Bundle bundle = getIntent().getExtras();
        nikUser = bundle.getString("nikUser", "");
        userName = bundle.getString("userName", "");
        Toolbar tbProfile = findViewById(R.id.toolbar);
        tbProfile.setTitle("My Profile");
        setSupportActionBar(tbProfile);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        userEntityList = new ArrayList<>();
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
        initView();
        initContent();
        initButton();
    }

    private void initButton(){

        rbLaki.setOnClickListener(v -> {
          rbPerempuan.setChecked(false);
          rbLaki.setChecked(true);
        });

        rbPerempuan.setOnClickListener(v -> {
            rbPerempuan.setChecked(true);
            rbLaki.setChecked(false);
        });


        btnUpdate.setOnClickListener(v -> {
            String gender="" ;
            if (rbLaki.isChecked()) {
                gender = rbLaki.getText().toString();
            } else {
                gender = rbPerempuan.getText().toString();
            }
            userController.setUpdateProfile(
                    edtUsername.getText().toString(),
                    edtPassword.getText().toString(),
                    gender,
                    edtNIK.getText().toString(),
                    edtNama.getText().toString(),edtUsia.getText().toString(),
                    edtStatusPekerjaan.getText().toString(),
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

    private void initView(){
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        edtNIK = findViewById(R.id.edtNIK);
        edtNama = findViewById(R.id.edtNama);
        edtUsia = findViewById(R.id.edtUsia);
        edtAlamat = findViewById(R.id.edtAlamat);
        edtStatusPekerjaan = findViewById(R.id.edtStatusPekerjaan);
        edtNoHP = findViewById(R.id.edtNoHP);
        rbLaki = findViewById(R.id.rbGenderLaki);
        rbPerempuan = findViewById(R.id.rbGenderPerempuan);
        btnUpdate = findViewById(R.id.btnUpdate);
    }

    private void initContent(){
        userController.getDataUserByUsername(userName);
    }

    @Override
    public void onFailed() {
        ToastUtility.showToast(this,"Maaf, Data Anda Gagal Di Ubah, Silahkan masukan informasi yang benar!");
    }

    @Override
    public void showData(List<UserEntity> userEntityList) {
        refreshData(userEntityList);
    }

    @Override
    public void onSuccessUpdateProfile(List<UserEntity> userEntityList) {
        ToastUtility.showToast(this,"Data Anda Berhasil Di Ubah");
        refreshData(userEntityList);
    }


    private void refreshData(List<UserEntity> userEntityList){
        for (UserEntity entity: userEntityList) {
            edtUsername.setEnabled(false);
            edtUsername.setText(entity.getUserName());
            edtPassword.setText(entity.getPassword());
            edtNIK.setText(entity.getNik());
            edtNama.setText(entity.getNama());
            edtUsia.setText(entity.getUsia());
            edtAlamat.setText(entity.getAlamat());
            edtStatusPekerjaan.setText(entity.getPekerjaan());
            edtNoHP.setText(entity.getNoHp());
            if (entity.getGender().contains("Laki - laki")) {
                rbLaki.setChecked(true);
            } else {
                rbPerempuan.setChecked(true);
            }
        }
        loadingDialog.dismiss();
    }

}
