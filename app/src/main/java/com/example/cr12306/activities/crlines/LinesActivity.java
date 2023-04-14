package com.example.cr12306.activities.crlines;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cr12306.R;
import com.example.cr12306.activities.more.SettingsActivity;

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

    //一级页面按钮和布局
    public LinearLayout lines_main;
    public ImageButton back_lines;
    public Button btn_china_rail, btn_cr_highSpeed;
    public TextView title;

    //二级页面按钮和布局
    public LinearLayout cr_list;
    public ImageButton back_lines_details;
    public ListView listView_Lines;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lines);

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
        listView_Lines = findViewById(R.id.listView_lines);

        back_lines.setOnClickListener(this);
        btn_china_rail.setOnClickListener(this);
        btn_cr_highSpeed.setOnClickListener(this);
        back_lines_details.setOnClickListener(this);
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
            //另有RecyclerView点击事件单独设置即进入三级页面
        }
    }

    private void initListView() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < CRLines.values().length; i++) {
            //list.add();
        }
        ArrayAdapter<String> adapter_CR = new ArrayAdapter<>(this, R.layout.line_item, list);
    }
}
