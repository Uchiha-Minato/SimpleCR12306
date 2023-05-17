package com.example.cr12306.activities.tickets;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cr12306.R;
import com.example.cr12306.adapter.BuyTicketAdapter;
import com.example.cr12306.adapter.SimpleTimeTableAdapter;
import com.example.cr12306.domain.BuyTicket;
import com.example.cr12306.domain.LeftTicket;
import com.example.cr12306.domain.TrainInfo;
import com.example.cr12306.utils.ClassUtils;

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

public class TicketDetailActivity extends AppCompatActivity {

    public ImageButton back2;
    public TextView txt_train_code, txt_details;
    public TextView detail_from_station, detail_train_no, detail_date, detail_start_time;
    public TextView detail_to_station, detail_arrive_time, detail_day_difference;
    public TextView txt_if_student;
    public RecyclerView recyclerView;
    public RecyclerView recyclerView_buy_ticket;
    //public Button btn_buy_ticket;

    public LeftTicket ticket = new LeftTicket();
    public ArrayList<BuyTicket> buyTicketArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if(getIntent().getSerializableExtra("leftTicket") != null) {
            ticket = (LeftTicket) getIntent().getSerializableExtra("leftTicket");
            ticket.setDate(getIntent().getStringExtra("date"));
        }

        txt_if_student = findViewById(R.id.txt_if_student);
        try {
            initBuyList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        initView();

    }
    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    public void initView() {
        back2 = findViewById(R.id.back2);
        back2.setOnClickListener(view -> {
            finish();
        });

        txt_train_code = findViewById(R.id.txt_train_code);
        txt_train_code.setText(ticket.getStation_train_code());
        txt_details = findViewById(R.id.txt_details);
        txt_details.setText(" " + ticket.getStart_station_name() + "-" + ticket.getEnd_station_name());

        detail_train_no = findViewById(R.id.detail_train_no);
        String train_no = this.getIntent().getStringExtra("train_no");
        detail_train_no.setText(train_no);

        detail_date = findViewById(R.id.detail_date);
        String date = ticket.getDate();
        detail_date.setText(date);

        //起始站信息
        detail_from_station = findViewById(R.id.detail_from_station);
        detail_from_station.setText(ticket.getFrom_station_name());
        detail_start_time = findViewById(R.id.detail_start_time);
        detail_start_time.setText(ticket.getStart_time());

        //目的站信息
        detail_to_station = findViewById(R.id.detail_to_station);
        detail_to_station.setText(ticket.getTo_station_name());
        detail_arrive_time = findViewById(R.id.detail_arrive_time);
        detail_arrive_time.setText(ticket.getArrive_time());
        detail_day_difference = findViewById(R.id.detail_day_difference);
        detail_day_difference.setText("+" + ticket.getDay_difference());

        recyclerView = findViewById(R.id.simple_timeTable);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = queryTrainInfo(train_no, date);
                Message message = new Message();
                message.what = 0;
                message.obj = result;

                handler.sendMessage(message);
            }
        }).start();

        recyclerView_buy_ticket = findViewById(R.id.recyclerView_buy_ticket);
        //btn_buy_ticket = findViewById(R.id.buy_ticket);
        BuyTicketAdapter buyTicketAdapter = new BuyTicketAdapter(buyTicketArrayList, this);
        recyclerView_buy_ticket.setLayoutManager(new LinearLayoutManager(TicketDetailActivity.this));
        recyclerView_buy_ticket.setAdapter(buyTicketAdapter);
    }
    /**
     * 处理JSON,更新视图
     * */
    private final Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            if(msg.what == 0) {
                String trainInfoJSON = msg.obj.toString();
                ArrayList<TrainInfo> info = parseTrainQueryInfo(trainInfoJSON);
                //初始化RecyclerView
                SimpleTimeTableAdapter adapter = new SimpleTimeTableAdapter(info);
                LinearLayoutManager manager = new LinearLayoutManager(TicketDetailActivity.this);
                manager.setOrientation(RecyclerView.HORIZONTAL);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(adapter);

                Toast.makeText(TicketDetailActivity.this, "更新时刻表", Toast.LENGTH_SHORT).show();
            }
        }
    };
    /**
     * 网络请求 时刻表查询
     * @param leftTicketDTO_train_no 详细车次
     * @param train_date 发车时间 格式 yyyy-mm-dd
     * @return 时刻表json数据字符串
     * */
    private String queryTrainInfo(String leftTicketDTO_train_no, String train_date) {

        String result;
        BufferedReader reader;
        HttpURLConnection connection;
        String url = "https://kyfw.12306.cn/otn/queryTrainInfo/query?";

        try {
            URL requestUrl = new URL(url +
                    "leftTicketDTO.train_no=" + leftTicketDTO_train_no +
                    "&leftTicketDTO.train_date=" + train_date +
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
     * 处理JSON字符串
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

    public void initBuyList() throws Exception {

        if(getIntent().getStringExtra("type").equals("学生票")) {

            txt_if_student.setVisibility(View.VISIBLE);

            if (ticket.isSwz_num()) {
                BuyTicket buyTicket = new BuyTicket();
                buyTicket.setSeat_type("商务座");
                buyTicket.setPrice(ticket.getSwz_price());
                ClassUtils.copyFatherToChild(ticket, buyTicket);
                buyTicketArrayList.add(buyTicket);
            }
            if (ticket.isZy_num()) {
                BuyTicket buyTicket = new BuyTicket();
                buyTicket.setSeat_type("一等座");
                buyTicket.setPrice(ticket.getZy_price());
                ClassUtils.copyFatherToChild(ticket, buyTicket);
                buyTicketArrayList.add(buyTicket);
            }
            if (ticket.isZe_num()) {
                BuyTicket buyTicket = new BuyTicket();
                buyTicket.setSeat_type("二等座");
                double price = Double.parseDouble(ticket.getZe_price());
                price = price * 0.75;
                @SuppressLint("DefaultLocale") String priceFinal = String.format("%.1f", price);
                buyTicket.setPrice(priceFinal);
                ClassUtils.copyFatherToChild(ticket, buyTicket);
                buyTicketArrayList.add(buyTicket);
            }
            if (ticket.isSrrb_num()) {
                BuyTicket buyTicket = new BuyTicket();
                buyTicket.setSeat_type("动卧");
                buyTicket.setPrice(ticket.getSrrb_price());
                ClassUtils.copyFatherToChild(ticket, buyTicket);
                buyTicketArrayList.add(buyTicket);
            }
            if (ticket.isGr_num()) {
                BuyTicket buyTicket = new BuyTicket();
                buyTicket.setSeat_type("高级软卧");
                buyTicket.setPrice(ticket.getGr_price());
                ClassUtils.copyFatherToChild(ticket, buyTicket);
                buyTicketArrayList.add(buyTicket);
            }
            if (ticket.isRw_num()) {
                BuyTicket buyTicket = new BuyTicket();
                buyTicket.setSeat_type("软卧");
                buyTicket.setPrice(ticket.getRw_price());
                ClassUtils.copyFatherToChild(ticket, buyTicket);
                buyTicketArrayList.add(buyTicket);
            }
            if (ticket.isYw_num()) {
                BuyTicket buyTicket = new BuyTicket();
                buyTicket.setSeat_type("硬卧");
                double price = Double.parseDouble(ticket.getYw_price());
                price = price * 0.75;
                @SuppressLint("DefaultLocale") String priceFinal = String.format("%.1f", price);
                buyTicket.setPrice(priceFinal);
                ClassUtils.copyFatherToChild(ticket, buyTicket);
                buyTicketArrayList.add(buyTicket);
            }
            if (ticket.isYz_num()) {
                BuyTicket buyTicket = new BuyTicket();
                buyTicket.setSeat_type("硬座");
                double price = Double.parseDouble(ticket.getYz_price());
                price = price * 0.75;
                @SuppressLint("DefaultLocale") String priceFinal = String.format("%.1f", price);
                buyTicket.setPrice(priceFinal);
                ClassUtils.copyFatherToChild(ticket, buyTicket);
                buyTicketArrayList.add(buyTicket);
            }
            if (ticket.isWz_num()) {
                BuyTicket buyTicket = new BuyTicket();
                buyTicket.setSeat_type("无座");
                double price = Double.parseDouble(ticket.getWz_price());
                price = price * 0.75;
                @SuppressLint("DefaultLocale") String priceFinal = String.format("%.1f", price);
                buyTicket.setPrice(priceFinal);
                ClassUtils.copyFatherToChild(ticket, buyTicket);
                buyTicketArrayList.add(buyTicket);
            }

        } else {
            txt_if_student.setVisibility(View.GONE);
            if (ticket.isSwz_num()) {
                BuyTicket buyTicket = new BuyTicket();
                buyTicket.setSeat_type("商务座");
                buyTicket.setPrice(ticket.getSwz_price());
                ClassUtils.copyFatherToChild(ticket, buyTicket);
                buyTicketArrayList.add(buyTicket);
            }
            if (ticket.isZy_num()) {
                BuyTicket buyTicket = new BuyTicket();
                buyTicket.setSeat_type("一等座");
                buyTicket.setPrice(ticket.getZy_price());
                ClassUtils.copyFatherToChild(ticket, buyTicket);
                buyTicketArrayList.add(buyTicket);
            }
            if (ticket.isZe_num()) {
                BuyTicket buyTicket = new BuyTicket();
                buyTicket.setSeat_type("二等座");
                buyTicket.setPrice(ticket.getZe_price());
                ClassUtils.copyFatherToChild(ticket, buyTicket);
                buyTicketArrayList.add(buyTicket);
            }
            if (ticket.isSrrb_num()) {
                BuyTicket buyTicket = new BuyTicket();
                buyTicket.setSeat_type("动卧");
                buyTicket.setPrice(ticket.getSrrb_price());
                ClassUtils.copyFatherToChild(ticket, buyTicket);
                buyTicketArrayList.add(buyTicket);
            }
            if (ticket.isGr_num()) {
                BuyTicket buyTicket = new BuyTicket();
                buyTicket.setSeat_type("高级软卧");
                buyTicket.setPrice(ticket.getGr_price());
                ClassUtils.copyFatherToChild(ticket, buyTicket);
                buyTicketArrayList.add(buyTicket);
            }
            if (ticket.isRw_num()) {
                BuyTicket buyTicket = new BuyTicket();
                buyTicket.setSeat_type("软卧");
                buyTicket.setPrice(ticket.getRw_price());
                ClassUtils.copyFatherToChild(ticket, buyTicket);
                buyTicketArrayList.add(buyTicket);
            }
            if (ticket.isYw_num()) {
                BuyTicket buyTicket = new BuyTicket();
                buyTicket.setSeat_type("硬卧");
                buyTicket.setPrice(ticket.getYw_price());
                ClassUtils.copyFatherToChild(ticket, buyTicket);
                buyTicketArrayList.add(buyTicket);
            }
            if (ticket.isYz_num()) {
                BuyTicket buyTicket = new BuyTicket();
                buyTicket.setSeat_type("硬座");
                buyTicket.setPrice(ticket.getYz_price());
                ClassUtils.copyFatherToChild(ticket, buyTicket);
                buyTicketArrayList.add(buyTicket);
            }
            if (ticket.isWz_num()) {
                BuyTicket buyTicket = new BuyTicket();
                buyTicket.setSeat_type("无座");
                buyTicket.setPrice(ticket.getWz_price());
                ClassUtils.copyFatherToChild(ticket, buyTicket);
                buyTicketArrayList.add(buyTicket);
            }
        }
    }
}
