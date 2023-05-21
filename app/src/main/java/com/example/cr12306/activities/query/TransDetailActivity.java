package com.example.cr12306.activities.query;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cr12306.R;
import com.example.cr12306.adapter.InterchangeAdapter;
import com.example.cr12306.domain.Interchange;
import com.example.cr12306.domain.Station;

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

public class TransDetailActivity extends AppCompatActivity {

    public ImageButton back_transDetail;
    public RecyclerView interchange_RecyclerView;
    public TextView from_to;

    public ArrayList<Interchange> plans;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transdetail);


        String date = getIntent().getStringExtra("date");
        Station bundle_start = (Station) getIntent().getSerializableExtra("bundle_start_station");
        Station bundle_end = (Station) getIntent().getSerializableExtra("bundle_end_station");
        from_to = findViewById(R.id.trans_query_result);
        from_to.setText(getIntent().getStringExtra("start_station") + "-" + getIntent().getStringExtra("end_station"));

        initView();

        new Thread(new Runnable() {
            @Override
            public void run() {
                String start_station_code = bundle_start.getTelecode();
                String end_station_code = bundle_end.getTelecode();
                String result = interchangeQuery(date, start_station_code, end_station_code);

                Message msg = new Message();
                msg.what = 0;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        }).start();
    }

    private void initView() {
        back_transDetail = findViewById(R.id.back_transDetail);
        back_transDetail.setOnClickListener(v -> finish());
        interchange_RecyclerView = findViewById(R.id.recyclerView_interchange);
    }

    /**
     * 网络请求 GET 中转查询
     * */
    private String interchangeQuery(String train_date, String from_station_telecode, String to_station_telecode) {
        String result;

        HttpURLConnection connection;
        BufferedReader reader;

        String url = "https://kyfw.12306.cn/lcquery/query?";

        try {
            //1.建立连接
            URL requestUrl = new URL(url +
                    "train_date=" + train_date +
                    "&from_station_telecode=" + from_station_telecode +
                    "&to_station_telecode=" + to_station_telecode +
                    "&middle_station=&result_index=0&can_query=Y&isShowWZ=N&purpose_codes=00&lc_ciphertext=OI6P%2BMcf%2FL8npsVi3s88qLbY3dorOinhNOnhVE3KH9U%3D");
            connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.connect();

            //2.获取二进制流
            InputStream stream = connection.getInputStream();
            //3.将二进制流包装
            reader = new BufferedReader((new InputStreamReader(stream)));

            //从buffer reader读取string字符串
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }

            if(builder.length() == 0)
                return null;

            result = builder.toString();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    /**
     * 解析JSON字符串
     * { 第一层
     *     "data": { 第二层
     *         "flag": true,
     *         "result_index": 10,
     *         "middleStationList": [...
     *         ],
     *         "can_query": "Y",
     *         "zd_yp_size": 6,
     *         "middleList": [ 第三层数组 为所有可选方案
     *             { 第四层 方案内部
     *                 "all_lishi": "8小时47分钟",
     *                 "all_lishi_minutes": 527,
     *                 "arrive_date": "2023-05-18",
     *                 "arrive_time": "18:47",
     *                 "end_station_code": "GBZ",
     *                 "end_station_name": "桂林北",
     *                 "first_train_no": "2400000G790N",
     *                 "from_station_code": "BXP",
     *                 "from_station_name": "北京西",
     *                 "fullList": [ 第五层数组：总数为2的车次
     *                     {
     *                         "arrive_time": "15:16",
     *                         "controlled_train_flag": "0",
     *                         "country_flag": "CHN,CHN",
     *                         "day_difference": "0",
     *                         "dw_flag": "5#1#0#S#z#0",
     *                         "end_station_name": "香港西九龙",
     *                         ...
     * */
    private ArrayList<Interchange> parseInterchangeJSONData(String jsonStr) {
        ArrayList<Interchange> interchanges = new ArrayList<>();

        try {
            //第一层
            JSONObject object = new JSONObject(jsonStr);
            //第二层
            JSONObject object1 = object.optJSONObject("data");
            assert object1 != null;
            //第三层数组
            JSONArray array = object1.optJSONArray("middleList");
            assert array != null;
            for(int i = 0; i < array.length(); i++) {
                //第四层
                JSONObject plan = array.optJSONObject(i);
                Interchange interchange = new Interchange();
                interchange.setAll_lishi(plan.optString("all_lishi"));
                interchange.setArrive_date(plan.optString("arrive_date"));
                interchange.setArrive_time(plan.optString("arrive_time"));
                interchange.setFrom_station_name(plan.optString("from_station_name"));
                interchange.setFrom_station_code(plan.optString("from_station_code"));
                interchange.setTo_station_name(plan.optString("end_station_name"));
                interchange.setTo_station_code(plan.optString("end_station_code"));
                interchange.setMiddle_station(plan.optString("middle_station_name"));
                interchange.setMiddle_date(plan.optString("middle_date"));
                interchange.setWait_time(plan.optString("wait_time"));
                //第五层数组
                JSONArray array1 = plan.optJSONArray("fullList");
                assert array1 != null;
                for(int j = 0; j < array1.length(); j++) {
                    JSONObject planDetail = array1.optJSONObject(j);
                    if(j == 0) {
                        interchange.setFirst_lishi(planDetail.optString("lishi"));
                        interchange.setFirst_from_station_name(planDetail.optString("from_station_name"));
                        interchange.setFirst_end_station_name(planDetail.optString("end_station_name"));
                        interchange.setFirst_station_train_code(planDetail.optString("station_train_code"));
                        interchange.setFirst_start_station_name(planDetail.optString("start_station_name"));
                        interchange.setFirst_to_station_name(planDetail.optString("to_station_name"));
                        interchange.setFirst_start_train_date(planDetail.optString("start_train_date"));
                        interchange.setFirst_start_time(planDetail.optString("start_time"));
                        interchange.setFirst_arrive_time(planDetail.optString("arrive_time"));
                    } else if(j == 1) {
                        interchange.setSecond_lishi(planDetail.optString("lishi"));
                        interchange.setSecond_from_station_name(planDetail.optString("from_station_name"));
                        interchange.setSecond_end_station_name(planDetail.optString("end_station_name"));
                        interchange.setSecond_station_train_code(planDetail.optString("station_train_code"));
                        interchange.setSecond_start_station_name(planDetail.optString("start_station_name"));
                        interchange.setSecond_to_station_name(planDetail.optString("to_station_name"));
                        interchange.setSecond_start_train_date(planDetail.optString("start_train_date"));
                        interchange.setSecond_start_time(planDetail.optString("start_time"));
                        interchange.setSecond_arrive_time(planDetail.optString("arrive_time"));
                    }
                }

                interchanges.add(interchange);
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return interchanges;
    }

    private final Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            if(msg.what == 0) {
                String data = msg.obj.toString();
                plans = parseInterchangeJSONData(data);
                initRecyclerView(plans);
            }
        }
    };
    /**
     * 更新视图
     * */
    private void initRecyclerView(ArrayList<Interchange> list) {
        interchange_RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        InterchangeAdapter adapter = new InterchangeAdapter(list);
        adapter.setClickListener(new InterchangeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Interchange plan = list.get(position);
                AlertDialog.Builder dialog = new AlertDialog.Builder(TransDetailActivity.this);
                dialog.setIcon(R.drawable.about);
                dialog.setTitle("详细换乘方案");
                String message1 = "出发日期：" + plan.getFirst_start_train_date()
                        + "\n第一程：\n" + plan.getFirst_station_train_code() + " "
                        + plan.getFirst_start_station_name() + "-" + plan.getFirst_end_station_name()
                        + "\n出发站：" + plan.getFirst_from_station_name() + "  到达站：" + plan.getFirst_to_station_name()
                        + "\n历时：" + plan.getFirst_lishi() + "\n到点：" + plan.getFirst_arrive_time();
                String message2 = "\n中转站(地)：" + plan.getMiddle_date() +" "+ plan.getMiddle_station();
                String message3 = "\n中转时长：" + plan.getWait_time();
                String message4 = "\n\n第二程：\n" + plan.getSecond_station_train_code() + " "
                        + plan.getSecond_start_station_name() + "-" +plan.getSecond_end_station_name()
                        + "\n出发站：" + plan.getSecond_from_station_name() + "  到达站：" + plan.getSecond_to_station_name()
                        + "\n历时：" + plan.getSecond_lishi() + "\n到点：" + plan.getSecond_arrive_time();
                dialog.setMessage(message1 + message2 + message3 + message4);
                dialog.setPositiveButton("好", ((dialog1, which) -> {

                }));
                dialog.setNegativeButton("复制以上信息", ((dialog1, which) -> copyToClipboard(message1 + message2 + message3 + message4)));
                dialog.show();
            }
        });

        interchange_RecyclerView.setAdapter(adapter);
    }
    /**
     * 复制到剪贴板
     **/
    private void copyToClipboard(String text) {
        try {
            ClipboardManager manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData data = ClipData.newPlainText("Label", text);
            manager.setPrimaryClip(data);
            Toast.makeText(this, "已经复制到剪贴板", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
