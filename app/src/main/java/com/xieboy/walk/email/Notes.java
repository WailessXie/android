package com.xieboy.walk.email;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Walk on 2017/5/2.
 */

public class Notes {

    private SQLiteDatabase dbread;

    public Notes(Context context) {
        dbread = new NotesDB(context).getReadableDatabase();
    }

    public List<Map<String, Object>> getData() {
        Cursor cursor = dbread.query("note", null, "content!=\"\"", null, null,
                null, null);

        List<Map<String, Object>> dataList = new ArrayList<>();
        Map<String,Object> map1 = new HashMap<String,Object>();
        map1.put("cv_content","" );
        map1.put("cv_date","2017-04-30" );
        map1.put("cv_title", "介绍");
        map1.put("cv_reciever", "Wailess");
        dataList.add(0,  map1);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("content"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            String title = cursor.getString(cursor.getColumnIndex(NotesDB.COLUMN_NAME_NOTE_TITLE));
            String reciever = cursor.getString(cursor.getColumnIndex(NotesDB.COLUMN_NAME_NOTE_RECIEVER));
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("cv_content", name);
            map.put("cv_date", date);
            map.put("cv_title", title);
            map.put("cv_reciever", reciever);
            dataList.add(map);
        }
        cursor.close();
        return dataList;
    }

}
