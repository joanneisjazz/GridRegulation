package com.jstech.gridregulation.api;

/**
 * 存放网络请求的url地址
 */
public class MyUrl {

    //grid/api/ta/supervi/get/enterprise/list/
    public final static String SUPERVI = "grid/api/ta/supervi/";//现场检查的接口
    public final static String SUPERVI_GET = SUPERVI + "get/";
    public final static String GET_ENTERPRISE = SUPERVI_GET + "enterprise/list/";//获取监管对象的信息
    public final static String GET_TABLE = SUPERVI_GET + "norm/table/list/";//获取检查表
    public final static String GET_ITEM = SUPERVI_GET + "norm/item/list/";//获取检查项目
    public final static String SUPERVI_SAVE = SUPERVI + "save/";
    public final static String ADD_TASK = SUPERVI_SAVE + "supervi/insp";//增加检查任务
    public final static String SAVE_ITEMS = SUPERVI_SAVE + "supervi/items";//保存检查项及结果
}
