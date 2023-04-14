package com.example.cr12306.activities.tickets;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cr12306.R;
import com.example.cr12306.MainActivity;
import com.example.cr12306.adapter.TicketAdapter;
import com.example.cr12306.domain.TicketTest;

import java.util.ArrayList;

public class LeftTicketActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    public ArrayList<TicketTest> list = new ArrayList<>();

    public TextView query_result;
    public Button btn_back;
    public ImageButton back1;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState){
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leftticket);

        Intent back = new Intent();
        back.setClass(LeftTicketActivity.this, MainActivity.class);

        back1 = findViewById(R.id.back1);
        back1.setOnClickListener(view -> {
            startActivity(back);
            LeftTicketActivity.this.finish();
        });

        query_result = findViewById(R.id.query_result);
        query_result.setText(getIntent().getCharSequenceExtra("start_station")
                + "-" + getIntent().getCharSequenceExtra("end_station"));

        btn_back = findViewById(R.id.query_back);
        btn_back.setOnClickListener( view -> {
            startActivity(back);
            LeftTicketActivity.this.finish();
        });

        initArrayList();
        initRecyclerView(list);
    }

    public void initArrayList(){
        TicketTest test = new TicketTest();
        test.setStation_train_code("Z335");
        test.setFrom_to("包头-南宁");
        test.setFrom_station_name("北京丰台");
        test.setStart_time("20:25");
        test.setTo_station_name("桂林北");
        test.setArrive_time("16:00");
        test.setLishi("19时35分");
        test.setTicket_msg("软卧:￥629 硬卧:￥399 硬座:￥236");

        list.add(test);

        TicketTest test1 = new TicketTest();
        test1.setStation_train_code("Z6");
        list.add(test1);
    }

    private void initRecyclerView(ArrayList<TicketTest> test){

        recyclerView = findViewById(R.id.recyclerView);//一定要写等号，否则空指针异常
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent next = new Intent();

        TicketAdapter adapter = new TicketAdapter(test);
        adapter.setClickListener((view, position) -> {
            next.setClass(LeftTicketActivity.this, TicketDetailActivity.class);
            next.putExtra("station_train_code", list.get(position).getStation_train_code());
            next.putExtra("from_station_name", list.get(position).getFrom_station_name());
            next.putExtra("to_station_name", list.get(position).getTo_station_name());
            next.putExtra("lishi", list.get(position).getLishi());
            next.putExtra("start_station", getIntent().getCharSequenceExtra("start_station"));
            next.putExtra("end_station", getIntent().getCharSequenceExtra("end_station"));
            Log.v("Tag", list.get(position).getStation_train_code());
            startActivity(next);

        });

        recyclerView.setAdapter(adapter);
    }


}
