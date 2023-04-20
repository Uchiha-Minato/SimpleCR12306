package com.example.cr12306;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cr12306.activities.tickets.LeftTicketActivity;
import com.example.cr12306.activities.more.SettingsActivity;
import com.example.cr12306.activities.query.TrainEquipmentActivity;

import com.example.cr12306.activities.query.TrainQueryActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;

import pl.com.salsoft.sqlitestudioremote.SQLiteStudioService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public Calendar calendar;
    public int year, month, day;

    public Button btn_start_station, btn_end_station, btn_query, choose_date;
    public TextView txt_start_station, txt_end_station;
    public ImageButton btn_change;

    public BottomNavigationView navigationView;

    //已登录用户的用户名信息传递
    public Intent intent_fromLogin = getIntent();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //连接SQLiteStudio
        SQLiteStudioService.instance().start(this);

        initButtons();
        initBottomNavView();

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        txt_start_station = findViewById(R.id.txt_start_station);
        txt_end_station = findViewById(R.id.txt_end_station);


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
        choose_date.setText(new StringBuilder().append(year).append("/").
                append(month + 1).append("/").append(day));
    }

    /**
     * 初始化按钮
     * */
    public void initButtons(){
        choose_date = findViewById(R.id.choose_date);
        btn_query = findViewById(R.id.btn_query);
        btn_change = findViewById(R.id.change);
        btn_start_station = findViewById(R.id.btn_start_station);
        btn_end_station = findViewById(R.id.btn_end_station);

        //choose_date.setOnClickListener(this);
        btn_query.setOnClickListener(this);
        btn_change.setOnClickListener(this);
        btn_start_station.setOnClickListener(this);
        btn_end_station.setOnClickListener(this);
    }

    public Intent intent = new Intent();

    /**
     * 设置点击事件
     * */
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_query -> {

                if(choose_date.getText() == "选择") {
                    Toast.makeText(this, "请选择日期", Toast.LENGTH_SHORT).show();
                } else {
                    intent.setClass(MainActivity.this, LeftTicketActivity.class);
                    intent.putExtra("start_station", txt_start_station.getText());
                    intent.putExtra("end_station", txt_end_station.getText());
                    //已登录用户的用户名信息传递, 用于确认订单界面
                    if(intent_fromLogin != null) {
                        intent.putExtra("username", intent_fromLogin.getStringExtra("username"));
                    }
                    startActivity(intent);
                    MainActivity.this.finish();
                }

            }
            case R.id.change -> {
                CharSequence temp;
                temp = txt_start_station.getText();
                txt_start_station.setText(txt_end_station.getText());
                txt_end_station.setText(temp);
            }
            case R.id.btn_start_station -> {

            }
            case R.id.btn_end_station -> {
                //
            }
        }
    }

    /**
     * 主页底部视图
     * */
    public void initBottomNavView(){
        navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_query -> Toast.makeText(MainActivity.this, "您已经在购票界面", Toast.LENGTH_SHORT).show();
                    case R.id.nav_train_Equip -> {
                        intent.setClass(MainActivity.this, TrainEquipmentActivity.class);
                        if(intent_fromLogin != null) {
                            intent.putExtra("username", intent_fromLogin.getStringExtra("username"));
                        }
                        startActivity(intent);
                        MainActivity.this.finish();
                    }
                    case R.id.nav_settings -> {
                        intent.setClass(MainActivity.this, SettingsActivity.class);
                        if(intent_fromLogin != null) {
                            intent.putExtra("username", intent_fromLogin.getStringExtra("username"));
                        }
                        startActivity(intent);
                        MainActivity.this.finish();
                    }
                    case R.id.nav_train_query -> {
                        intent.setClass(MainActivity.this, TrainQueryActivity.class);
                        if(intent_fromLogin != null) {
                            intent.putExtra("username", intent_fromLogin.getStringExtra("username"));
                        }
                        startActivity(intent);
                        MainActivity.this.finish();
                    }
                }

                return true;
            }
        });
    }

}