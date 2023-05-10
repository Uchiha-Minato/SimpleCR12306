package com.example.cr12306.activities.tickets;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cr12306.R;
import com.example.cr12306.domain.BuyTicket;

import java.util.Objects;

public class ConfirmOrderActivity extends AppCompatActivity {

    public BuyTicket buyTicket;
    public ImageButton back_confirm;
    public TextView train_code, start_end, from_station, to_station;
    public TextView from_station1, start_time, start_day, seat_type, price;

    public LinearLayout choose_seat_ZE, choose_seat_ZY;
    public CheckBox seat_A_ZE, seat_B_ZE, seat_C_ZE, seat_D_ZE, seat_F_ZE;
    public CheckBox seat_A_ZY, seat_C_ZY, seat_D_ZY, seat_F_ZY;
    public Button btn_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        if(getIntent().getSerializableExtra("confirm") != null) {
            buyTicket = (BuyTicket) getIntent().getSerializableExtra("confirm");
        }

        initView();
        updateViews();
    }

    private void initView() {
        back_confirm = findViewById(R.id.back_confirm);
        back_confirm.setOnClickListener(v -> finish());

        train_code = findViewById(R.id.confirm_train_code);
        start_end = findViewById(R.id.confirm_start_end);
        from_station = findViewById(R.id.confirm_from_station);
        to_station = findViewById(R.id.confirm_to_station);
        from_station1 = findViewById(R.id.confirm_from_station1);
        start_time = findViewById(R.id.confirm_start_time);
        start_day = findViewById(R.id.confirm_start_day);
        seat_type = findViewById(R.id.confirm_seat_type);
        price = findViewById(R.id.confirm_price);

        choose_seat_ZE = findViewById(R.id.choose_seat_ZE);
        seat_A_ZE = findViewById(R.id.seat_a);
        seat_B_ZE = findViewById(R.id.seat_b);
        seat_C_ZE = findViewById(R.id.seat_c);
        seat_D_ZE = findViewById(R.id.seat_d);
        seat_F_ZE = findViewById(R.id.seat_f);

        choose_seat_ZY = findViewById(R.id.choose_seat_ZY);
        seat_A_ZY = findViewById(R.id.seat_a_ZY);
        seat_C_ZY = findViewById(R.id.seat_c_ZY);
        seat_D_ZY = findViewById(R.id.seat_d_ZY);
        seat_F_ZY = findViewById(R.id.seat_f_ZY);
        btn_confirm = findViewById(R.id.btn_confirm);
    }

    @SuppressLint("SetTextI18n")
    private void updateViews() {
        train_code.setText(buyTicket.getStation_train_code());
        start_end.setText(buyTicket.getStart_station_name() + "-" + buyTicket.getEnd_station_name());
        from_station.setText(buyTicket.getFrom_station_name());
        to_station.setText(buyTicket.getTo_station_name());
        from_station1.setText(buyTicket.getFrom_station_name());
        start_time.setText(buyTicket.getStart_time());
        start_day.setText(buyTicket.getDate());
        seat_type.setText(buyTicket.getSeat_type());
        price.setText("￥" + buyTicket.getPrice());

        if(Objects.equals(buyTicket.getSeat_type(), "二等座")) {
            choose_seat_ZE.setVisibility(View.VISIBLE);
            choose_seat_ZY.setVisibility(View.GONE);
        } else if(Objects.equals(buyTicket.getSeat_type(), "一等座")) {
            choose_seat_ZY.setVisibility(View.VISIBLE);
            choose_seat_ZE.setVisibility(View.GONE);
        } else {
            choose_seat_ZY.setVisibility(View.GONE);
            choose_seat_ZE.setVisibility(View.GONE);
        }

        if(choose_seat_ZY.getVisibility() == View.VISIBLE) {
            seat_A_ZY.setOnCheckedChangeListener(((buttonView, isChecked) -> {
                if(seat_A_ZY.isChecked()) {
                    seat_C_ZY.setChecked(false);
                    seat_D_ZY.setChecked(false);
                    seat_F_ZY.setChecked(false);
                }
            }));
            seat_C_ZY.setOnCheckedChangeListener(((buttonView, isChecked) -> {
                if(seat_C_ZY.isChecked()) {
                    seat_A_ZY.setChecked(false);
                    seat_D_ZY.setChecked(false);
                    seat_F_ZY.setChecked(false);
                }
            }));
            seat_D_ZY.setOnCheckedChangeListener(((buttonView, isChecked) -> {
                if(seat_D_ZY.isChecked()) {
                    seat_A_ZY.setChecked(false);
                    seat_C_ZY.setChecked(false);
                    seat_F_ZY.setChecked(false);
                }
            }));
            seat_F_ZY.setOnCheckedChangeListener(((buttonView, isChecked) -> {
                if(seat_F_ZY.isChecked()) {
                    seat_A_ZY.setChecked(false);
                    seat_D_ZY.setChecked(false);
                    seat_C_ZY.setChecked(false);
                }
            }));
        }
        if(choose_seat_ZE.getVisibility() == View.VISIBLE) {
            seat_A_ZE.setOnCheckedChangeListener(((buttonView, isChecked) -> {
                if(seat_A_ZE.isChecked()) {
                    seat_B_ZE.setChecked(false);
                    seat_C_ZE.setChecked(false);
                    seat_D_ZE.setChecked(false);
                    seat_F_ZE.setChecked(false);
                }
            }));
            seat_B_ZE.setOnCheckedChangeListener(((buttonView, isChecked) -> {
                if(seat_B_ZE.isChecked()) {
                    seat_A_ZE.setChecked(false);
                    seat_C_ZE.setChecked(false);
                    seat_D_ZE.setChecked(false);
                    seat_F_ZE.setChecked(false);
                }
            }));
            seat_C_ZE.setOnCheckedChangeListener(((buttonView, isChecked) -> {
                if(seat_C_ZE.isChecked()) {
                    seat_B_ZE.setChecked(false);
                    seat_A_ZE.setChecked(false);
                    seat_D_ZE.setChecked(false);
                    seat_F_ZE.setChecked(false);
                }
            }));
            seat_D_ZE.setOnCheckedChangeListener(((buttonView, isChecked) -> {
                if(seat_D_ZE.isChecked()) {
                    seat_B_ZE.setChecked(false);
                    seat_C_ZE.setChecked(false);
                    seat_A_ZE.setChecked(false);
                    seat_F_ZE.setChecked(false);
                }
            }));
            seat_F_ZE.setOnCheckedChangeListener(((buttonView, isChecked) -> {
                if(seat_F_ZE.isChecked()) {
                    seat_B_ZE.setChecked(false);
                    seat_C_ZE.setChecked(false);
                    seat_D_ZE.setChecked(false);
                    seat_A_ZE.setChecked(false);
                }
            }));
        }
    }
}
