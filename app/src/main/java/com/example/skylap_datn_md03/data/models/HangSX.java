package com.example.skylap_datn_md03.data.models;

public class HangSX {
    private String _id;
    private String tenHangSx;
    private String imageLogo;
    private boolean trangThai;

    public HangSX() {
    }


    public HangSX(String _id, String tenHangSx, String logo, boolean trangThai) {
        this._id = _id;
        this.tenHangSx = tenHangSx;
        this.imageLogo = logo;
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

    public String getLogo() {
        return imageLogo;
    }

    public void setLogo(String logo) {
        this.imageLogo = logo;
    }
}
