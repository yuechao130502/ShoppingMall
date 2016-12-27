package com.wyuyc.shoppingmall.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wyuyc.shoppingmall.R;
import com.wyuyc.shoppingmall.app.GoodsInfoActivity;
import com.wyuyc.shoppingmall.home.bean.GoodsBean;
import com.wyuyc.shoppingmall.home.bean.ResultBeanData;
import com.wyuyc.shoppingmall.utils.Contants;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.loader.ImageLoaderInterface;
import com.zhy.magicviewpager.transformer.AlphaPageTransformer;
import com.zhy.magicviewpager.transformer.ScaleInTransformer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by yc on 2016/12/22.
 */

public class HomeFragmentAdapter extends RecyclerView.Adapter {

    private static final String TAG = HomeFragmentAdapter.class.getSimpleName();

    /**
     *  适配器的五种类型
     */
    /**
     * 横幅广告
     */
    public static final int BANNER = 0;
    /**
     * 频道
     */
    public static final int CHANNEL = 1;
    /**
     * 活动
     */
    public static final int ACT = 2;
    /**
     * 秒杀
     */
    public static final int SECKILL = 3;
    /**
     * 推荐
     */
    public static final int RECOMMEND = 4;
    /**
     * 热卖
     */
    public static final int HOT = 5;
    private static final String GOODS_BEAN = "goodsBean";
    private TextView tvTime;
    private Context mContext;
    /**
     * 数据
     */
    private ResultBeanData.ResultBean resultBean;

    /**
     * 当前类型
     */
    private int currentType = BANNER;
    /**
     * 用来初始化布局
     */
    private final LayoutInflater mLayoutInflater;

    /**
     * 秒杀结束还有多少毫秒
     */
    private long dt = 0;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            dt -= 1000;
            String time = format.format(new Date(dt));

