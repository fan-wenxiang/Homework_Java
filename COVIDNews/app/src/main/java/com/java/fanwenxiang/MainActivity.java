package com.java.fanwenxiang;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fManager=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        Data.hashSet=new HashSet<>();
        Data.record=new ArrayList<>();
        try {
            File dir=new File(getFilesDir(), "history.txt");

            FileInputStream fileInputStream=openFileInput("history.txt");
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(fileInputStream));


            if (bufferedReader.readLine()==null)
            {
                bufferedReader.close();
                fileInputStream.close();
                FileOutputStream fileOutputStream = new FileOutputStream(dir);
                fileOutputStream.write("[]".getBytes());
                fileOutputStream.close();
            }
            else
            {
                bufferedReader.close();
                fileInputStream.close();
            }

            fileInputStream=openFileInput("history.txt");
            bufferedReader=new BufferedReader(new InputStreamReader(fileInputStream));
            JSONArray history=new JSONArray(bufferedReader.readLine());
            for (int i=0; i<history.length(); i++)
            {
                Data.record.add(history.getJSONObject(i));
                Data.hashSet.add(history.getJSONObject(i).getString("_id"));
            }
            fileInputStream.close();

        } catch (Exception e) {}
    }

    public boolean getTypeNews()
    {
        return Data.addNewsType;
    }

    public boolean getTypePaper()
    {
        return Data.addPaperType;
    }

    public void setTypeNews(boolean b)
    {
        Data.addNewsType=b;
    }

    public void setTypePaper(boolean b)
    {
        Data.addPaperType=b;
    }

    public boolean setContains(String id)
    {
        return Data.hashSet.contains(id);
    }

    public void setAdd(JSONObject news)
    {
        try {
            Data.record.add(0, news);
            Data.hashSet.add(news.getString("_id"));
        } catch (Exception e) {}
    }

    @Override
    public void onRestart()
    {
        super.onRestart();
        fManager=getSupportFragmentManager();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        try {
            JSONArray history=new JSONArray(Data.record);

            File dir=new File(getFilesDir(), "history.txt");
            FileOutputStream fileOutputStream = new FileOutputStream(dir);
            fileOutputStream.write(history.toString().getBytes());
            fileOutputStream.close();
        } catch (Exception e) {}
    }
}