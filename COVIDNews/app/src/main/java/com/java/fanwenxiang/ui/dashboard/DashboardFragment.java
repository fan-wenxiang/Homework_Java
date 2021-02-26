package com.java.fanwenxiang.ui.dashboard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bin.david.form.annotation.SmartTable;
import com.java.fanwenxiang.R;
import com.java.fanwenxiang.ui.home.News;
import com.java.fanwenxiang.ui.home.NewsContextActivity;
import com.java.fanwenxiang.ui.home.NewsListFragment;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class DashboardFragment extends Fragment {
    TextView textView;
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        textView = root.findViewById(R.id.text_dashboard);

        Intent intent=new Intent(getActivity(), NewsdataActivity.class);
        startActivity(intent);
        return root;
    }
}