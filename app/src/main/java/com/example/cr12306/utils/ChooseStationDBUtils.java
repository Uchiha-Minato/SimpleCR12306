package com.example.cr12306.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.os.FileUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.cr12306.R;
import com.example.cr12306.dbhelpers.StationDBHelper;
import com.example.cr12306.domain.Station;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class ChooseStationDBUtils {

    private static final File PATH = Environment.getExternalStorageDirectory();
    private final StationDBHelper dbHelper;
    private SQLiteDatabase database;
    public Context context;

    public ChooseStationDBUtils(Context context) {
        this.context = context;
        dbHelper = new StationDBHelper(context);
    }

    /**
     * 根据输入的车站名查找车站
     * @param station_name 输入的车站名
     * @return station ArrayList
     * */
    @SuppressLint("Range")
    public ArrayList<Station> queryStationByName(@NonNull String station_name) {
        ArrayList<Station> stations = new ArrayList<>();
        database = dbHelper.getReadableDatabase();
        String query_sql = "select * from station where station_name like ?";
        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(query_sql, new String[]{"%"+station_name+"%"});

        if(cursor.moveToNext()) {
            do {
                Station station = new Station();
                station.setStation_name(cursor.getString(cursor.getColumnIndex("station_name")));
                station.setTelecode(cursor.getString(cursor.getColumnIndex("telecode")));
                stations.add(station);
            } while (cursor.moveToNext());
        }
        Toast.makeText(context, "查找完成", Toast.LENGTH_SHORT).show();
        return stations;
    }

    /**
     * 判断表内数据是否存在 第二次启动后用于隐藏按钮
     * */
    public Boolean dataExists() {
        database = dbHelper.getReadableDatabase();
        String sql_queryAll = "select * from station";
        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(sql_queryAll, new String[]{});
        return cursor.moveToNext();
    }

    /**
     * 判断车站文件是否存在
     * */
    public Boolean fileExists() {
        try {
            File file = new File(PATH,"/Android/data/com.example.cr12306/files/station_names1.json");
            if(!file.exists())
                return false;
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 初始化时用，只用一次
     * 解析JSON数据之后 加入数据库
     * json数据文件为 内部存储设备/Android/data/com.example.cr12306/files/station_names1.json
     * 在此project内位置： app/libs/station_names1.json
     * */
    public void initStations() {
        try {
            File file = new File(PATH,"/Android/data/com.example.cr12306/files/station_names1.json");
            FileInputStream inputStream = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuilder strJSON = new StringBuilder();
            String strTmp;

            ArrayList<Station> stationArrayList = new ArrayList<>();

            while((strTmp = bufferedReader.readLine()) != null) {
                strJSON.append(strTmp);
            }

            //第一层
            JSONObject object = new JSONObject(strJSON.toString());
            //第二层数组 数组中的元素是object
            JSONArray array = object.optJSONArray("stations");
            assert array != null;

            for(int i = 0; i< array.length(); i++) {
                Station station = new Station();
                String name = array.getString(i).split(":")[0].split("\"")[1];
                String telecode = array.getString(i).split(":")[1].split("\"")[1];

                station.setStation_name(name);
                station.setTelecode(telecode);
                stationArrayList.add(station);
            }

            database = dbHelper.getWritableDatabase();
            String insert_sql =
                    "insert into station(station_name, telecode) values(?,?)";
            for(int i = 0; i < stationArrayList.size(); i++) {
                String name = stationArrayList.get(i).getStation_name();
                String telecode = stationArrayList.get(i).getTelecode();
                database.execSQL(insert_sql, new String[]{name, telecode});
            }
            Toast.makeText(context, "数据添加成功", Toast.LENGTH_SHORT).show();

        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }
    public void createExternalStoragePrivateFile() {
        File file = new File(context.getExternalFilesDir(null), "station_names1.json");
        try{
            Log.i("Storage", String.valueOf(context.getExternalFilesDir(null)));
            InputStream is = new FileInputStream(file);
            OutputStream os = new FileOutputStream(file);
            byte[] data = new byte[is.available()];
            int result = is.read(data);
            os.write(data);
            Log.i("Storage", String.valueOf(result));
            is.close();
            os.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
