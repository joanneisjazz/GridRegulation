package com.jstech.gridregulation.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.jstech.gridregulation.ConstantValue;
import com.jstech.gridregulation.MyApplication;
import com.jstech.gridregulation.R;
import com.jstech.gridregulation.utils.LogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 手写签字
 * 检查人签字（2个）
 * 被检企业负责人签字（1个）
 */
public class SignatureActivity extends Activity implements View.OnClickListener {


    SignaturePad signaturePad;
    Button btnSave, btnClear;
    public String signType = "";//

    public static final String SIGN_TYPE_REGULATOR_1 = "1";//检查人1
    public static final String SIGN_TYPE_REGULATOR_2 = "2";//检查人2
    public static final String SIGN_TYPE_OBJECT = "0";//被检单位负责人
    public String strPictureName = "";
    MyApplication application;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);
        application = (MyApplication) getApplication();
        if (signType.equals(SIGN_TYPE_REGULATOR_1)) {
            strPictureName = "regulator_1_" + application.getUserBean().getUserName();

        } else if (signType.equals(SIGN_TYPE_REGULATOR_2)) {
            strPictureName = "regulator_2_" + application.getUserBean().getUserName();

        } else {
            strPictureName = "object_" + application.getUserBean().getUserName();
        }
        signaturePad = findViewById(R.id.sign_pad);
        btnClear = findViewById(R.id.btn_clear);
        btnSave = findViewById(R.id.btn_save);
        btnClear.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        signaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {

            }

            @Override
            public void onSigned() {

            }

            @Override
            public void onClear() {
                Toast.makeText(SignatureActivity.this, "清除当前签名，请重新签名", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_clear:
                signaturePad.clear();
                break;
            case R.id.btn_save:
                Observable.just(signaturePad.getSignatureBitmap())
                        .observeOn(Schedulers.newThread())
                        .map(new Func1<Bitmap, String>() {
                            @Override
                            public String call(Bitmap bitmap) {
                                return addJpgSignatureToGallery(signaturePad.getSignatureBitmap());
                            }
                        }).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<String>() {
                            @Override
                            public void onCompleted() {
                                LogUtils.d("保存成功");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(SignatureActivity.this, e.getMessage(), Toast.LENGTH_SHORT);
                            }

                            @Override
                            public void onNext(String s) {
                                Intent intent = new Intent();
                                intent.putExtra("path", s);
                                SignatureActivity.this.setResult(RESULT_OK, intent);
                                SignatureActivity.this.finish();
                            }
                        });


                break;
            default:
                break;
        }
    }

    public String addJpgSignatureToGallery(Bitmap signature) {
        File photo = null;
        try {
            photo = new File(getAlbumStorageDir(ConstantValue.PATH_SIGN_PICTURE), String.format("Signature_%d.jpg", System.currentTimeMillis()));
            LogUtils.d("photo path = " + photo.getAbsolutePath());
            saveBitmapToJPG(signature, photo);
            scanMediaFile(photo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return photo.getAbsolutePath();
    }

    public File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
//        File file = new File(Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_PICTURES), albumName);
        File file = new File(Environment.getExternalStorageDirectory(), albumName);
        if (!file.mkdirs()) {
            Log.e("SignaturePad", "Directory not created");
        }
        return file;
    }

    public void saveBitmapToJPG(Bitmap bitmap, File photo) throws IOException {
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        OutputStream stream = new FileOutputStream(photo);
        newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        stream.close();
    }

    private void scanMediaFile(File photo) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(photo);
        mediaScanIntent.setData(contentUri);
        SignatureActivity.this.sendBroadcast(mediaScanIntent);
    }
}
