package com.wyuyc.shoppingmall.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wyuyc.shoppingmall.R;
import com.wyuyc.shoppingmall.home.bean.ResultBeanData;
import com.wyuyc.shoppingmall.utils.Constants;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by yc on 2016/12/25.
 */
public class HotGridViewAdapter extends BaseAdapter {
    private final Context mContext;
    private final List<ResultBeanData.ResultBean.HotInfoBean> datas;

    public HotGridViewAdapter(Context mContext, List<ResultBeanData.ResultBean.HotInfoBean> hot_info) {
        this.mContext = mContext;
        this.datas = hot_info;
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_hot_grid_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //设置数据
        ResultBeanData.ResultBean.HotInfoBean hotInfoBean = datas.get(position);
        Glide.with(mContext)
                .load(Constants.BASE_URl_IMAGE + hotInfoBean.getFigure())
                .into(holder.ivHot);
        holder.tvName.setText(hotInfoBean.getName());
        holder.tvPrice.setText("￥" + hotInfoBean.getCover_price());
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.iv_hot)
        ImageView ivHot;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_price)
        TextView tvPrice;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
