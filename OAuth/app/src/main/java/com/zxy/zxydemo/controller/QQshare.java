package com.zxy.zxydemo.controller;

import android.content.Context;
import android.widget.Toast;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.zxy.zxydemo.contants.Config;

/**
 * Created by Administrator on 2016/4/25.
 */
public class QQshare implements IUiListener{
    private Context context;
    public static Tencent mTencent;

    public QQshare(Context context) {
        this.context = context;
        if (mTencent == null) {
            mTencent = Tencent.createInstance(Config.QQ_APP_ID, context);
        }
    }

    @Override
    public void onComplete(Object o) {
        Toast.makeText(context, "分享成功", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onError(UiError uiError) {
        Toast.makeText(context, "分享失败", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCancel() {
        Toast.makeText(context, "分享取消", Toast.LENGTH_LONG).show();
    }
}
