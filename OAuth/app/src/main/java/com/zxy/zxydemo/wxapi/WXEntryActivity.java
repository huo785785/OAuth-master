package com.zxy.zxydemo.wxapi;

import android.content.Intent;
import android.widget.Toast;

import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.zxy.zxydemo.R;
import com.zxy.zxydemo.base.BaseActivity;
import com.zxy.zxydemo.controller.WeiXinShare;

public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {



    @Override
    public void initData() {
        // TODO Auto-generated method stub
        super.initData();
    }

    @Override
    public void initView() {
        // TODO Auto-generated method stub
        super.initView();
        //如果分享的时候，该界面没有开启，那么微信开始这个activity时，会调用onCreate，所以这里要处理微信的返回结果
        WeiXinShare.api.handleIntent(getIntent(), this);
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq baseReq) {

    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    @Override
    public void onResp(BaseResp resp) {
        int result = 0;
        System.out.println(resp.errCode+"dsa");
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = R.string.errcode_success;
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = R.string.errcode_cancel;
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = R.string.errcode_deny;
                break;
            default:
                result = R.string.errcode_unknown;
                break;
        }

        Toast.makeText(this, result, Toast.LENGTH_LONG).show();

        finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //如果分享的时候，该已经开启，那么微信开始这个activity时，会调用onNewIntent，所以这里要处理微信的返回结果
        setIntent(intent);
        WeiXinShare.api.handleIntent(intent, this);
    }



    @Override
    public int getLayout() {
        return R.layout.activity_login;
    }

}
