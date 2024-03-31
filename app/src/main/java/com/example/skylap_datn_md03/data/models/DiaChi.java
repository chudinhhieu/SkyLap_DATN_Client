package com.example.skylap_datn_md03.data.models;

public class DiaChi {
    private String idTinh, diaChi, _id;

    public DiaChi() {
    }

    public DiaChi(String idTinh, String diaChi) {
        this.idTinh = idTinh;
        this.diaChi = diaChi;
    }

    public String getIdTinh() {
        return idTinh;
    }

    public void setIdTinh(String idTinh) {
        this.idTinh = idTinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

}
