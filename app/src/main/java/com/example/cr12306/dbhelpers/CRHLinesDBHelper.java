package com.example.cr12306.dbhelpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.cr12306.utils.MyApplication;

public class CRHLinesDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "crh_lines";
    private static final int DB_VER = 1;

    public CRHLinesDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    private static final String CreateTable_crh_corridor =
            "create table crh_corridor (" +
                    "corridor_id integer not null primary key autoincrement," +
                    "corridor_name varchar(50) unique not null)";
    private static final String CreateTable_lines =
            "create table lines (" +
                    "corridor_name varchar(50) not null," +
                    "line_name varchar(50) not null," +
                    "from_to varchar(50)," +
                    "foreign key(corridor_name) references crh_corridor(corridor_name))";

    private static final String drop_table_crh_corridor =
            "drop table if exists crh_corridor";
    private static final String drop_table_lines =
            "drop table if exists lines";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CreateTable_crh_corridor);
        Toast.makeText(MyApplication.getContext(), "通道列表创建成功", Toast.LENGTH_SHORT).show();
        db.execSQL(CreateTable_lines);
        Toast.makeText(MyApplication.getContext(), "线路名列表创建成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(drop_table_lines);
        db.execSQL(drop_table_crh_corridor);
        onCreate(db);
    }
}
