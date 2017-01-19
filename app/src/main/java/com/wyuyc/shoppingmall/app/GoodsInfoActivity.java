package com.wyuyc.shoppingmall.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wyuyc.shoppingmall.R;
import com.wyuyc.shoppingmall.home.bean.GoodsBean;
import com.wyuyc.shoppingmall.home.utils.VirtualkeyboardHeight;
import com.wyuyc.shoppingmall.shoppingcart.activity.ShoppingCartActivity;
import com.wyuyc.shoppingmall.shoppingcart.utils.CartProvider;
import com.wyuyc.shoppingmall.shoppingcart.view.NumberAddSubView;
import com.wyuyc.shoppingmall.utils.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.alipay.sdk.data.a.a;

public class GoodsInfoActivity extends Activity {

    public static final String GoodsInfo_URL = "http://www.baidu.com";
    @Bind(R.id.ib_good_info_back)
    ImageButton ibGoodInfoBack;
    @Bind(R.id.ib_good_info_more)
    ImageButton ibGoodInfoMore;
    @Bind(R.id.iv_good_info_image)
    ImageView ivGoodInfoImage;

    @Bind(R.id.tv_good_info_name)
    TextView tvGoodInfoName;
    @Bind(R.id.tv_good_info_desc)
    TextView tvGoodInfoDesc;
    @Bind(R.id.tv_good_info_price)

    TextView tvGoodInfoPrice;
    @Bind(R.id.tv_good_info_store)
    TextView tvGoodInfoStore;
    @Bind(R.id.tv_good_info_style)
    TextView tvGoodInfoStyle;
    /*@Bind(R.id.wb_good_info_more)
    WebView wbGoodInfoMore;*/

    @Bind(R.id.tv_good_info_callcenter)
    TextView tvGoodInfoCallcenter;
    @Bind(R.id.tv_good_info_collection)
    TextView tvGoodInfoCollection;
    @Bind(R.id.tv_good_info_cart)
    TextView tvGoodInfoCart;
    @Bind(R.id.btn_good_info_addcart)
    Button btnGoodInfoAddcart;
    @Bind(R.id.ll_goods_root)
    LinearLayout llGoodsRoot;
    @Bind(R.id.tv_more_share)
    TextView tvMoreShare;
    @Bind(R.id.tv_more_search)
    TextView tvMoreSearch;
    @Bind(R.id.tv_more_home)
    TextView tvMoreHome;
    @Bind(R.id.btn_more)
    Button btnMore;
    @Bind(R.id.ll_root)
    LinearLayout llRoot;

