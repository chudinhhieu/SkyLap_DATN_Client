package com.example.skylap_datn_md03.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.ZaloPay.CreateOrder;

import org.json.JSONObject;

import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;


public class ThanhToanZaloPay extends AppCompatActivity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan_zalo_pay);
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // ZaloPay SDK Init
        ZaloPaySDK.init(2554, Environment.SANDBOX);
        button = findViewById(R.id.btn_zalopay);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateOrder orderApi = new CreateOrder();

                try {
                    JSONObject data = orderApi.createOrder("100000");
                    String code = data.getString("return_code");

                    if (code.equals("1")) {
                        String token = data.getString("zp_trans_token");
                        Toast.makeText(ThanhToanZaloPay.this, ""+token, Toast.LENGTH_SHORT).show();
                        ZaloPaySDK.getInstance().payOrder(ThanhToanZaloPay.this, token, "demozpdk://app", new PayOrderListener() {
                            @Override
                            public void onPaymentSucceeded(String s, String s1, String s2) {
                                Toast.makeText(ThanhToanZaloPay.this, "onPaymentSucceeded", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onPaymentCanceled(String s, String s1) {
                                Toast.makeText(ThanhToanZaloPay.this, "onPaymentCanceled", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onPaymentError(ZaloPayError zaloPayError, String s, String s1) {
                                Toast.makeText(ThanhToanZaloPay.this, "onPaymentError", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }
}
