package com.example.skylap_datn_md03.data.models;

import java.util.List;

public class SanPham {
    private String _id;
    private String idShop;
    private int soLuong;

    private String tenSanPham;
    private boolean trangThai;
    private double giaTien;
    private String chieuCao;
    private String chieuRong;
    private String chieuDoc;
    private String trongLuong;
    private String ram;
    private String rom;
    private String baohanh;
    private String os;
    private String cpu;
    private String gpu;
    private String display;
    private String moTa;
    private String phuKien;
    private List<String> anh;
    private String idHangSX;
    private String idLoaiSP;
    private String tamNen;
    private String tanSoQuet;
    public SanPham() {
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public String getChieuDoc() {
        return chieuDoc;
    }

    public void setChieuDoc(String chieuDoc) {
        this.chieuDoc = chieuDoc;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getRom() {
        return rom;
    }

    public void setRom(String rom) {
        this.rom = rom;
    }

    public String getBaohanh() {
        return baohanh;
    }

    public void setBaohanh(String baohanh) {
        this.baohanh = baohanh;
    }

    public String getPhuKien() {
        return phuKien;
    }

    public void setPhuKien(String phuKien) {
        this.phuKien = phuKien;
    }

    public String getTamNen() {
        return tamNen;
    }

    public void setTamNen(String tamNen) {
        this.tamNen = tamNen;
    }

    public String getTanSoQuet() {
        return tanSoQuet;
    }

    public void setTanSoQuet(String tanSoQuet) {
        this.tanSoQuet = tanSoQuet;
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

    public String getChieuCao() {
        return chieuCao;
    }

    public void setChieuCao(String chieuCao) {
        this.chieuCao = chieuCao;
    }

    public String getChieuRong() {
        return chieuRong;
    }

    public void setChieuRong(String chieuRong) {
        this.chieuRong = chieuRong;
    }

    public String getTrongLuong() {
        return trongLuong;
    }

    public void setTrongLuong(String trongLuong) {
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

    public String getIdShop() {
        return idShop;
    }

    public void setIdShop(String idShop) {
        this.idShop = idShop;
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
