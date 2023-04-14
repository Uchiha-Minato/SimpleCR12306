package com.example.cr12306.utils;

import android.content.Context;

import com.example.cr12306.dbhelpers.CRLinesDBHelper;

public class CRLineDBUtils {

    public CRLinesDBHelper helper;

    public CRLineDBUtils(Context context) {
        helper = new CRLinesDBHelper(context);
    }
}
