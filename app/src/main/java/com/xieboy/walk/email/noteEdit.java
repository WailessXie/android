package com.xieboy.walk.email;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Walk on 2017/4/21.
 */

public class noteEdit extends AppCompatActivity {
    private TextView tv_date;
    private EditText et_content;
    private FloatingActionButton btn_ok;
    private FloatingActionButton btn_cancel;
    private NotesDB DB;
    private SQLiteDatabase dbread;
    public static int ENTER_STATE = 0;
    public static String last_content;
    public static int id;
    private Toolbar Toolbar;
    private MaterialEditText et_title;
    private MaterialEditText et_reciever;
    private MaterialEditText ete_content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.acivity_show);

        Toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(Toolbar);


        tv_date = (TextView) findViewById(R.id.tv_date);
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateString = simpleDateFormat.format(date);
        tv_date.setText(dateString);
        //et_content = (EditText) findViewById(R.id.et_content);

        et_title = (MaterialEditText) findViewById(R.id.title);
        et_reciever = (MaterialEditText) findViewById(R.id.reciever);
        ete_content = (MaterialEditText) findViewById(R.id.content);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        DB = new NotesDB(this);
        dbread = DB.getReadableDatabase();

        Bundle mybundle = this.getIntent().getExtras();
        last_content = mybundle.getString("info");
        Log.d("LAST_CONTENT", last_content);
        ete_content.setText(last_content);

        btn_ok = (FloatingActionButton) findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = ete_content.getText().toString();
                String title = et_title.getText().toString();
                String reciever = et_reciever.getText().toString();
                Log.d("LOG1", content);
// 获取写日志时间
                Date date = new Date();
                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                String dateNum = simpleDateFormat1.format(date);
                String sql;
                String sql_count = "select count(*) from note";
                SQLiteStatement sqLiteStatement = dbread.compileStatement(sql_count);
                long count = sqLiteStatement.simpleQueryForLong();
                Log.d("COUNT", count + "");
                Log.d("ENTER_STATE", ENTER_STATE + "");
                //添加一个新的日志
                if (ENTER_STATE == 0) {
                    if (!content.equals("")) {
                        sql = "insert into " + NotesDB.TABLE_NAME_NOTES
                                + "(" + NotesDB.COLUMN_NAME_ID + ","
                                + NotesDB.COLUMN_NAME_NOTE_CONTENT + ","
                                + NotesDB.COLUMN_NAME_NOTE_DATE + ","
                                + NotesDB.COLUMN_NAME_NOTE_RECIEVER + ","
                                + NotesDB.COLUMN_NAME_NOTE_TITLE + ")"
                                + " values(" + count + "," + "'" + content
                                + "'" + "," + "'" + dateNum + "','" + reciever + "', '" + title + "')";
                        Log.d("LOG", sql);
                        dbread.execSQL(sql);
                    }
                }
                Intent data = new Intent();
                setResult(2, data);
                finish();
            }
        });
        btn_cancel = (FloatingActionButton) findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
