package com.example.cr12306.activities.query;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cr12306.MainActivity;
import com.example.cr12306.R;

public class TrainQueryActivity extends AppCompatActivity implements View.OnClickListener{

    public Intent intent_query = new Intent();

    public ImageButton back_trainQuery;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainquery);

        initViews();
    }

    private void initViews() {
        back_trainQuery = findViewById(R.id.back_trainQuery);

        back_trainQuery.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_trainQuery -> {
                intent_query.setClass(TrainQueryActivity.this, MainActivity.class);
                startActivity(intent_query);
                TrainQueryActivity.this.finish();
            }
        }
    }
}
