package com.wyuyc.shoppingmall.home.activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wyuyc.shoppingmall.R;
import com.wyuyc.shoppingmall.home.bean.ResultBeanData;
import com.wyuyc.shoppingmall.utils.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ActInfoActivity extends Activity {

    @Bind(R.id.wv_act_info)
    WebView wvActInfo;

    private ResultBeanData.ResultBean.ActInfoBean actInfoBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_info);
        ButterKnife.bind(this);
        actInfoBean = (ResultBeanData.ResultBean.ActInfoBean) getIntent().getSerializableExtra("act_info");
        if (actInfoBean != null) {
            setDataForView(actInfoBean);
        }
    }

    private void setDataForView(ResultBeanData.ResultBean.ActInfoBean actInfoBean) {
        wvActInfo.loadUrl(Constants.BASE_URl_IMAGE + actInfoBean.getUrl());
        WebSettings settings = wvActInfo.getSettings();
        //设置支持JS
        settings.setJavaScriptEnabled(true);
        //设置支持双击页面变大变小
        settings.setUseWideViewPort(true);
        //设置优先使用缓存
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //
        wvActInfo.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(request.getUrl().toString());
                }
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
    }
}
