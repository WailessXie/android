package com.xieboy.walk.email;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * Created by Walk on 2017/5/3.
 */

public class activity_detail  extends AppCompatActivity {
    AppCompatTextView title;
    AppCompatTextView date;
    AppCompatTextView content;
    AppCompatTextView reciever;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        title=(AppCompatTextView)findViewById(R.id.title);
        date=(AppCompatTextView)findViewById(R.id.date);
        content=(AppCompatTextView)findViewById(R.id.all_content);
        reciever=(AppCompatTextView)findViewById(R.id.reciever);
        Intent intent=this.getIntent();
        title.setText("主题："+intent.getStringExtra("title"));
        date.setText("发送日期："+intent.getStringExtra("date"));
        content.setText("正文："+intent.getStringExtra("content"));
        reciever.setText("接收者："+intent.getStringExtra("reciever"));
    }
}
