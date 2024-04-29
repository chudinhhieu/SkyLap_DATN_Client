package com.example.skylap_datn_md03.data.models;

import java.io.Serializable;

public class BaoHanh implements Serializable {
    private String _id;
    private String imei;
    private int tinhTrang;

    public BaoHanh(String _id, String imei, int tinhTrang) {
        this._id = _id;
        this.imei = imei;
        this.tinhTrang = tinhTrang;
    }

    public BaoHanh() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public int getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(int tinhTrang) {
        this.tinhTrang = tinhTrang;
    }
}
