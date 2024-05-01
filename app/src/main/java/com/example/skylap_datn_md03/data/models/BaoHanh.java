package com.example.skylap_datn_md03.data.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class BaoHanh implements Serializable {
    private String _id;
    private String imei;
    private String lyDoTuChoi;
    private int tinhTrang;
    private List<String> anh;
    private String idSanPham;
    private String idDonDatHang;
    private String idAccount;
    private String lyDo;
    private Date thoiGian;

    public BaoHanh() {
    }

    public String getIdDonDatHang() {
        return idDonDatHang;
    }

    public void setIdDonDatHang(String idDonDatHang) {
        this.idDonDatHang = idDonDatHang;
    }

    public String getLyDo() {
        return lyDo;
    }

    public String getLyDoTuChoi() {
        return lyDoTuChoi;
    }

    public void setLyDoTuChoi(String lyDoTuChoi) {
        this.lyDoTuChoi = lyDoTuChoi;
    }

    public List<String> getAnh() {
        return anh;
    }

    public void setAnh(List<String> anh) {
        this.anh = anh;
    }

    public void setLyDo(String lyDo) {
        this.lyDo = lyDo;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public int getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(int tinhTrang) {
        this.tinhTrang = tinhTrang;
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

    public Date getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(Date thoiGian) {
        this.thoiGian = thoiGian;
    }
}
