package com.example.sisteminformasikliniik.View.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.sisteminformasikliniik.Controller.LoginController;
import com.example.sisteminformasikliniik.DB.KlinikDatabase;
import com.example.sisteminformasikliniik.R;
import com.example.sisteminformasikliniik.View.Admin.DataMaster.DataMasterDokterActivity;
import com.example.sisteminformasikliniik.shared.Utility.LoadingDialog;
import com.example.sisteminformasikliniik.shared.Utility.ToastUtility;
import com.example.sisteminformasikliniik.shared.interfaces.IRegister;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity implements IRegister {
    private LoginController loginController;
    private Button btnRegister;
    private EditText edtUsername,edtNIK,edtNama,edtUsia,
            edtAlamat,edtStatusPekerjaan,edtNoHP;
    private TextInputEditText edtPassword;
    private RadioButton rbLaki,rbPerempuan;
    private KlinikDatabase klinikDatabase;
    private LoadingDialog loadingDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        klinikDatabase = KlinikDatabase.getKlinikDatabase(getApplicationContext());
        loadingDialog = new LoadingDialog(RegisterActivity.this);
        loginController = new LoginController(this);
        loginController.setDialog(loadingDialog);
        loginController.setKlinikDatabase(klinikDatabase);
        Toolbar tbRegist = findViewById(R.id.toolbar);
        tbRegist.setTitle("Daftar Akun");
        setSupportActionBar(tbRegist);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        initView();
        initButton();
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
        btnRegister = findViewById(R.id.btnRegistrasi);
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

        btnRegister.setOnClickListener(v -> {
            if (edtPassword.getText().toString().length() < 6) {
                ToastUtility.showToast(RegisterActivity.this,"Maaf, password kurang dari 6 karakter.");
            }
            loginController.isSuccessRegister(edtUsername,edtPassword,edtNIK,edtNama,edtUsia,edtAlamat,edtStatusPekerjaan,edtNoHP);
        });

    }

    @Override
    public void onFailed(String error) {
        ToastUtility.showToast(this,error);
    }

    @Override
    public void onSuccessCheckData() {
        String gender="" ;
        if (rbLaki.isChecked()) {
            gender = rbLaki.getText().toString();
        } else {
            gender = rbPerempuan.getText().toString();
        }

        loginController.setRegister(
                edtUsername.getText().toString(),
                edtPassword.getText().toString(),
                gender,
                edtNIK.getText().toString(),
                edtNama.getText().toString(),edtUsia.getText().toString(),
                edtStatusPekerjaan.getText().toString(),
                edtNoHP.getText().toString(),
                edtAlamat.getText().toString()
        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onSuccessRegister() {
        ToastUtility.showToast(this,"Selamat Anda Berhasil Registrasi, Silahkan Login");
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
