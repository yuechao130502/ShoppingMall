package com.wyuyc.shoppingmall.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wyuyc.shoppingmall.R;
import com.wyuyc.shoppingmall.home.bean.ResultBeanData;
import com.wyuyc.shoppingmall.utils.Constants;

import java.util.List;

/**
 * Created by yc on 2016/12/22.
 */

public class SeckillRecyclerViewAdapter extends RecyclerView.Adapter<SeckillRecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private final List<ResultBeanData.ResultBean.SeckillInfoBean.ListBean> list;


    public SeckillRecyclerViewAdapter(Context mContext, List<ResultBeanData.ResultBean.SeckillInfoBean.ListBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(mContext, R.layout.item_seckill, null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //设置数据
        setData(holder, position);
    }

    private void setData(ViewHolder holder, final int position) {
        ResultBeanData.ResultBean.SeckillInfoBean.ListBean listBean = list.get(position);
        Glide.with(mContext)
                .load(Constants.BASE_URl_IMAGE + listBean.getFigure())
                .into(holder.ivFigure);
        holder.tvCoverPrice.setText(" ￥" + listBean.getCover_price());
        holder.tvOriginPrice.setText(" ￥" + listBean.getOrigin_price());
        holder.ll_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext, "position==" + position, Toast.LENGTH_SHORT).show();
                if (onSeckillRecyclerView != null) {
                    onSeckillRecyclerView.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivFigure;
        private TextView tvCoverPrice;
        private TextView tvOriginPrice;
        private LinearLayout ll_root;

        public ViewHolder(View itemView) {
            super(itemView);
            ivFigure = (ImageView) itemView.findViewById(R.id.iv_figure);
            tvCoverPrice = (TextView) itemView.findViewById(R.id.tv_cover_price);
            tvOriginPrice = (TextView) itemView.findViewById(R.id.tv_origin_price);
            ll_root = (LinearLayout) itemView.findViewById(R.id.ll_root);
        }
    }

    private OnSeckillRecyclerView onSeckillRecyclerView;

    public interface OnSeckillRecyclerView {
        /**
         * 当每条被点击的时候回调
         * @param position
         */
        void onItemClick(int position);
    }

    /**
     * 设置item的监听
     * @param onSeckillRecyclerView
     */
    public void setOnSeckillRecyclerView(OnSeckillRecyclerView onSeckillRecyclerView) {
        this.onSeckillRecyclerView = onSeckillRecyclerView;
    }
}
