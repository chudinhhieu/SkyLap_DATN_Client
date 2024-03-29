package com.example.skylap_datn_md03.data.models;

import java.io.Serializable;

public class SanPhamYeuThich implements Serializable {
    private String _id;
    private String idSanPham;
    private String idAccount;

    public SanPhamYeuThich() {
    }

    public SanPhamYeuThich(String idSanPham, String idAccount) {
        this.idSanPham = idSanPham;
        this.idAccount = idAccount;
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
}

