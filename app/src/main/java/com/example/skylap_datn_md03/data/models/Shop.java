package com.example.skylap_datn_md03.data.models;

public class Shop {
    private String _id;
    private String idChuShop;
    private String tenShop;
    private boolean trangThai;
    private String moTa;
    private String avatar;
    private String anhBia;

    public Shop() {
    }

    public Shop(String idChuShop, String tenShop, boolean trangThai, String moTa, String avatar, String anhBia) {
        this.idChuShop = idChuShop;
        this.tenShop = tenShop;
        this.trangThai = trangThai;
        this.moTa = moTa;
        this.avatar = avatar;
        this.anhBia = anhBia;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getIdChuShop() {
        return idChuShop;
    }

    public void setIdChuShop(String idChuShop) {
        this.idChuShop = idChuShop;
    }

    public String getTenShop() {
        return tenShop;
    }

    public void setTenShop(String tenShop) {
        this.tenShop = tenShop;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAnhBia() {
        return anhBia;
    }

    public void setAnhBia(String anhBia) {
        this.anhBia = anhBia;
    }
}
