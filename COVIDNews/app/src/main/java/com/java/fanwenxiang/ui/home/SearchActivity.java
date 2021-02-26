package com.java.fanwenxiang.ui.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.java.fanwenxiang.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class SearchActivity extends Activity implements AdapterView.OnItemClickListener {
    private EditText edit_text_key_word=null;
    private ArrayList<News> newsList=null;
    private int pageSize=20;
    private int pageNumber=1;
    private ListView listNews;
    private NewsAdapter newsAdapter=null;
    private Toast toast;

    private String keyWord;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        edit_text_key_word=findViewById(R.id.edit_text_key_word);

        listNews = (ListView) findViewById(R.id.history_list);
        newsList=new ArrayList<>();

        newsAdapter = new NewsAdapter(newsList, this);
        listNews.setAdapter(newsAdapter);
        listNews.setOnItemClickListener(this);

        edit_text_key_word.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (startSearch!=null)
                {
                    startHandler.removeCallbacks(startSearch);
                }
                keyWord=editable.toString();
                startHandler.postDelayed(startSearch, 600);
            }
        });
    }

    @SuppressLint("HandlerLeak")
    private Handler startHandler=new Handler() {
        @Override
        public void handleMessage(Message message) {
            super.getMessageName(message);
            new Thread(networkLink).start();
        }
    };

    private Runnable startSearch=new Runnable() {
        @Override
        public void run() {
            Message message=new Message();
            startHandler.sendMessage(message);
        }
    };


    @SuppressLint("HandlerLeak")
    Handler handler=new Handler() {
        @Override
        public void handleMessage(Message message) {
            super.getMessageName(message);
            Bundle bundle=message.getData();
            try {
                int num=bundle.getInt("num");
                JSONArray json=new JSONArray(bundle.getString("json"));
                for (int i=0; i<num; i++)
                {
                    News news=new News(json.get(i).toString(), false);
                    newsList.add(news);
                }
                newsAdapter.notifyDataSetChanged();
            } catch (Exception e) {}
        }
    };

    Runnable networkLink=new Runnable() {
        @Override
        public void run() {
            try {
                URL url=null;
                BufferedReader in=null;
                JSONArray array=null;
                ArrayList<JSONObject> contains=new ArrayList<>();
                for (pageNumber=0; pageNumber<30; pageNumber++)
                {
                    url = new URL("https://covid-dashboard.aminer.cn/api/events/list"+"?type=all&page="+pageNumber+"&size="+pageSize);
                    in=new BufferedReader(new InputStreamReader(url.openStream()));
                    array=new JSONObject(in.readLine()).getJSONArray("data");
                    for (int i=0; i<pageSize; i++)
                        if (((JSONObject)array.get(i)).getString("title").contains(keyWord))
                            contains.add((JSONObject)array.get(i));
                }
                if (contains.size()<20)
                {
                    for (pageNumber=50; pageNumber<150; pageNumber++)
                    {
                        url = new URL("https://covid-dashboard.aminer.cn/api/events/list"+"?type=all&page="+pageNumber+"&size="+pageSize);
                        in=new BufferedReader(new InputStreamReader(url.openStream()));
                        array=new JSONObject(in.readLine()).getJSONArray("data");
                        for (int i=0; i<pageSize; i++)
                            if (((JSONObject)array.get(i)).getString("title").contains(keyWord))
                                contains.add((JSONObject)array.get(i));
                    }
                }
                if (contains.size()<20)
                {
                    for (pageNumber=50; pageNumber<400; pageNumber++)
                    {
                        url = new URL("https://covid-dashboard.aminer.cn/api/events/list"+"?type=all&page="+pageNumber+"&size="+pageSize);
                        in=new BufferedReader(new InputStreamReader(url.openStream()));
                        array=new JSONObject(in.readLine()).getJSONArray("data");
                        for (int i=0; i<pageSize; i++)
                            if (((JSONObject)array.get(i)).getString("title").contains(keyWord))
                                contains.add((JSONObject)array.get(i));
                    }
                }
                Bundle bundle=new Bundle();
                bundle.putInt("num", contains.size());
                bundle.putString("json", new JSONArray(contains).toString());
                Message message=new Message();
                message.setData(bundle);
                handler.sendMessage(message);
            } catch (Exception e) {}
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(this, NewsContextActivity.class);
        Bundle bd = new Bundle();

        bd.putString("title", newsList.get(position).getNewsTitle());
        bd.putString("content", newsList.get(position).getNewsContent());
        bd.putString("source", newsList.get(position).getSource());
        bd.putString("type", newsList.get(position).getType());
        bd.putString("date", newsList.get(position).getDate());
        intent.putExtras(bd);
        startActivity(intent);
    }
}