            tvTime.setText(time);
            removeCallbacksAndMessages(0);
            sendEmptyMessageDelayed(0, 1000);
        }
    };

    public HomeFragmentAdapter(Context mContext, ResultBeanData.ResultBean resultBean) {
        this.mContext = mContext;
        this.resultBean = resultBean;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    /**
     * @param parent
     * @param viewType 当前的类型
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == BANNER) {
            return new BannerViewHolder(mContext, mLayoutInflater.inflate(R.layout.banner_viewpager, null));
        } else if (viewType == CHANNEL) {
            return new ChannelViewHolder(mContext, mLayoutInflater.inflate(R.layout.channel_item, null));
        } else if (viewType == ACT) {
            return new ACTViewHolder(mContext, mLayoutInflater.inflate(R.layout.act_item, null));
        } else if (viewType == SECKILL) {
            return new SeckillViewHolder(mContext, mLayoutInflater.inflate(R.layout.seckill_item, null));
        } else if (viewType == RECOMMEND) {
            return new RecommendViewHolder(mContext, mLayoutInflater.inflate(R.layout.recommend_item, null));
        } else if (viewType == HOT) {
            return new HotViewHolder(mContext, mLayoutInflater.inflate(R.layout.hot_item, null));
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case BANNER:
                currentType = BANNER;
                break;
            case CHANNEL:
                currentType = CHANNEL;
                break;
            case ACT:
                currentType = ACT;
                break;
            case SECKILL:
                currentType = SECKILL;
                break;
            case RECOMMEND:
                currentType = RECOMMEND;
                break;
            case HOT:
                currentType = HOT;
                break;
        }
        return currentType;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == BANNER) {
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            bannerViewHolder.setData(resultBean.getBanner_info());
        } else if (getItemViewType(position) == CHANNEL) {
            ChannelViewHolder channelViewHolder = (ChannelViewHolder) holder;
            channelViewHolder.setData(resultBean.getChannel_info());
        } else if (getItemViewType(position) == ACT) {
            ACTViewHolder actViewHolder = (ACTViewHolder) holder;
            actViewHolder.setData(resultBean.getAct_info());
        } else if (getItemViewType(position) == SECKILL) {
            SeckillViewHolder seckillViewHolder = (SeckillViewHolder) holder;
            seckillViewHolder.setData(resultBean.getSeckill_info());
        } else if (getItemViewType(position) == RECOMMEND) {
            RecommendViewHolder recommendViewHolder = (RecommendViewHolder) holder;
            recommendViewHolder.setData(resultBean.getRecommend_info());
        } else if (getItemViewType(position) == HOT) {
            HotViewHolder hotViewHolder = (HotViewHolder) holder;
            hotViewHolder.setData(resultBean.getHot_info());
        }
    }

    class ChannelViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private GridView gv_channel;

        public ChannelViewHolder(Context context, View itemView) {
            super(itemView);
            this.mContext = context;
            this.gv_channel = (GridView) itemView.findViewById(R.id.gv_channel);
        }

        public void setData(List<ResultBeanData.ResultBean.ChannelInfoBean> channel_info) {
            gv_channel.setAdapter(new ChannelGridViewAdapter(mContext, channel_info));
            gv_channel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(mContext, "position=" + position, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    class BannerViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private Banner banner;

        public BannerViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            this.banner = (Banner) itemView.findViewById(R.id.banner);
        }

        public void setData(final List<ResultBeanData.ResultBean.BannerInfoBean> banner_info) {
            //得到图片地址集合 imagesUrl
            List<String> imagesUrl = new ArrayList<>();
            for (int i = 0; i < banner_info.size(); i++) {
                String imageUrl = banner_info.get(i).getImage();
                imagesUrl.add(imageUrl);
            }
            //Banner 1.4.5版本使用
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR) //设置banner样式:显示圆形指示器
                    .setIndicatorGravity(BannerConfig.CENTER)    //设置指示器位置（当banner模式中有指示器时）
                    .setBannerAnimation(Transformer.Accordion)   //设置banner动画效果:手风琴
                    .setDelayTime(2000) //设置轮播时间 默认2000
                    .setImages(imagesUrl) //设置图片集合
                    .setImageLoader(new ImageLoaderInterface() { //设置图片加载器
                        @Override
                        public void displayImage(Context context, Object path, View imageView) {
                            Glide.with(context).load(Contants.Base_URl_IMAGE + path).into((ImageView) imageView);
                        }

                        @Override
                        public View createImageView(Context context) {
                            ImageView imageView = new ImageView(context);
                            return imageView;
                        }
                    })
                    .setOnBannerClickListener(new OnBannerClickListener() { //设置banner条目点击事件
                        @Override
                        public void OnBannerClick(int position) {
                            //横幅广告类
                            ResultBeanData.ResultBean.BannerInfoBean bannerInfoBean = banner_info.get(position);
                            GoodsBean goodsBean = new GoodsBean();
                            goodsBean.setFigure(bannerInfoBean.getImage());
                            startGoodsInfoActivity(goodsBean);
                            Toast.makeText(mContext, "position = " + position, Toast.LENGTH_SHORT).show();
                        }
                    })
                    .start(); //banner设置方法全部调用完毕时最后调用

           /*  Banner 1.3.3版本使用
            //设置循环指示点
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            //设置手风琴效果
            banner.setBannerAnimation(Transformer.Accordion);
            //加载数据
            banner.setImages(imagesUrl, new OnLoadImageListener() {
                @Override
                public void OnLoadImage(ImageView view, Object url) {
                    //联网请求图片-Glide
                    Glide.with(mContext).load(Contants.Base_URl_IMAGE + url).into(view);
                }
            });
            //设置点击事件
            banner.setOnBannerClickListener(new OnBannerClickListener() {
                @Override
                public void OnBannerClick(int position) {
                    Toast.makeText(mContext, "position = " + position, Toast.LENGTH_SHORT).show();
                }
            });*/
        }
    }

    private void startGoodsInfoActivity(GoodsBean goodsBean) {
        Intent intent = new Intent(mContext, GoodsInfoActivity.class);
        intent.putExtra(GOODS_BEAN,goodsBean);
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    class ACTViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private ViewPager act_viewpager;

        public ACTViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            this.act_viewpager = (ViewPager) itemView.findViewById(R.id.act_viewpager);
        }

        public void setData(List<ResultBeanData.ResultBean.ActInfoBean> act_info) {
            act_viewpager.setPageMargin(20);
            act_viewpager.setOffscreenPageLimit(3);
            act_viewpager.setPageTransformer(true, new AlphaPageTransformer(new ScaleInTransformer()));
            //绑定数据
            act_viewpager.setAdapter(new ActViewPagerAdapter(mContext, act_info));
            act_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    Toast.makeText(mContext, "position:" + position, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
        }
    }

    class SeckillViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private TextView tvMore;
        private RecyclerView rv_seckill;
        private SeckillRecyclerViewAdapter adapter;

        public SeckillViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            tvMore = (TextView) itemView.findViewById(R.id.tv_more_seckill);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time_seckill);
            rv_seckill = (RecyclerView) itemView.findViewById(R.id.rv_seckill);
        }

        public void setData(final ResultBeanData.ResultBean.SeckillInfoBean seckill_info) {
            final List<ResultBeanData.ResultBean.SeckillInfoBean.ListBean> list = seckill_info.getList();
            adapter = new SeckillRecyclerViewAdapter(mContext,list);
            rv_seckill.setAdapter(adapter);
            //设置布局管理器
            rv_seckill.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            //设置item的点击事件
            adapter.setOnSeckillRecyclerView(new SeckillRecyclerViewAdapter.OnSeckillRecyclerView() {
                @Override
                public void onItemClick(int position) {
                    //秒杀商品信息类
                    ResultBeanData.ResultBean.SeckillInfoBean.ListBean seckillInfoBean = list.get(position);
                    //商品信息类
                    GoodsBean goodsBean = new GoodsBean();
                    goodsBean.setCover_price(seckillInfoBean.getCover_price());
                    goodsBean.setFigure(seckillInfoBean.getFigure());
                    goodsBean.setName(seckillInfoBean.getName());
                    goodsBean.setProduct_id(seckillInfoBean.getProduct_id());
                    startGoodsInfoActivity(goodsBean);
                }
            });
            //计算秒杀倒计时
            getCountDown();
            handler.sendEmptyMessageDelayed(0, 1000);
        }

        private void getCountDown() {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            calendar.set(Calendar.HOUR_OF_DAY, -8);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            //当天剩余时间毫秒数=第二天0点毫秒数-当前时间毫秒数
            dt = calendar.getTimeInMillis() - System.currentTimeMillis();
        }

    }

    class RecommendViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private GridView gv_recommend;
        private TextView tv_more_recommend;

        public RecommendViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            gv_recommend = (GridView) itemView.findViewById(R.id.gv_recommend);
            tv_more_recommend = (TextView) itemView.findViewById(R.id.tv_more_recommend);
        }

        public void setData(final List<ResultBeanData.ResultBean.RecommendInfoBean> recommend_info) {
            gv_recommend.setAdapter(new RecommendGridViewAdapter(mContext, recommend_info));
            gv_recommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //推荐商品信息类
                    ResultBeanData.ResultBean.RecommendInfoBean recommendInfoBean = recommend_info.get(position);
                    //商品信息类
                    GoodsBean goodsBean = new GoodsBean();
                    goodsBean.setCover_price(recommendInfoBean.getCover_price());
                    goodsBean.setFigure(recommendInfoBean.getFigure());
                    goodsBean.setName(recommendInfoBean.getName());
                    goodsBean.setProduct_id(recommendInfoBean.getProduct_id());
                    startGoodsInfoActivity(goodsBean);
                }
            });
        }
    }

    private class HotViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private GridView gv_hot;
        private TextView tv_more_hot;

        public HotViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            gv_hot = (GridView) itemView.findViewById(R.id.gv_hot);
            tv_more_hot = (TextView) itemView.findViewById(R.id.tv_more_hot);
        }

        public void setData(final List<ResultBeanData.ResultBean.HotInfoBean> hot_info) {
            gv_hot.setAdapter(new HotGridViewAdapter(mContext, hot_info));
            gv_hot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //热卖商品信息类
                    ResultBeanData.ResultBean.HotInfoBean hotInfoBean = hot_info.get(position);
                    //商品信息类
                    GoodsBean goodsBean = new GoodsBean();
                    goodsBean.setCover_price(hotInfoBean.getCover_price());
                    goodsBean.setFigure(hotInfoBean.getFigure());
                    goodsBean.setName(hotInfoBean.getName());
                    goodsBean.setProduct_id(hotInfoBean.getProduct_id());
                    startGoodsInfoActivity(goodsBean);
                }
            });
            tv_more_hot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "查看更多", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
