package com.example.skylap_datn_md03.data.models;

public class DiaChi {
    //tenDiaChi : "Công ty"
    //diaChi : "Trịnh Văn Bô"
    //trangThai : true
    //_id : "65e5cc8a5cbf37d3549d60db"
    private String tenDiaChi, diaChi, _id;
    private Boolean trangThai;

    public DiaChi() {
    }

    public String getTenDiaChi() {
        return tenDiaChi;
    }

    public void setTenDiaChi(String tenDiaChi) {
        this.tenDiaChi = tenDiaChi;
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

    public Boolean getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Boolean trangThai) {
        this.trangThai = trangThai;
    }

    public DiaChi(String tenDiaChi, String diaChi, String _id, Boolean trangThai) {
        this.tenDiaChi = tenDiaChi;
        this.diaChi = diaChi;
        this._id = _id;
        this.trangThai = trangThai;
    }
}
