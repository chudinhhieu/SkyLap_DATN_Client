package com.example.skylap_datn_md03.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.skylap_datn_md03.R;
import com.example.skylap_datn_md03.data.models.BienThe;
import com.example.skylap_datn_md03.data.models.SanPham;

public class ChiTietSanPhamActivity extends AppCompatActivity {
    private SanPham sanPham;
    private BienThe bienThe;
    private ImageView imgBack;
    private TextView tvCongNgheCPU, tvSoNhan, tvSoLuong, tvTocDoCPU, tvTocDoToiDa, tvBoNhoDem;
    private TextView tvRam, tvLoaiRam, tvTocDoBusRam, tvHoTroRamToiDa, tvOCung;
    private TextView tvManHinh, tvDoPhanGiai, tvTanSoQuet, tvDoPhuMau, tvCongNgheManHinh;
    private TextView tvCardManHinh, tvCongNgheAmThanh;

    private TextView tvCongGiaoTiep, tvKetNoiKhongDay, tvWebCam, tvDenBanPhim, tvTinhNangKhac;

    private TextView tvKhoiLuongKichThuoc, tvChatLieu, tvMauSac;

    private TextView tvThongTinPin, tvCongSuatBoSac, tvHeDieuHanh, tvBaoHanh, tvThoiDiemRaMat, tvPhuKien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("SanPham")) {
                sanPham = (SanPham) intent.getSerializableExtra("SanPham");
            }
            if (intent.hasExtra("BienThe")) {
                bienThe = (BienThe) intent.getSerializableExtra("BienThe");
            }
        }
        initView();
        fillDataToView(sanPham);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void fillDataToView(SanPham sanPham) {
        // Kiểm tra nếu đối tượng SanPham không null
        if (sanPham != null) {
            // Fill dữ liệu vào TextViews
            tvCongNgheCPU.setText(sanPham.getCpu());
            tvSoNhan.setText(String.valueOf(sanPham.getSoNhan()));
            tvSoLuong.setText(String.valueOf(sanPham.getSoLuongCPU()));
            tvTocDoCPU.setText(sanPham.getTocDoCPU());
            tvTocDoToiDa.setText(sanPham.getTocDoToiDa());
            tvBoNhoDem.setText(sanPham.getBoNhoDem());

            tvRam.setText(bienThe.getRam());
            tvLoaiRam.setText(sanPham.getLoaiRam());
            tvTocDoBusRam.setText(sanPham.getTocDoBusRam());
            tvHoTroRamToiDa.setText(sanPham.getHoTroRamToiDa());
            tvOCung.setText(bienThe.getRom());

            tvManHinh.setText(sanPham.getDisplay());
            tvDoPhanGiai.setText(sanPham.getDoPhanGiai());
            tvTanSoQuet.setText(sanPham.getTanSoQuet());
            tvDoPhuMau.setText(sanPham.getDoPhuMau());
            tvCongNgheManHinh.setText(sanPham.getCongNgheManHinh());

            tvCardManHinh.setText(sanPham.getGpu());
            tvCongNgheAmThanh.setText(sanPham.getCongNgheAmThanh());

            tvCongGiaoTiep.setText(sanPham.getCongGiaoTiep());
            tvKetNoiKhongDay.setText(sanPham.getKetNoiKhongDay());
            tvWebCam.setText(sanPham.getWebCam());
            tvDenBanPhim.setText(sanPham.getDenBanPhim());
            tvTinhNangKhac.setText(sanPham.getTinhNangKhac());

            tvKhoiLuongKichThuoc.setText(sanPham.getKichThuocKhoiLuong());
            tvChatLieu.setText(sanPham.getChatLieu());
            tvMauSac.setText(sanPham.getMauSac());

            tvThongTinPin.setText(sanPham.getPin());
            tvCongSuatBoSac.setText(sanPham.getCongSuatSac());
            tvHeDieuHanh.setText(sanPham.getOs());
            tvBaoHanh.setText(sanPham.getBaohanh());
            tvThoiDiemRaMat.setText(sanPham.getThoiDiemRaMat());
            tvPhuKien.setText(sanPham.getPhuKien());
        }
    }


    private void initView() {
        imgBack = findViewById(R.id.actsp_img_back);

        tvCongNgheCPU = findViewById(R.id.actsp_tv_cncpu);
        tvSoNhan = findViewById(R.id.actsp_tv_sn);
        tvSoLuong = findViewById(R.id.actsp_tv_sl);
        tvTocDoCPU = findViewById(R.id.actsp_tv_tdcpu);
        tvTocDoToiDa = findViewById(R.id.actsp_tv_tdtd);
        tvBoNhoDem = findViewById(R.id.actsp_tv_bnd);

        tvRam = findViewById(R.id.actsp_tv_ram);
        tvLoaiRam = findViewById(R.id.actsp_tv_lr);
        tvTocDoBusRam = findViewById(R.id.actsp_tv_tdbr);
        tvHoTroRamToiDa = findViewById(R.id.actsp_tv_htrtd);
        tvOCung = findViewById(R.id.actsp_tv_oc);

        tvManHinh = findViewById(R.id.actsp_tv_mh);
        tvDoPhanGiai = findViewById(R.id.actsp_tv_dpg);
        tvTanSoQuet = findViewById(R.id.actsp_tv_tsq);
        tvDoPhuMau = findViewById(R.id.actsp_tv_dpm);
        tvCongNgheManHinh = findViewById(R.id.actsp_tv_cnmh);

        tvCardManHinh = findViewById(R.id.actsp_tv_cmh);
        tvCongNgheAmThanh = findViewById(R.id.actsp_tv_cnat);

        tvCongGiaoTiep = findViewById(R.id.actsp_tv_cgt);
        tvKetNoiKhongDay = findViewById(R.id.actsp_tv_knkd);
        tvWebCam = findViewById(R.id.actsp_tv_wc);
        tvDenBanPhim = findViewById(R.id.actsp_tv_dbp);
        tvTinhNangKhac = findViewById(R.id.actsp_tv_tnk);

        tvKhoiLuongKichThuoc = findViewById(R.id.actsp_tv_ktkl);
        tvChatLieu = findViewById(R.id.actsp_tv_cl);
        tvMauSac = findViewById(R.id.actsp_tv_ms);

        tvThongTinPin = findViewById(R.id.actsp_tv_ttp);
        tvCongSuatBoSac = findViewById(R.id.actsp_tv_csbs);
        tvHeDieuHanh = findViewById(R.id.actsp_tv_hdh);
        tvBaoHanh = findViewById(R.id.actsp_tv_bh);
        tvThoiDiemRaMat = findViewById(R.id.actsp_tv_tdrm);
        tvPhuKien = findViewById(R.id.actsp_tv_pk);


    }
}