package com.example.cr12306.activities.query;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cr12306.R;
import com.example.cr12306.adapter.TrainInfoAdapter;
import com.example.cr12306.domain.TrainInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

public class TrainQueryActivity extends AppCompatActivity implements View.OnClickListener{

    public ImageButton back_trainQuery;

    //一级页面
    public LinearLayout trainQuery_main;
    public TextView title;
    public EditText train_no;
    public Calendar calendar;
    public int year, month, day;
    public Button choose_date, query;

    //二级页面
    public LinearLayout trainQuery_result;
    public ImageButton back_queryResult;
    public TextView test;//测试网络请求

    //全局变量，网络请求获得的train_no
    public String leftTicketDTO_train_no;
    //表格控件
    public HorizontalScrollView scrollView;
    public RecyclerView recyclerView;
    public TextView train_class;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainquery);

        initViews();

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        test = findViewById(R.id.test);
    }

    /**
     * 日期选择框
     * */
    public void setDate(View view) {
        showDialog(999);
    }

    @Override
    protected Dialog onCreateDialog(int id){
        if(id == 999){
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private final DatePickerDialog.OnDateSetListener myDateListener = (datePicker, i, i1, i2) -> {
        showDateInButton(i,i1,i2);
    };
    private void showDateInButton(int year, int month, int day){
        StringBuilder builder = new StringBuilder();
        builder.append(year);
        if(month >= 9) {
            builder.append(month+1);
        } else builder.append("0").append(month+1);

        if(day >= 10) {
            builder.append(day);
        } else builder.append("0").append(day);

        choose_date.setText(builder);
    }

    private void initViews() {
        back_trainQuery = findViewById(R.id.back_trainQuery);
        title = findViewById(R.id.trainQuery_title);
        train_no = findViewById(R.id.trainQuery_train_no);
        choose_date = findViewById(R.id.query_choose_date);
        query = findViewById(R.id.train_query);
        trainQuery_main = findViewById(R.id.trainQuery_main);

        trainQuery_result = findViewById(R.id.include_result);
        back_queryResult = findViewById(R.id.back_trainQuery_details);

        train_class = findViewById(R.id.train_class);
        scrollView = findViewById(R.id.horizontalView);
        recyclerView = findViewById(R.id.list_result);

        back_trainQuery.setOnClickListener(this);
        query.setOnClickListener(this);
        back_queryResult.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_trainQuery -> TrainQueryActivity.this.finish();
            case R.id.train_query -> {
                String trainNo = train_no.getText().toString();
                String date = choose_date.getText().toString();

                if(trainNo.equals("")) {
                    Toast.makeText(this, "请输入要查询的列车车次", Toast.LENGTH_SHORT).show();
                } else if (date.equals("请选择日期")) {
                    Toast.makeText(this, "请选择日期", Toast.LENGTH_SHORT).show();
                } else {
                    trainQuery_main.setVisibility(View.GONE);
                    trainQuery_result.setVisibility(View.VISIBLE);
                    //先用GET 车次关键词搜索获取train_no
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String result = keywordQuery(trainNo, date);
                            Message msg = new Message();
                            msg.what = 0;
                            msg.obj = result;

                            handler.sendMessage(msg);
                        }
                    }).start();
                }

            }
            case R.id.back_trainQuery_details -> {
                trainQuery_main.setVisibility(View.VISIBLE);
                trainQuery_result.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 网络请求1,车次关键词搜索
     * @param keyword 车次
     * @param date 日期 格式为 yyyymmdd
     * @return JSON
     * */
    private String keywordQuery(String keyword, String date) {
        String result = "";

        HttpURLConnection connection;
        BufferedReader reader;

        String url = "https://search.12306.cn/search/v1/train/search?";

        try {
            //1.建立链接
            URL requestUrl = new URL(url +
                  "keyword=" + keyword + "&date=" + date);
            connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.connect();

            //2.获取二进制流
            InputStream stream = connection.getInputStream();
            //3.将二进制流包装
            reader = new BufferedReader((new InputStreamReader(stream)));

            //从buffer reader读取String字符串
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null){
                builder.append(line).append("\n");
            }

            if(builder.length() == 0) {
                return null;
            }

            result = builder.toString();

        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 主线程接收并处理数据
     * */
    private final Handler handler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg){
            super.handleMessage(msg);

            if(msg.what == 0){
                String data = msg.obj.toString();

                //处理JSON字符串
                //测试
                //test.setText(data);
                leftTicketDTO_train_no = parseTrainNoJSONData(data);

                //再用GET 时刻表查询得到最终结果
                String date = choose_date.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String timeTableResult = queryTrainInfo(leftTicketDTO_train_no, date);
                        Message msg1 = new Message();
                        msg1.what = 1;
                        msg1.obj = timeTableResult;

                        handler1.sendMessage(msg1);
                    }
                }).start();

                Toast.makeText(TrainQueryActivity.this, "主线程收到消息", Toast.LENGTH_SHORT).show();
            }
        }
    };
    private final Handler handler1 = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1) {
                String trainInfoJSON = msg.obj.toString();
                //处理时刻表JSON字符串
                ArrayList<TrainInfo> info = parseTrainQueryInfo(trainInfoJSON);
                //设置车型
                train_class.setText(info.get(0).getTrain_class_name());
                //初始化时刻表RecyclerView
                TrainInfoAdapter adapter = new TrainInfoAdapter(info);
                recyclerView.setLayoutManager(new LinearLayoutManager(TrainQueryActivity.this));
                recyclerView.setAdapter(adapter);

                Toast.makeText(TrainQueryActivity.this, "主线程更新时刻表", Toast.LENGTH_SHORT).show();
            }
        }
    };

    /**
     * JSON 数据1：车次关键词搜索
     * { 第一层
     * "data": [ 第二层
     *         { 第三层
     *             "date": "20230419", 日期
     *             "from_station": "上海虹桥", 始发站
     *             "station_train_code": "G24", 车次
     *             "to_station": "北京南", 终到站
     *             "total_num": "5", 车站数
     *             "train_no": "5l00000G2410" 详细车次代码
     *         },
     *   ...
     *   ],
     *   "status": true
     * }
     * @param jsonStr String类型的json数据
     * @return train_no 详细车次代码
     * */
    @SuppressLint("SetTextI18n")
    public String parseTrainNoJSONData(String jsonStr) {

        try {
            //第一层大括号
            JSONObject object = new JSONObject(jsonStr);
            //第二层数组 数组中的每一个元素都是一个JSONObject
            JSONArray array = object.optJSONArray("data");
            assert array != null;
            //第三层大括号，index = 0 意思是取数组的第一个
            JSONObject data = array.optJSONObject(0);

            String date = data.optString("date");
            String from_station = data.optString("from_station");
            String station_train_code = data.optString("station_train_code");
            String to_station = data.optString("to_station");
            String train_no = data.optString("train_no");


            test.setText("日期:"+ date +"\t\t" +
                    "车次:" + station_train_code + "\n" +
                    "始发站:" + from_station + "\t\t" +
                    "终到站:" + to_station);

            return train_no;

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 网络请求2 时刻表查询
     * @param leftTicketDTO_train_no 详细车次
     * @param train_date 发车时间 格式 yyyy-mm-dd
     * @return 时刻表json数据字符串
     * */
    private String queryTrainInfo(String leftTicketDTO_train_no, String train_date) {

        //更改一下train_date的格式
        String year = train_date.substring(0, 4);
        String month = train_date.substring(4, 6);
        String day = train_date.substring(6, 8);
        String leftTicketDTO_train_date = year + "-" + month + "-" + day;

        String result;
        BufferedReader reader;
        HttpURLConnection connection;
        String url = "https://kyfw.12306.cn/otn/queryTrainInfo/query?";

        try {
            URL requestUrl = new URL(url +
                    "leftTicketDTO.train_no=" + leftTicketDTO_train_no +
                    "&leftTicketDTO.train_date=" + leftTicketDTO_train_date +
                    "&rand_code=HTTP/1.1");
            connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.connect();

            Log.i("debug",
                    "leftTicketDTO_train_no=" + leftTicketDTO_train_no);

            InputStream stream = connection.getInputStream();

            reader = new BufferedReader((new InputStreamReader(stream)));

            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null){
                builder.append(line).append("\n");
            }

            if(builder.length() == 0) {
                return null;
            }

            result = builder.toString();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
    /**
     * JSON数据2 时刻表查询
     * { 第一层 JSONObject
     *     "validateMessagesShowId": "_validatorMessage",
     *     "status": true,
     *     "httpstatus": 200,
     *     "data": {  第二层 JSONObject
     *         "data": [ 第三层 JSONArray
     *             {   第四层 JSONObject
     *                 "arrive_day_str": "当日到达",
     *                 "station_name": "上海虹桥",
     *                 "train_class_name": "高速",
     *                 "is_start": "Y",
     *                 "service_type": "2",
     *                 "end_station_name": "北京南",
     *                 "arrive_time": "----",
     *                 "start_station_name": "上海虹桥",
     *                 "station_train_code": "G24",
     *                 "arrive_day_diff": "0",
     *                 "start_time": "17:00",
     *                 "station_no": "01",
     *                 "wz_num": "--",
     *                 "running_time": "00:00"
     *             },
     *           ...
     *        ]
     *    }
     * }
     * @param JSONData 时刻表JSON数据
     * @return ArrayList
     * */
    private ArrayList<TrainInfo> parseTrainQueryInfo(String JSONData) {

        ArrayList<TrainInfo> infoList = new ArrayList<>();

        try {
            //第一层
            JSONObject object = new JSONObject(JSONData);
            //第二层
            JSONObject object1 = object.optJSONObject("data");
            assert object1 != null;
            //第三层 JSON数组
            JSONArray array = object1.optJSONArray("data");
            assert array != null;

            //对于数组中的每个元素(JSONObject)，加入链表
            for(int i = 0; i < array.length(); i++) {
                JSONObject dataInfo = array.optJSONObject(i);
                TrainInfo info = new TrainInfo();

                if(i == 0) { //起点站
                    info.setArrive_time("----");
                } else {
                    info.setArrive_time(dataInfo.optString("arrive_time"));
                }

                info.setTrain_class_name(dataInfo.optString("train_class_name"));
                info.setArrive_day_str(dataInfo.optString("arrive_day_str"));
                info.setStation_train_code(dataInfo.optString("station_train_code"));
                info.setStation_name(dataInfo.optString("station_name"));
                info.setRunning_time(dataInfo.optString("running_time"));

                if(i == array.length()- 1) { //终到站
                    info.setStart_time("----");
                } else {
                    info.setStart_time(dataInfo.optString("start_time"));
                }

                infoList.add(info);
            }

            return infoList;

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }

}
