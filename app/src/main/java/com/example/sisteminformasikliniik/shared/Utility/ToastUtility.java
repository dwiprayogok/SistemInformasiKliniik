package com.example.sisteminformasikliniik.shared.Utility;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sisteminformasikliniik.R;

public class ToastUtility {

    public static void showToast(Activity activity, String message) {
        LayoutInflater inflater =  activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) activity.findViewById(R.id.custom_toast_layout));
        TextView tv = (TextView) layout.findViewById(R.id.textviewToast);
        tv.setText(message);
        Toast toast = new Toast(activity);
        toast.setGravity(Gravity.BOTTOM, 0, 100);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}