    private GoodsBean goodsBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_info);
        ButterKnife.bind(this);
        //接收数据
        goodsBean = (GoodsBean) getIntent().getSerializableExtra(Constants.GOODS_BEAN);
        if (goodsBean != null) {
            setDataForView(goodsBean);
        }
    }

    private void setDataForView(GoodsBean goodsBean) {
        tvGoodInfoName.setText(goodsBean.getName());
        Glide.with(GoodsInfoActivity.this)
                .load(Constants.BASE_URl_IMAGE + goodsBean.getFigure())
                .into(ivGoodInfoImage);
        tvGoodInfoPrice.setText("￥" + goodsBean.getCover_price());
//        setWebViewData(goodsBean.getProduct_id());
    }

    /*private void setWebViewData(String product_id) {
        if (product_id != null) {
            wbGoodInfoMore.loadUrl(GoodsInfo_URL);
            WebSettings settings = wbGoodInfoMore.getSettings();
            //设置支持JavaScript
            settings.setJavaScriptEnabled(true);
            //支持双击页面变大变小
            settings.setUseWideViewPort(true);
            //设置优先使用缓存
            settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            wbGoodInfoMore.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    //返回值为true的时候控制去webView打开,为false则 调用系统浏览器或第三方浏览器
                    return true;
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        view.loadUrl(request.getUrl().toString());
                    }
                    return true;
                }
            });
        }
    }*/

    @OnClick({R.id.ib_good_info_back, R.id.ib_good_info_more, R.id.btn_good_info_addcart, R.id.btn_more, R.id.tv_good_info_callcenter,
            R.id.tv_good_info_collection, R.id.tv_good_info_cart, R.id.tv_more_share, R.id.tv_more_search, R.id.tv_more_home})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_good_info_back: //返回
                finish();
                break;
            case R.id.ib_good_info_more: //更多
                if (llRoot.getVisibility() == View.VISIBLE) {
                    llRoot.setVisibility(View.GONE);
                } else {
                    llRoot.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.btn_good_info_addcart:
                showPopwindow();
                break;
            case R.id.btn_more:
                llRoot.setVisibility(View.GONE);
                break;
            case R.id.tv_good_info_callcenter:
                Toast.makeText(this, "联系客服", Toast.LENGTH_SHORT).show();
                // Intent intent = new Intent(this,CallCenterActivity.class);
                // startActivity(intent);
                break;
            case R.id.tv_good_info_collection:
                Toast.makeText(this, "收藏", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_good_info_cart:
                //跳转到购物车界面
                startActivity(new Intent(GoodsInfoActivity.this, ShoppingCartActivity.class));
                finish();
                break;
            case R.id.tv_more_share:
                llRoot.setVisibility(View.GONE);
                Toast.makeText(this, "分享", Toast.LENGTH_SHORT).show();
//                showShare();
                break;
            case R.id.tv_more_search:
                llRoot.setVisibility(View.GONE);
                Toast.makeText(this, "搜索", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_more_home:
                llRoot.setVisibility(View.GONE);
                startActivity(new Intent(GoodsInfoActivity.this, MainActivity.class));
                finish();
                break;
            case R.id.tv_good_info_style:

                break;
        }
    }

    /**
     * 显示popupWindow
     */
    private void showPopwindow() {
        // 1.获得View
        View view = View.inflate(this, R.layout.popupwindow_add_product, null);
        // 2.得到宽度和高度 两种方法
        final PopupWindow popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
//        new PopupWindow(view,getWindow().getDecorView().getWidth(),getWindow().getDecorView().getHeight());
        // 3.参数设置
        // 设置PopupWindow窗口可点击
        popupWindow.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xFFFFFFFF);
        popupWindow.setBackgroundDrawable(dw);
        // 设置PopupWindow的显示和消失动画
        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 4.控件处理
        ImageView iv_goodinfo_photo = (ImageView) view.findViewById(R.id.iv_goodinfo_photo);
        TextView tv_goodinfo_name = (TextView) view.findViewById(R.id.tv_goodinfo_name);
        TextView tv_goodinfo_price = (TextView) view.findViewById(R.id.tv_goodinfo_price);
        NumberAddSubView nas_goodinfo_num = (NumberAddSubView) view.findViewById(R.id.nas_goodinfo_num);
        Button bt_goodinfo_cancel = (Button) view.findViewById(R.id.bt_goodinfo_cancel);
        Button bt_goodinfo_confim = (Button) view.findViewById(R.id.bt_goodinfo_confim);
        // 加载图片
        Glide.with(this)
                .load(Constants.BASE_URl_IMAGE+goodsBean.getFigure())
                .into(iv_goodinfo_photo);
        // 设置名称
        tv_goodinfo_name.setText(goodsBean.getName());
        // 设置价格
        tv_goodinfo_price.setText("￥"+goodsBean.getCover_price());
        // 设置商品数量最大值和当前值
        nas_goodinfo_num.setMaxValue(10);
        nas_goodinfo_num.setValue(1);

        nas_goodinfo_num.setOnNumberChangeListener(new NumberAddSubView.OnNumberChangeListener() {
            @Override
            public void addNumber(View view, int value) {
                goodsBean.setNumber(value);
            }

            @Override
            public void subNumner(View view, int value) {
                goodsBean.setNumber(value);
            }
        });

        bt_goodinfo_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        bt_goodinfo_confim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                Toast.makeText(GoodsInfoActivity.this, "成功加入购物车"+goodsBean.getNumber(), Toast.LENGTH_SHORT).show();
                CartProvider.getInstance().addData(goodsBean);

            }
        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                popupWindow.dismiss();
            }
        });

        // 5.在底部显示
        popupWindow.showAtLocation(GoodsInfoActivity.this.findViewById(R.id.ll_goods_root),
                Gravity.BOTTOM,0, VirtualkeyboardHeight.getBottomStatusHeight(GoodsInfoActivity.this));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
