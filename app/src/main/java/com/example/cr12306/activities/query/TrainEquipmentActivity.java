package com.example.cr12306.activities.query;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cr12306.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TrainEquipmentActivity extends AppCompatActivity {

    public String trainCode;
    public EditText editText;
    public Button btn_equip_query;
    public ImageButton back3;

    public TextView txt_equipment;
    public WebView webView;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment);

        txt_equipment = findViewById(R.id.txt_equipment);
        webView = findViewById(R.id.webView);

        editText = findViewById(R.id.edit_trainCode);
        btn_equip_query = findViewById(R.id.btn_equip_query);
        btn_equip_query.setOnClickListener(view -> {
            trainCode = editText.getText().toString();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    String result = netWorkRequest(trainCode);
                    Message msg = new Message();
                    msg.what = 0;
                    msg.obj = result;

                    handler.sendMessage(msg);
                }
            }).start();

        });

        back3 = findViewById(R.id.back3);
        back3.setOnClickListener(view -> TrainEquipmentActivity.this.finish());

    }

    /**
     * 网络请求
     * @return 字符串结果
     * */
    private String netWorkRequest(String train_code){
        HttpURLConnection connection;
        BufferedReader reader;
        String result;

        try{
            //1.建立连接
            URL url = new URL("http://xyrail.cn/trainquery/showTrainEquip.php?"+
                    "trainCode="+ train_code + "&showSeq=true");
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");
            connection.connect();

            //2.获取二进制流
            InputStream stream = connection.getInputStream();
            //3.将二进制流包装
            reader = new BufferedReader((new InputStreamReader(stream)));

            //从buffer reader读取String字符串
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null){
                builder.append(line).append("\n");
            }

            if(builder.length() == 0){
                return null;
            }
            //result = builder.toString();
            result = url.toString();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private final Handler handler = new Handler(Looper.myLooper()){
      @Override
      public void handleMessage(@NonNull Message msg){
          super.handleMessage(msg);

          if(msg.what == 0){
              String data = msg.obj.toString();
              //txt_equipment.setText(data);
              webView.loadUrl(data);
              Toast.makeText(TrainEquipmentActivity.this, "主线程接收到消息", Toast.LENGTH_SHORT).show();
          }
      }
    };
}
