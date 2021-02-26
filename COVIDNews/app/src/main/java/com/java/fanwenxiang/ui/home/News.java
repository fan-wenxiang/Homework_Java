package com.java.fanwenxiang.ui.home;


import org.json.JSONObject;

public class News {
    private JSONObject jsonObject;
    private String id;
    private String type;
    private String source;
    private String newsTitle;
    private String newsContent;
    private String date;
    private boolean read=false;

    public News(String json, boolean read)
    {
        try {
            jsonObject=new JSONObject(json);
            id=jsonObject.getString("_id");
            newsTitle=jsonObject.getString("title");
            newsContent=jsonObject.getString("content");
            date=jsonObject.getString("date");
            type=jsonObject.getString("type");
            source=jsonObject.getString("source");
        } catch (Exception e) {}
        this.read=read;
    }

    public String getNewsTitle()
    {
        return newsTitle;
    }
    public String getId()
    {
        return id;
    }
    public String getType()
    {
        return type;
    }
    public String getSource()
    {
        return source;
    }
    public String getDate()
    {
        return date;
    }
    public String getNewsContent()
    {
        return newsContent;
    }

    public JSONObject getJsonObject()
    {
        return jsonObject;
    }

    public void read()
    {
        read=true;
    }

    public boolean isRead()
    {
        return read;
    }

}
