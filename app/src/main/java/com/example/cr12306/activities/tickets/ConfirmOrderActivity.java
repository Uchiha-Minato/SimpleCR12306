package com.example.cr12306.activities.tickets;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cr12306.MainActivity;
import com.example.cr12306.R;
import com.example.cr12306.activities.more.LoginActivity;
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

    private static final String fileName = "config";
    private static final String key_UserName = "UserName";
    public SharedPreferences preferences;
    public String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        preferences = getSharedPreferences(fileName, MODE_PRIVATE);

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
        btn_confirm.setOnClickListener(v -> {
            username = preferences.getString(key_UserName, null);
            if(username == null) {
                Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, LoginActivity.class);
                int requestCode = 1;
                startActivityForResult(intent, requestCode);
            } else {
                if(choose_seat_ZY.getVisibility() == View.VISIBLE) {
                    if(!seat_A_ZY.isChecked() && !seat_C_ZY.isChecked()
                            && !seat_D_ZY.isChecked() && !seat_F_ZY.isChecked()) {
                        Toast.makeText(this, "请选择一个偏好座位", Toast.LENGTH_SHORT).show();
                    } else {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(ConfirmOrderActivity.this);
                        dialog.setIcon(R.drawable.about);
                        dialog.setTitle("已经提交的信息");
                        String message = "已登录用户名：" + username + "\n"
                                + buyTicket.getStation_train_code()+"次\n"
                                + buyTicket.getFrom_station_name() + "-" + buyTicket.getTo_station_name()
                                + "\n" + buyTicket.getDate() + "\nZY(一等座) " + seatHasChecked();
                        dialog.setMessage(message);
                        dialog.setPositiveButton("好", (dialog1, which) -> {
                            copyToClipboard(message);
                            Intent intent = new Intent(this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        });
                        dialog.show();
                    }
                } else if(choose_seat_ZE.getVisibility() == View.VISIBLE) {
                    if(!seat_A_ZE.isChecked() && !seat_B_ZE.isChecked() && !seat_C_ZE.isChecked()
                            && !seat_D_ZE.isChecked() && !seat_F_ZE.isChecked()) {
                        Toast.makeText(this, "请选择一个偏好座位", Toast.LENGTH_SHORT).show();
                    } else {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(ConfirmOrderActivity.this);
                        dialog.setIcon(R.drawable.about);
                        dialog.setTitle("已经提交的信息");
                        String message = "已登录用户名：" + username + "\n"
                                + buyTicket.getStation_train_code()+"次\n"
                                + buyTicket.getFrom_station_name() + "-" + buyTicket.getTo_station_name()
                                + "\n" + buyTicket.getDate() + "\nZE(二等座) " + seatHasChecked();
                        dialog.setMessage(message);
                        dialog.setPositiveButton("好", (dialog1, which) -> {
                            copyToClipboard(message);
                            Intent intent = new Intent(this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        });
                        dialog.show();
                    }
                } else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(ConfirmOrderActivity.this);
                    dialog.setIcon(R.drawable.about);
                    dialog.setTitle("已经提交的信息");
                    String message = "已登录用户名：" + username + "\n"
                            + buyTicket.getStation_train_code()+"次\n"
                            + buyTicket.getFrom_station_name() + "-" + buyTicket.getTo_station_name()
                            + "\n" + buyTicket.getDate() + "\n" + buyTicket.getSeat_type();
                    dialog.setMessage(message);
                    dialog.setPositiveButton("好", (dialog1, which) -> {
                        copyToClipboard(message);
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    });
                    dialog.show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //登陆成功
        if(resultCode == 1) {
            username = preferences.getString(key_UserName, null);
        }
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
    /**
     * 选座判断, 返回选定的座位
     * */
    private String seatHasChecked() {
        String seat = "";

        if(choose_seat_ZY.getVisibility() == View.VISIBLE) {
            if(seat_A_ZY.isChecked())
                seat = seat_A_ZY.getText().toString();
            else if(seat_C_ZY.isChecked())
                seat = seat_C_ZY.getText().toString();
            else if(seat_D_ZY.isChecked())
                seat = seat_D_ZY.getText().toString();
            else if(seat_F_ZY.isChecked())
                seat = seat_F_ZY.getText().toString();
        } else if(choose_seat_ZE.getVisibility() == View.VISIBLE) {
            if(seat_A_ZE.isChecked())
                seat = seat_A_ZE.getText().toString();
            else if(seat_B_ZE.isChecked())
                seat = seat_B_ZE.getText().toString();
            else if(seat_C_ZE.isChecked())
                seat = seat_C_ZE.getText().toString();
            else if(seat_D_ZE.isChecked())
                seat = seat_D_ZE.getText().toString();
            else if(seat_F_ZE.isChecked())
                seat = seat_F_ZE.getText().toString();
        }

        return seat;
    }
    /**
     * 复制到剪贴板
     **/
    private void copyToClipboard(String text) {
        try {
            ClipboardManager manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData data = ClipData.newPlainText("Label", text);
            manager.setPrimaryClip(data);
            Toast.makeText(this, "已经复制到剪贴板", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
