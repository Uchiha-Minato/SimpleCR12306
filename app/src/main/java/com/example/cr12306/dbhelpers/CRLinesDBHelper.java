package com.example.cr12306.dbhelpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.cr12306.utils.CRLineDBUtils;
import com.example.cr12306.utils.MyApplication;

public class CRLinesDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "cr_lines";
    private static final int DB_VER = 1;


    public CRLinesDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    private static final String CreateTable_crlines =
            "create table crlines (" +
                    "line_id integer not null primary key autoincrement," +
                    "line_name varchar(50) unique not null)";
    private static final String CreateTable_stations =
            "create table stations (" +
                    "line_name varchar(50) not null," +
                    "station_name varchar(25) not null," +
                    "distance integer," +
                    "foreign key(line_name) references crlines(line_name))";

    private static final String drop_table_crlines =
            "drop table if exists crlines";
    private static final String drop_table_stations =
            "drop table if exists stations";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CreateTable_crlines);
        Toast.makeText(MyApplication.getContext(), "线路列表创建成功", Toast.LENGTH_SHORT).show();
        db.execSQL(CreateTable_stations);
        Toast.makeText(MyApplication.getContext(), "车站列表创建成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(drop_table_stations);
        db.execSQL(drop_table_crlines);
        onCreate(db);
    }
}
