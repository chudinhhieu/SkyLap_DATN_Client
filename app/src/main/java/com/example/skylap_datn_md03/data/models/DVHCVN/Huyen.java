package com.example.skylap_datn_md03.data.models.DVHCVN;

import java.util.List;

public class Huyen {
    private String id;
    private String name;
    private List<Xa> level3s;

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

    public List<Xa> getLevel3s() {
        return level3s;
    }

    public void setLevel3s(List<Xa> level3s) {
        this.level3s = level3s;
    }
}
