package com.example.skylap_datn_md03.ui.dialogs;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skylap_datn_md03.R;

public class CustomToast {

    public static void showToast(Context context, String message) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_toast_layout, null);

        TextView textView = view.findViewById(R.id.custom_toast_text);
        textView.setText(message);

        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);

        toast.setView(view);
        toast.show();
    }
}
