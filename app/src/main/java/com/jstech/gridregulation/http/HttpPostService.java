package com.jstech.gridregulation.http;

import com.jstech.gridregulation.api.MyUrl;

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
    Observable<String> getEnterprise(@Url String url);

    @POST
    Observable<String> getTable(@Url String url);
}
