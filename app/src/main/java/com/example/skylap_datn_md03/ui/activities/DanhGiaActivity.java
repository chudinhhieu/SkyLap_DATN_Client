package com.example.skylap_datn_md03.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.skylap_datn_md03.R;
import com.github.dhaval2404.imagepicker.ImagePicker;

public class DanhGiaActivity extends AppCompatActivity {
    private RatingBar rtbRate;
    private TextView tvDanhgia;
    private Button btnGuidg;
    private ImageView imgAddphoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_gia);

        rtbRate = findViewById(R.id.rtbRate);
        tvDanhgia = findViewById(R.id.tvDanhgia);
        btnGuidg = findViewById(R.id.btnGuidg);
        imgAddphoto = findViewById(R.id.imgAddphoto);

        rtbRate.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                switch ((int) rating){
                    case 1:
                        tvDanhgia.setText("Rất tệ");
                        break;
                    case 2:
                        tvDanhgia.setText("Tệ");
                        break;
                    case 3:
                        tvDanhgia.setText("Bình thường");
                        break;
                    case 4:
                        tvDanhgia.setText("Tốt");
                        break;
                    case 5:
                        tvDanhgia.setText("Tuyệt vời");
                        break;
                }
            }
        });

        btnGuidg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DanhGiaActivity.this, "Đánh giá của bạn đã được gửi", Toast.LENGTH_SHORT).show();
            }
        });

        imgAddphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(DanhGiaActivity.this)
                        .crop()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .start();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        imgAddphoto.setImageURI(uri);
    }
}