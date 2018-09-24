package com.jstech.gridregulation.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;

import com.jstech.gridregulation.R;
import com.jstech.gridregulation.base.BaseActivity;
import com.jstech.gridregulation.fragment.PersonalCenterFragment;
import com.jstech.gridregulation.fragment.ProblemReportFragment;
import com.jstech.gridregulation.fragment.SiteRegulateMapFragment;

public class MainActivity extends BaseActivity {

    BottomNavigationView bottomNavigationView;
    PersonalCenterFragment personalCenterFragment = PersonalCenterFragment.newInstance();
    ProblemReportFragment problemReportFragment = ProblemReportFragment.newInstance();
    SiteRegulateMapFragment siteRegulateMapFragment = SiteRegulateMapFragment.newInstance();
    FragmentManager fm = getSupportFragmentManager();
    Fragment active = siteRegulateMapFragment;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        setToolBarTitle("网格化监管");
        setToolSubBarTitle("");
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        fm.beginTransaction().add(R.id.frame_content, personalCenterFragment, "个人中心").hide(personalCenterFragment).commit();
        fm.beginTransaction().add(R.id.frame_content, problemReportFragment, "线索上报").hide(problemReportFragment).commit();
        fm.beginTransaction().add(R.id.frame_content, siteRegulateMapFragment, "现场检查").commit();

    }

    BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.site_regulate:
                    fm.beginTransaction().hide(active).show(siteRegulateMapFragment).commit();
                    active = siteRegulateMapFragment;
                    return true;
                case R.id.problem_report:
                    fm.beginTransaction().hide(active).show(problemReportFragment).commit();
                    active = problemReportFragment;
                    return true;
                case R.id.personal_center:
                    fm.beginTransaction().hide(active).show(personalCenterFragment).commit();
                    active = personalCenterFragment;
                    return true;
                default:
                    break;
            }
            return false;
        }
    };
}
