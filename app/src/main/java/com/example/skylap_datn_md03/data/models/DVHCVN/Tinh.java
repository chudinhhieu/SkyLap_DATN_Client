package com.example.skylap_datn_md03.data.models.DVHCVN;

import java.util.List;

public class Tinh {
    private String id;
    private String name;
    private List<Huyen> level2s;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Huyen> getLevel2s() {
        return level2s;
    }

    public void setLevel2s(List<Huyen> level2s) {
        this.level2s = level2s;
    }
}
