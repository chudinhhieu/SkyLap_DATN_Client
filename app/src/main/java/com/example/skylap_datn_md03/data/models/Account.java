package com.example.skylap_datn_md03.data.models;

import java.io.Serializable;

public class Account implements Serializable {
    private String _id;
    private String taiKhoan;
    private String matKhau;
    private String quyenTk;
    private String avatar;
    private Boolean trangThai;

    public Account() {
    }

    public Account(String taiKhoan, String matKhau) {
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getQuyenTk() {
        return quyenTk;
    }

    public void setQuyenTk(String quyenTk) {
        this.quyenTk = quyenTk;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Boolean getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Boolean trangThai) {
        this.trangThai = trangThai;
    }
}
