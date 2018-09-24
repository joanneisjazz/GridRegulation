package com.jstech.gridregulation.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

@Entity
public class CheckItemEntity implements Serializable{



    /**
     * id : 1636f6d8ef5348168bb11f2be730e943
     * content : 测试
     * method : 测试
     * iskey : 0
     * tableid : 51a83f55ef89426699ca7888cf931f10
     */

    @Id(autoincrement = true)
    private Long itemId;
    private String id;
    private String content;
    private String method;
    private String iskey;
    private String tableid;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getIskey() {
        return iskey;
    }

    public void setIskey(String iskey) {
        this.iskey = iskey;
    }

    public String getTableid() {
        return tableid;
    }

    public void setTableid(String tableid) {
        this.tableid = tableid;
    }
}
