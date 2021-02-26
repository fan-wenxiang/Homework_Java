package com.java.fanwenxiang.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.java.fanwenxiang.R;

import java.util.List;

public class NewsAdapter extends BaseAdapter {
    private List<News> newsList;
    private Context context;

    public NewsAdapter(List<News> newsList, Context context)
    {
        this.newsList=newsList;
        this.context=context;
    }

    @Override
    public int getCount()
    {
        return newsList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder;
        if (convertView==null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.item_layout=(ConstraintLayout) convertView.findViewById(R.id.item_layout);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        TextView item_news_title=(TextView) viewHolder.item_layout.findViewById(R.id.item_news_title);
        TextView item_news_type=(TextView) viewHolder.item_layout.findViewById(R.id.item_news_type);
        TextView item_news_content=(TextView) viewHolder.item_layout.findViewById(R.id.item_news_content);

        if (newsList.get(position).isRead())
        {
            item_news_type.setTextColor(0xFF444444);
            item_news_title.setTextColor(0xFF333333);
        }
        else
        {
            item_news_type.setTextColor(0xFF93278F);
            item_news_title.setTextColor(0xFF000000);
        }

        item_news_title.setText(newsList.get(position).getNewsTitle());
        String type=newsList.get(position).getType();
        if (type.equals("news"))
            item_news_type.setText(R.string.type_news);
        else if (type.equals("paper"))
            item_news_type.setText(R.string.type_paper);
        else
            item_news_type.setText(R.string.type_all);
        item_news_content.setText(newsList.get(position).getNewsContent());

        return convertView;
    }

    private class ViewHolder{
        ConstraintLayout item_layout;
    }
}
