package com.example.sisteminformasikliniik.shared.Utility;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.sisteminformasikliniik.R;

public class LoadingDialog extends Dialog {
    private static LoadingDialog instance;
    public Boolean AutoClose = false;
    private Dialog dialog;
    public LoadingDialog(@NonNull Context context) {
        super(context);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.dialog = this;
        this.requestWindowFeature(1);
        this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        this.setContentView(R.layout.progress_bar_dialog);
        this.setCanceledOnTouchOutside(false);
        this.setCancelable(false);
    }
    private static void getInstance(Context context){
        if(null == instance){
            instance = new LoadingDialog(context);
        }
    }
    public static void showDialog(Context context){
        if(null == instance){
            getInstance(context);
        }
        instance.show();
    }
    public static void dismissDialog(){
        if(null != instance && instance.isShowing()){
            instance.dismiss();
        }
        instance = null;
    }

    public void setAutoClose(Boolean autoClose) {
        this.AutoClose = autoClose;
    }

    public void showDialogOnUIThread() {
        LoadingDialog.this.dialog.show();
    }
}

