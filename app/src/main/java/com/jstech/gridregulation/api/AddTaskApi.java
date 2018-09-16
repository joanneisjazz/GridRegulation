package com.jstech.gridregulation.api;

import com.jstech.gridregulation.bean.AddTaskBean;
import com.jstech.gridregulation.http.HttpPostService;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.Api.BaseApi;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * 增加检查任务的接口
 */
public class AddTaskApi extends BaseApi {

    AddTaskBean params;

    public AddTaskApi() {
        setCache(false);
        setMethod(MyUrl.ADD_TASK);
    }

    public AddTaskBean getParams() {
        return params;
    }

    public void setParams(AddTaskBean params) {
        this.params = params;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpPostService service = retrofit.create(HttpPostService.class);
        return service.addTask(getParams());
    }
}
