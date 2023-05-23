package com.example.cr12306.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cr12306.activities.crlines.CRHLines;
import com.example.cr12306.dbhelpers.CRHLinesDBHelper;
import com.example.cr12306.domain.CorridorDetail;

import java.util.ArrayList;

public class CRHLineDBUtils {
    public CRHLinesDBHelper helper;
    private SQLiteDatabase database;

    public CRHLineDBUtils(Context context) {
        helper = new CRHLinesDBHelper(context);
    }

    /**
     * 判断数据库中是否有数据存在
     * */
    public Boolean dataExists() {
        database = helper.getReadableDatabase();
        String query_sql = "select * from crh_corridor";
        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(query_sql, null);
        return cursor.moveToNext();
    }

    /**
     * 添加线路类型进入数据库
     * */
    public void addEnumIntoDB() {
        database = helper.getWritableDatabase();
        CRHLines[] crLines = CRHLines.values();
        String insert_sql =
                "insert into crh_corridor(corridor_name) values(?)";
        for(CRHLines line: crLines) {
            database.execSQL(insert_sql, new String[]{line.toString()});
        }
    }

    /**
     * 获取所有的通道,返回一个String链表
     * */
    @SuppressLint("Range")
    public ArrayList<String> getAllCorridors() {
        ArrayList<String> result = new ArrayList<>();
        database = helper.getReadableDatabase();
        String query_sql = "select corridor_name from crh_corridor";
        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(query_sql, null);

        if(cursor.moveToNext()) {
            do {
                String line_name = cursor.getString(cursor.getColumnIndex("corridor_name"));
                result.add(line_name);
            } while (cursor.moveToNext());
        }
        return result;
    }

    /**
     * 获取给定通道的线路列表
     * @param corridor_name 通道名
     * @return ArrayList<DistanceDetail>
     * */
    @SuppressLint("Range")
    public ArrayList<CorridorDetail> getStationsByCorridor(String corridor_name) {
        ArrayList<CorridorDetail> result = new ArrayList<>();
        database = helper.getReadableDatabase();
        String query_sql =
                "select * from lines where corridor_name = ?";
        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(query_sql, new String[]{corridor_name});

        if(cursor.moveToNext()) {
            do {
                String corridor_name1 = cursor.getString(cursor.getColumnIndex("corridor_name"));
                String line_name = cursor.getString(cursor.getColumnIndex("line_name"));
                String from_to = cursor.getString(cursor.getColumnIndex("from_to"));
                CorridorDetail detail = new CorridorDetail(corridor_name1, line_name);
                detail.setFrom_to(from_to);
                result.add(detail);
            } while (cursor.moveToNext());
        }
        return result;
    }
}
