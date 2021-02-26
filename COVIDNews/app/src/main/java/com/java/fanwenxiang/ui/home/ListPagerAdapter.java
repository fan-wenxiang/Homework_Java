package com.java.fanwenxiang.ui.home;


import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ListPagerAdapter extends FragmentPagerAdapter {
    private String[] titles;
    private ArrayList<NewsListFragment> fragmentList;
    private FragmentManager fragmentManager;
    private View view;

    public ListPagerAdapter(FragmentManager fManger, ArrayList<NewsListFragment> fragmentList, String[] titles, View view)
    {
        super(fManger, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.fragmentManager=fManger;
        this.fragmentList=fragmentList;
        this.titles=titles;
        this.view=view;
    }

    @Override
    public int getCount()
    {
        return fragmentList.size();
    }

    @Override
    public Fragment getItem(int position)
    {
        return fragmentList.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return titles[position];
    }


}
