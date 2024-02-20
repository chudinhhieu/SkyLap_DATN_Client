package com.example.skylap_datn_md03.data.models;

import java.io.Serializable;
import java.util.Date;

public class DonHang implements Serializable {
    private String _id;
    private int tienShip;
    private int tongTien;
    private String trangThai;
    private String idGioHang;
    private String idShipper;
    private String ghiChu;
    private Date thoiGianMua;
    private String idKhuyenMai;
    private String idPTTT;

    public DonHang() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getTienShip() {
        return tienShip;
    }

    public void setTienShip(int tienShip) {
        this.tienShip = tienShip;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getIdGioHang() {
        return idGioHang;
    }

    public void setIdGioHang(String idGioHang) {
        this.idGioHang = idGioHang;
    }

    public String getIdShipper() {
        return idShipper;
    }

    public void setIdShipper(String idShipper) {
        this.idShipper = idShipper;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public Date getThoiGianMua() {
        return thoiGianMua;
    }

    public void setThoiGianMua(Date thoiGianMua) {
        this.thoiGianMua = thoiGianMua;
    }

    public String getIdKhuyenMai() {
        return idKhuyenMai;
    }

    public void setIdKhuyenMai(String idKhuyenMai) {
        this.idKhuyenMai = idKhuyenMai;
    }

    public String getIdPTTT() {
        return idPTTT;
    }

    public void setIdPTTT(String idPTTT) {
        this.idPTTT = idPTTT;
    }
}
