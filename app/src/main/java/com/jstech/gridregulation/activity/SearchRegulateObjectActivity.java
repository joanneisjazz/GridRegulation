package com.jstech.gridregulation.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import com.jstech.gridregulation.MyApplication;
import com.jstech.gridregulation.R;
import com.jstech.gridregulation.adapter.SearchObjectAdapter;
import com.jstech.gridregulation.base.BaseActivity;
import com.jstech.gridregulation.bean.RegulateObjectBean;
import com.jstech.gridregulation.db.RegulateObjectBeanDao;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by hesm on 2018/9/24.
 */

public class SearchRegulateObjectActivity extends BaseActivity {

    @BindView(R.id.edt_search)
    EditText edtSearch;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    List<RegulateObjectBean> data = new ArrayList<>();
    List<RegulateObjectBean> historyList = new ArrayList<>();
    SearchObjectAdapter mAdapter;

    RegulateObjectBeanDao regulateObjectBeanDao;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_regulate_object;
    }

    @Override
    public void initView() {
        regulateObjectBeanDao = MyApplication.getInstance().getSession().getRegulateObjectBeanDao();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SearchObjectAdapter(data, this, R.layout.item_searh_object);
        recyclerView.setAdapter(mAdapter);
        loadHistory();
        setlistener();

    }


    /**
     * 从本地数据库查询历史搜索的记录
     */
    private void loadHistory() {
        Observable.just(MyApplication.getInstance().getUserBean().getUserId())
                .observeOn(Schedulers.newThread())
                .map(new Func1<String, List<RegulateObjectBean>>() {

                    @Override
                    public List<RegulateObjectBean> call(String s) {
                        ArrayList<RegulateObjectBean> list = (ArrayList<RegulateObjectBean>) regulateObjectBeanDao.queryBuilder()
                                .where(RegulateObjectBeanDao.Properties.UserId.eq(s), RegulateObjectBeanDao.Properties.IsSearched.eq("1"))
                                .list();
                        if (null != list) {
                            historyList.addAll(list);
                            return historyList;
                        } else {
                            return null;
                        }
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<RegulateObjectBean>>() {
                    @Override
                    public void call(List<RegulateObjectBean> o) {
                        if (null != o) {
                            data.clear();
                            data.addAll(historyList);
                        } else {
                            Toast.makeText(SearchRegulateObjectActivity.this, "无历史查询企业", Toast.LENGTH_LONG);
                        }

                    }
                });
    }

    /**
     * 设置输入框的监听，模糊搜索符合条件的企业
     */
    private void setlistener() {
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (null == charSequence || "".equals(charSequence)) {
                    data.clear();
                    data.addAll(historyList);
                    mAdapter.notifyDataSetChanged();
                    return;
                }

                Observable.just(charSequence.toString())
                        .observeOn(Schedulers.newThread())
                        .map(new Func1<String, List<RegulateObjectBean>>() {

                            @Override
                            public List<RegulateObjectBean> call(String s) {
                                ArrayList<RegulateObjectBean> list = (ArrayList<RegulateObjectBean>) regulateObjectBeanDao.queryBuilder().where(RegulateObjectBeanDao.Properties.UserId.eq(MyApplication.getInstance().getUserBean().getUserId()),
                                        RegulateObjectBeanDao.Properties.Name.like("%" + s + "%")).list();
                                if (null != list) {
                                    data.addAll(list);
                                    return data;
                                } else {
                                    return null;
                                }
                            }
                        }).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<List<RegulateObjectBean>>() {
                            @Override
                            public void call(List<RegulateObjectBean> o) {
                                if (null != o) {
                                    mAdapter.notifyDataSetChanged();
                                }

                            }
                        });
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //更新搜索记录，将该企业设为已经搜索过
                RegulateObjectBean bean = data.get(i);
                bean.setIsSearched("1");
                regulateObjectBeanDao.update(bean);
                Intent intent = new Intent();
                intent.putExtra("id", bean.getId());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
