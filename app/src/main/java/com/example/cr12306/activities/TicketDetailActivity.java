package com.example.cr12306.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cr12306.R;

public class TicketDetailActivity extends AppCompatActivity {

    public ImageButton back2;
    public TextView txt_train_code, txt_details;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        back2 = findViewById(R.id.back2);
        back2.setOnClickListener(view -> {
            Intent back2 = new Intent(TicketDetailActivity.this, LeftTicketActivity.class);
            back2.putExtra("start_station", getIntent().getCharSequenceExtra("start_station"));
            back2.putExtra("end_station", getIntent().getCharSequenceExtra("end_station"));
            startActivity(back2);
            TicketDetailActivity.this.finish();
        });

        txt_train_code = findViewById(R.id.txt_train_code);
        txt_train_code.setText(getIntent().getStringExtra("station_train_code"));
        txt_details = findViewById(R.id.txt_details);
        txt_details.setText(" " + getIntent().getCharSequenceExtra("start_station")
                + "-" + getIntent().getCharSequenceExtra("end_station"));
    }
}
