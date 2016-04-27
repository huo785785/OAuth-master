package com.zxy.zxydemo.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.zxy.zxydemo.R;
import com.zxy.zxydemo.contants.Config;
import com.zxy.zxydemo.utils.AccessTokenKeeper;
import com.zxy.zxydemo.view.ShareActivity;

/**
 * Created by Administrator on 2016/4/25.
 */
public class WeiBoLogin implements WeiboAuthListener{
    private Oauth2AccessToken mAccessToken;
    public static SsoHandler mSsoHandler;
    private AuthInfo mAuthInfo;
    private Context context;

    public WeiBoLogin(Context context) {
        this.context = context;
        mAuthInfo = new AuthInfo(context, Config.WEIBO_APP_KEY,
                Config.REDIRECT_URL, Config.SCOPE);
        mSsoHandler = new SsoHandler((Activity) context, mAuthInfo);
        mAccessToken = AccessTokenKeeper.readAccessToken(context);
    }

    @Override
    public void onComplete(Bundle values) {
        mAccessToken = Oauth2AccessToken.parseAccessToken(values);
        if (mAccessToken.isSessionValid()) {
            AccessTokenKeeper
                    .writeAccessToken((Activity) context, mAccessToken);
            String UID=mAccessToken.getUid();
            String accessToken=mAccessToken.getToken();
            Log.d("gaolei", "WeiBoLogin,KEY_UID:" + mAccessToken.getUid() + ",KEY_ACCESS_TOKEN:" + mAccessToken.getToken());
            Toast.makeText((Activity) context,
                    R.string.weibosdk_demo_toast_auth_success,
                    Toast.LENGTH_SHORT).show();
            Toast.makeText((Activity) context,
                    "WeiBoLogin,KEY_UID:"+mAccessToken.getUid()+",KEY_ACCESS_TOKEN:"+mAccessToken.getToken(),
                    Toast.LENGTH_SHORT).show();
            context.startActivity(new Intent(context, ShareActivity.class));
        } else {
            String code = values.getString("code");
            String message = context
                    .getString(R.string.weibosdk_demo_toast_auth_failed);
            if (!TextUtils.isEmpty(code)) {
                message = message + "\ncode: " + code;
            }
            Toast.makeText((Activity) context, message, Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public void onWeiboException(WeiboException e) {
        Toast.makeText((Activity) context, R.string.weibosdk_demo_toast_auth_failed, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancel() {
        Toast.makeText((Activity) context,
                R.string.weibosdk_demo_toast_auth_canceled, Toast.LENGTH_SHORT)
                .show();
    }
}
