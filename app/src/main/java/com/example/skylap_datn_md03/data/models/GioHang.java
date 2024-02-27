package com.example.skylap_datn_md03.data.models;

import java.util.List;

public class GioHang {

    private String _id;
    private List<String> idSanPham;
    private String idNguoiMua;
    private int soLuong;




    public GioHang() {
    }

    public GioHang(String _id, List<String> idSanPham, String idNguoiMua, int soLuong) {
        this._id = _id;
        this.idSanPham = idSanPham;
        this.idNguoiMua = idNguoiMua;
        this.soLuong = soLuong;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<String> getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(List<String> idSanPham) {
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
}
