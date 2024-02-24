package com.example.skylap_datn_md03.data.models;

import java.util.Date;
import java.util.List;

public class DanhGia {
    private String _id;
    private String _idNguoiMua;
    private String _idSanPham;
    private Date thoiGianDG;
    private int soSao;
    private List<String> anhDG;

    public DanhGia(String _idNguoiMua, String _idSanPham, Date thoiGianDG, int soSao, List<String> anhDG) {
        this._idNguoiMua = _idNguoiMua;
        this._idSanPham = _idSanPham;
        this.thoiGianDG = thoiGianDG;
        this.soSao = soSao;
        this.anhDG = anhDG;
    }

    public DanhGia() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_idNguoiMua() {
        return _idNguoiMua;
    }

    public void set_idNguoiMua(String _idNguoiMua) {
        this._idNguoiMua = _idNguoiMua;
    }

    public String get_idSanPham() {
        return _idSanPham;
    }

    public void set_idSanPham(String _idSanPham) {
        this._idSanPham = _idSanPham;
    }

    public Date getThoiGianDG() {
        return thoiGianDG;
    }

    public void setThoiGianDG(Date thoiGianDG) {
        this.thoiGianDG = thoiGianDG;
    }

    public int getSoSao() {
        return soSao;
    }

    public void setSoSao(int soSao) {
        this.soSao = soSao;
    }

    public List<String> getAnhDG() {
        return anhDG;
    }

    public void setAnhDG(List<String> anhDG) {
        this.anhDG = anhDG;
    }
}
