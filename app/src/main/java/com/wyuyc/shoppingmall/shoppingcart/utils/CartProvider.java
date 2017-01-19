package com.wyuyc.shoppingmall.shoppingcart.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wyuyc.shoppingmall.app.MyApplication;
import com.wyuyc.shoppingmall.home.bean.GoodsBean;
import com.wyuyc.shoppingmall.utils.CacheUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 购物车数据存储类
 */
public class CartProvider {
    public static final String JSON_CART = "json_cart";
    private Context mContext;
    //优化过的HashMap集合
    private SparseArray<GoodsBean> sparseArray;

    private static CartProvider cartProvider;

    private CartProvider(Context mContext) {
        this.mContext = mContext;
        sparseArray = new SparseArray<>(100);
        listToSparseArray();
    }

    /**
     * @return 得到购车实例
     */
    public static CartProvider getInstance() {
        if (cartProvider == null) {
            cartProvider = new CartProvider(MyApplication.getContext());
        }
        return cartProvider;
    }

    /**
     * 从本地读取的数据加入到SparseArray中
     */
    private void listToSparseArray() {
        List<GoodsBean> goodsBeanList = getAllData();
        //放到sparseArry中
        if (goodsBeanList != null && goodsBeanList.size() > 0) {
            for (int i = 0; i < goodsBeanList.size(); i++) {
                GoodsBean goodsBean = goodsBeanList.get(i);
                sparseArray.put(Integer.parseInt(goodsBean.getProduct_id()), goodsBean);
            }
        }
    }


    /**
     * SparseArray转换成List
     * @return
     */
    private List<GoodsBean> parsesToList() {
        List<GoodsBean> goodsBeanList = new ArrayList<>();
        if (sparseArray != null && sparseArray.size() > 0) {
            for (int i = 0; i < sparseArray.size(); i++) {
                GoodsBean shoppingCart = sparseArray.valueAt(i);
                goodsBeanList.add(shoppingCart);
            }
        }
        return goodsBeanList;
    }

    /**
     * @return 获取本地所有的数据
     */
    public List<GoodsBean> getAllData() {
        return getDataFromLocal();
    }

    /**
     * 本地获取json数据，并且通过Gson解析成list列表数据
     * @return
     */
    public List<GoodsBean> getDataFromLocal() {
        List<GoodsBean> goodsBeanList = new ArrayList<>();
        //从本地获取缓存数据
        String savaJson = CacheUtils.getString(mContext, JSON_CART);
        if (!TextUtils.isEmpty(savaJson)) {
            //把数据转换成列表--->把String转化为List
            goodsBeanList = new Gson().fromJson(savaJson, new TypeToken<List<GoodsBean>>() {
            }.getType());
        }
        return goodsBeanList;

    }

    /**
     * 添加数据
     * @param goodsBean
     */
    public void addData(GoodsBean goodsBean) {

        //1.添加到内存中SparseArray
        //如果当前数据已经存在，就修改成number递增
        GoodsBean tempCart = sparseArray.get(Integer.parseInt(goodsBean.getProduct_id()));
        if (tempCart != null) {
            //内存中有了这条数据
            tempCart.setNumber(tempCart.getNumber() + goodsBean.getNumber());
        } else {
            tempCart = goodsBean;
            tempCart.setNumber(goodsBean.getNumber());
        }
        //同步到内存中
        sparseArray.put(Integer.parseInt(tempCart.getProduct_id()), tempCart);

        //保存数据,同步到本地
        saveLocal();
    }


    /**
     * 保存数据到本地
     */
    private void saveLocal() {
        //1.SparseArray转换成List
        List<GoodsBean> goodsBeanList = parsesToList();
        //2.使用Gson把列表转换成String类型
        String json = new Gson().toJson(goodsBeanList);
        //3.把String数据保存
        CacheUtils.putString(mContext, JSON_CART, json);
    }


    /**
     * 删除数据
     * @param goodsBean
     */
    public void deleteData(GoodsBean goodsBean) {
        //1.内存中删除数据
        sparseArray.delete(Integer.parseInt(goodsBean.getProduct_id()));
        //2.把内存的数据保持到本地
        saveLocal();
    }

    /**
     * 更新数据
     * @param goodsBean
     */
    public void updataData(GoodsBean goodsBean) {
        //修改数据
        sparseArray.put(Integer.parseInt(goodsBean.getProduct_id()), goodsBean);
        //保存数据
        saveLocal();
    }
}
