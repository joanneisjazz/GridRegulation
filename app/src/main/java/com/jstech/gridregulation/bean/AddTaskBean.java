package com.jstech.gridregulation.bean;

public class AddTaskBean {

    /**
     * entid : 1
     * entname : 测试数据
     * enttype : 0
     * entregion : 140110001
     * entcredit : B
     * oisuper : 241671aee7cc4ead88b2532b7686183c
     * insptable : 51a83f55ef89426699ca7888cf931f10,5c54ddc9f724434590b954a4cc4dd0d2
     * createBy : 241671aee7cc4ead88b2532b7686183c
     * updateBy : 241671aee7cc4ead88b2532b7686183c
     */

    private long entid;
    private String entname;
    private String enttype;
    private String entregion;
    private String entcredit;
    private String oisuper;
    private String insptable;
    private String createBy;
    private String updateBy;

    public AddTaskBean() {
    }

    public long getEntid() {
        return entid;
    }

    public void setEntid(long entid) {
        this.entid = entid;
    }

    public String getEntname() {
        return entname;
    }

    public void setEntname(String entname) {
        this.entname = entname;
    }

    public String getEnttype() {
        return enttype;
    }

    public void setEnttype(String enttype) {
        this.enttype = enttype;
    }

    public String getEntregion() {
        return entregion;
    }

    public void setEntregion(String entregion) {
        this.entregion = entregion;
    }

    public String getEntcredit() {
        return entcredit;
    }

    public void setEntcredit(String entcredit) {
        this.entcredit = entcredit;
    }

    public String getOisuper() {
        return oisuper;
    }

    public void setOisuper(String oisuper) {
        this.oisuper = oisuper;
    }

    public String getInsptable() {
        return insptable;
    }

    public void setInsptable(String insptable) {
        this.insptable = insptable;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
}
