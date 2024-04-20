package com.example.skylap_datn_md03.data.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DonHang implements Serializable {
    private String _id;
    private ArrayList<TrangThai> trangThai;
    private String idSanPham;
    private String idBienThe;
    private double tienShip;
    private String idAccount;
    private String idKhuyenMai;
    private Date thoiGianMua;
    private String idPttt;
    private int soLuong;
    private double tongTien;
    private String ghiChu;
    private boolean thanhToan;
    private DanhGia danhGia;
    public boolean isThanhToan() {
        return thanhToan;
    }

    public void setThanhToan(boolean thanhToan) {
        this.thanhToan = thanhToan;
    }

    public DonHang() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public DonHang(String idSanPham, String idBienThe, double tienShip, String idAccount, String idKhuyenMai, int soLuong, double tongTien, String ghiChu, boolean thanhToan) {
        this.idSanPham = idSanPham;
        this.idBienThe = idBienThe;
        this.tienShip = tienShip;
        this.idAccount = idAccount;
        this.idKhuyenMai = idKhuyenMai;
        this.soLuong = soLuong;
        this.tongTien = tongTien;
        this.ghiChu = ghiChu;
        this.thanhToan = thanhToan;
    }

    public String getIdBienThe() {
        return idBienThe;
    }

    public void setIdBienThe(String idBienThe) {
        this.idBienThe = idBienThe;
    }

    public double getTienShip() {
        return tienShip;
    }

    public void setTienShip(double tienShip) {
        this.tienShip = tienShip;
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

    public String getIdKhuyenMai() {
        return idKhuyenMai;
    }

    public void setIdKhuyenMai(String idKhuyenMai) {
        this.idKhuyenMai = idKhuyenMai;
    }

    public Date getThoiGianMua() {
        return thoiGianMua;
    }

    public void setThoiGianMua(Date thoiGianMua) {
        this.thoiGianMua = thoiGianMua;
    }

    public String getIdPttt() {
        return idPttt;
    }

    public ArrayList<TrangThai> getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(ArrayList<TrangThai> trangThai) {
        this.trangThai = trangThai;
    }

    public void setIdPttt(String idPttt) {
        this.idPttt = idPttt;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    @Override
    public String toString() {
        return "DonHang{" +
                "_id='" + _id + '\'' +
                ", TrangThai='" + trangThai + '\'' +
                ", idSanPham='" + idSanPham + '\'' +
                ", idAccount='" + idAccount + '\'' +
                ", idKhuyenMai='" + idKhuyenMai + '\'' +
                ", thoiGianMua=" + thoiGianMua +
                ", idPttt='" + idPttt + '\'' +
                ", soLuong=" + soLuong +
                ", tongTien=" + tongTien +
                ", ghiChu='" + ghiChu + '\'' +
                ", thanhToan=" + thanhToan +
                '}';
    }

    public DanhGia getDanhGia() {
        return danhGia;
    }

    public void setDanhGia(DanhGia danhGia) {
        this.danhGia = danhGia;
    }
}
