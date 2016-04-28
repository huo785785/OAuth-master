package com.zxy.zxydemo.view;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.connect.common.Constants;
import com.tencent.tauth.Tencent;
import com.zxy.zxydemo.R;
import com.zxy.zxydemo.base.BaseActivity;
import com.zxy.zxydemo.controller.QQLogin;
import com.zxy.zxydemo.controller.WeiBoLogin;

public class LoinActivity extends BaseActivity implements View.OnClickListener {
    private TextView qq_login, weibo_login, weixin_login;
    private QQLogin qqLogin;
    private WeiBoLogin weiboLogin;
    @Override
    public int getLayout() {
        return R.layout.activity_login;
    }

    /**
     * hhha我是hth的分支
     */
    @Override
    public void initEvent() {
        super.initEvent();
        qq_login.setOnClickListener(this);
        weibo_login.setOnClickListener(this);
        weixin_login.setOnClickListener(this);
    }

    @Override
    public void initData() {
        super.initData();
        qqLogin = new QQLogin(this);
        weiboLogin=new WeiBoLogin(this);
    }
//dsad
    @Override
    public void initView() {
        super.initView();
        qq_login = (TextView) findViewById(R.id.tv_tx);
        weibo_login = (TextView) findViewById(R.id.tv_wb);
        weixin_login = (TextView) findViewById(R.id.tv_wx);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_tx:
                if (!QQLogin.mTencent.isSessionValid()) {
                    QQLogin.mTencent.login(this, "all", qqLogin);
                }
                break;
            case R.id.tv_wb:
                /**
                 * 这个是有客户端吊起客户端，没有则网页
                 */
                WeiBoLogin.mSsoHandler.authorize(weiboLogin);
                break;
            case R.id.tv_wx:
                Toast.makeText(this, R.string.weibosdk_demo_toast_auth_AppID_failed, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * 当 SSO 授权 Activity 退出时，该函数被调用。
     *
     * @see {@link Activity#onActivityResult}
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == Constants.REQUEST_LOGIN
                || requestCode == Constants.REQUEST_APPBAR) {
            Tencent.onActivityResultData(requestCode, resultCode, data,
                    qqLogin);
        }else{
            // SSO 授权回调
            // 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResults
            if (WeiBoLogin.mSsoHandler != null) {
                WeiBoLogin.mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
            }
        }

    }
}
