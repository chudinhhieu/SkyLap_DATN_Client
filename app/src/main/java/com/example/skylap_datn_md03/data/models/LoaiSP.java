package com.example.skylap_datn_md03.data.models;

public class LoaiSP {
    private String _id;
    private String tenLoai;
    private boolean trangThai;

    public LoaiSP() {
    }

    public LoaiSP(String tenLoai, boolean trangThai) {
        this.tenLoai = tenLoai;
        this.trangThai = trangThai;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }
}
