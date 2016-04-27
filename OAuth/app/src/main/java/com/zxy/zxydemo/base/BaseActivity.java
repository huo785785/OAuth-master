package com.zxy.zxydemo.base;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Administrator on 2016/4/22.
 */
public abstract  class BaseActivity extends Activity{
    public Bundle savedInstanceState;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        this.savedInstanceState=savedInstanceState;
        initView();
        initData();
        initEvent();

    }

    public void initEvent() {

    }


    public void initData() {

    }

    public void initView() {
    }
    public abstract int getLayout();
}
