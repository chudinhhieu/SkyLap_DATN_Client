package com.example.skylap_datn_md03.data.models;

import java.io.Serializable;
import java.util.Date;

public class DonHang implements Serializable {
    private String _id;

    private String trangThai;
    private String idSanPham;
    private String idAccount;
    private String idKhuyenMai;
    private Date thoiGianMua;
    private String idPttt;
    private int soLuong;
    private long tongTien;
    private String ghiChu;

    public DonHang() {
    }

    public DonHang(String _id, String trangThai, String idSanPham, String idAccount, String idKhuyenMai, Date thoiGianMua, String idPttt, int soLuong, long tongTien, String ghiChu) {
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
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
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

    public void setIdPttt(String idPttt) {
        this.idPttt = idPttt;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public long getTongTien() {
        return tongTien;
    }

    public void setTongTien(long tongTien) {
        this.tongTien = tongTien;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
<<<<<<< Updated upstream
=======

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
    private DanhGia danhGia;

    // Thêm getter và setter cho DanhGia
    public DanhGia getDanhGia() {
        return danhGia;
    }

    public void setDanhGia(DanhGia danhGia) {
        this.danhGia = danhGia;
    }
>>>>>>> Stashed changes
}
