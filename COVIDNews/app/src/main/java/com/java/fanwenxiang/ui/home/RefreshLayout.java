package com.java.fanwenxiang.ui.home;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.ListView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.java.fanwenxiang.R;

public class RefreshLayout extends SwipeRefreshLayout {
    private View view=null;
    private ListView listView=null;
    private OnLoadListener loadListener;
    private boolean isLoading;
    private int slop;
    private float downY;
    private float upY;

    public RefreshLayout(Context context, AttributeSet attributeSet)
    {
        super(context, attributeSet);
        view=View.inflate(context, R.layout.blank, null);
        slop= ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom)
    {
        super.onLayout(changed, left, top, right, bottom);
        if (listView==null)
        {
            if (getChildCount()>0)
            {
                if (getChildAt(0) instanceof ListView)
                {
                    listView=(ListView) getChildAt(0);
                    setListViewOnScroll();
                }
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY=motionEvent.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (canLoadMore()) {
                    loadData();
                }
                break;
            case MotionEvent.ACTION_UP:
                upY = getY();
                break;
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    private boolean canLoadMore()
    {
        boolean upAction=(downY-upY)>=slop;
        boolean lastItem = false;
        if (listView != null && listView.getAdapter() != null)
        {
            lastItem = listView.getLastVisiblePosition() == (listView.getAdapter().getCount() - 1);
        }
        return upAction && lastItem && (!isLoading);
    }

    private void loadData()
    {
        if (loadListener != null) {
            setLoading(true);
            loadListener.onLoad();
        }
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
        if (isLoading) {
            listView.addFooterView(view);
        } else {
            listView.removeFooterView(view);
            downY = 0;
            upY = 0;
        }
    }

    private void setListViewOnScroll() {
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (canLoadMore()) {
                    loadData();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
    }

    public interface OnLoadListener {
        void onLoad();
    }

    public void setOnLoadListener(OnLoadListener listener) {
        this.loadListener = listener;
    }

}
