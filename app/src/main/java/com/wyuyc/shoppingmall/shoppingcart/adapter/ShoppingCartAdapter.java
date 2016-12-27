package com.wyuyc.shoppingmall.shoppingcart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wyuyc.shoppingmall.R;
import com.wyuyc.shoppingmall.home.bean.GoodsBean;
import com.wyuyc.shoppingmall.shoppingcart.utils.CartProvider;
import com.wyuyc.shoppingmall.shoppingcart.view.NumberAddSubView;
import com.wyuyc.shoppingmall.utils.Contants;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.R.string.no;

/**
 * Created by yc on 2016/12/26.
 */
public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder> {

    private final Context mContext;
    private final List<GoodsBean> goodsBeanList;
    private final TextView tvShopcartTotal;
    private final CheckBox checkboxAll;
    /**
     * 完成状态下的checkbox
     */
    private final CheckBox cbAll;

    public ShoppingCartAdapter(Context mContext, List<GoodsBean> goodsBeanList, TextView tvShopcartTotal, CheckBox checkboxAll, CheckBox cbAll) {
        this.mContext = mContext;
        this.goodsBeanList = goodsBeanList;
        this.tvShopcartTotal = tvShopcartTotal;
        this.checkboxAll = checkboxAll;
        this.cbAll = cbAll;
        showTotalPrice();
        //设置点击事件
        setListener();
        //校验是否全选
        checkAll();
    }

    private void setListener() {
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //1.根据位置找到对应的bean对象
                GoodsBean goodsBean = goodsBeanList.get(position);
                //2.设置取反状态
                goodsBean.setIsChildSelected(!goodsBean.isChildSelected());
                CartProvider.getInstance().updataData(goodsBean); //保存状态
                //3.刷新状态
                notifyItemChanged(position);
                //4.校验是否全选
                checkAll();
                //5.重新计算价格
                showTotalPrice();
            }
        });
        //设置checkboxAll的点击事件
        checkboxAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1.得到状态
                boolean isCheck = checkboxAll.isChecked();
                //2.根据状态设置全选和非全选
                checkAll_none(isCheck);
                //3.计算总价格
                showTotalPrice();
            }
        });
        //设置cbAll的点击事件
        cbAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1.得到状态
                boolean isCheck = cbAll.isChecked();
                //2.根据状态设置全选和非全选
                checkAll_none(isCheck);
            }
        });
    }

    /**
     * 设置全选和非全选
     *
     * @param isCheck 是否全选
     */
    public void checkAll_none(boolean isCheck) {
        if (goodsBeanList != null && goodsBeanList.size() > 0) {
            for (int i = 0; i < goodsBeanList.size(); i++) {
                GoodsBean goodsBean = goodsBeanList.get(i);
                goodsBean.setIsChildSelected(isCheck);
                checkboxAll.setChecked(isCheck);
                cbAll.setChecked(isCheck);
                CartProvider.getInstance().updataData(goodsBean);
                notifyItemChanged(i);
            }
        }
    }


    public void checkAll() {
        if (goodsBeanList != null && goodsBeanList.size() > 0) {
            int number = 0;
            for (int i = 0; i < goodsBeanList.size(); i++) {
                GoodsBean goodsBean = goodsBeanList.get(i);
                if (!goodsBean.isChildSelected()) {
                    //非全选
                    checkboxAll.setChecked(false);
                    cbAll.setChecked(false);
                } else {
                    number++;
                }
            }
            if (number == goodsBeanList.size()) {
                //全选
                checkboxAll.setChecked(true);
                cbAll.setChecked(true);
            }
        } else {
            checkboxAll.setChecked(false);
            cbAll.setChecked(false);
        }
    }

    public void showTotalPrice() {
        tvShopcartTotal.setText(getTotalPrice() + "");
    }

    public double getTotalPrice() {
        double totalPrice = 0.0;
        if (goodsBeanList != null && goodsBeanList.size() > 0) {
            for (int i = 0; i < goodsBeanList.size(); i++) {
                GoodsBean goodsBean = goodsBeanList.get(i);
                if (goodsBean.isChildSelected()) {
                    totalPrice += Double.valueOf(goodsBean.getCover_price()) * Double.valueOf(goodsBean.getNumber());
                }
            }
        }
        return totalPrice;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(mContext, R.layout.item_shop_cart, null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.setData(goodsBeanList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return goodsBeanList.size();
    }

    public void deleteData() {
        if (goodsBeanList != null && goodsBeanList.size() > 0) {
            for (int i = 0; i < goodsBeanList.size(); i++) {
                //删除选中的
                GoodsBean goodsBean = goodsBeanList.get(i);
                if (goodsBean.isChildSelected()) {
                    //内存移除
                    goodsBeanList.remove(goodsBean);
                    //保存到本地
                    CartProvider.getInstance().deleteData(goodsBean);
                    //刷新
                    notifyItemRemoved(i);
                    i--;
                }
            }
        }

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.cb_gov)
        CheckBox cbGov;
        @Bind(R.id.iv_gov)
        ImageView ivGov;
        @Bind(R.id.tv_desc_gov)
        TextView tvDescGov;
        @Bind(R.id.tv_price_gov)
        TextView tvPriceGov;
        @Bind(R.id.numberAddSubView)
        NumberAddSubView numberAddSubView;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            //设置item的点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(getLayoutPosition());
                    }
                }
            });
        }

        public void setData(final GoodsBean goodsBean, final int position) {
            Glide.with(mContext)
                    .load(Contants.Base_URl_IMAGE + goodsBean.getFigure())
                    .into(ivGov);
            cbGov.setChecked(goodsBean.isChildSelected());
            tvDescGov.setText(goodsBean.getName());
            tvPriceGov.setText("￥" + goodsBean.getCover_price());
            //设置数字加减回调
            numberAddSubView.setValue(goodsBean.getNumber());
            numberAddSubView.setMinValue(1);
            numberAddSubView.setMaxValue(20);
            //设置商品数量变换监听
            numberAddSubView.setOnNumberChangeListener(new NumberAddSubView.OnNumberChangeListener() {
                @Override
                public void addNumber(View view, int value) {
                    //1.当前列表内存中
                    goodsBean.setNumber(value);
                    //2.本地更新
                    CartProvider.getInstance().updataData(goodsBean);
                    //3.刷新适配器
                    notifyItemChanged(position);
                    //4.再次计算价格
                    showTotalPrice();
                }

                @Override
                public void subNumner(View view, int value) {
                    //1.当前列表内存中
                    goodsBean.setNumber(value);
                    //2.本地更新
                    CartProvider.getInstance().updataData(goodsBean);
                    //3.刷新适配器
                    notifyItemChanged(position);
                    //4.再次计算价格
                    showTotalPrice();
                }
            });
        }
    }

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        public void onItemClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
