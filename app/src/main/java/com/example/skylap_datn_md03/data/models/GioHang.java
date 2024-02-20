package com.example.skylap_datn_md03.data.models;

public class GioHang {
    private String _id;
    private String idSanPham;
    private String idNguoiMua;
    private int soLuong;
    private boolean trangThai;

    public GioHang() {
    }

    public GioHang(String idSanPham, String idNguoiMua, int soLuong, boolean trangThai) {
        this.idSanPham = idSanPham;
        this.idNguoiMua = idNguoiMua;
        this.soLuong = soLuong;
        this.trangThai = trangThai;
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
}
