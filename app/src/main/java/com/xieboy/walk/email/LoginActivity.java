package com.xieboy.walk.email;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import javax.mail.Store;

/**
 * Created by Walk on 2017/5/30.
 */

public class LoginActivity extends AppCompatActivity {
    private EditText et_user, et_pw;
    private Button btn_login;
    private CheckBox cb_savepw, cb_autologin;
    private Context context;
    private Handler handler;

    private static final String POP3HOST = "pop3host";
    private static final String SMTPHOST = "smtphost";
    private static final String SAVEPASSWORD = "savepassword";
    private static final String AUTOLOGIN = "autologin";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        handler = new MyHandler(this);
        setContentView(R.layout.activity_login);
        initView();
        if (PreferencesUtil.getSharedBooleanData(context, AUTOLOGIN)) {
            login();
        } else {
//            setContentView(R.layout.activity_login);
//            initView();
            setListener();
        }
        if (PreferencesUtil.getSharedBooleanData(context, SAVEPASSWORD)) {
            cb_savepw.setChecked(PreferencesUtil.getSharedBooleanData(context, SAVEPASSWORD));
            et_user.setText(PreferencesUtil.getSharedStringData(context, USERNAME));
            et_pw.setText(PreferencesUtil.getSharedStringData(context, PASSWORD));
        }
    }

    private void setListener() {
        cb_autologin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cb_savepw.setEnabled(!isChecked);
                if (isChecked) {
                    cb_savepw.setChecked(isChecked);
                }
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_user.getText()) || TextUtils.isEmpty(et_pw.getText())) {
                    Toast.makeText(getApplicationContext(), "账户名或密码不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
                PreferencesUtil.setSharedBooleanData(context, SAVEPASSWORD, cb_savepw.isChecked());
                PreferencesUtil.setSharedBooleanData(context, AUTOLOGIN, cb_autologin.isChecked());
                PreferencesUtil.setSharedStringData(context, USERNAME, et_user.getText().toString());
                PreferencesUtil.setSharedStringData(context, PASSWORD, et_pw.getText().toString());
                PreferencesUtil.setSharedStringData(context, POP3HOST, Connect.getPOP3Host(et_user.getText().toString()));
                PreferencesUtil.setSharedStringData(context, SMTPHOST, Connect.getSMTPHost(et_user.getText().toString()));
                login();
            }
        });
    }

    private void initView() {
        et_user = (EditText) findViewById(R.id.et_user);
        et_pw = (EditText) findViewById(R.id.et_pw);
        btn_login = (Button) findViewById(R.id.btn_login);
        cb_savepw = (CheckBox) findViewById(R.id.cb_savepw);
        cb_autologin = (CheckBox) findViewById(R.id.cb_autologin);
    }

    private static class MyHandler extends Handler {
        private WeakReference<LoginActivity> weakReference;

        public MyHandler(LoginActivity activity) {
            this.weakReference = new WeakReference<LoginActivity>(activity);
        }

        public void handleMessage(android.os.Message msg) {
            final LoginActivity activity = weakReference.get();
            switch (msg.what) {
                case 0:
                    Toast.makeText(activity.getApplicationContext(), "账户名或授权码错误", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }

    private void login() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Store store = Connect.login(PreferencesUtil.getSharedStringData(context, POP3HOST), PreferencesUtil.getSharedStringData(context, USERNAME),
                        PreferencesUtil.getSharedStringData(context, PASSWORD));
                if (store != null) {
                    startActivity(new Intent(context, MainActivity.class));
                    finish();
                } else {
                    handler.obtainMessage(0).sendToTarget();
                }
            }
        }).start();
    }

}
