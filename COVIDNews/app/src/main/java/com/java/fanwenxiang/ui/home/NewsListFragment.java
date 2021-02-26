package com.java.fanwenxiang.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.java.fanwenxiang.MainActivity;
import com.java.fanwenxiang.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class NewsListFragment extends Fragment implements AdapterView.OnItemClickListener
{
    private View view;
    private ArrayList<News> newsList=null;
    private int pageSize=20;
    private int pageNumber=1;
    private String type;
    private RefreshLayout refresh_layout;
    private ListView listNews;
    private NewsAdapter newsAdapter=null;

    public NewsListFragment(String type)
    {
        this.type=type;
        newsList=new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.news_list, container, false);
        listNews = (ListView) view.findViewById(R.id.list_news);
        refresh_layout=view.findViewById(R.id.refresh_layout);

        newsAdapter = new NewsAdapter(newsList, getActivity());
        listNews.setAdapter(newsAdapter);
        listNews.setOnItemClickListener(this);
        newsList.clear();

        new Thread(networkLink).start();

        refresh_layout.setColorSchemeResources(R.color.colorPrimary);
        refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (pageNumber>4)
                    pageNumber=1;
                else if (pageNumber>1)
                    pageNumber--;
                else
                    pageNumber=3;
                newsList.clear();
                new Thread(networkLink).start();
            }
        });

        refresh_layout.setOnLoadListener(new RefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                if (pageNumber<=3)
                    pageNumber=5;
                else
                    pageNumber++;
                new Thread(networkLink).start();
            }
        });
        return view;
    }

    public void changeType(String type)
    {
        this.type=type;
        pageNumber=1;
        newsList.clear();
        new Thread(networkLink).start();
    }

    @SuppressLint("HandlerLeak")
    Handler handler=new Handler() {
        @Override
        public void handleMessage(Message message) {
            super.getMessageName(message);
            Bundle bundle=message.getData();
            try {
                JSONObject json=new JSONObject(bundle.getString("page"+pageNumber));
                for (int i=0; i<20; i++)
                {
                    News news=new News(json.getJSONArray("data").get(i).toString(),
                            ((MainActivity)getActivity()).setContains(((JSONObject)(json.getJSONArray("data").get(i))).getString("_id")));
                    newsList.add(news);
                }
                newsAdapter.notifyDataSetChanged();
                refresh_layout.setRefreshing(false);
                refresh_layout.setLoading(false);
            } catch (Exception e) {}
        }

    };

    Runnable networkLink=new Runnable() {
        @Override
        public void run() {
            try {
                URL url = new URL("https://covid-dashboard.aminer.cn/api/events/list"+"?type="+type+"&page="+pageNumber+"&size="+pageSize);
                BufferedReader in=new BufferedReader(new InputStreamReader(url.openStream()));
                Bundle bundle=new Bundle();
                bundle.putString("page"+pageNumber, in.readLine());
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
        if (!((MainActivity)getActivity()).setContains(newsList.get(position).getId()))
        {
            ((MainActivity)getActivity()).setAdd(newsList.get(position).getJsonObject());
            newsList.get(position).read();
        }

        bd.putString("title", newsList.get(position).getNewsTitle());
        bd.putString("content", newsList.get(position).getNewsContent());
        bd.putString("source", newsList.get(position).getSource());
        bd.putString("type", newsList.get(position).getType());
        bd.putString("date", newsList.get(position).getDate());
        intent.putExtras(bd);
        startActivity(intent);

    }

}
