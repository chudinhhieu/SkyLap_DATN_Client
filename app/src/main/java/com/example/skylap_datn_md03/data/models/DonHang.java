package com.example.skylap_datn_md03.data.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class DonHang implements Serializable {
    private String _id;
    private ArrayList<TrangThai> trangThai;
    private String idSanPham;
    private String idAccount;
    private String idKhuyenMai;
    private Date thoiGianMua;
    private String idPttt;
    private int soLuong;
    private double tongTien;
    private String ghiChu;
    private boolean thanhToan;

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

    public DonHang(String _id, ArrayList<TrangThai> trangThai, String idSanPham, String idAccount, String idKhuyenMai,
                   Date thoiGianMua, String idPttt, int soLuong, long tongTien, String ghiChu, boolean thanhToan) {
        this._id = _id;
        this.trangThai = trangThai;
        this.idSanPham = idSanPham;
        this.idAccount = idAccount;
        this.idKhuyenMai = idKhuyenMai;
        this.thoiGianMua = thoiGianMua;
        this.idPttt = idPttt;
        this.soLuong = soLuong;
        this.tongTien = tongTien;
        this.ghiChu = ghiChu;
        this.thanhToan = thanhToan;
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
}
