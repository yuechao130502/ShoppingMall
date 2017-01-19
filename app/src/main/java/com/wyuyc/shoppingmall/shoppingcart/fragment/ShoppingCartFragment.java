package com.wyuyc.shoppingmall.shoppingcart.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.wyuyc.shoppingmall.R;
import com.wyuyc.shoppingmall.app.MessageEvent;
import com.wyuyc.shoppingmall.base.BaseFragment;
import com.wyuyc.shoppingmall.home.bean.GoodsBean;
import com.wyuyc.shoppingmall.shoppingcart.adapter.ShoppingCartAdapter;
import com.wyuyc.shoppingmall.shoppingcart.pay.PayResult;
import com.wyuyc.shoppingmall.shoppingcart.pay.SignUtils;
import com.wyuyc.shoppingmall.shoppingcart.utils.CartProvider;

import org.greenrobot.eventbus.EventBus;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.R.attr.start;


/**
 * Created by yc on 2016/12/21.
 * 购物车的Fragment
 */

public class ShoppingCartFragment extends BaseFragment {

    @Bind(R.id.tv_shopcart_edit)
    TextView tvShopcartEdit;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.checkbox_all)
    CheckBox checkboxAll;
    @Bind(R.id.tv_shopcart_total)
    TextView tvShopcartTotal;
    @Bind(R.id.btn_check_out)
    Button btnCheckOut;
    @Bind(R.id.llCheckAll)
    LinearLayout llCheckAll;
    @Bind(R.id.cbAll)
    CheckBox cbAll;
    @Bind(R.id.btn_delete)
    Button btnDelete;
    @Bind(R.id.btn_collection)
    Button btnCollection;
    @Bind(R.id.llDelete)
    LinearLayout llDelete;
    @Bind(R.id.iv_empty)
    ImageView ivEmpty;
    @Bind(R.id.tv_empty_cart_tobuy)
    TextView tvEmptyCartTobuy;
    @Bind(R.id.llEmptyShopcart)
    LinearLayout llEmptyShopcart;

    private ShoppingCartAdapter adapter;

    /**
     * 编辑状态
     */
    private static final int ACTION_EDIT = 1;
    /**
     * 完成状态
     */
    private static final int ACTION_COMPLETE = 2;

    private static final String TAG = ShoppingCartFragment.class.getSimpleName();

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_shoppingcart, null);
        ButterKnife.bind(this, view);
        Log.e(TAG, "initView" + "ShoppingCartFragment初始化");
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        showData();
        //设置默认的编辑状态
        tvShopcartEdit.setTag(ACTION_EDIT);
    }

    @Override
    public void onResume() {
        super.onResume();
        showData();
    }

    /**
     * 空购物车
     */
    private void emptyShoppingCart() {
        llEmptyShopcart.setVisibility(View.VISIBLE);
        tvShopcartEdit.setVisibility(View.GONE);
        llDelete.setVisibility(View.GONE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.tv_shopcart_edit, R.id.btn_check_out,  R.id.btn_delete, R.id.btn_collection, R.id.tv_empty_cart_tobuy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_shopcart_edit: //编辑--完成
                int action = (int) view.getTag();
                if (action == ACTION_EDIT) {
                    //切换为完成状态
                    showDelete();
                } else if (action == ACTION_COMPLETE) {
                    //切换成编辑状态
                    hideDelete();
                }
                break;
            case R.id.btn_check_out: //结算
                pay(view);
                break;
            case R.id.btn_delete:
                //删除选中的
                adapter.deleteData();
                //校验状态
                adapter.checkAll();
                //数据大小为0时
                if (adapter.getItemCount() == 0) {
                    emptyShoppingCart();
                }
                break;
            case R.id.btn_collection: //收藏
                Toast.makeText(mContext, "收藏", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_empty_cart_tobuy:
                EventBus.getDefault().post(new MessageEvent("",""));
                break;
        }
    }


    private void showData() {
        CartProvider cartProvider = CartProvider.getInstance();
        List<GoodsBean> goodsBeanList = cartProvider.getAllData();
        if (goodsBeanList != null && goodsBeanList.size() > 0) {
            tvShopcartEdit.setVisibility(View.VISIBLE);
            llCheckAll.setVisibility(View.VISIBLE);
            llEmptyShopcart.setVisibility(View.GONE);
            adapter = new ShoppingCartAdapter(mContext, goodsBeanList, tvShopcartTotal, cartProvider, checkboxAll, cbAll);
            recyclerview.setAdapter(adapter);
            recyclerview.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        } else {
            emptyShoppingCart();
        }
    }

    private void hideDelete() {
        //1.设置状态和文本-编辑全部
        tvShopcartEdit.setTag(ACTION_EDIT);
        tvShopcartEdit.setText("编辑全部");
        //2.还原勾选状态
        if (adapter != null) {
            adapter.checkAll_none(true);
            adapter.showTotalPrice();
        }
        //3.删除视图隐藏
        llDelete.setVisibility(View.GONE);
        //4.结算视图显示
        llCheckAll.setVisibility(View.VISIBLE);
    }

    private void showDelete() {
        //1.设置状态和文本-完成
        tvShopcartEdit.setTag(ACTION_COMPLETE);
        tvShopcartEdit.setText("完成");
        //2.变成非勾选
        if (adapter != null) {
            adapter.checkAll_none(false);
            adapter.checkAll();
        }
        //3.删除视图显示
        llDelete.setVisibility(View.VISIBLE);
        //4.结算视图隐藏
        llCheckAll.setVisibility(View.GONE);
    }

    //------支付宝支付集成-------//

    // 商户PID
    public static final String PARTNER = "2088911876712776";
    // 商户收款账号
    public static final String SELLER = "chenlei@atguigu.com";
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAOkOAePlQvVY3lP3\n" +
            "Pb1dQaX+P/+7fkmPBJeaPYKMNTJLKxBlWVAriRXd1nbSjvOHuUhXfCUqPd9D6Znk\n" +
            "n0zNCvkcodmj387sUa/aqjyV5eczDcc4+djX4tyoVb7FmVgNA7ta1GAPvX/20zV+\n" +
            "uGqWbqlmdWjvEdhBeLcQzv9Q3L9NAgMBAAECgYBPJrS5o7mT/jS0K7pKQAv1xl4S\n" +
            "CqmfbfVPT9EoL1o51JGxpdopgj+yr38RJ/3o8+WXji7ZqHkPBM+ntp/rw4+/2Vhb\n" +
            "8ixAQKvodnZsxMme7N3wtOfJVqt/ZTs+Ws1ur/p45aIqGTMyZUy2hlX6uGHYWNz7\n" +
            "nzjkhjrF9IEJYvIAOQJBAPzKZSrTGgnxltX8ICDmpezEtpqKnIAvrTrqgJeDqggj\n" +
            "7HD4ZMkD9Q+o/0rR0NafsdWxAYI+/1gpyFwFyLvdTtcCQQDsA3fBk2JQ5xyxykxz\n" +
            "RHOB5i4ZVWxvwF1+dO6Q9DF83gzKT14U0QKgsa2fP/R4Anw9C0sm70gp7oZfgKnC\n" +
            "EVJ7AkAM4Rb0bpr2BLwFnPnaE0ZC4ObYytUcZtFxnf79OTURgxsJym4AG9aBfL+9\n" +
            "BSGZvbsmwwTrqOADkuVlYtoOUEEzAkBYVLPtbwvM27KRl/Uk7umMJGn8cUw0Rvq2\n" +
            "6WygM8SRx0liLnI3uEITgmxIvdbxU3zMG/30hHmtt+fytmTSjUkhAkEAgkHMvnT6\n" +
            "zi/wLdgiiiqSz1XwRSX3Xl1BUPKsIWzTdussddq17IGZDGIGkKg3cAI79HsNE7za\n" +
            "60v0UkT9/ohPJw==";
    // 支付宝公钥 公钥还要保存在服务器上
    public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDpDgHj5UL1WN5T9z29XUGl/j//u35JjwSXmj2CjDUySysQZVlQK4kV3dZ20o7zh7lIV3wlKj3fQ+mZ5J9MzQr5HKHZo9/O7FGv2qo8leXnMw3HOPnY1+LcqFW+xZlYDQO7WtRgD71/9tM1frhqlm6pZnVo7xHYQXi3EM7/UNy/TQIDAQAB";
    private static final int SDK_PAY_FLAG = 1;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(mContext, "支付结果确认中", Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(mContext, "支付失败", Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

    /**
     * call alipay sdk pay. 调用SDK支付
     *
     */
    public void pay(View v) {
        if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE) || TextUtils.isEmpty(SELLER)) {
            new AlertDialog.Builder(mContext).setTitle("警告").setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            //
//                            finish();
                        }
                    }).show();
            return;
        }
        String orderInfo = getOrderInfo("五邑商城商品", "五邑商城商品的详细描述", adapter.getTotalPrice() +"");

        /**
         * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
         */
        String sign = sign(orderInfo);
        try {
            /**
             * 仅需对sign 做URL编码
             */
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        /**
         * 完整的符合支付宝参数规范的订单信息
         */
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask((Activity) mContext);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * create the order info. 创建订单信息
     *
     */
    private String getOrderInfo(String subject, String body, String price) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm" + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     *
     */
    private String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content
     *            待签名订单信息
     */
    private String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     *
     */
    private String getSignType() {
        return "sign_type=\"RSA\"";
    }
}
