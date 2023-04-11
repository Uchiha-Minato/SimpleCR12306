package com.example.cr12306.domain;

import android.provider.BaseColumns;

public final class Users {

    private Users(){}

    public static class User implements BaseColumns{

        public static final String TABLE_NAME = "user";
        public static final String Col_name_uid = "uid";
        public static final String Col_name_uname = "username";
        public static final String Col_name_pwd = "password";
        public static final String Col_name_phone = "phone";

    }
}
