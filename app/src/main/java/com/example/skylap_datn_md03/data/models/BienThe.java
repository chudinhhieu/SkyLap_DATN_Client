package com.example.skylap_datn_md03.data.models;

import java.io.Serializable;

public class BienThe implements Serializable {
    private String _id;
    private int soLuong;
    private double giaTien;
    private String ram;
    private String rom;
    private boolean isSelected;

    public BienThe() {
    }

    public BienThe(int soLuong, double giaTien, String ram, String rom) {
        this.soLuong = soLuong;
        this.giaTien = giaTien;
        this.ram = ram;
        this.rom = rom;
        this.isSelected = false;
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
    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(double giaTien) {
        this.giaTien = giaTien;
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
}
