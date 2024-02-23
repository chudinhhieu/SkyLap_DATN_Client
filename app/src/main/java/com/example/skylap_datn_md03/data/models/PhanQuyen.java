package com.example.skylap_datn_md03.data.models;

public class PhanQuyen {
    private String _id;
    private String ten;

    public PhanQuyen(String ten) {
        this.ten = ten;
    }

    public PhanQuyen() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }
}
