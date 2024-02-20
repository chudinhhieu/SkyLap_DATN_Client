package com.example.skylap_datn_md03.data.models;

public class HangSX {
    private String _id;
    private String tenHangSx;
    private boolean trangThai;

    public HangSX() {
    }

    public HangSX(String tenHangSx, boolean trangThai) {
        this.tenHangSx = tenHangSx;
        this.trangThai = trangThai;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTenHangSx() {
        return tenHangSx;
    }

    public void setTenHangSx(String tenHangSx) {
        this.tenHangSx = tenHangSx;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }
}
