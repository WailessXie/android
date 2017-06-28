package com.xieboy.walk.email;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by Walk on 2017/5/28.
 */

public class WelcomeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        new Handler().postDelayed(new Runnable(){
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this,LoginActivity.class);//跳转到LoginActivity
                startActivity(intent);
                WelcomeActivity.this.finish();
            }
        }, 2500);//运行2500ms
    }
}
