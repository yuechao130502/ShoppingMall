package com.wyuyc.shoppingmall.home.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wyuyc.shoppingmall.R;
import com.wyuyc.shoppingmall.app.GoodsInfoActivity;
import com.wyuyc.shoppingmall.home.adapter.GoodsListAdapter;
import com.wyuyc.shoppingmall.home.bean.GoodsBean;
import com.wyuyc.shoppingmall.home.utils.SpaceItemDecoration;
import com.wyuyc.shoppingmall.utils.Constants;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class BannerGoodsListActivity extends Activity {

    @Bind(R.id.rcv_goodsinfo_list)
    RecyclerView rcvGoodsinfoList;

    @Bind(R.id.iv_title)
    ImageView ivTitle;

    private GoodsListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_list_info);
        ButterKnife.bind(this);
        final List<GoodsBean> goodsBeanList = (List<GoodsBean>) getIntent().getSerializableExtra(Constants.GOODS_BEAN);
        // 设置列表标题图片
        setListTitle();
        // 设置不显示原始价格
        Constants.isShowOriginPrice = false;
        adapter = new GoodsListAdapter(BannerGoodsListActivity.this, goodsBeanList);
        rcvGoodsinfoList.setAdapter(adapter);
        rcvGoodsinfoList.setLayoutManager(new GridLayoutManager(BannerGoodsListActivity.this,2,GridLayoutManager.VERTICAL,false));
        rcvGoodsinfoList.addItemDecoration(new SpaceItemDecoration(10));
        adapter.setOnGoodsListClickListener(new GoodsListAdapter.OnGoodsListClickListener() {
            @Override
            public void onItemClick(int position) {
                GoodsBean goodsBean = goodsBeanList.get(position);
                Intent intent = new Intent(BannerGoodsListActivity.this, GoodsInfoActivity.class);
                intent.putExtra(Constants.GOODS_BEAN,goodsBean);
                startActivity(intent);
            }
        });
    }

    /**
     * 设置列表标题图片
     */
    private void setListTitle() {
        String imageUrl = getIntent().getStringExtra(Constants.GOODSLIST_TITLE);
        Glide.with(this)
                .load(Constants.BASE_URl_IMAGE +imageUrl)
                .into(ivTitle);
    }
}
