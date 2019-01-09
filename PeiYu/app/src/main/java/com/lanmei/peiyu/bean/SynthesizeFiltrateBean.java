package com.lanmei.peiyu.bean;

/**
 * @author xkai 综合筛选
 */
public class SynthesizeFiltrateBean {

    private boolean isSelect;
    private int id;
    private String name;

    public boolean isSelect() {
        return isSelect;
    }

    public String getName() {
        return name;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}