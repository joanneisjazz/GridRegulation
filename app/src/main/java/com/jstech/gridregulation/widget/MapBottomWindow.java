package com.jstech.gridregulation.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jstech.gridregulation.R;
import com.jstech.gridregulation.utils.SystemUtil;

/**
 * 在地图上显示的底部弹框
 */
public class MapBottomWindow {

    PopupWindow mPopupWindow;
    TextView tvAddress, tvDistance, tvDetails, tvTel;
    Button btnCheck;
    private Context mContext;
    View contentview;

    public MapBottomWindow(Context mContext) {
        this.mContext = mContext;
    }

    public MapBottomWindow(Builder builder) {
        mContext = builder.context;
        contentview = LayoutInflater.from(mContext).inflate(R.layout.layout_map_bottom_window, null);
        mPopupWindow =
                new PopupWindow(contentview, builder.width, builder.height, builder.fouse);
        tvAddress = contentview.findViewById(R.id.tv_object_name);
        tvDistance = contentview.findViewById(R.id.tv_distance);
        tvDetails = contentview.findViewById(R.id.tv_details);
        tvTel = contentview.findViewById(R.id.tv_tel);
        btnCheck = contentview.findViewById(R.id.btn_check);

        setOnClickListener(builder.listener);
        btnCheck.setText(builder.status);

        mPopupWindow.setOutsideTouchable(builder.outsidecancel);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        mPopupWindow.setAnimationStyle(builder.animstyle);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                SystemUtil.setBackgroundAlpha(1.0f, (Activity) mContext);
            }
        });
    }

    /**
     * 设置点击事件
     *
     * @param listener
     */
    private void setOnClickListener(View.OnClickListener listener) {
        if (null == btnCheck) {
            return;
        }
        btnCheck.setOnClickListener(null);
        if (null != listener) {
            btnCheck.setOnClickListener(listener);
        }
    }

    /**
     * builder 类
     */
    public static class Builder {
        private int width;
        private int height;
        private boolean fouse;
        private boolean outsidecancel;
        private int animstyle;
        private String status;
        private View.OnClickListener listener;

        private Context context;

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder setListener(View.OnClickListener listener) {
            this.listener = listener;
            return this;
        }

        public Builder setContext(Context context) {
            this.context = context;
            return this;
        }

        public Builder setwidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setheight(int height) {
            this.height = height;
            return this;
        }

        public Builder setFouse(boolean fouse) {
            this.fouse = fouse;
            return this;
        }

        public Builder setOutSideCancel(boolean outsidecancel) {
            this.outsidecancel = outsidecancel;
            return this;
        }

        public Builder setAnimationStyle(int animstyle) {
            this.animstyle = animstyle;
            return this;
        }

        public MapBottomWindow builder() {
            return new MapBottomWindow(this);
        }
    }
}
