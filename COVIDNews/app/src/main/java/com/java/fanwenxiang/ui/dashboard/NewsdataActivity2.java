package com.java.fanwenxiang.ui.dashboard;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.data.style.FontStyle;
import com.java.fanwenxiang.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Objects;


public class NewsdataActivity2 extends Activity {
    //TextView textView;
    private Button button = null;

    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_china);
        button = (Button) findViewById(R.id.btnOne);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        new Thread(networkLink2).start();
    }

    @SuppressLint("HandlerLeak")
    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            super.getMessageName(message);
            Bundle bundle = message.getData();
            //textView = findViewById(R.id.text_dashboard);
            //textView.setText("3");
            List<Newsdata> countryList = new ArrayList<>();
            SmartTable<Newsdata> table = findViewById(R.id.table);
            try {
                JSONObject json = new JSONObject(Objects.requireNonNull(bundle.getString("district")));
                Iterator<String> iterator = json.keys();
                while (iterator.hasNext()) {
                    String string = iterator.next();
                    if (string.contains("|")&&string.startsWith("Ch")) {
                        JSONObject dataJsonObject = json.getJSONObject(string);
                        Newsdata newsdata = new Newsdata(string, dataJsonObject.toString());
                        countryList.add(newsdata);
                    }
                }
            } catch (Exception e) {
            }
            table.setData(countryList);
            table.getConfig().setContentStyle(new FontStyle(50, Color.BLACK));
        }

    };

    Runnable networkLink2 = new Runnable() {
        @Override
        public void run() {
            try {
                URL url = new URL("https://covid-dashboard.aminer.cn/api/dist/epidemic.json");
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                Bundle bundle = new Bundle();
                bundle.putString("district", in.readLine());
                Message message = new Message();
                message.setData(bundle);
                handler.sendMessage(message);
            } catch (Exception e) {
            }
        }
    };
}