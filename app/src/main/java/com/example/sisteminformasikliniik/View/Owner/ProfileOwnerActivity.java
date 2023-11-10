package com.example.sisteminformasikliniik.View.Owner;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.sisteminformasikliniik.Controller.UserController;
import com.example.sisteminformasikliniik.DB.DataPemilik.DataPemilikEntity;
import com.example.sisteminformasikliniik.DB.KlinikDatabase;
import com.example.sisteminformasikliniik.DB.DataPasien.UserEntity;
import com.example.sisteminformasikliniik.R;
import com.example.sisteminformasikliniik.shared.Utility.LoadingDialog;
import com.example.sisteminformasikliniik.shared.Utility.ToastUtility;
import com.example.sisteminformasikliniik.shared.interfaces.profile.IProfile;
import com.example.sisteminformasikliniik.shared.interfaces.profile.IProfileOwner;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class ProfileOwnerActivity extends AppCompatActivity  implements IProfileOwner {

    private EditText edtUsername,edtNIK,edtNama,edtAlamat,edtNoHP;
    private RadioButton rbLaki,rbPerempuan;
    private Button btnUpdate;
    private TextInputEditText edtPassword;
    private KlinikDatabase klinikDatabase;
    private List<UserEntity> userEntityList;
    private String nikUser, userName;
    private LoadingDialog loadingDialog;
    private UserController userController;
    private int idUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_profile);
        loadingDialog = new LoadingDialog(ProfileOwnerActivity.this);
        userController = new UserController(getApplicationContext());
        userController.setDialog(loadingDialog);
        userController.setProfileOwner(this);
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
            userController.setUpdateProfileOwner(
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

    private void initContent(){
        userController.getDataOwnerByUsername(userName);
    }

    private void refreshData(List<DataPemilikEntity> dataPemilikEntityList){
        for (DataPemilikEntity entity: dataPemilikEntityList) {
            edtUsername.setEnabled(false);
            edtUsername.setText(entity.getUsername());
            edtPassword.setText(entity.getPassword());
            edtNIK.setText(entity.getNik());
            edtNama.setText(entity.getNamaPemilik());
            edtAlamat.setText(entity.getAlamat());
            edtNoHP.setText(entity.getNoHp());
        }
        loadingDialog.dismiss();
    }


    @Override
    public void onFailed(String message) {
        ToastUtility.showToast(this,message);
    }

    @Override
    public void showDataOwner(List<DataPemilikEntity> dataPemilikEntityList) {
        refreshData(dataPemilikEntityList);
    }

    @Override
    public void onSuccessUpdateDataOwner(List<DataPemilikEntity> dataPemilikEntityList) {
        ToastUtility.showToast(this,"Data Anda Berhasil Di Ubah");
        refreshData(dataPemilikEntityList);
    }
}
