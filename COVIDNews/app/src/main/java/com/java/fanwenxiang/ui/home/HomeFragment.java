package com.java.fanwenxiang.ui.home;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.java.fanwenxiang.MainActivity;
import com.java.fanwenxiang.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private ViewPager viewPager;
    private ArrayList<NewsListFragment> fragmentList;
    private NewsListFragment fragment1;
    private NewsListFragment fragment2;
    private NewsListFragment fragment3;
    private ListPagerAdapter adapter;
    private TabLayout tabLayout;
    private String[] titles=new String[3];
    private String[] types=new String[] {"all", "news", "paper"};
    private ImageButton change_category_button=null;
    private ImageButton search_button=null;
    private FragmentManager fragmentManager=null;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        viewPager=(ViewPager) view.findViewById(R.id.view_pager);
        tabLayout=(TabLayout) view.findViewById(R.id.tab_layout);
        change_category_button=(ImageButton) view.findViewById(R.id.change_category_button);
        search_button=(ImageButton) view.findViewById(R.id.search_button);
        fragmentList=new ArrayList<>();
        fragment1=new NewsListFragment(types[0]);
        fragment2=new NewsListFragment(types[1]);
        fragment3=new NewsListFragment(types[2]);
        fragmentList.add(fragment1);
        fragmentList.add(fragment2);
        fragmentList.add(fragment3);

        setList();

        fragmentManager=getChildFragmentManager();
        adapter=new ListPagerAdapter(fragmentManager, fragmentList, titles, view);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        change_category_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), ChangeCategoryActivity.class);
                intent.putExtra("addNewsType", ((MainActivity)getActivity()).getTypeNews());
                intent.putExtra("addPaperType", ((MainActivity)getActivity()).getTypePaper());
                startActivityForResult(intent, 0);
            }
        });

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void setList()
    {
        titles[0]="综合";
        int i=1;
        if (((MainActivity)getActivity()).getTypeNews())
        {
            titles[i]="新闻";
            i++;
        }
        if (((MainActivity)getActivity()).getTypePaper())
        {
            titles[i]="论文";
        }

        fragmentList.get(0).changeType(types[0]);
        if (fragmentList.size()==1)
        {
            if (((MainActivity)getActivity()).getTypeNews())
            {
                fragment2.changeType(types[1]);
                fragmentList.add(fragment2);
                if (((MainActivity)getActivity()).getTypePaper())
                {
                    fragment3.changeType(types[2]);
                    fragmentList.add(fragment3);
                }
            }
            else if (((MainActivity)getActivity()).getTypePaper())
            {
                fragment2.changeType(types[2]);
                fragmentList.add(fragment2);
            }

        }
        else if (fragmentList.size()==2)
        {
            if (((MainActivity)getActivity()).getTypeNews())
            {
                fragmentList.get(1).changeType(types[1]);
                if (((MainActivity)getActivity()).getTypePaper())
                {
                    fragment3.changeType(types[2]);
                    fragmentList.add(fragment3);
                }
            }
            else if (((MainActivity)getActivity()).getTypePaper())
            {
                fragmentList.get(1).changeType(types[2]);
            }
            else
            {
                fragmentList.remove(1);
            }

        }
        else
        {
            if (((MainActivity)getActivity()).getTypeNews())
            {
                fragmentList.get(1).changeType(types[1]);
                if (((MainActivity)getActivity()).getTypePaper())
                {
                    fragmentList.get(2).changeType(types[2]);
                }
                else
                {
                    fragmentList.remove(2);
                }
            }
            else if (((MainActivity)getActivity()).getTypePaper())
            {
                fragmentList.get(1).changeType(types[2]);
                fragmentList.remove(2);
            }
            else
            {
                fragmentList.remove(2);
                fragmentList.remove(1);
            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        if (resultCode==0)
        {
            ((MainActivity)getActivity()).setTypeNews(intent.getBooleanExtra("addNewsType", false));
            ((MainActivity)getActivity()).setTypePaper(intent.getBooleanExtra("addPaperType", false));
            setList();

            adapter.notifyDataSetChanged();

        }
    }


}