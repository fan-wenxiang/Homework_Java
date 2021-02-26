package com.java.fanwenxiang.ui.dashboard;

import com.bin.david.form.annotation.SmartColumn;
import com.bin.david.form.annotation.SmartTable;

import org.json.JSONArray;
import org.json.JSONObject;

@SmartTable(name = "各国疫情")
public class Newsdata {
    private JSONObject Object;
    private int[] confirmed = new int[300];
    private int[] suspected = new int[300];
    private int[] cured = new int[300];
    private int[] dead = new int[300];

    public Newsdata(){};
    public Newsdata(String name, String json)
    {
        try {
            Object = new JSONObject(json);
            district = name;
            begintime = Object.getString("begin");
            JSONArray dataJsonArray = Object.getJSONArray("data");
            JSONArray data_today = dataJsonArray.getJSONArray(dataJsonArray.length() - 1);
            confirmed_today = data_today.getInt(0);
            if(data_today.get(1).equals(null))
            {
                suspected_today = 0;//data_today.getInt(1);
            }
            else
            {
                suspected_today = data_today.getInt(1);
            }
            cured_today = data_today.getInt(2);
            dead_today = data_today.getInt(3);
            for(int i = 0; i < dataJsonArray.length(); i ++)
            {
                JSONArray thisdata = dataJsonArray.getJSONArray(i);
                confirmed[i] = thisdata.getInt(0);
                suspected[i] = thisdata.getInt(1);
                cured[i] = thisdata.getInt(2);
                dead[i] = thisdata.getInt(3);
            }
        } catch (Exception e) {}
    }

    public String getdistrict()
    {
        return district;
    }
    public String getbegintime()
    {
        return begintime;
    }
    public int getconfirmed(int i)
    {
        return confirmed[i];
    }
    public int getsuspected(int i)
    {
        return suspected[i];
    }
    public int getcured(int i)
    {
        return cured[i];
    }
    public int getdead(int i)
    {
        return dead[i];
    }

    @SmartColumn(name = "地区")
    private String district;
    @SmartColumn(id = 1, name = "疫情始于")
    private String begintime;
    @SmartColumn(id = 2, name = "累计确诊")
    private int confirmed_today;
    @SmartColumn(id = 3, name = "疑似")
    private int suspected_today;
    @SmartColumn(id = 4, name = "累计治愈")
    private int cured_today;
    @SmartColumn(id = 5, name = "总死亡")
    private int dead_today;
}
