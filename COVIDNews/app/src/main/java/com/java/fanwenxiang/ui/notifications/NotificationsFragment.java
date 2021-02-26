package com.java.fanwenxiang.ui.notifications;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.java.fanwenxiang.Data;
import com.java.fanwenxiang.R;
import com.java.fanwenxiang.ui.home.News;
import com.java.fanwenxiang.ui.home.NewsAdapter;
import com.java.fanwenxiang.ui.home.NewsContextActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class NotificationsFragment extends Fragment implements AdapterView.OnItemClickListener {
    private ArrayList<News> newsList=null;
    private ListView listNews;
    private NewsAdapter newsAdapter=null;
    private Button clear_button=null;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        listNews=view.findViewById(R.id.history_list);
        clear_button=view.findViewById(R.id.clear_button);
        newsList=new ArrayList<>();
        newsAdapter = new NewsAdapter(newsList, getActivity());
        listNews.setAdapter(newsAdapter);
        listNews.setOnItemClickListener(this);
        new Thread(networkLink).start();

        clear_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Data.record.clear();
                Data.hashSet.clear();
                newsList.clear();
                newsAdapter.notifyDataSetChanged();
            }
        });
        return view;
    }

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
                Bundle bundle=new Bundle();
                bundle.putInt("num", Data.record.size());
                bundle.putString("json", new JSONArray(Data.record).toString());
                Message message=new Message();
                message.setData(bundle);
                handler.sendMessage(message);
            } catch (Exception e) {}
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(getActivity(), NewsContextActivity.class);
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