package com.wyuyc.shoppingmall.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.wyuyc.shoppingmall.R;

public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //启动主界面
                startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                finish();
            }
        }, 1000);
    }
}
