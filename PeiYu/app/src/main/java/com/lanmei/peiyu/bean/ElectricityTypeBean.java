package com.lanmei.peiyu.bean;

import java.util.List;

/**
 * Created by xkai on 2019/1/7.
 * 模拟电价
 */

public class ElectricityTypeBean {

    private List<TypeBean> type1;
    private List<TypeBean> type2;
    private List<TypeBean> type3; //电费类型（type1电费价格 type2国家补贴电费价格 type3省市级电费补贴）

    public List<TypeBean> getType1() {
        return type1;
    }

    public void setType1(List<TypeBean> type1) {
        this.type1 = type1;
    }

    public List<TypeBean> getType2() {
        return type2;
    }

    public void setType2(List<TypeBean> type2) {
        this.type2 = type2;
    }

    public List<TypeBean> getType3() {
        return type3;
    }

    public void setType3(List<TypeBean> type3) {
        this.type3 = type3;
    }

    public static class TypeBean {
        /**
         * id : 113
         * problemname : 无
         * setval : 22
         * type : 3
         */

        private String id;
        private String problemname;
        private String setval;
        private String type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getProblemname() {
            return problemname;
        }

        public void setProblemname(String problemname) {
            this.problemname = problemname;
        }

        public String getSetval() {
            return setval;
        }

        public void setSetval(String setval) {
            this.setval = setval;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
