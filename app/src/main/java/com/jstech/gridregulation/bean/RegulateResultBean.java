package com.jstech.gridregulation.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
@Entity
public class RegulateResultBean {


    /**
     * itemid : 1636f6d8ef5348168bb11f2be730e943
     * itemcontent : 测试
     * inspresult : 1
     * inspdesc :
     * inspid : 0e0c872bc1c848eeaf713e9f899f2e0a
     * insptable : 51a83f55ef89426699ca7888cf931f10
     * oisuper : 241671aee7cc4ead88b2532b7686183c
     * insploc : 112.48123,37.760321
     * createBy : 241671aee7cc4ead88b2532b7686183c
     * updateBy : 241671aee7cc4ead88b2532b7686183c
     */

    @Id(autoincrement = true)
    private Long id;

    private String itemid;
    private String itemcontent;
    private String inspresult;
    private String inspdesc;
    private String inspid;
    private String insptable;
    private String oisuper;
    private String insploc;
    private String createBy;
    private String updateBy;

    @Generated(hash = 509904045)
    public RegulateResultBean(Long id, String itemid, String itemcontent,
            String inspresult, String inspdesc, String inspid, String insptable,
            String oisuper, String insploc, String createBy, String updateBy) {
        this.id = id;
        this.itemid = itemid;
        this.itemcontent = itemcontent;
        this.inspresult = inspresult;
        this.inspdesc = inspdesc;
        this.inspid = inspid;
        this.insptable = insptable;
        this.oisuper = oisuper;
        this.insploc = insploc;
        this.createBy = createBy;
        this.updateBy = updateBy;
    }

    @Generated(hash = 414442485)
    public RegulateResultBean() {
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getItemcontent() {
        return itemcontent;
    }

    public void setItemcontent(String itemcontent) {
        this.itemcontent = itemcontent;
    }

    public String getInspresult() {
        return inspresult;
    }

    public void setInspresult(String inspresult) {
        this.inspresult = inspresult;
    }

    public String getInspdesc() {
        return inspdesc;
    }

    public void setInspdesc(String inspdesc) {
        this.inspdesc = inspdesc;
    }

    public String getInspid() {
        return inspid;
    }

    public void setInspid(String inspid) {
        this.inspid = inspid;
    }

    public String getInsptable() {
        return insptable;
    }

    public void setInsptable(String insptable) {
        this.insptable = insptable;
    }

    public String getOisuper() {
        return oisuper;
    }

    public void setOisuper(String oisuper) {
        this.oisuper = oisuper;
    }

    public String getInsploc() {
        return insploc;
    }

    public void setInsploc(String insploc) {
        this.insploc = insploc;
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

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
