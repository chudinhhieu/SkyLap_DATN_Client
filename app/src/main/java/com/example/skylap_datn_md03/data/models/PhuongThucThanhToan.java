package com.example.skylap_datn_md03.data.models;

public class PhuongThucThanhToan {
    private String _id;
    private String tenPhuongThuc;

    public PhuongThucThanhToan() {
    }

    public PhuongThucThanhToan(String tenPhuongThuc) {
        this.tenPhuongThuc = tenPhuongThuc;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTenPhuongThuc() {
        return tenPhuongThuc;
    }

    public void setTenPhuongThuc(String tenPhuongThuc) {
        this.tenPhuongThuc = tenPhuongThuc;
    }
}
