package com.wyuyc.shoppingmall.home.fragment;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.wyuyc.shoppingmall.R;
import com.wyuyc.shoppingmall.base.BaseFragment;
import com.wyuyc.shoppingmall.home.adapter.HomeRecyclerAdapter;
import com.wyuyc.shoppingmall.home.bean.ResultBeanData;
import com.wyuyc.shoppingmall.user.activity.MessageCenterActivity;
import com.wyuyc.shoppingmall.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by yc on 2016/12/21.
 * 主页面的Fragment
 */

public class HomeFragment extends BaseFragment {

    private RecyclerView rvHome;
    private ImageView ib_top;
    private TextView tv_search_home;
    private TextView tv_message_home;

    private static final String TAG = HomeFragment.class.getSimpleName();
    private ResultBeanData.ResultBean resultBean;

    private HomeRecyclerAdapter adapter;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_home, null);
        rvHome = (RecyclerView) view.findViewById(R.id.rv_home);
        ib_top = (ImageView) view.findViewById(R.id.ib_top);
        tv_search_home = (TextView) view.findViewById(R.id.tv_search_home);
        tv_message_home = (TextView) view.findViewById(R.id.tv_message_home);
        // 设置点击事件
        initListener();
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        getDataFromNet();
    }

    /**
     * 联网请求主页的数据
     */
    private void getDataFromNet() {
        String url = Constants.HOME_URL;
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    /**
                     * 当请求失败的时候回调
                     * @param call
                     * @param e
                     * @param id
                     */
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError---首页请求失败==" + e.getMessage());
                    }

                    /**
                     * 联网成功的时候回调
                     * @param response 请求成功的数据
                     * @param id
                     */
                    @Override
                    public void onResponse(String response, int id) {
                        //解析数据
                        processData(response);
                    }
                });
    }

    private void processData(String json) {
        if (!TextUtils.isEmpty(json)) {
            ResultBeanData resultBeanData = JSON.parseObject(json, ResultBeanData.class);
            this.resultBean = resultBeanData.getResult();
//            Log.e(TAG, "processData" + resultBeanData.toString());
            if (this.resultBean != null) {
                //有数据
                //设置适配器
                adapter = new HomeRecyclerAdapter(mContext, this.resultBean);
                rvHome.setAdapter(adapter);
                //设置布局管理者
                GridLayoutManager manager = new GridLayoutManager(mContext, 1);
                //设置跨度大小监听
                manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        if (position <= 3) {
                            ib_top.setVisibility(View.GONE);
                        } else {
                            ib_top.setVisibility(View.VISIBLE);
                        }
                        return 1;
                    }
                });
                rvHome.setLayoutManager(manager);
            } else {
                //没有数据
                Log.e(TAG, "processData" + "没有数据");
            }
            Log.e(TAG, "onResponse---首页请求成功---resultBeanData==" + this.resultBean.getBanner_info());
        }
    }

    private void initListener() {
        // 置顶的监听
        ib_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 回到顶部
                rvHome.scrollToPosition(0);
            }
        });
        // 搜素的监听
        tv_search_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, " 搜索", Toast.LENGTH_SHORT).show();
            }
        });
        // 消息的监听
        tv_message_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MessageCenterActivity.class);
                mContext.startActivity(intent);
            }
        });
    }
}
