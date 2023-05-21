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
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cr12306.activities.more.SettingsActivity;
import com.example.cr12306.activities.query.TransActivity;
import com.example.cr12306.activities.query.TrainEquipmentActivity;
import com.example.cr12306.activities.query.TrainQueryActivity;
import com.example.cr12306.activities.tickets.ChooseStationActivity;
import com.example.cr12306.activities.tickets.LeftTicketActivity;
import com.example.cr12306.domain.Station;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;

import pl.com.salsoft.sqlitestudioremote.SQLiteStudioService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public Calendar calendar;
    public int year, month, day;

    public Button btn_start_station, btn_end_station, btn_query, choose_date, btn_toTrans;
    public TextView txt_start_station, txt_end_station;
    public ImageButton btn_change;

    public CheckBox checkBox_common, checkBox_student;//订票类型复选框
    public CheckBox checkBox_chooseType;//筛选：只看高铁动车

    public BottomNavigationView navigationView;

    public Station start_station = new Station();
    public Station end_station = new Station();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //连接SQLiteStudio
        SQLiteStudioService.instance().start(this);

        //设置缺省值
        start_station.setStation_name("北京");
        start_station.setTelecode("BJP");
        end_station.setStation_name("广州");
        end_station.setTelecode("GZQ");

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
     * 当Activity重新获得焦点时调用
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 3 || resultCode == 4) {
            Station station = (Station) data.getSerializableExtra("station");
            switch (resultCode) {
                case 3 -> {
                    txt_start_station.setText(station.getStation_name());
                    start_station = station;
                }
                case 4 -> {
                    txt_end_station.setText(station.getStation_name());
                    end_station = station;
                }
            }
        }
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
            builder.append("-").append(month+1);
        } else builder.append("-").append("0").append(month+1);

        if(day >= 10) {
            builder.append("-").append(day);
        } else builder.append("-").append("0").append(day);

        choose_date.setText(builder);
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
        btn_toTrans = findViewById(R.id.btn_trans);

        checkBox_common = findViewById(R.id.checkbox_common);
        checkBox_student = findViewById(R.id.checkbox_student);
        checkBox_chooseType = findViewById(R.id.checkbox_choose_type);

        checkBox_common.setOnCheckedChangeListener((buttonView, isChecked) -> checkBox_student.setChecked(!isChecked));
        checkBox_student.setOnCheckedChangeListener((buttonView, isChecked) -> checkBox_common.setChecked(!isChecked));

        //choose_date.setOnClickListener(this);
        btn_query.setOnClickListener(this);
        btn_change.setOnClickListener(this);
        btn_start_station.setOnClickListener(this);
        btn_end_station.setOnClickListener(this);
        btn_toTrans.setOnClickListener(this);
    }

    public Intent intent = new Intent();

    /**
     * 设置点击事件
     * */
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

        //进选择车站界面用
        int requestCode;

        switch (view.getId()) {
            case R.id.btn_query -> {

                if(choose_date.getText().toString().equals("选择")) {
                    Toast.makeText(this, "请选择日期", Toast.LENGTH_SHORT).show();
                } else {
                    intent.setClass(MainActivity.this, LeftTicketActivity.class);
                    intent.putExtra("start_station", txt_start_station.getText());
                    intent.putExtra("end_station", txt_end_station.getText());
                    intent.putExtra("date", choose_date.getText());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("bundle_start_station", start_station);
                    bundle.putSerializable("bundle_end_station", end_station);
                    intent.putExtras(bundle);

                    if(checkBox_student.isChecked())
                        intent.putExtra("type", "学生票");
                    if(checkBox_common.isChecked())
                        intent.putExtra("type", "普通票");

                    if(checkBox_chooseType.isChecked())
                        intent.putExtra("filtration", "只看高铁动车");

                    startActivity(intent);
                }

            }
            case R.id.change -> {
                CharSequence temp;
                temp = txt_start_station.getText();
                txt_start_station.setText(txt_end_station.getText());
                txt_end_station.setText(temp);

                Station tmp;
                tmp = start_station;
                start_station = end_station;
                end_station = tmp;
            }
            case R.id.btn_start_station -> {
                intent.setClass(this, ChooseStationActivity.class);
                requestCode = 3;
                intent.putExtra("requestCode", requestCode);
                startActivityForResult(intent, requestCode);
            }
            case R.id.btn_end_station -> {
                intent.setClass(this, ChooseStationActivity.class);
                requestCode = 4;
                intent.putExtra("requestCode", requestCode);
                startActivityForResult(intent, requestCode);
            }
            case R.id.btn_trans -> {
                intent.setClass(MainActivity.this, TransActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("bundle_start_station", start_station);
                bundle.putSerializable("bundle_end_station", end_station);
                intent.putExtras(bundle);
                if(!choose_date.getText().toString().equals("选择"))
                    intent.putExtra("date", choose_date.getText().toString());
                startActivity(intent);
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
                        startActivity(intent);
                    }
                    case R.id.nav_settings -> {
                        intent.setClass(MainActivity.this, SettingsActivity.class);
                        startActivity(intent);
                    }
                    case R.id.nav_train_query -> {
                        intent.setClass(MainActivity.this, TrainQueryActivity.class);
                        startActivity(intent);
                    }
                }

                return true;
            }
        });
    }

}