package com.wyuyc.shoppingmall.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wyuyc.shoppingmall.home.activity.ActInfoActivity;
import com.wyuyc.shoppingmall.home.bean.ResultBeanData;
import com.wyuyc.shoppingmall.utils.Constants;

import java.util.List;

/**
 * Created by yc on 2016/12/22.
 */
public class ActViewPagerAdapter extends PagerAdapter {

    private static final String ACT_INFO = "act_info";
    private final List<ResultBeanData.ResultBean.ActInfoBean> datas;
    private final Context mContext;

    /**
     * @param container viewpager
     * @param position  对应页面的位置
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        //活动商品信息类
        final ResultBeanData.ResultBean.ActInfoBean actInfoBean = datas.get(position);
        ImageView view = new ImageView(mContext);
        view.setScaleType(ImageView.ScaleType.FIT_XY);
        //加载图片
        Glide.with(mContext)
                .load(Constants.BASE_URl_IMAGE + actInfoBean.getIcon_url())
                .into(view);
        //设置点击事件
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ActInfoActivity.class);
                intent.putExtra(ACT_INFO, actInfoBean);
                mContext.startActivity(intent);
            }
        });
        //添加到容器中
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public ActViewPagerAdapter(Context mContext, List<ResultBeanData.ResultBean.ActInfoBean> act_info) {
        this.mContext = mContext;
        this.datas = act_info;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
