package com.example.skylap_datn_md03.data.models;

import java.util.List;

public class GioHang {
    private String _id;
    private String idSanPham;
    private String idAccount;
    private int soLuong;
    private boolean Checked;

    public GioHang() {
    }

    public GioHang(String idSanPham, String idAccount, int soLuong) {
        this.idSanPham = idSanPham;
        this.idAccount = idAccount;
        this.soLuong = soLuong;
    }

    public boolean isChecked() {
        return Checked;
    }

    public void setChecked(boolean checked) {
        Checked = checked;
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

    public String getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(String idAccount) {
        this.idAccount = idAccount;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
