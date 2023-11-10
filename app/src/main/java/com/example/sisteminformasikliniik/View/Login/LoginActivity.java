package com.example.sisteminformasikliniik.View.Login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sisteminformasikliniik.Controller.LoginController;
import com.example.sisteminformasikliniik.DB.DataLogin.DataLoginEntitiy;
import com.example.sisteminformasikliniik.DB.KlinikDatabase;
import com.example.sisteminformasikliniik.R;
import com.example.sisteminformasikliniik.shared.Utility.LoadingDialog;
import com.example.sisteminformasikliniik.shared.Utility.PrefManager;
import com.example.sisteminformasikliniik.shared.Utility.ToastUtility;
import com.example.sisteminformasikliniik.shared.interfaces.ILogin;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements ILogin {

    private LoginController loginController;
    private EditText edtPassword,edtUsername;
    private Button btnLogin;
    private TextView tvRegister;
    private PrefManager prefManager;
    private LoadingDialog loadingDialog;
    private KlinikDatabase klinikDatabase;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        loginController = new LoginController(this);
        loadingDialog = new LoadingDialog(LoginActivity.this);
        klinikDatabase = KlinikDatabase.getKlinikDatabase(getApplicationContext());
        loginController.setKlinikDatabase(klinikDatabase);
        loginController.setDialog(loadingDialog);
        initView();
        checkLogin();
    }

    private void checkLogin(){
        if (prefManager.isLogin()) {
            Intent intent = new Intent(this, HomeActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("nama",prefManager.getName());
            bundle.putString("userName",prefManager.getUsername());
            bundle.putString("roleUser",prefManager.getRoleUser());
            bundle.putString("nikUser",prefManager.getNikUser());
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }
    }

    private void  initView(){
        prefManager = new PrefManager(getApplicationContext());
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);
        initButton();
    }

    private void initButton() {

        btnLogin.setOnClickListener(v -> {
            loginController.isSuccessLogin(getApplicationContext(),edtUsername.getText().toString(),edtPassword.getText().toString());
        });

        tvRegister.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onUserAndPasswordEmpty() {
        ToastUtility.showToast(this,"Harap Isi Username dan Password");
        loginController.clearField(edtUsername,edtPassword);
        edtUsername.requestFocus();
    }

    @Override
    public void onPasswordEmpty() {
        ToastUtility.showToast(this,"Maaf, Password anda kurang dari 6 karakter!");
        edtPassword.setText("");
        edtPassword.requestFocus();
    }

    @Override
    public void onSuccessLogin(List<DataLoginEntitiy> dataLoginEntitiyList) {
        for(DataLoginEntitiy entity : dataLoginEntitiyList) {
            ToastUtility.showToast(this,"Selamat Anda Berhasil Login");
            prefManager.setLoggin(true);
            prefManager.setUsername(entity.getUsername());
            prefManager.setName(entity.getNama());
            prefManager.setRoleUser(entity.getRole());
            prefManager.setNikUser(entity.getNik());
            Intent intent = new Intent(this,HomeActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("nama",entity.getNama());
            bundle.putString("userName",entity.getUsername());
            bundle.putString("roleUser",entity.getRole());
            bundle.putString("nikUser",entity.getNik());
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void onFailedLogin() {
        ToastUtility.showToast(this,"Maaf, Informasi yang Anda masukkan salah !");
        loginController.clearField(edtUsername,edtPassword);
        edtUsername.requestFocus();
    }

    @Override
    public void onSuccessCheckData() {
        loginController.setLoginUser(edtUsername.getText().toString(),edtPassword.getText().toString());
    }
}
