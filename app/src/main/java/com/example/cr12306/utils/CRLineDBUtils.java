package com.example.cr12306.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cr12306.activities.crlines.CRLines;
import com.example.cr12306.dbhelpers.CRLinesDBHelper;
import com.example.cr12306.domain.DistanceDetail;

import java.util.ArrayList;

public class CRLineDBUtils {

    public CRLinesDBHelper helper;
    private SQLiteDatabase database;

    public CRLineDBUtils(Context context) {
        helper = new CRLinesDBHelper(context);
    }

    /**
     * 判断数据库中是否有数据存在
     * */
    public Boolean dataExists() {
        database = helper.getReadableDatabase();
        String query_sql = "select * from crlines";
        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(query_sql, null);
        return cursor.moveToNext();
    }

    /**
     * 添加线路类型进入数据库
     * */
    public void addEnumIntoDB() {
        database = helper.getWritableDatabase();
        CRLines[] crLines = CRLines.values();
        String insert_sql =
                "insert into crlines(line_name) values(?)";
        for(CRLines line: crLines) {
            database.execSQL(insert_sql, new String[]{line.toString()});
        }
    }

    /**
     * 获取所有的线路,返回一个String链表
     * */
    @SuppressLint("Range")
    public ArrayList<String> getAllLines() {
        ArrayList<String> result = new ArrayList<>();
        database = helper.getReadableDatabase();
        String query_sql = "select line_name from crlines";
        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(query_sql, null);

        if(cursor.moveToNext()) {
            do {
                String line_name = cursor.getString(cursor.getColumnIndex("line_name"));
                result.add(line_name);
            } while (cursor.moveToNext());
        }
        return result;
    }

    /**
     * 获取给定线路的车站列表
     * @param line_name 线路名
     * @return ArrayList<DistanceDetail>
     * */
    @SuppressLint("Range")
    public ArrayList<DistanceDetail> getStationsByLine(String line_name) {
        ArrayList<DistanceDetail> result = new ArrayList<>();
        database = helper.getReadableDatabase();
        String query_sql =
                "select * from stations where line_name = ? order by distance";
        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(query_sql, new String[]{line_name});

        if(cursor.moveToNext()) {
            do {
                String station = cursor.getString(cursor.getColumnIndex("station_name"));
                int distance = cursor.getInt(cursor.getColumnIndex("distance"));
                DistanceDetail detail = new DistanceDetail(station, distance);
                result.add(detail);
            } while (cursor.moveToNext());
        }
        return result;
    }
}
