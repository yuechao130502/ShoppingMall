package com.wyuyc.shoppingmall.utils;

/**
 * Created by yc on 2016/12/21.
 * 配置联网地址
 */

public class Constants {
    //是否显示原始价格
    public static Boolean isShowOriginPrice = false;

    //是否返回首页
    public static Boolean isBackHome = false;

    public static final String GOODS_BEAN = "goodsBean";

    //banner条目的列表标题
    public static final String GOODSLIST_TITLE = "goodslist_title";

    public static final String BASE = "http://192.168.43.42:8080";
//    public static final String BASE = "http://www.lhcx821.com";

    //  请求 Json 数据基本 URL
    public static final String BASE_URL_JSON = BASE+"/wuyishoppingmall/json/";
    //  请求图片基本 URL
    public static final String BASE_URl_IMAGE = BASE+"/wuyishoppingmall/img";

    // 主页路径
    public static final String HOME_URL = BASE_URL_JSON + "HOME_URL.json";

    //----------banner item里面的数据
    //海尔组图
    public static final String Haier_URl_IMAGE = BASE_URl_IMAGE +"/haier";
    //百草味组图
    public static final String Baicao_URl_IMAGE = BASE_URl_IMAGE +"/baicaowei";
    //361°组图
    public static final String Sanliuyi_URl_IMAGE = BASE_URl_IMAGE +"/sanliuyi";



    // 小裙子
    public static final String SKIRT_URL = BASE_URL_JSON + "SKIRT_URL.json";
    // 上衣
    public static final String JACKET_URL = BASE_URL_JSON + "JACKET_URL.json";
    // 下装(裤子)
    public static final String PANTS_URL = BASE_URL_JSON + "PANTS_URL.json";
    // 外套
    public static final String OVERCOAT_URL = BASE_URL_JSON + "PANTS_URL.json/OVERCOAT_URL.json";
    // 配件
    public static final String ACCESSORY_URL = BASE_URL_JSON + "PANTS_URL.json/ACCESSORY_URL.json";
    // 包包
    public static final String BAG_URL = BASE_URL_JSON + "BAG_URL.json";
    // 装扮
    public static final String DRESS_UP_URL = BASE_URL_JSON + "DRESS_UP_URL.json";
    // 居家宅品
    public static final String HOME_PRODUCTS_URL = BASE_URL_JSON + "HOME_PRODUCTS_URL.json";
    // 办公文具
    public static final String STATIONERY_URL = BASE_URL_JSON + "STATIONERY_URL.json";
    // 数码周边
    public static final String DIGIT_URL = BASE_URL_JSON + "DIGIT_URL.json";
    // 游戏专区
    public static final String GAME_URL = BASE_URL_JSON + "GAME_URL.json";

    //分类Fragment里面的标签Fragment页面数据
    public static final String TAG_URL = BASE_URL_JSON + "TAG_URL.json";

    public static final String NEW_POST_URL = BASE_URL_JSON + "NEW_POST_URL.json";
    public static final String HOT_POST_URL = BASE_URL_JSON + "HOT_POST_URL.json";

    // 页面的具体数据的 id
    public static final String GOODSINFO_URL = BASE_URL_JSON + "GOODSINFO_URL.json";
    // 客服数据
// public static final String CALL_CENTER = BASE_URL_JSON + "CALL_CENTER.json";
    public static final String CALL_CENTER = "http://www6.53kf.com/webCompany.php?arg=10007377&style=1&kflist=off&kf=info@atguigu.com," + "video@atguigu.com,public@atguigu.com,3069368606@qq.com,215648937@qq.com,sudan@atguigu.com," + "sszhang@atguigu.com&zdkf_type=1&language=zh-cn&charset=gbk&referer=http%3A%2F%2Fwww.atguigu.com%2Fcontant.shtml&keyword=&tfrom=1&tpl=crystal_blue&timeStamp=1479001706368&ucust_id=";

    //-----------channer item里面的数据
    // 服饰
    public static final String CLOSE_STORE = BASE_URL_JSON + "CLOSE_STORE.json";
    // 游戏
    public static final String GAME_STORE = BASE_URL_JSON + "GAME_STORE.json";
    // 动漫
    public static final String COMIC_STORE = BASE_URL_JSON + "COMIC_STORE.json";
    // 装扮
    public static final String COSPLAY_STORE = BASE_URL_JSON + "COSPLAY_STORE.json";
    // 古风
    public static final String GUFENG_STORE = BASE_URL_JSON + "GUFENG_STORE.json";
    // 漫展票务
    public static final String STICK_STORE = BASE_URL_JSON + "STICK_STORE.json";
    // 文具
    public static final String WENJU_STORE = BASE_URL_JSON + "WENJU_STORE.json";
    // 零食
    public static final String FOOD_STORE = BASE_URL_JSON + "FOOD_STORE.json";
    // 首饰
    public static final String SHOUSHI_STORE = BASE_URL_JSON + "SHOUSHI_STORE.json";

}
