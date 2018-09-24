package com.jstech.gridregulation.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.bumptech.glide.Glide;
import com.jstech.gridregulation.R;
import com.jstech.gridregulation.base.BaseActivity;
import com.jstech.gridregulation.utils.LogUtils;

import java.io.File;

import butterknife.BindView;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

public class SiteCheckUploadActivity extends BaseActivity {

    @BindView(R.id.btn_regulator_sign)
    Button btnRegulatorSign;
    @BindView(R.id.btn_object_sign)
    Button btnObjectSign;
    @BindView(R.id.iv_regulator_sign_1)
    ImageView ivRegulatorSign1;
    @BindView(R.id.iv_regulator_sign_2)
    ImageView ivRegulatorSign2;
    @BindView(R.id.iv_object)
    ImageView ivObjectSign;

    public final static int REQUEST_CODE_REGULATOR_1 = 100;
    public final static int REQUEST_CODE_REGULATOR_2 = 101;
    public final static int REQUEST_CODE_OBJECT = 102;

    private boolean isFisrtRegulator = true;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_site_check_upload;
    }

    @Override
    protected void onResume() {
        /**
         * 设置为横屏
         */
//        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        }
        super.onResume();
    }

    @Override
    public void initView() {

        btnRegulatorSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SiteCheckUploadActivity.this, SignatureActivity.class);
                if (isFisrtRegulator) {
                    startActivityForResult(intent, REQUEST_CODE_REGULATOR_1);
                } else {
                    startActivityForResult(intent, REQUEST_CODE_REGULATOR_2);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_REGULATOR_1://第一个检查人签字
                    isFisrtRegulator = false;
                    String path = data.getStringExtra("path");
                    Observable.just(path).doOnSubscribe(new Action0() {
                        @Override
                        public void call() {

                        }
                    }).map(new Func1<String, Bitmap>() {
                        @Override
                        public Bitmap call(String s) {
                            Bitmap bitmap = BitmapDescriptorFactory.fromPath(s).getBitmap();
                            return bitmap;
                        }
                    }).subscribe(new Action1<Bitmap>() {
                        @Override
                        public void call(Bitmap bitmap) {
//                            Glide.with(SiteCheckUploadActivity.this).load("").into();
                        }
                    });
                    break;
                case REQUEST_CODE_REGULATOR_2://第二个检查人签字
                    break;
                case REQUEST_CODE_OBJECT:
                    break;
                default:
                    LogUtils.d(data.getStringExtra("1"));
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }
}
