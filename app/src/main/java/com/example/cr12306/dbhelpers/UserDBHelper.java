package com.example.cr12306.dbhelpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.cr12306.domain.Users;
import com.example.cr12306.utils.MyApplication;

public class UserDBHelper extends SQLiteOpenHelper {

    private static final String DB_Name = "user.db";
    private static final int DB_ver = 1;

    public UserDBHelper(Context context){
        super(context, DB_Name, null, DB_ver);
    }

    public static final String SQL_CREATE_DATABASE =
            "create table " + Users.User.TABLE_NAME + " (" +
                    Users.User.Col_name_uid + " integer primary key autoincrement," +
                    Users.User.Col_name_uname + " varchar(25) not null," +
                    Users.User.Col_name_pwd +" varchar(25) not null," +
                    Users.User.Col_name_phone + " long )";
    public static final String sql_del = "drop table if exists user";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_DATABASE);
        Toast.makeText(MyApplication.getContext(), "用户数据库创建成功", Toast.LENGTH_SHORT).show();
        Log.v("Tag", "用户数据库创建成功");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(sql_del);
        onCreate(sqLiteDatabase);
    }
}
