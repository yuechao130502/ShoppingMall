package com.wyuyc.shoppingmall.home.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wyuyc.shoppingmall.R;
import com.wyuyc.shoppingmall.app.GoodsInfoActivity;
import com.wyuyc.shoppingmall.home.adapter.ExpandableListViewAdapter;
import com.wyuyc.shoppingmall.home.adapter.GoodsListAdapter;
import com.wyuyc.shoppingmall.home.bean.ChannerListBean;
import com.wyuyc.shoppingmall.home.bean.GoodsBean;
import com.wyuyc.shoppingmall.home.utils.SpaceItemDecoration;
import com.wyuyc.shoppingmall.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

public class ChannelGoodsListActivity extends Activity {

    @Bind(R.id.ib_goods_list_back)
    ImageButton ibGoodsListBack;
    @Bind(R.id.tv_goods_list_search)
    TextView tvGoodsListSearch;
    @Bind(R.id.ib_goods_list_home)
    ImageButton ibGoodsListHome;
    @Bind(R.id.tv_goods_list_sort)
    TextView tvGoodsListSort;
    @Bind(R.id.tv_goods_list_price)
    TextView tvGoodsListPrice;
    @Bind(R.id.iv_goods_list_arrow)
    ImageView ivGoodsListArrow;
    @Bind(R.id.ll_goods_list_price)
    LinearLayout llGoodsListPrice;
    @Bind(R.id.tv_goods_list_select)
    TextView tvGoodsListSelect;
    @Bind(R.id.ll_goods_list_head)
    LinearLayout llGoodsListHead;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.ib_drawer_layout_back)
    ImageButton ibDrawerLayoutBack;
    @Bind(R.id.tv_ib_drawer_layout_title)
    TextView tvIbDrawerLayoutTitle;
    @Bind(R.id.tv_drawer_layout_confirm)
    TextView ibDrawerLayoutConfirm;
    @Bind(R.id.rb_select_hot)
    RadioButton rbSelectHot;
    @Bind(R.id.rb_select_new)
    RadioButton rbSelectNew;
    @Bind(R.id.rg_select)
    RadioGroup rgSelect;
    @Bind(R.id.tv_drawer_price)
    TextView tvDrawerPrice;
    @Bind(R.id.rl_select_price_title)
    RelativeLayout rlSelectPriceTitle;
    @Bind(R.id.tv_drawer_recommend)
    TextView tvDrawerRecommend;
    @Bind(R.id.rl_select_recommend_theme)
    RelativeLayout rlSelectRecommendTheme;
    @Bind(R.id.tv_drawer_type)
    TextView tvDrawerType;
    @Bind(R.id.rl_select_type)
    RelativeLayout rlSelectType;
    @Bind(R.id.btn_select_all)
    Button btnSelectAll;
    @Bind(R.id.ll_select_root)
    LinearLayout llSelectRoot;
    @Bind(R.id.btn_drawer_price_cancel)
    Button btnDrawerLayoutCancel;
    @Bind(R.id.btn_drawer_price_confirm)
    Button btnDrawerLayoutConfirm;
    @Bind(R.id.iv_price_no_limit)
    ImageView ivPriceNoLimit;
    @Bind(R.id.rl_price_nolimit)
    RelativeLayout rlPriceNolimit;
    @Bind(R.id.iv_price_0_15)
    ImageView ivPrice015;
    @Bind(R.id.rl_price_0_15)
    RelativeLayout rlPrice015;
    @Bind(R.id.iv_price_15_30)
    ImageView ivPrice1530;
    @Bind(R.id.rl_price_15_30)
    RelativeLayout rlPrice1530;
    @Bind(R.id.iv_price_30_50)
    ImageView ivPrice3050;
    @Bind(R.id.rl_price_30_50)
    RelativeLayout rlPrice3050;
    @Bind(R.id.iv_price_50_70)
    ImageView ivPrice5070;
    @Bind(R.id.rl_price_50_70)
    RelativeLayout rlPrice5070;
    @Bind(R.id.iv_price_70_100)
    ImageView ivPrice70100;
    @Bind(R.id.rl_price_70_100)
    RelativeLayout rlPrice70100;
    @Bind(R.id.iv_price_100)
    ImageView ivPrice100;
    @Bind(R.id.rl_price_100)
    RelativeLayout rlPrice100;
    @Bind(R.id.et_price_start)
    EditText etPriceStart;
    @Bind(R.id.v_price_line)
    View vPriceLine;
    @Bind(R.id.et_price_end)
    EditText etPriceEnd;
    @Bind(R.id.rl_select_price)
    RelativeLayout rlSelectPrice;
    @Bind(R.id.ll_price_root)
    LinearLayout llPriceRoot;
    @Bind(R.id.btn_drawer_theme_cancel)
    Button btnDrawerThemeCancel;
    @Bind(R.id.btn_drawer_theme_confirm)
    Button btnDrawerThemeConfirm;
    @Bind(R.id.iv_theme_all)
    ImageView ivThemeAll;
    @Bind(R.id.rl_theme_all)
    RelativeLayout rlThemeAll;
    @Bind(R.id.iv_theme_note)
    ImageView ivThemeNote;
    @Bind(R.id.rl_theme_note)
    RelativeLayout rlThemeNote;
    @Bind(R.id.iv_theme_funko)
    ImageView ivThemeFunko;
    @Bind(R.id.rl_theme_funko)
    RelativeLayout rlThemeFunko;
    @Bind(R.id.iv_theme_gsc)
    ImageView ivThemeGsc;
    @Bind(R.id.rl_theme_gsc)
    RelativeLayout rlThemeGsc;
    @Bind(R.id.iv_theme_origin)
    ImageView ivThemeOrigin;
    @Bind(R.id.rl_theme_origin)
    RelativeLayout rlThemeOrigin;
    @Bind(R.id.iv_theme_sword)
    ImageView ivThemeSword;
    @Bind(R.id.rl_theme_sword)
    RelativeLayout rlThemeSword;
    @Bind(R.id.iv_theme_food)
    ImageView ivThemeFood;
    @Bind(R.id.rl_theme_food)
    RelativeLayout rlThemeFood;
    @Bind(R.id.iv_theme_moon)
    ImageView ivThemeMoon;
    @Bind(R.id.rl_theme_moon)
    RelativeLayout rlThemeMoon;
    @Bind(R.id.iv_theme_quanzhi)
    ImageView ivThemeQuanzhi;
    @Bind(R.id.rl_theme_quanzhi)
    RelativeLayout rlThemeQuanzhi;
    @Bind(R.id.iv_theme_gress)
    ImageView ivThemeGress;
    @Bind(R.id.rl_theme_gress)
    RelativeLayout rlThemeGress;
    @Bind(R.id.ll_theme_root)
    LinearLayout llThemeRoot;
    @Bind(R.id.btn_drawer_type_cancel)
    Button btnDrawerTypeCancel;
    @Bind(R.id.btn_drawer_type_confirm)
    Button btnDrawerTypeConfirm;
    @Bind(R.id.expandableListView)
    ExpandableListView expandableListView;
    @Bind(R.id.ll_type_root)
    LinearLayout llTypeRoot;
    @Bind(R.id.dl_left)
    DrawerLayout dlLeft;

    private String[] urls = new String[]{
            Constants.CLOSE_STORE,  // 服饰
            Constants.GAME_STORE,   // 游戏
            Constants.COMIC_STORE,  // 动漫
            Constants.COSPLAY_STORE,// 装扮
            Constants.GUFENG_STORE, // 古风
            Constants.STICK_STORE,  // 漫展票务
            Constants.WENJU_STORE,  // 文具
            Constants.FOOD_STORE,   // 零食
            Constants.SHOUSHI_STORE,// 首饰
    };
    private int position;
    private List<GoodsBean> goodsBeanList;
    private GoodsListAdapter goodsListAdapter;
    private Boolean isPriceSort;
    private ArrayList<String> group;
    private ArrayList<List<String>> child;
    private ExpandableListViewAdapter adapter;

    private int childP;
    private int groupP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_goods_list);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        position = intent.getIntExtra("position", -1);
        getDataforNet();

        llSelectRoot.setVisibility(View.VISIBLE);
        ibDrawerLayoutBack.setVisibility(View.VISIBLE);
        showSelectorLayout();
        initListener();
    }

    /**
     * 显示抽屉 筛选界面(抽屉默认显示筛选界面)
     */
    private void showSelectorLayout() {
        llTypeRoot.setVisibility(View.GONE);
        llPriceRoot.setVisibility(View.GONE);
        llThemeRoot.setVisibility(View.GONE);
    }

    /**
     * 显示抽屉 价格界面
     */
    private void showPriceLayout() {
        llSelectRoot.setVisibility(View.GONE);
        llTypeRoot.setVisibility(View.GONE);
        llThemeRoot.setVisibility(View.GONE);
    }

    /**
     * 显示抽屉 推荐主题界面
     */
    private void showThemeLayout() {
        llSelectRoot.setVisibility(View.GONE);
        llPriceRoot.setVisibility(View.GONE);
        llTypeRoot.setVisibility(View.GONE);
    }

    /**
     * 显示抽屉 分类界面
     */
    private void showTypeLayout() {
        llSelectRoot.setVisibility(View.GONE);
        llPriceRoot.setVisibility(View.GONE);
        llThemeRoot.setVisibility(View.GONE);

        //初始化ExpandableListView
        initExpandableListView();
        adapter = new ExpandableListViewAdapter(this, group, child);
        expandableListView.setAdapter(adapter);
    }

    private void initListener() {
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                childP = childPosition;
                groupP = groupPosition;
                Toast.makeText(ChannelGoodsListActivity.this, "childPosition==" + childPosition+"---groupPosition==" + groupPosition+"\n "+group.get(groupP)+"---"+child.get(groupP).get(childP), Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
                return false;
            }
        });
    }

    private void initExpandableListView() {
        group = new ArrayList<>();
        child = new ArrayList<>();
        //去掉默认箭头
        expandableListView.setGroupIndicator(null);
        addInfo("全部", new String[]{});
        addInfo("上衣", new String[]{"古风", "和风", "lolita", "日常"});
        addInfo("下装", new String[]{"日常", "泳衣", "汉风", "lolita", "创意T恤"});
        addInfo("外套", new String[]{"汉风", "古风", "lolita", "胖次", "南瓜裤", "日常"});
        // 这里是控制如果列表没有孩子菜单不展开的效果
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (child.get(groupPosition).isEmpty()) {
                    return true;
                } else {
                    return false;

                }
            }
        });
    }

    /**
     * 添加数据信息
     *
     * @param g
     * @param c
     */
    private void addInfo(String g, String[] c) {
        group.add(g);
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < c.length; i++) {
            list.add(c[i]);
        }
        child.add(list);
    }

    private void getDataforNet() {
        OkHttpUtils
                .get()
                .url(urls[position])
                .id(100)
                .build()
                .execute(new MyStringCallback());
    }

    @OnClick({R.id.ib_goods_list_back, R.id.ib_goods_list_home, R.id.ll_goods_list_price, R.id.tv_goods_list_select, R.id.rl_select_price, R.id.tv_goods_list_sort, R.id.tv_goods_list_search, R.id.ib_drawer_layout_back, R.id.btn_drawer_price_confirm, R.id.btn_drawer_price_cancel, R.id.btn_drawer_type_cancel, R.id.btn_drawer_type_confirm, R.id.tv_drawer_layout_confirm, R.id.rb_select_hot, R.id.rb_select_new, R.id.btn_select_all, R.id.btn_drawer_theme_cancel, R.id.btn_drawer_theme_confirm, R.id.rl_select_recommend_theme, R.id.rl_select_type
    })
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_goods_list_back:       //商品界面--返回箭头
                finish();
                break;
            case R.id.tv_goods_list_search:     //商品界面--搜索
                Toast.makeText(this, "搜索", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ib_goods_list_home:       //商品界面--回到首页
                finish();
                break;
            case R.id.tv_goods_list_sort:       //商品界面--综合排序
                isPriceSort = false;
                ivGoodsListArrow.setBackgroundResource(R.drawable.new_price_sort_normal);
                tvGoodsListPrice.setTextColor(Color.parseColor("#333538"));
                tvGoodsListSort.setTextColor(Color.parseColor("#ed4141"));
                break;
            case R.id.ll_goods_list_price:      //商品界面--价格
                //综合排序变为默认
                tvGoodsListSort.setTextColor(Color.parseColor("#333538"));
                //价格字体颜色变成红色
                tvGoodsListPrice.setTextColor(Color.parseColor("#ed4141"));
                if (isPriceSort) {
                    isPriceSort = false;
                    ivGoodsListArrow.setBackgroundResource(R.drawable.new_price_sort_asc);
                } else {
                    isPriceSort = true;
                    ivGoodsListArrow.setBackgroundResource(R.drawable.new_price_sort_desc);
                }
                break;
            case R.id.tv_goods_list_select:      //商品界面--筛选
                dlLeft.openDrawer(Gravity.RIGHT);
                break;

            case R.id.ib_drawer_layout_back:     //筛选界面--返回箭头
                dlLeft.closeDrawers();
                break;
            case R.id.tv_drawer_layout_confirm:  //筛选界面--确定
                Toast.makeText(this, "确定", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rb_select_hot:             //筛选界面--热卖
                Toast.makeText(this, "热卖", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rb_select_new:             //筛选界面--新品
                Toast.makeText(this, "新品", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rl_select_price:           //筛选界面--价格条目
                llPriceRoot.setVisibility(View.VISIBLE);
                ibDrawerLayoutBack.setVisibility(View.GONE);
                showPriceLayout();
                break;
            case R.id.rl_select_recommend_theme: //筛选界面--推荐主题条目
                llThemeRoot.setVisibility(View.VISIBLE);
                ibDrawerLayoutBack.setVisibility(View.GONE);
                showThemeLayout();
                break;
            case R.id.rl_select_type:            //筛选界面--分类条目
                llTypeRoot.setVisibility(View.VISIBLE);
                ibDrawerLayoutBack.setVisibility(View.GONE);
                showTypeLayout();
                break;
            case R.id.btn_select_all:            //筛选界面--重置
                Toast.makeText(this, "重置", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_drawer_price_cancel:   //价格界面--取消
                Toast.makeText(this, "取消", Toast.LENGTH_SHORT).show();
                llSelectRoot.setVisibility(View.VISIBLE);
                ibDrawerLayoutBack.setVisibility(View.VISIBLE);
                showSelectorLayout();
                break;
            case R.id.btn_drawer_price_confirm:  //价格界面--确定
                Toast.makeText(this, "确定", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_drawer_theme_cancel:   //推荐主题界面--取消
                Toast.makeText(this, "取消", Toast.LENGTH_SHORT).show();
                llSelectRoot.setVisibility(View.VISIBLE);
                ibDrawerLayoutBack.setVisibility(View.VISIBLE);
                showSelectorLayout();
                break;
            case R.id.btn_drawer_theme_confirm:  //推荐主题界面--确定
                Toast.makeText(this, "确定", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_drawer_type_cancel:   //分类界面--取消
                Toast.makeText(this, "取消", Toast.LENGTH_SHORT).show();
                llSelectRoot.setVisibility(View.VISIBLE);
                ibDrawerLayoutBack.setVisibility(View.VISIBLE);
                showSelectorLayout();
                break;
            case R.id.btn_drawer_type_confirm:  //分类界面--确定
                Toast.makeText(this, "确定", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    public class MyStringCallback extends StringCallback {

        @Override
        public void onBefore(Request request, int id) {
            super.onBefore(request, id);
        }

        @Override
        public void onAfter(int id) {
            super.onAfter(id);
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            Log.e("TAG", "联网失败" + e.getMessage());
        }

        @Override
        public void onResponse(String response, int id) {
            switch (id) {
                case 100:
                    if (response != null) {
                        processData(response);
                        // 设置样式
                        GridLayoutManager manager = new GridLayoutManager(ChannelGoodsListActivity.this, 2);
                        recyclerview.setLayoutManager(manager);
                        // 设置显示原始价格
                        Constants.isShowOriginPrice = true;
                        // 设置适配器
                        goodsListAdapter = new GoodsListAdapter(ChannelGoodsListActivity.this, goodsBeanList);
                        recyclerview.setAdapter(goodsListAdapter);
                        // 添加分割线
                        recyclerview.addItemDecoration(new SpaceItemDecoration(10));
                        // 设置item的点击事件
                        goodsListAdapter.setOnGoodsListClickListener(new GoodsListAdapter.OnGoodsListClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                GoodsBean goodsBean = goodsBeanList.get(position);
                                Intent intent = new Intent(ChannelGoodsListActivity.this, GoodsInfoActivity.class);
                                intent.putExtra(Constants.GOODS_BEAN, goodsBean);
                                startActivity(intent);
                            }
                        });
                    }
                    break;
                case 101:
                    Toast.makeText(ChannelGoodsListActivity.this, "https", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    private void processData(String response) {
        // Gson解析数据
        Gson gson = new Gson();
        ChannerListBean channerListBean = gson.fromJson(response, ChannerListBean.class);
        // 将pageDataBeanList转化为goodsBeanList
        List<ChannerListBean.ResultBean.PageDataBean> pageDataBeanList = channerListBean.getResult().getPage_data();
        goodsBeanList = new ArrayList<GoodsBean>();
        if (pageDataBeanList != null && pageDataBeanList.size() > 0) {
            for (int i = 0; i < pageDataBeanList.size(); i++) {
                ChannerListBean.ResultBean.PageDataBean pageDataBean = pageDataBeanList.get(i);
                GoodsBean goodsBean = new GoodsBean();
                goodsBean.setFigure(pageDataBean.getFigure());
                goodsBean.setCover_price(pageDataBean.getCover_price());
                goodsBean.setOrigin_price(pageDataBean.getOrigin_price());
                goodsBean.setName(pageDataBean.getName());
                goodsBean.setProduct_id(pageDataBean.getProduct_id());
                goodsBeanList.add(goodsBean);
            }
        }
    }
}
