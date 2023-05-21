package com.example.cr12306.activities.query;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cr12306.R;
import com.example.cr12306.activities.tickets.ChooseStationActivity;
import com.example.cr12306.domain.Station;

import java.util.Calendar;

public class TransActivity extends AppCompatActivity implements View.OnClickListener{

    public Calendar calendar;
    public int year, month, day;

    public ImageButton back_trans;
    public Button btn_start_station, btn_end_station, choose_date, query;
    public TextView txt_start_station, txt_end_station;
    public ImageButton change;

    public Station start_station = new Station();
    public Station end_station = new Station();
    public String train_date;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interchange);

        //设置缺省值
        start_station.setStation_name("北京");
        start_station.setTelecode("BJP");
        end_station.setStation_name("广州");
        end_station.setTelecode("GZQ");

        if(getIntent().getSerializableExtra("bundle_start_station") != null
                && getIntent().getSerializableExtra("bundle_end_station") != null
                && getIntent().getStringExtra("date") != null) {
            start_station = (Station) getIntent().getSerializableExtra("bundle_start_station");
            end_station = (Station) getIntent().getSerializableExtra("bundle_end_station");
            train_date = getIntent().getStringExtra("date");
        }

        initView();

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

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

    private void initView() {
        back_trans = findViewById(R.id.back_trans);
        back_trans.setOnClickListener(v -> finish());
        btn_start_station = findViewById(R.id.trans_start_station);
        txt_start_station = findViewById(R.id.trans_txt_start_station);
        txt_start_station.setText(start_station.getStation_name());

        btn_end_station = findViewById(R.id.trans_end_station);
        txt_end_station = findViewById(R.id.trans_txt_end_station);
        txt_end_station.setText(end_station.getStation_name());

        choose_date = findViewById(R.id.trans_choose_date);
        if(train_date != null)
            choose_date.setText(train_date);
        query = findViewById(R.id.btn_query_trans);
        change = findViewById(R.id.trans_change);

        btn_start_station.setOnClickListener(this);
        btn_end_station.setOnClickListener(this);
        query.setOnClickListener(this);
        change.setOnClickListener(this);

    }

    Intent intent = new Intent();
    public int requestCode;
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.trans_start_station -> {
                intent.setClass(this, ChooseStationActivity.class);
                requestCode = 3;
                intent.putExtra("requestCode", requestCode);
                startActivityForResult(intent, requestCode);
            }
            case R.id.trans_end_station -> {
                intent.setClass(this, ChooseStationActivity.class);
                requestCode = 4;
                intent.putExtra("requestCode", requestCode);
                startActivityForResult(intent, requestCode);
            }
            case R.id.trans_change -> {
                CharSequence temp;
                temp = txt_start_station.getText();
                txt_start_station.setText(txt_end_station.getText());
                txt_end_station.setText(temp);

                Station tmp;
                tmp = start_station;
                start_station = end_station;
                end_station = tmp;
            }
            case R.id.btn_query_trans -> {
                if(choose_date.getText().toString().equals("选择")) {
                    Toast.makeText(this, "请选择日期", Toast.LENGTH_SHORT).show();
                } else {
                    intent.setClass(this, TransDetailActivity.class);
                    intent.putExtra("start_station", txt_start_station.getText());
                    intent.putExtra("end_station", txt_end_station.getText());
                    intent.putExtra("date", choose_date.getText());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("bundle_start_station", start_station);
                    bundle.putSerializable("bundle_end_station", end_station);
                    intent.putExtras(bundle);

                    startActivity(intent);
                }

            }
        }
    }

}
