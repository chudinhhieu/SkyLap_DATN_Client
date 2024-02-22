package com.example.skylap_datn_md03.data.models;

import java.util.List;

public class GioHang {
    private String _id;
    private String idSanPham;
    private String idNguoiMua;
    private int soLuong;
    private boolean trangThai;

    private String tenSanPham;
    private double giaSanPham;
    private List<String> anhSanPham;


    public GioHang() {
    }

    public GioHang(String idSanPham, String idNguoiMua, int soLuong, boolean trangThai,
                   String tenSanPham, double giaSanPham, List<String> anhSanPham) {
        this.idSanPham = idSanPham;
        this.idNguoiMua = idNguoiMua;
        this.soLuong = soLuong;
        this.trangThai = trangThai;
        //
        this.tenSanPham = tenSanPham;
        this.giaSanPham = giaSanPham;
        this.anhSanPham = anhSanPham;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(String idSanPham) {
        this.idSanPham = idSanPham;
    }

    public String getIdNguoiMua() {
        return idNguoiMua;
    }

    public void setIdNguoiMua(String idNguoiMua) {
        this.idNguoiMua = idNguoiMua;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public double getGiaSanPham() {
        return giaSanPham;
    }

    public void setGiaSanPham(double giaSanPham) {
        this.giaSanPham = giaSanPham;
    }

    public List<String> getAnhSanPham() {
        return anhSanPham;
    }

    public void setAnhSanPham(List<String> anhSanPham) {
        this.anhSanPham = anhSanPham;
    }
}
