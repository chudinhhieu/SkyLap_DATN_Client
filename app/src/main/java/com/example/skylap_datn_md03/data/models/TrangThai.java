package com.example.skylap_datn_md03.data.models;

public class TrangThai {
    private String  trangThai;
    private String isNow, thoiGian;

    public TrangThai(String trangThai, String isNow, String thoiGian) {
        this.trangThai = trangThai;
        this.isNow = isNow;
        this.thoiGian = thoiGian;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getIsNow() {
        return isNow;
    }

    public void setIsNow(String isNow) {
        this.isNow = isNow;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }
}
