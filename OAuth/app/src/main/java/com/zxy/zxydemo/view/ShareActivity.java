package com.zxy.zxydemo.view;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.constant.WBConstants;
import com.tencent.connect.common.Constants;
import com.zxy.zxydemo.R;
import com.zxy.zxydemo.base.BaseActivity;
import com.zxy.zxydemo.contants.Config;
import com.zxy.zxydemo.controller.QQshare;
import com.zxy.zxydemo.controller.WeiXinShare;
import com.zxy.zxydemo.widget.ShareDialog;

/**
 * Created by Administrator on 2016/4/25.
 */
public class ShareActivity extends BaseActivity implements View.OnClickListener, IWeiboHandler.Response{
    private TextView tv_share;
    private QQshare qQshare;
    private IWeiboShareAPI mWeiboShareAPI = null;
    private WeiXinShare inShare;
    @Override
    public int getLayout() {
        return R.layout.activity_share;
    }

    @Override
    public void initEvent() {
        super.initEvent();
        tv_share.setOnClickListener(this);
    }

    @Override
    public void initData() {
        super.initData();
        // 创建微博分享接口实例
        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(this,
                Config.WEIBO_APP_KEY);

        // 注册第三方应用到微博客户端中，注册成功后该应用将显示在微博的应用列表中。
        // 但该附件栏集成分享权限需要合作申请，详情请查看 Demo 提示
        // NOTE：请务必提前注册，即界面初始化的时候或是应用程序初始化时，进行注册
        mWeiboShareAPI.registerApp();

        // 当 Activity 被重新初始化时（该 Activity 处于后台时，可能会由于内存不足被杀掉了），
        // 需要调用 {@link IWeiboShareAPI#handleWeiboResponse} 来接收微博客户端返回的数据。
        // 执行成功，返回 true，并调用 {@link IWeiboHandler.Response#onResponse}；
        // 失败返回 false，不调用上述回调
        if (savedInstanceState != null) {
            mWeiboShareAPI.handleWeiboResponse(getIntent(), this);
        }
        qQshare = new QQshare(this);
        inShare=new WeiXinShare(this);
    }

    @Override
    public void initView() {
        super.initView();
        tv_share = (TextView) findViewById(R.id.tv_share);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_share:
                ShareDialog shareDialog = new ShareDialog(this, qQshare, mWeiboShareAPI,inShare);
                shareDialog.show();
                break;

            default:
                break;
        }
    }

    @Override
    public void onResponse(BaseResponse baseResp) {
        if (baseResp != null) {
            switch (baseResp.errCode) {
                case WBConstants.ErrorCode.ERR_OK:
                    Toast.makeText(this, "分享成功", Toast.LENGTH_LONG).show();
                    break;
                case WBConstants.ErrorCode.ERR_CANCEL:
                    Toast.makeText(this, "分享取消", Toast.LENGTH_LONG).show();
                    break;
                case WBConstants.ErrorCode.ERR_FAIL:
                    Toast.makeText(this, "分享失败" + baseResp.errMsg,
                            Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (qQshare.mTencent != null) {
            qQshare.mTencent.releaseResource();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        // 从当前应用唤起微博并进行分享后，返回到当前应用时，需要在此处调用该函数
        // 来接收微博客户端返回的数据；执行成功，返回 true，并调用
        // {@link IWeiboHandler.Response#onResponse}；失败返回 false，不调用上述回调
        mWeiboShareAPI.handleWeiboResponse(intent, this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_QQ_SHARE) {
            qQshare.mTencent.onActivityResultData(requestCode, resultCode,
                    data, qQshare);
        } else {

        }
    }
}
