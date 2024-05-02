package com.example.skylap_datn_md03.data.models;

import java.util.Date;

public class TrangThai {
    private String  trangThai;
    private Date thoiGian;
    private Boolean isNow;

    public TrangThai(String trangThai, Date thoiGian, Boolean isNow) {
        this.trangThai = trangThai;
        this.thoiGian = thoiGian;
        this.isNow = isNow;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public Boolean getIsNow() {
        return isNow;
    }

    public void setIsNow(Boolean isNow) {
        this.isNow = isNow;
    }

    public Date getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(Date thoiGian) {
        this.thoiGian = thoiGian;
    }

    public Boolean getNow() {
        return isNow;
    }

    public void setNow(Boolean now) {
        isNow = now;
    }
}
