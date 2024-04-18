package com.example.skylap_datn_md03.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.ui.activities.auth.LoginActivity;

public class CheckDialog {
    public static void showCheckDialog(Context context, String title, String message) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_check);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView tvNoiDung = dialog.findViewById(R.id.dal_noiDung);
        TextView tvTieuDe = dialog.findViewById(R.id.dal_tieuDe);
        Button btnHuy = dialog.findViewById(R.id.dal_btnHuy);
        Button btnOk = dialog.findViewById(R.id.dal_btnOk);
        tvTieuDe.setText(title);
        tvNoiDung.setText(message);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                context.startActivity(new Intent(context, LoginActivity.class));
            }
        });
        dialog.show();
    }
}
