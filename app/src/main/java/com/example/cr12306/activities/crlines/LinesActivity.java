package com.example.cr12306.activities.crlines;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cr12306.R;
import com.example.cr12306.activities.more.SettingsActivity;
import com.example.cr12306.adapter.CRLinesAdapter;
import com.example.cr12306.adapter.DistanceAdapter;
import com.example.cr12306.domain.DistanceDetail;
import com.example.cr12306.utils.CRLineDBUtils;

import java.util.ArrayList;

/**
 * CR: China Railway 此处为 国铁I级客货共线线路
 * CRH: China Railway High-Speed 高速线/客运专线
 * */
public class LinesActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String TITLE = "中国国家铁路干线";
    public static final String CR = "国铁I级客货共线线路";
    public static final String CRH = "高速线/客运专线";

    public Intent intent_lines_main = new Intent();
    private final CRLineDBUtils util = new CRLineDBUtils(this);

    //一级页面按钮和布局
    public LinearLayout lines_main;
    public ImageButton back_lines;
    public Button btn_china_rail, btn_cr_highSpeed;
    public TextView title;

    //二级页面按钮和布局
    public LinearLayout cr_list;
    public ImageButton back_lines_details;
    public RecyclerView recyclerView_Lines;

    //三级页面按钮和布局
    public LinearLayout cr_distance;
    public ImageButton back_distance_detail;
    public RecyclerView recyclerView_details;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lines);
        // 只能用一次
        // util.addEnumIntoDB();

        initViews();
    }

    private void initViews() {
        //一级页面
        lines_main = findViewById(R.id.line_main_buttons);//布局
        title = findViewById(R.id.line_title);
        back_lines = findViewById(R.id.back_lines);
        btn_china_rail = findViewById(R.id.btn_cr);
        btn_cr_highSpeed = findViewById(R.id.btn_cr_highspeed);
        //二级页面
        cr_list = findViewById(R.id.include_cr);
        back_lines_details = findViewById(R.id.back_lines_details);
        recyclerView_Lines = findViewById(R.id.RecyclerView_lines);
        //三级页面
        cr_distance = findViewById(R.id.include_distance);
        back_distance_detail = findViewById(R.id.back_distance_detail);
        recyclerView_details = findViewById(R.id.RecyclerView_distance);

        back_lines.setOnClickListener(this);
        btn_china_rail.setOnClickListener(this);
        btn_cr_highSpeed.setOnClickListener(this);
        back_lines_details.setOnClickListener(this);
        back_distance_detail.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //一级页面
            case R.id.back_lines -> {
                intent_lines_main.setClass(this, SettingsActivity.class);
                startActivity(intent_lines_main);
                this.finish();
            }
            case R.id.btn_cr -> {
                lines_main.setVisibility(View.GONE);
                title.setText(CR);
                cr_list.setVisibility(View.VISIBLE);
                initRecyclerView_CR();
            }
            case R.id.btn_cr_highspeed -> {
                lines_main.setVisibility(View.GONE);
                title.setText(CRH);
                cr_list.setVisibility(View.VISIBLE);

            }
            //二级页面
            case R.id.back_lines_details -> {
                cr_list.setVisibility(View.GONE);
                title.setText(TITLE);
                lines_main.setVisibility(View.VISIBLE);
            }
            //另有ListView点击事件单独设置即进入三级页面
            //三级页面
            case R.id.back_distance_detail -> {
                cr_distance.setVisibility(View.GONE);
                title.setText(CR);//此处需要更改
                cr_list.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 初始化ListView控件
     * */
    private void initRecyclerView_CR() {
        ArrayList<String> list = util.getAllLines();
/*        CRLines[] allLines = CRLines.values();
        int row = allLines.length;
        if(row != 0) {
            for (CRLines lines: allLines) {
                list.add(String.valueOf(lines));
            }
        }*/

        CRLinesAdapter adapter = new CRLinesAdapter(list);

        recyclerView_Lines.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_Lines.setAdapter(adapter);
        //点击事件,进入三级页面
        adapter.setClickListener((view, position) -> {
            cr_list.setVisibility(View.GONE);
            cr_distance.setVisibility(View.VISIBLE);
            String line = list.get(position);
            title.setText(line);
            Toast.makeText(this, "你点击了"+line , Toast.LENGTH_SHORT).show();

            initDistanceRecyclerView(line);

        });
    }
    private void initRecyclerView_CRH() {
        CRHLines[] allLines = CRHLines.values();
        int row = allLines.length;
        ArrayList<String> list = new ArrayList<>();
        if(row != 0) {
            for (CRHLines lines: allLines) {
                list.add(String.valueOf(lines));
            }
        }

    }

    /**
     * 三级页面RecyclerView设置
     **/
    private void initDistanceRecyclerView(String line_name) {
        //1.新建链表//2.从数据库取数据加入链表
        ArrayList<DistanceDetail> details = util.getStationsByLine(line_name);
        //3.显示
        DistanceAdapter adapter = new DistanceAdapter(details);
        recyclerView_details.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_details.setAdapter(adapter);
        //点击事件
        adapter.setClickListener(((view, position) -> {
            Toast.makeText(this,
                    details.get(position).getStation() + "站距离始发站 "
                            + details.get(0).getStation() + details.get(position).getDistance() + "千米"
                    , Toast.LENGTH_SHORT).show();
        }));
    }
}
