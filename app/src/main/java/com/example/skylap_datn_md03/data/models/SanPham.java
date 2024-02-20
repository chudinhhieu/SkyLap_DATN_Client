package com.example.skylap_datn_md03.data.models;

import java.util.List;

public class SanPham {
    private String _id;
    private String tenSanPham;
    private int soLuong;
    private boolean trangThai;
    private double giaTien;
    private double chieuCao;
    private double chieuRong;
    private double trongLuong;
    private String os;
    private String cpu;
    private String gpu;
    private String display;
    private String moTa;
    private List<String> anh;
    private String idShop;
    private String idHangSX;
    private String idLoaiSP;

    public SanPham() {
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public SanPham(int soLuong, boolean trangThai,
                   double giaTien, double chieuCao, double chieuRong, double trongLuong,
                   String os, String cpu, String gpu, String display, String moTa, List<String> anh,
                   String idShop, String idHangSX, String idLoaiSP, String tenSanPham) {
        this.soLuong = soLuong;
        this.trangThai = trangThai;
        this.giaTien = giaTien;
        this.chieuCao = chieuCao;
        this.chieuRong = chieuRong;
        this.trongLuong = trongLuong;
        this.os = os;
        this.cpu = cpu;
        this.gpu = gpu;
        this.display = display;
        this.moTa = moTa;
        this.anh = anh;
        this.idShop = idShop;
        this.idHangSX = idHangSX;
        this.tenSanPham = tenSanPham;
        this.idLoaiSP = idLoaiSP;
    }

    public String getIdShop() {
        return idShop;
    }

    public void setIdShop(String idShop) {
        this.idShop = idShop;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }


    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public double getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(double giaTien) {
        this.giaTien = giaTien;
    }

    public double getChieuCao() {
        return chieuCao;
    }

    public void setChieuCao(double chieuCao) {
        this.chieuCao = chieuCao;
    }

    public double getChieuRong() {
        return chieuRong;
    }

    public void setChieuRong(double chieuRong) {
        this.chieuRong = chieuRong;
    }

    public double getTrongLuong() {
        return trongLuong;
    }

    public void setTrongLuong(double trongLuong) {
        this.trongLuong = trongLuong;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getGpu() {
        return gpu;
    }

    public void setGpu(String gpu) {
        this.gpu = gpu;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public List<String> getAnh() {
        return anh;
    }

    public void setAnh(List<String> anh) {
        this.anh = anh;
    }

    public String getIdHangSX() {
        return idHangSX;
    }

    public void setIdHangSX(String idHangSX) {
        this.idHangSX = idHangSX;
    }

    public String getIdLoaiSP() {
        return idLoaiSP;
    }

    public void setIdLoaiSP(String idLoaiSP) {
        this.idLoaiSP = idLoaiSP;
    }
}
