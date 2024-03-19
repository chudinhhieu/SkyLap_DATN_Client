package com.example.skylap_datn_md03.data.models;

import java.io.Serializable;
import java.util.List;

public class SanPham implements Serializable {
    private String _id;
    private String idHangSx;
    private int soLuong;
    private String tenSanPham;
    private boolean trangThai;
    private double giaTien;
    private String anhSanPham;
    private List<String> anh;

    private String cpu;
    private int soNhan;
    private int soLuongCPU;
    private String tocDoCPU;
    private String tocDoToiDa;
    private String boNhoDem;

    private String ram;
    private String loaiRam;
    private String tocDoBusRam;
    private String hoTroRamToiDa;
    private String rom;

    private String display;
    private String doPhanGiai;
    private String tanSoQuet;
    private String doPhuMau;
    private String congNgheManHinh;

    private String moTa;
    private String mauSac;
    private String gpu;
    private String congNgheAmThanh;

    private String congGiaoTiep;
    private String ketNoiKhongDay;
    private String webCam;
    private String tinhNangKhac;
    private String denBanPhim;

    private String kichThuocKhoiLuong;
    private String chatLieu;

    private String pin;
    private String congSuatSac;
    private String thoiDiemRaMat;
    private String baohanh;
    private String os;
    private String phuKien;

    public SanPham() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getIdHangSx() {
        return idHangSx;
    }

    public void setIdHangSx(String idHangSx) {
        this.idHangSx = idHangSx;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
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

    public String getAnhSanPham() {
        return anhSanPham;
    }

    public void setAnhSanPham(String anhSanPham) {
        this.anhSanPham = anhSanPham;
    }

    public List<String> getAnh() {
        return anh;
    }

    public void setAnh(List<String> anh) {
        this.anh = anh;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public int getSoNhan() {
        return soNhan;
    }

    public void setSoNhan(int soNhan) {
        this.soNhan = soNhan;
    }

    public int getSoLuongCPU() {
        return soLuongCPU;
    }

    public void setSoLuongCPU(int soLuongCPU) {
        this.soLuongCPU = soLuongCPU;
    }

    public String getTocDoCPU() {
        return tocDoCPU;
    }

    public void setTocDoCPU(String tocDoCPU) {
        this.tocDoCPU = tocDoCPU;
    }

    public String getTocDoToiDa() {
        return tocDoToiDa;
    }

    public void setTocDoToiDa(String tocDoToiDa) {
        this.tocDoToiDa = tocDoToiDa;
    }

    public String getBoNhoDem() {
        return boNhoDem;
    }

    public void setBoNhoDem(String boNhoDem) {
        this.boNhoDem = boNhoDem;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getLoaiRam() {
        return loaiRam;
    }

    public void setLoaiRam(String loaiRam) {
        this.loaiRam = loaiRam;
    }

    public String getTocDoBusRam() {
        return tocDoBusRam;
    }

    public void setTocDoBusRam(String tocDoBusRam) {
        this.tocDoBusRam = tocDoBusRam;
    }

    public String getHoTroRamToiDa() {
        return hoTroRamToiDa;
    }

    public void setHoTroRamToiDa(String hoTroRamToiDa) {
        this.hoTroRamToiDa = hoTroRamToiDa;
    }

    public String getRom() {
        return rom;
    }

    public void setRom(String rom) {
        this.rom = rom;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getDoPhanGiai() {
        return doPhanGiai;
    }

    public void setDoPhanGiai(String doPhanGiai) {
        this.doPhanGiai = doPhanGiai;
    }

    public String getTanSoQuet() {
        return tanSoQuet;
    }

    public void setTanSoQuet(String tanSoQuet) {
        this.tanSoQuet = tanSoQuet;
    }

    public String getDoPhuMau() {
        return doPhuMau;
    }

    public void setDoPhuMau(String doPhuMau) {
        this.doPhuMau = doPhuMau;
    }

    public String getCongNgheManHinh() {
        return congNgheManHinh;
    }

    public void setCongNgheManHinh(String congNgheManHinh) {
        this.congNgheManHinh = congNgheManHinh;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getMauSac() {
        return mauSac;
    }

    public void setMauSac(String mauSac) {
        this.mauSac = mauSac;
    }

    public String getGpu() {
        return gpu;
    }

    public void setGpu(String gpu) {
        this.gpu = gpu;
    }

    public String getCongNgheAmThanh() {
        return congNgheAmThanh;
    }

    public void setCongNgheAmThanh(String congNgheAmThanh) {
        this.congNgheAmThanh = congNgheAmThanh;
    }

    public String getCongGiaoTiep() {
        return congGiaoTiep;
    }

    public void setCongGiaoTiep(String congGiaoTiep) {
        this.congGiaoTiep = congGiaoTiep;
    }

    public String getKetNoiKhongDay() {
        return ketNoiKhongDay;
    }

    public void setKetNoiKhongDay(String ketNoiKhongDay) {
        this.ketNoiKhongDay = ketNoiKhongDay;
    }

    public String getWebCam() {
        return webCam;
    }

    public void setWebCam(String webCam) {
        this.webCam = webCam;
    }

    public String getTinhNangKhac() {
        return tinhNangKhac;
    }

    public void setTinhNangKhac(String tinhNangKhac) {
        this.tinhNangKhac = tinhNangKhac;
    }

    public String getDenBanPhim() {
        return denBanPhim;
    }

    public void setDenBanPhim(String denBanPhim) {
        this.denBanPhim = denBanPhim;
    }

    public String getKichThuocKhoiLuong() {
        return kichThuocKhoiLuong;
    }

    public void setKichThuocKhoiLuong(String kichThuocKhoiLuong) {
        this.kichThuocKhoiLuong = kichThuocKhoiLuong;
    }

    public String getChatLieu() {
        return chatLieu;
    }

    public void setChatLieu(String chatLieu) {
        this.chatLieu = chatLieu;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getCongSuatSac() {
        return congSuatSac;
    }

    public void setCongSuatSac(String congSuatSac) {
        this.congSuatSac = congSuatSac;
    }

    public String getThoiDiemRaMat() {
        return thoiDiemRaMat;
    }

    public void setThoiDiemRaMat(String thoiDiemRaMat) {
        this.thoiDiemRaMat = thoiDiemRaMat;
    }

    public String getBaohanh() {
        return baohanh;
    }

    public void setBaohanh(String baohanh) {
        this.baohanh = baohanh;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getPhuKien() {
        return phuKien;
    }

    public void setPhuKien(String phuKien) {
        this.phuKien = phuKien;
    }
}
