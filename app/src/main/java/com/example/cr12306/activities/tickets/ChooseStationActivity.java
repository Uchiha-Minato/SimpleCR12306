package com.example.cr12306.activities.tickets;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cr12306.MainActivity;
import com.example.cr12306.R;
import com.example.cr12306.adapter.QueryStationAdapter;
import com.example.cr12306.domain.Station;
import com.example.cr12306.utils.ChooseStationDBUtils;

import java.util.ArrayList;

public class ChooseStationActivity extends AppCompatActivity implements View.OnClickListener{

    public ChooseStationDBUtils util;
    public Intent intent_fromMain;
    private int requestCode;

    public ImageButton back;
    public EditText editText;
    public Button query_station;

    public TableLayout tableLayout;
    public Button beijing, shanghai, guangzhou, wuhan, shenzhen, chengdu, guilin, chongqing, lanzhou;
    public RecyclerView recyclerView_queryResult;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_station);

        //直接初始化数据库
        util = new ChooseStationDBUtils(this);

        intent_fromMain = getIntent();
        requestCode = getIntent().getIntExtra("requestCode", 0);
        Log.i("Storage", String.valueOf(getExternalFilesDir(null)));

        Button button = findViewById(R.id.btn_create_db);
        if(util.dataExists()) {
            button.setVisibility(View.GONE);
        }
        button.setOnClickListener(v -> {
            //util.createExternalStoragePrivateFile();
            if(util.fileExists()) {
                util.initStations();
            } else {
                Toast.makeText(this, "车站文件不存在！", Toast.LENGTH_SHORT).show();
            }
        });
        initView();
    }

    @Override
    protected void onStop() {
        super.onStop();
        setResult(2);
        finish();
    }

    private void initView() {
        back = findViewById(R.id.back_choose_station);
        editText = findViewById(R.id.edit_query_station);
        query_station = findViewById(R.id.btn_query_station);

        tableLayout = findViewById(R.id.table_common_use);
        beijing = findViewById(R.id.beijing);
        shanghai = findViewById(R.id.shanghai);
        guangzhou = findViewById(R.id.guangzhou);
        wuhan = findViewById(R.id.wuhan);
        shenzhen = findViewById(R.id.shenzhen);
        chengdu = findViewById(R.id.chengdu);
        guilin = findViewById(R.id.guilin);
        chongqing = findViewById(R.id.chongqing);
        lanzhou = findViewById(R.id.lanzhou);

        back.setOnClickListener(this);
        query_station.setOnClickListener(this);
        beijing.setOnClickListener(this);
        shanghai.setOnClickListener(this);
        guangzhou.setOnClickListener(this);
        wuhan.setOnClickListener(this);
        shenzhen.setOnClickListener(this);
        chengdu.setOnClickListener(this);
        guilin.setOnClickListener(this);
        chongqing.setOnClickListener(this);
        lanzhou.setOnClickListener(this);
    }

    public Station station = new Station();
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_choose_station -> {
                setResult(2);
                finish();
            }
            case R.id.btn_query_station -> {
                String stationName = editText.getText().toString();
                if(stationName.equals("")) {
                    Toast.makeText(this, "请输入要查询的车站（中文）", Toast.LENGTH_SHORT).show();
                } else {
                    ArrayList<Station> stationArrayList = util.queryStationByName(stationName);
                    initRecyclerView(stationArrayList);
                }
            }
            case R.id.beijing -> {
                station.setStation_name("北京");
                station.setTelecode("BJP");
                packUpBundle(station);
            }
            case R.id.shanghai -> {
                station.setStation_name("上海");
                station.setTelecode("SHH");
                packUpBundle(station);
            }
            case R.id.guangzhou -> {
                station.setStation_name("广州");
                station.setTelecode("GZQ");
                packUpBundle(station);
            }
            case R.id.wuhan -> {
                station.setStation_name("武汉");
                station.setTelecode("WHN");
                packUpBundle(station);
            }
            case R.id.shenzhen -> {
                station.setStation_name("深圳");
                station.setTelecode("SZQ");
                packUpBundle(station);
            }
            case R.id.chengdu -> {
                station.setStation_name("成都");
                station.setTelecode("CDW");
                packUpBundle(station);
            }
            case R.id.guilin -> {
                station.setStation_name("桂林");
                station.setTelecode("GLZ");
                packUpBundle(station);
            }
            case R.id.chongqing -> {
                station.setStation_name("重庆");
                station.setTelecode("CQW");
                packUpBundle(station);
            }
            case R.id.lanzhou -> {
                station.setStation_name("兰州");
                station.setTelecode("LZJ");
                packUpBundle(station);
            }
        }
    }

    private void initRecyclerView(ArrayList<Station> list) {
        recyclerView_queryResult = findViewById(R.id.recyclerView_query_station);
        QueryStationAdapter adapter = new QueryStationAdapter(list, this);
        recyclerView_queryResult.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_queryResult.setAdapter(adapter);

        adapter.setClickListener(((view, position) -> {
            Station station = list.get(position);
            station.setStation_name(station.getStation_name());
            station.setTelecode(station.getTelecode());

            packUpBundle(station);
        }));
    }
    private void packUpBundle(Station station) {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("station", station);
        intent.putExtras(bundle);
        setResult(requestCode, intent);//这里的requestCode就是MainActivity收到的resultCode
        finish();
    }
}
