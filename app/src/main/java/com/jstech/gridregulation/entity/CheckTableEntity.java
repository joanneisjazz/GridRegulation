package com.jstech.gridregulation.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

@Entity
public class CheckTableEntity implements Serializable {

    /**
     * id : 51a83f55ef89426699ca7888cf931f10
     * name : 测试1
     * num : 2
     * type : 测试
     */

    @Id(autoincrement = true)
    private Long tableId;
    private String id;
    private String name;
    private int num;
    private String type;



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

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
