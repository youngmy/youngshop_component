package com.young.module.demo;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.young.commonconfig.helper.ARouterHelperKt.*;
import com.young.base.activity.BaseActivity;
import com.young.helper.ARouterHelper;
import com.young.module.demo.databinding.ActivityStaggerdRecyclerViewBinding;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.young.module.demo.adapter.StaggerdRecyclerViewAdapter;
import com.young.module.demo.bean.StaggerdRecyclerViewBean;
import com.young.module.demo.view.FullyStaggeredGridLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;


import java.util.ArrayList;
import java.util.List;


@Route(path = "/demo/activity/StaggerdRecyclerViewActivity")
public class StaggerdRecyclerViewActivity extends BaseActivity<ActivityStaggerdRecyclerViewBinding> {


    private List<StaggerdRecyclerViewBean> dataList = new ArrayList<>();
    private StaggerdRecyclerViewAdapter adapter;
    private final static String TAG = "DemoStaggerdRecyclerView";
    private final static String CDN_URL="http://172.16.4.249/mkcdn";
    private FullyStaggeredGridLayoutManager slm=null;


    @Override
    public int getLayoutId() {
        return R.layout.activity_staggerd_recycler_view;
    }

    @Override
    public void initData() {
        slm=new FullyStaggeredGridLayoutManager(2,
                FullyStaggeredGridLayoutManager.VERTICAL);

        mBinding.recyclerView.setLayoutManager(slm);

        ((SimpleItemAnimator)mBinding.recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        ((DefaultItemAnimator)mBinding.recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        mBinding.recyclerView.getItemAnimator().setChangeDuration(0);
        mBinding.recyclerView.setHasFixedSize(true);

        initDatas();
    }


    private void initDatas() {

        dataList.add(new StaggerdRecyclerViewBean(CDN_URL+"/img/recommend/recom_13.jpeg", "1"));
        dataList.add(new StaggerdRecyclerViewBean(CDN_URL+"/img/recommend/recom_14.jpeg", "2"));
        dataList.add(new StaggerdRecyclerViewBean(CDN_URL+"/img/recommend/recom_15.jpeg", "3"));
        dataList.add(new StaggerdRecyclerViewBean(CDN_URL+"/img/recommend/recom_16.jpeg", "4"));
        dataList.add(new StaggerdRecyclerViewBean(CDN_URL+"/img/recommend/recom_17.jpeg", "5"));
        dataList.add(new StaggerdRecyclerViewBean(CDN_URL+"/img/recommend/recom_18.jpeg", "6"));
        dataList.add(new StaggerdRecyclerViewBean(CDN_URL+"img/recommend/recom_19.jpeg", "7"));
        dataList.add(new StaggerdRecyclerViewBean(CDN_URL+"/img/recommend/recom_20.jpeg", "8"));
        dataList.add(new StaggerdRecyclerViewBean(CDN_URL+"/img/recommend/recom_21.jpeg", "9"));
        dataList.add(new StaggerdRecyclerViewBean(CDN_URL+"/img/recommend/recom_22.jpeg", "10"));
        dataList.add(new StaggerdRecyclerViewBean(CDN_URL+"/img/recommend/recom_23.jpeg", "11"));
        dataList.add(new StaggerdRecyclerViewBean(CDN_URL+"/img/recommend/recom_24.jpeg", "12"));
        adapter = new StaggerdRecyclerViewAdapter(this, dataList);
        //主要就是这个LayoutManager，就是用这个来实现瀑布流的，2表示有2列(垂直)或3行(水平)，我们这里用的垂直VERTICAL

        //mBinding.recyclerView.addItemDecoration(new SpaceItemDecoration(2, 20));


        mBinding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Log.i(TAG, "上拉拉不动时触发加载新数据");
                    dataList = new ArrayList<>();
                    dataList.add(new StaggerdRecyclerViewBean(CDN_URL+"/img/recommend/recom_25.jpeg", "13"));
                    dataList.add(new StaggerdRecyclerViewBean(CDN_URL+"/img/recommend/recom_26.jpeg", "14"));
                    dataList.add(new StaggerdRecyclerViewBean(CDN_URL+"/img/recommend/recom_27.jpeg", "15"));
                    dataList.add(new StaggerdRecyclerViewBean(CDN_URL+"/img/recommend/recom_28.jpeg", "16"));
                    dataList.add(new StaggerdRecyclerViewBean(CDN_URL+"/img/recommend/recom_29.jpeg", "17"));
                    dataList.add(new StaggerdRecyclerViewBean(CDN_URL+"/img/recommend/recom_30.jpeg", "18"));
                    adapter.refreshDatas(dataList);
                }
                if (!recyclerView.canScrollVertically(-1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Log.i(TAG, "下拉拉不动时触发加载新数据");
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                slm.invalidateSpanAssignments();//防止第一行到顶部有空白
            }
        });
        //((SimpleItemAnimator)RecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        mBinding.recyclerView.setAdapter(adapter);
    }

}
