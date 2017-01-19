package com.wyuyc.shoppingmall.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wyuyc.shoppingmall.R;
import com.wyuyc.shoppingmall.home.bean.GoodsBean;
import com.wyuyc.shoppingmall.utils.Constants;

import java.util.List;

/**
 * Created by yc on 2016/12/30.
 */

public class GoodsListAdapter extends RecyclerView.Adapter<GoodsListAdapter.ViewHolder> {

    private final Context mContext;
    private final List<GoodsBean> list;

    public GoodsListAdapter(Context mContext, List<GoodsBean> goodsBeanList) {
        this.mContext = mContext;
        this.list = goodsBeanList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(mContext, R.layout.item_goods_list_info, null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //设置数据
        setData(holder, position);
    }

    private void setData(ViewHolder holder, final int position) {
        GoodsBean goodsBean = list.get(position);
        //设置条目图片、价格等信息
        Glide.with(mContext)
                .load(Constants.BASE_URl_IMAGE + goodsBean.getFigure())
                .into(holder.ivFigure);
        holder.tvCoverPrice.setText(" ￥" + goodsBean.getCover_price());
        // 判断是否显示原始价格 默认不显示
        // 若为ture显示原始价格,则设置原始价格
        if (Constants.isShowOriginPrice) {
            holder.tvOriginPrice.setText(" ￥" + goodsBean.getOrigin_price());
        }
        holder.tv_name.setText(goodsBean.getName());
        holder.ll_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onGoodsListClickListener != null) {
                    onGoodsListClickListener.onItemClick(position);
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
        private TextView tv_name;
        private LinearLayout ll_root;
        private RelativeLayout rl_origin_price;

        public ViewHolder(View itemView) {
            super(itemView);
            ivFigure = (ImageView) itemView.findViewById(R.id.iv_figure);
            tvCoverPrice = (TextView) itemView.findViewById(R.id.tv_cover_price);
            tvOriginPrice = (TextView) itemView.findViewById(R.id.tv_origin_price);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            ll_root = (LinearLayout) itemView.findViewById(R.id.ll_root);
            rl_origin_price = (RelativeLayout) itemView.findViewById(R.id.rl_origin_price);
            // 判断是否显示原始价格 默认不显示
            if (Constants.isShowOriginPrice) {
                rl_origin_price.setVisibility(View.VISIBLE);
            } else {
                rl_origin_price.setVisibility(View.GONE);
            }
        }
    }

    private OnGoodsListClickListener onGoodsListClickListener;

    public interface OnGoodsListClickListener {
        void onItemClick(int position);
    }

    public void setOnGoodsListClickListener(OnGoodsListClickListener onGoodsListClickListener) {
        this.onGoodsListClickListener = onGoodsListClickListener;
    }
}
