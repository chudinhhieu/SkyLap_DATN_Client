package com.example.skylap_datn_md03.data.models;

import java.util.Date;

public class KhuyenMai {
    private String _id, code, moTa;
    private Date thoiGianKetThuc, thoiGianBatDau;
    private int soLuong;
    private double soTienGiam;
    private boolean trangThai;
    private String anh;

    public KhuyenMai(String _id, String code, String moTa, Date thoiGianKetThuc, Date thoiGianBatDau, int soLuong, double soTienGiam, boolean trangThai, String anh) {
        this._id = _id;
        this.code = code;
        this.moTa = moTa;
        this.thoiGianKetThuc = thoiGianKetThuc;
        this.thoiGianBatDau = thoiGianBatDau;
        this.soLuong = soLuong;
        this.soTienGiam = soTienGiam;
        this.trangThai = trangThai;
        this.anh = anh;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public Date getThoiGianKetThuc() {
        return thoiGianKetThuc;
    }

    public void setThoiGianKetThuc(Date thoiGianKetThuc) {
        this.thoiGianKetThuc = thoiGianKetThuc;
    }

    public Date getThoiGianBatDau() {
        return thoiGianBatDau;
    }

    public void setThoiGianBatDau(Date thoiGianBatDau) {
        this.thoiGianBatDau = thoiGianBatDau;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getSoTienGiam() {
        return soTienGiam;
    }

    public void setSoTienGiam(double soTienGiam) {
        this.soTienGiam = soTienGiam;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }
}