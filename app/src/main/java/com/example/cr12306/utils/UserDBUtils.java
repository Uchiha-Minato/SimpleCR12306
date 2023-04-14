package com.example.cr12306.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cr12306.dbhelpers.UserDBHelper;
import com.example.cr12306.domain.Users;

public class UserDBUtils {

    private final UserDBHelper dbHelper;
    private SQLiteDatabase database;

    public UserDBUtils(Context context){
         dbHelper = new UserDBHelper(context);
    }

    /**
     * 登陆时用
     * */
    @SuppressLint("Range")
    public String querySingleUser(String username, String password) {
        database = dbHelper.getReadableDatabase();
        String query_sql = "select * from " +
                Users.User.TABLE_NAME + " where " +
                Users.User.Col_name_uname + " = '" + username + "' and " +
                Users.User.Col_name_pwd + " = '" + password + "'";
        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(query_sql, null);

        if(cursor.moveToNext()) {
            return cursor.getString(cursor.getColumnIndex(Users.User.Col_name_uname));
        } else return null;
    }

    /**
     * 注册时添加数据
     * */
    public void signUp(String username, String password, String phone) {
        database = dbHelper.getWritableDatabase();
        String insert_sql = "insert into user(username, password, phone) values(?,?,?)";
        database.execSQL(insert_sql, new String[]{username, password, phone});
    }

    /**
     * 忘记密码时用
     * */
    public void forgetPwd(String username, String password, String phone) {
        database = dbHelper.getWritableDatabase();
        String update_sql = "update user set password = ?, phone = ? where username = ?";
        database.execSQL(update_sql, new String[]{password, phone, username});
    }

    /**
     * 根据用户名查找用户
     * @return 若存在即返回true
     * */
    public boolean userExists(String username) {
        database = dbHelper.getReadableDatabase();
        String query_sql = "select username from user where username = ?";
        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(query_sql, new String[]{username});
        return cursor.moveToNext();
    }


}
