package com.zxy.zxydemo.controller;

import android.content.Context;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.zxy.zxydemo.contants.Config;

/**
 * Created by Administrator on 2016/4/25.
 */
public class WeiXinShare {
    public static IWXAPI api;
    private Context context;

    public WeiXinShare(Context context) {
        this.context = context;
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(context, Config.WEIXIN_APP_ID, false);
        // 将该app注册到微信
        api.registerApp(Config.WEIXIN_APP_ID);
    }
}
