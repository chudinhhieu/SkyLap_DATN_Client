package com.example.skylap_datn_md03.data.models;

import java.util.Date;

public class KhuyenMai {
    //"_id": "65dc5afc2839febd1c458c90",
    //        "thoiGianBatDau": "2024-02-25T17:00:00.000Z",
    //        "thoiGianKetThuc": "2024-04-02T17:00:00.000Z",
    //        "code": "banmoi500",
    //        "moTa": "Giảm ngay 500k cho bạn mới",
    //        "soLuong": -1,
    //        "soTienGiam": 500000,
    //        "trangThai": true,
    //        "anh": "https://storage.googleapis.com/skylap-md03.appspot.com/KhuyenMai/1708940027246_cf67d9174b22e67cbf33.jpg",
    //        "__v": 0
    private String _id, thoiGianBatDau, thoiGianKetThuc, code, moTa;
    private int soLuong;
    private double soTienGiam;
    private boolean trangThai;
    private String anh;

    public KhuyenMai(String _id, String thoiGianBatDau, String thoiGianKetThuc, String code,
                     String moTa, int soLuong, double soTienGiam, boolean trangThai, String anh) {
        this._id = _id;
        this.thoiGianBatDau = thoiGianBatDau;
        this.thoiGianKetThuc = thoiGianKetThuc;
        this.code = code;
        this.moTa = moTa;
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

    public String getThoiGianBatDau() {
        return thoiGianBatDau;
    }

    public void setThoiGianBatDau(String thoiGianBatDau) {
        this.thoiGianBatDau = thoiGianBatDau;
    }

    public String getThoiGianKetThuc() {
        return thoiGianKetThuc;
    }

    public void setThoiGianKetThuc(String thoiGianKetThuc) {
        this.thoiGianKetThuc = thoiGianKetThuc;
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

    public KhuyenMai() {
    }
}