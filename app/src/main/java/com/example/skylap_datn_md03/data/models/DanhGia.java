package com.example.skylap_datn_md03.data.models;

import java.util.Date;
import java.util.List;

public class DanhGia {
    private String _id;
    private Date thoiGian;
    private  String noiDung;
    private int soSao;
    private List<String> anh;

    public DanhGia(String noiDung, int soSao) {
        this.noiDung = noiDung;
        this.soSao = soSao;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Date getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(Date thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public int getSoSao() {
        return soSao;
    }

    public void setSoSao(int soSao) {
        this.soSao = soSao;
    }

    public List<String> getAnh() {
        return anh;
    }

    public void setAnh(List<String> anh) {
        this.anh = anh;
    }
}
