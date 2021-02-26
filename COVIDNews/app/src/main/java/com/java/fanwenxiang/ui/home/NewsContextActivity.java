package com.java.fanwenxiang.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;


import com.java.fanwenxiang.R;

public class NewsContextActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_context);
        TextView context_news_content=(TextView) findViewById(R.id.context_news_content);
        TextView context_news_title=(TextView) findViewById(R.id.context_news_title);
        TextView context_news_source=(TextView) findViewById(R.id.context_news_source);
        TextView context_page_type=(TextView) findViewById(R.id.context_page_type);
        Intent intent=getIntent();
        Bundle bd=intent.getExtras();
        context_news_content.setText(bd.getString("content"));
        context_news_title.setText(bd.getString("title"));
        String type=bd.getString("type");
        if (type.equals("news"))
            type="新闻";
        else if (type.equals("paper"))
            type="论文";
        else
            type="综合";
        String source="类型: "+type+"\n来源: "+bd.getString("source")+"\n时间: "+bd.getString("date");
        context_page_type.setText("  "+type);
        context_news_source.setText(source);
    }
}
