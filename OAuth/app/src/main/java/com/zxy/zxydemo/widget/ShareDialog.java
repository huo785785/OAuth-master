package com.zxy.zxydemo.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.WeiboMessage;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMessageToWeiboRequest;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.zxy.zxydemo.R;
import com.zxy.zxydemo.contants.Config;
import com.zxy.zxydemo.controller.QQshare;
import com.zxy.zxydemo.controller.WeiXinShare;
import com.zxy.zxydemo.utils.AccessTokenKeeper;
import com.zxy.zxydemo.utils.DensityUtils;
import com.zxy.zxydemo.utils.Util;

/**
 * Created by Administrator on 2016/4/25.
 */
public class ShareDialog extends Dialog implements View.OnClickListener {
    private static final int THUMB_SIZE = 150;
    private Activity activity;
    private IWeiboShareAPI boshare;
    private QQshare qQshare;
    private WeiXinShare inShare;
    public ShareDialog(Context context, int theme) {
        super(context, theme);
    }

    public ShareDialog(Activity activity, QQshare qQshare, IWeiboShareAPI boshare,WeiXinShare inShare) {
        this(activity, R.style.share_Dialog);
        this.activity = activity;
        this.qQshare = qQshare;
        this.boshare = boshare;
        this.inShare=inShare;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_share_dialog);
        Window window = getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.alpha = 1.0f;
        wl.gravity = Gravity.BOTTOM;
        wl.width = DensityUtils.getWindowsWidth(getContext());
        window.setAttributes(wl);
        setCancelable(true);
        setCanceledOnTouchOutside(true);

        findViewById(R.id.weibo_sina).setOnClickListener(this);
        findViewById(R.id.weibo_tt).setOnClickListener(this);
        findViewById(R.id.wechat).setOnClickListener(this);
        findViewById(R.id.QQzone).setOnClickListener(this);
        findViewById(R.id.qq).setOnClickListener(this);
        findViewById(R.id.friends).setOnClickListener(this);
        findViewById(R.id.message).setOnClickListener(this);
        findViewById(R.id.tv_shareCancel).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.weibo_sina:
                testShareImage(v);
                cancel();
                break;
            case R.id.weibo_tt:

                break;
            case R.id.wechat:
                share2Wx(true);
                cancel();
                break;
            case R.id.QQzone:
                qQshare.mTencent.shareToQQ(activity, shareOnlyImageOnQZone(v),
                        qQshare);
                cancel();
                break;
            case R.id.qq:
                qQshare.mTencent
                        .shareToQQ(activity, shareOnlyImageOnQQ(v), qQshare);
                cancel();
                break;
            case R.id.friends:
                share2Wx(false);
                cancel();
                break;
            case R.id.message:

                break;
            case R.id.tv_shareCancel:
                cancel();
                break;
        }

    }

    /**
     * qq空间分享
     *
     * @param view
     * @return
     */
    public Bundle shareOnlyImageOnQZone(View view) {
        Bundle params = new Bundle();
        // 本地地址一定要传sdcard路径，不要什么getCacheDir()或getFilesDir()
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,
                "http://imgcache.qq.com/music/photo/mid_album_300/V/E/000J1pJ50cDCVE.jpg");
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "测试应用");
        params.putString(QQShare.SHARE_TO_QQ_TITLE, "测测更健康");
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,
                "http://y.qq.com/i/song.html?songid=XXX&source=mobileQQ#wechat_redirect");
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,
                "http://y.qq.com/i/song.html?songid=XXX&source=mobileQQ#wechat_redirect");
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "测试应用");
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "OOXX这里是摘要，是摘要是摘要是摘要");
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,
                QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN); // 打开这句话，可以实现分享纯图到QQ空间
        return params;
    }

    /**
     * qq分享
     *
     * @param view
     * @return
     */
    public Bundle shareOnlyImageOnQQ(View view) {
        Bundle params = new Bundle();
        // 本地地址一定要传sdcard路径，不要什么getCacheDir()或getFilesDir()
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,
                "http://imgcache.qq.com/music/photo/mid_album_300/V/E/000J1pJ50cDCVE.jpg");
        params.putString(QQShare.SHARE_TO_QQ_TITLE, "测测更健康");
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,
                "http://y.qq.com/i/song.html?songid=XXX&source=mobileQQ#wechat_redirect");
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "测试应用");
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "OOXX这里是摘要，是摘要是摘要是摘要");
        // params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE,
        // QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);//控制分享类型
        return params;
    }

    /**
     * 新浪微博分享
     * @param view
     */
    public void testShareImage(View view) {
        WeiboMessage weiboMessage = new WeiboMessage();
        ImageObject imageObject = new ImageObject();
        Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(),
                R.mipmap.ic_launcher);
        imageObject.setImageObject(bitmap);
        weiboMessage.mediaObject = imageObject;
        SendMessageToWeiboRequest request = new SendMessageToWeiboRequest();
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.message = weiboMessage;
        AuthInfo authInfo = new AuthInfo(activity, Config.WEIBO_APP_KEY, Config.REDIRECT_URL, Config.SCOPE);
        Oauth2AccessToken accessToken = AccessTokenKeeper.readAccessToken(activity);
        String token = "";
        if (accessToken != null) {
            token = accessToken.getToken();
        }

        boshare.sendRequest(activity, request, authInfo, token, new WeiboAuthListener() {

            @Override
            public void onWeiboException( WeiboException arg0 ) {
                Toast.makeText(activity, "onAuthorizeComplete token = " + "WeiboException", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onComplete( Bundle bundle ) {
                // TODO Auto-generated method stub
                Oauth2AccessToken newToken = Oauth2AccessToken.parseAccessToken(bundle);
                AccessTokenKeeper.writeAccessToken(activity, newToken);
                Toast.makeText(activity, "onAuthorizeComplete token = " + newToken.getToken(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(activity, "onAuthorizeComplete token = " + "cancel", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * @param isShareFriend true 分享到朋友，false分享到朋友圈
     */
    private void share2Wx(boolean isShareFriend) {
        Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), R.mipmap.ic_launcher);
        WXImageObject imgObj = new WXImageObject(bitmap);

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, THUMB_SIZE, THUMB_SIZE, true);//缩略图大小
        bitmap.recycle();
        msg.thumbData = Util.bmpToByteArray(thumbBmp, true);  // 设置缩略图
        msg.title = "笔记";
        msg.description = "哈哈哈";
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = isShareFriend ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        inShare.api.sendReq(req);
    }
    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}
