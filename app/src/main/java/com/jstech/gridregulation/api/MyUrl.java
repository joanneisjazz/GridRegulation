package com.jstech.gridregulation.api;

/**
 * 存放网络请求的url地址
 */
public class MyUrl {

    //grid/api/ta/supervi/get/enterprise/list/
    public final static String SUPERVI = "grid/api/ta/supervi/get/";//现场检查的接口
    public final static String GET_ENTERPRISE = SUPERVI + "enterprise/list/";//获取监管对象的信息
    public final static String GET_TABLE = SUPERVI + "norm/table/list/";
    public final static String GET_ITEM = SUPERVI + "norm/item/list/";

}
