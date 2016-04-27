package com.zxy.zxydemo.controller;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.zxy.zxydemo.R;
import com.zxy.zxydemo.contants.Config;
import com.zxy.zxydemo.view.ShareActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/4/25.
 */
public class QQLogin implements IUiListener{
    private Context context;
    public static Tencent mTencent;

    public QQLogin(Context context) {
        this.context = context;
        if (mTencent == null) {
            mTencent = Tencent.createInstance(Config.QQ_APP_ID, context);
        }
    }

    @Override
    public void onComplete(Object response) {
        if (null == response) {
            Toast.makeText(context, R.string.weibosdk_demo_toast_auth_failed,
                    Toast.LENGTH_SHORT).show();
            return;
        }
        JSONObject jsonResponse = (JSONObject) response;
        if (null != jsonResponse && jsonResponse.length() == 0) {
            Toast.makeText(context, R.string.weibosdk_demo_toast_auth_failed,
                    Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(context, R.string.weibosdk_demo_toast_auth_success,
                Toast.LENGTH_SHORT).show();

        String openId = null, accessToken = null;
        try {
            openId = ((JSONObject) response).getString(Constants.PARAM_OPEN_ID);
            accessToken = ((JSONObject) response)
                    .getString(Constants.PARAM_ACCESS_TOKEN);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Log.d("gaolei", "QQLogin,openId:" + openId + ",accessToken:"
                + accessToken + ",platform:" + "qq");
        Toast.makeText(context,
                "QQLogin,openId:" + openId + ",accessToken:" + accessToken,
                Toast.LENGTH_SHORT).show();
        doComplete((JSONObject) response);
    }

    protected void doComplete(JSONObject values) {
        context.startActivity(new Intent(context, ShareActivity.class));
    }

    @Override
    public void onError(UiError uiError) {
        Toast.makeText(context, R.string.weibosdk_demo_toast_auth_failed,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancel() {
        Toast.makeText(context, R.string.weibosdk_demo_toast_auth_canceled,
                Toast.LENGTH_SHORT).show();
    }
}
