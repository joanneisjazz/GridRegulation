package com.jstech.gridregulation.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class UserBean {
    @Id
    private String userId;
    private String userName;
    private String entregion;

    @Generated(hash = 1219995478)
    public UserBean(String userId, String userName, String entregion) {
        this.userId = userId;
        this.userName = userName;
        this.entregion = entregion;
    }

    @Generated(hash = 1203313951)
    public UserBean() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEntregion() {
        return entregion;
    }

    public void setEntregion(String entregion) {
        this.entregion = entregion;
    }
}
