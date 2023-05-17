package com.example.cr12306.activities.more;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cr12306.R;
import com.example.cr12306.utils.UserDBUtils;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public UserDBUtils utils = new UserDBUtils(this);
    public Intent intent_login = new Intent();

    public EditText username, password;
    public Button sign_in, sign_up;
    public TextView forget_password;
    public ImageButton back_login;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
    }

    private void initViews(){
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        forget_password = findViewById(R.id.forget_password);
        sign_in = findViewById(R.id.sign_in);
        sign_up = findViewById(R.id.sign_up);
        back_login = findViewById(R.id.back_login);

        sign_in.setOnClickListener(this);
        sign_up.setOnClickListener(this);
        back_login.setOnClickListener(this);
        //forget_password.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in -> {

                String uname = getUsernameFromView();
                String pwd = getPasswordFromView();
                // TO-DO database actions
                String result = utils.querySingleUser(uname, pwd);
                if (result != null){
                    Toast.makeText(this, "欢迎你，" + result, Toast.LENGTH_SHORT).show();
                    intent_login.setClass(LoginActivity.this, SettingsActivity.class);
                    intent_login.putExtra("username", result);
                    int requestCode = 1;
                    setResult(requestCode, intent_login);
                    LoginActivity.this.finish();
                } else {
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
                    dialog.setIcon(R.drawable.about);
                    dialog.setTitle("登陆失败");
                    dialog.setMessage("用户名或密码错误");
                    dialog.setPositiveButton("好", (dialogInterface, i) ->
                            Toast.makeText(LoginActivity.this, "寄", Toast.LENGTH_SHORT).show());
                    dialog.show();
                }
            }
            case R.id.sign_up -> signUpDialog();
            case R.id.forget_password -> forgetPwdDialog();
            case R.id.back_login -> {
                int requestCode = 0;
                setResult(requestCode);
                LoginActivity.this.finish();
            }
        }
    }

    /**
     * 注册填表
     * */
    @SuppressLint("InflateParams")
    private void signUpDialog() {
        final TableLayout tableLayout = (TableLayout) getLayoutInflater().inflate(R.layout.signup_user, null);
        new AlertDialog.Builder(this)
                .setTitle("注册").setView(tableLayout)
                .setPositiveButton("确定", (dialogInterface, i) -> {
                    String strUname = ((EditText)tableLayout.findViewById(R.id.signup_username)).getText().toString();
                    String strPwd = ((EditText)tableLayout.findViewById(R.id.signup_password)).getText().toString();
                    String checkPwd = ((EditText)tableLayout.findViewById(R.id.signup_checkpwd)).getText().toString();
                    String phone = ((EditText)tableLayout.findViewById(R.id.signup_phone)).getText().toString();

                    if(!strUname.equals("")) {
                        if (!strPwd.equals("")) {
                            if (!strPwd.equals(checkPwd)) {
                                Toast.makeText(this, "两次输入的密码不同！", Toast.LENGTH_SHORT).show();
                                signUpDialog();
                            } else {
                                utils.signUp(strUname, strPwd, phone);
                                Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "密码不能为空!", Toast.LENGTH_SHORT).show();
                            signUpDialog();
                        }
                    } else {
                        Toast.makeText(this, "用户名不能为空!", Toast.LENGTH_SHORT).show();
                        signUpDialog();
                    }
                }).setNegativeButton("取消", (dialogInterface, i) -> {

                }).create().show();
    }
    /**
     * 忘记密码
     * */
    @SuppressLint("InflateParams")
    private void forgetPwdDialog() {
        final TableLayout tableLayout = (TableLayout) getLayoutInflater().inflate(R.layout.signup_user, null);
        new AlertDialog.Builder(this)
                .setTitle("忘记密码").setView(tableLayout)
                .setPositiveButton("确定", (dialogInterface, i) -> {
                    String strUname = ((EditText)tableLayout.findViewById(R.id.signup_username)).getText().toString();
                    String strPwd = ((EditText)tableLayout.findViewById(R.id.signup_password)).getText().toString();
                    String checkPwd = ((EditText)tableLayout.findViewById(R.id.signup_checkpwd)).getText().toString();
                    String phone = ((EditText)tableLayout.findViewById(R.id.signup_phone)).getText().toString();

                    if(utils.userExists(strUname)) {

                        if (!strPwd.equals(checkPwd)) {
                            Toast.makeText(this, "两次输入的密码不同！", Toast.LENGTH_SHORT).show();
                            forgetPwdDialog();
                        } else {
                            utils.forgetPwd(strUname, strPwd, phone);
                            Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "此用户名不存在!", Toast.LENGTH_SHORT).show();
                        forgetPwdDialog();
                    }
                }).setNegativeButton("取消", (dialogInterface, i) -> {

                }).create().show();
    }

    /**
     * 从界面获取用户名
     * @return 用户名和密码
     * */
    private String getUsernameFromView() {
        return username.getText().toString();
    }
    private String getPasswordFromView() {
        return password.getText().toString();
    }
}
