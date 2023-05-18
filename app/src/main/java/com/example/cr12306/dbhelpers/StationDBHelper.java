package com.example.cr12306.dbhelpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.cr12306.utils.MyApplication;

public class StationDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "stationTelecode";
    private static final int DB_VER = 1;

    public StationDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    private static final String createTable_station =
            "create table station (" +
                    "station_name varchar(50) not null," +
                    "telecode varchar(10) not null)";

    private static final String drop_table_station =
            "drop table if exists station";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTable_station);
        Toast.makeText(MyApplication.getContext(), "车站数据库创建成功", Toast.LENGTH_SHORT).show();
        Log.i("db", "数据库创建成功");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(drop_table_station);
        onCreate(db);
    }
}
