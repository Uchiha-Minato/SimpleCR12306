package com.example.cr12306.activities.tickets;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cr12306.R;
import com.example.cr12306.adapter.TicketAdapter;
import com.example.cr12306.domain.LeftTicket;
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

public class LeftTicketActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    //public ArrayList<TicketTest> list = new ArrayList<>();
    public ArrayList<LeftTicket> tickets = new ArrayList<>();

    public TextView query_result;
    public Button btn_back;
    public ImageButton back1;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState){
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leftticket);

        back1 = findViewById(R.id.back1);
        back1.setOnClickListener(view -> {
            LeftTicketActivity.this.finish();
        });

        query_result = findViewById(R.id.query_result);
        query_result.setText(getIntent().getCharSequenceExtra("start_station")
                + "-" + getIntent().getCharSequenceExtra("end_station") + " "
                + getIntent().getStringExtra("type"));

        btn_back = findViewById(R.id.query_back);
        btn_back.setOnClickListener( view -> {
            LeftTicketActivity.this.finish();
        });

        String date = getIntent().getStringExtra("date");
        Station bundle_start = (Station) getIntent().getSerializableExtra("bundle_start_station");
        Station bundle_end = (Station) getIntent().getSerializableExtra("bundle_end_station");
        if(date == null)
            date = "2023-05-13";
        String finalDate = date;

        new Thread(new Runnable() {
            @Override
            public void run() {
                //暂时写死
                String result = leftTicketQuery(finalDate, bundle_start.getTelecode(), bundle_end.getTelecode());
                Message msg = new Message();
                msg.what = 0;
                msg.obj = result;

                handler.sendMessage(msg);
            }
        }).start();

    }

    private void initRecyclerView(ArrayList<LeftTicket> tickets){

        recyclerView = findViewById(R.id.recyclerView);//一定要写等号，否则空指针异常
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent next = new Intent();

        TicketAdapter adapter = new TicketAdapter(tickets);
        adapter.setClickListener((view, position) -> {
            next.setClass(LeftTicketActivity.this, TicketDetailActivity.class);
            next.putExtra("date", getIntent().getStringExtra("date"));
            next.putExtra("train_no", tickets.get(position).getTrain_no());
            next.putExtra("station_train_code", tickets.get(position).getStation_train_code());
            next.putExtra("from_station_name", tickets.get(position).getFrom_station_name());
            next.putExtra("to_station_name", tickets.get(position).getTo_station_name());
            next.putExtra("lishi", tickets.get(position).getLishi());
            next.putExtra("start_station", getIntent().getCharSequenceExtra("start_station"));
            next.putExtra("end_station", getIntent().getCharSequenceExtra("end_station"));
            next.putExtra("type", getIntent().getStringExtra("type"));

            Bundle bundle = new Bundle();
            bundle.putSerializable("leftTicket", tickets.get(position));
            next.putExtras(bundle);

            Log.v("Tag", tickets.get(position).getStation_train_code());
            startActivity(next);

        });

        recyclerView.setAdapter(adapter);
    }

    /**
     * 网络请求
     * GET 余票票价查询
     * @return JSON
     * */
    private String leftTicketQuery(String date, String from_station, String to_station) {
        String result = "";

        HttpURLConnection connection;
        BufferedReader reader;

        String url = "https://kyfw.12306.cn/otn/leftTicketPrice/query?";

        try {
            //1.建立连接
            URL requestUrl = new URL(url +
                    "leftTicketDTO.train_date=" + date +
                    "&leftTicketDTO.from_station=" + from_station +
                    "&leftTicketDTO.to_station=" + to_station +
                    "&leftTicketDTO.type=1&randCode=");
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
     * 解析余票信息JSON数据
     * { 第一层
     *    //...
     *    "data": [ 第二层
     *      { 第三层
     *          queryLeftNewDTO: { 第四层
     *              "train_no": "240000G42111",
     *              "station_train_code": "G421",
     *              "start_station_telecode": "BXP",
     *              "start_station_name": "北京西",
     *              "end_station_telecode": "NFZ",
     *              "end_station_name": "南宁东",
     *              "from_station_telecode": "BXP",
     *              "from_station_name": "北京西",
     *              "to_station_telecode": "GBZ",
     *              "to_station_name": "桂林北",
     *              "start_time": "07:31",
     *              "arrive_time": "18:01",
     *              "day_difference": "0",
     *              //...
     *          }
     *      },
     *      {
     *         //...
     *      },
     *      //...
     * }
     * @return ArrayList LeftTicket
     * */
    @SuppressLint("SetTextI18n")
    private ArrayList<LeftTicket> parseLeftTicketJSONData(String jsonStr) {

        ArrayList<LeftTicket> tickets = new ArrayList<>();

        try {
            //第一层
            JSONObject object = new JSONObject(jsonStr);
            //第二层数组 数组中的元素是object
            JSONArray array = object.optJSONArray("data");
            assert array != null;
            //第三层大括号为要取的数据，但还包了一层object
            for(int i = 0; i < array.length(); i++) {
                JSONObject queryLeftNewDTO = array.optJSONObject(i);
                JSONObject finalData = queryLeftNewDTO.getJSONObject("queryLeftNewDTO");

                LeftTicket leftTicket = new LeftTicket();
                leftTicket.setTrain_no(finalData.optString("train_no"));
                leftTicket.setStation_train_code(finalData.optString("station_train_code"));

                leftTicket.setStart_station_telecode(finalData.optString("start_station_telecode"));
                leftTicket.setStart_station_name(finalData.optString("start_station_name"));

                leftTicket.setEnd_station_telecode(finalData.optString("end_station_telecode"));
                leftTicket.setEnd_station_name(finalData.optString("end_station_name"));

                leftTicket.setFrom_station_telecode(finalData.optString("from_station_telecode"));
                leftTicket.setFrom_station_name(finalData.optString("from_station_name"));

                leftTicket.setTo_station_telecode(finalData.optString("to_station_telecode"));
                leftTicket.setTo_station_name(finalData.optString("to_station_name"));

                leftTicket.setStart_time(finalData.optString("start_time"));
                leftTicket.setArrive_time(finalData.optString("arrive_time"));
                leftTicket.setDay_difference(finalData.optInt("day_difference"));

                String[] lishi = finalData.optString("lishi").split(":");
                leftTicket.setLishi(lishi[0] + "时" + lishi[1] + "分");
                leftTicket.setStart_train_date(finalData.optString("start_train_date"));

                //软卧
                if(finalData.optString("rw_num").equals("-1")) {
                    leftTicket.setRw_num(false);
                } else {
                    leftTicket.setRw_num(true);
                    String price = finalData.optString("rw_price");
                    String integer = price.substring(0, price.length()-1);
                    String floating = price.substring(price.length()-1);
                    leftTicket.setRw_price(Integer.parseInt(integer) + "." + floating);
                }
                //硬卧
                if(finalData.optString("yw_num").equals("-1")) {
                    leftTicket.setYw_num(false);
                } else {
                    leftTicket.setYw_num(true);
                    String price = finalData.optString("yw_price");
                    String integer = price.substring(0, price.length()-1);
                    String floating = price.substring(price.length()-1);
                    leftTicket.setYw_price(Integer.parseInt(integer) + "." + floating);
                }
                //硬座
                if(finalData.optString("yz_num").equals("-1")) {
                    leftTicket.setYz_num(false);
                } else {
                    leftTicket.setYz_num(true);
                    String price = finalData.optString("yz_price");
                    String integer = price.substring(0, price.length()-1);
                    String floating = price.substring(price.length()-1);
                    leftTicket.setYz_price(Integer.parseInt(integer) + "." + floating);
                }
                //动卧
                if(finalData.optString("srrb_num").equals("-1")) {
                    leftTicket.setSrrb_num(false);
                } else {
                    leftTicket.setSrrb_num(true);
                    String price = finalData.optString("srrb_price");
                    String integer = price.substring(0, price.length()-1);
                    String floating = price.substring(price.length()-1);
                    leftTicket.setSrrb_price(Integer.parseInt(integer) + "." + floating);
                }
                //高级软卧
                if(finalData.optString("gr_num").equals("-1")) {
                    leftTicket.setGr_num(false);
                } else {
                    leftTicket.setGr_num(true);
                    String price = finalData.optString("gr_price");
                    String integer = price.substring(0, price.length()-1);
                    String floating = price.substring(price.length()-1);
                    leftTicket.setGr_price(Integer.parseInt(integer) + "." + floating);
                }
                //无座
                if(finalData.optString("wz_num").equals("-1")) {
                    leftTicket.setWz_num(false);
                } else {
                    leftTicket.setWz_num(true);
                    String price = finalData.optString("wz_price");
                    String integer = price.substring(0, price.length()-1);
                    String floating = price.substring(price.length()-1);
                    leftTicket.setWz_price(Integer.parseInt(integer) + "." + floating);
                }
                //二等座
                if(finalData.optString("ze_num").equals("-1")) {
                    leftTicket.setZe_num(false);
                } else {
                    leftTicket.setZe_num(true);
                    String price = finalData.optString("ze_price");
                    String integer = price.substring(0, price.length()-1);
                    String floating = price.substring(price.length()-1);
                    leftTicket.setZe_price(Integer.parseInt(integer) + "." + floating);
                }
                //一等座
                if(finalData.optString("zy_num").equals("-1")) {
                    leftTicket.setZy_num(false);
                } else {
                    leftTicket.setZy_num(true);
                    String price = finalData.optString("zy_price");
                    String integer = price.substring(0, price.length()-1);
                    String floating = price.substring(price.length()-1);
                    leftTicket.setZy_price(Integer.parseInt(integer) + "." + floating);
                }
                //商务座
                if(finalData.optString("swz_num").equals("-1")) {
                    leftTicket.setSwz_num(false);
                } else {
                    leftTicket.setSwz_num(true);
                    String price = finalData.optString("swz_price");
                    String integer = price.substring(0, price.length()-1);
                    String floating = price.substring(price.length()-1);
                    leftTicket.setSwz_price(Integer.parseInt(integer) + "." + floating);
                }

                tickets.add(leftTicket);
            }

            return tickets;

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }
    private final Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            if(msg.what == 0) {
                String data = msg.obj.toString();
                Toast.makeText(LeftTicketActivity.this, "主线程收到消息", Toast.LENGTH_SHORT).show();
                //处理JSON字符串
                tickets = parseLeftTicketJSONData(data);
                //判断是否筛选只看高铁动车
                if((getIntent().getStringExtra("filtration")) != null && getIntent().getStringExtra("filtration").equals("只看高铁动车")) {
                    tickets = filtrateZTK(tickets);
                    Toast.makeText(LeftTicketActivity.this, "筛选：只看高铁动车", Toast.LENGTH_SHORT).show();
                }
                //更新RecyclerView
                initRecyclerView(tickets);
                Toast.makeText(LeftTicketActivity.this, "主线程更新视图", Toast.LENGTH_SHORT).show();
            }
        }
    };
    /**
     * 筛选：只看高铁/动车
     * */
    private ArrayList<LeftTicket> filtrateZTK(ArrayList<LeftTicket> list) {
        ArrayList<LeftTicket> result = new ArrayList<>();

        for(LeftTicket ticket : list) {
            String station_train_code = ticket.getStation_train_code();
            if(station_train_code.startsWith("G") || station_train_code.startsWith("D"))
                result.add(ticket);
        }

        return result;
    }
}
