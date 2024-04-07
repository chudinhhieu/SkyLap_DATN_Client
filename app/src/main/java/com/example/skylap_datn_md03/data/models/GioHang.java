package com.example.skylap_datn_md03.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class GioHang implements Serializable {
    private String _id;
    private String idSanPham;
    private String idAccount;
    private int soLuong;
    private boolean isChecked;

    public GioHang() {
    }

    @Override
    public String toString() {
        return "GioHang{" +
                "_id='" + _id + '\'' +
                ", idSanPham='" + idSanPham + '\'' +
                ", idAccount='" + idAccount + '\'' +
                ", soLuong=" + soLuong +
                '}';
    }

    public GioHang(String idSanPham, String idAccount, int soLuong) {
        this.idSanPham = idSanPham;
        this.idAccount = idAccount;
        this.soLuong = soLuong;
    }

    public GioHang(int soLuong) {
        this.soLuong = soLuong;
    }


    protected GioHang(Parcel in) {
        _id = in.readString();
        idSanPham = in.readString();
        idAccount = in.readString();
        soLuong = in.readInt();
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

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

}
