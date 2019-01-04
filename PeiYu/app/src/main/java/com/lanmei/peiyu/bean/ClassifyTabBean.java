package com.lanmei.peiyu.bean;

import java.util.List;

/**
 * Created by xkai on 2018/12/19.
 */

public class ClassifyTabBean {


    /**
     * id : 1
     * name : 风电
     * parent_id : 0
     * _child : [{"id":"18","name":"水果","parent_id":"1"}]
     */

    private String id;
    private String name;
    private String parent_id;
    private boolean isChoose;

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }

    private List<ChildBean> child;

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

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public List<ChildBean> get_child() {
        return child;
    }

    public void setChild(List<ChildBean> child) {
        this.child = child;
    }

    public static class ChildBean {
        /**
         * id : 18
         * name : 水果
         * parent_id : 1
         */

        private String id;
        private String name;
        private String parent_id;
        private String pic;
        private String sort;
        private String model_id;

        public String getPic() {
            return pic;
        }

        public String getSort() {
            return sort;
        }

        public String getModel_id() {
            return model_id;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public void setModel_id(String model_id) {
            this.model_id = model_id;
        }

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

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }
    }
}
