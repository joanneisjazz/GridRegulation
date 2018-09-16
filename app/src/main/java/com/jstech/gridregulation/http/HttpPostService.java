package com.jstech.gridregulation.http;

import com.jstech.gridregulation.api.MyUrl;
import com.jstech.gridregulation.bean.AddTaskBean;
import com.jstech.gridregulation.bean.RegulateResultBean;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;
import rx.Observable;

public interface HttpPostService {

    //    @HTTP(method = "POST", path = MyUrl.ENTERPRISE + "{id}" )
    @POST
    Observable<String> getEnterprise(@Url String url);//获取监管对象

    @POST
    Observable<String> getTable(@Url String url);//获取检查表

    @POST
    Observable<String> getItem(@Url String url);//获取检查项目

    @POST(MyUrl.ADD_TASK)
    Observable<String> addTask(@Body AddTaskBean params);

    @POST(MyUrl.SAVE_ITEMS)
    Observable<String> saveItemResult(@Body List<RegulateResultBean> params);
}
