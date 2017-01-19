package com.wyuyc.shoppingmall.user.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.wyuyc.shoppingmall.R;
import com.wyuyc.shoppingmall.base.BaseFragment;
import com.wyuyc.shoppingmall.user.activity.MessageCenterActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yc on 2016/12/21.
 * 用户中心的Fragment
 */

public class UserFragment extends BaseFragment {
    private static final String TAG = UserFragment.class.getSimpleName();
    @Bind(R.id.ib_user_icon_avator)
    ImageButton ibUserIconAvator;
    @Bind(R.id.tv_username)
    TextView tvUsername;
    @Bind(R.id.rl_all_order)
    RelativeLayout rlAllOrder;
    @Bind(R.id.tv_user_pay)
    TextView tvUserPay;
    @Bind(R.id.tv_user_receive)
    TextView tvUserReceive;
    @Bind(R.id.tv_user_finish)
    TextView tvUserFinish;
    @Bind(R.id.tv_user_drawback)
    TextView tvUserDrawback;
    @Bind(R.id.tv_user_location)
    TextView tvUserLocation;
    @Bind(R.id.tv_user_collect)
    TextView tvUserCollect;
    @Bind(R.id.tv_user_coupon)
    TextView tvUserCoupon;
    @Bind(R.id.tv_user_score)
    TextView tvUserScore;
    @Bind(R.id.tv_user_prize)
    TextView tvUserPrize;
    @Bind(R.id.tv_user_ticket)
    TextView tvUserTicket;
    @Bind(R.id.tv_user_invitation)
    TextView tvUserInvitation;
    @Bind(R.id.tv_user_callcenter)
    TextView tvUserCallcenter;
    @Bind(R.id.tv_user_feedback)
    TextView tvUserFeedback;
    @Bind(R.id.scrollview)
    ScrollView scrollview;
    @Bind(R.id.tv_usercenter)
    TextView tvUsercenter;
    @Bind(R.id.ib_user_setting)
    ImageButton ibUserSetting;
    @Bind(R.id.ib_user_message)
    ImageButton ibUserMessage;
    @Bind(R.id.tv_user_consignment)
    TextView tvUserConsignment;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_user, null);
        ButterKnife.bind(this, view);
        tvUsercenter.setAlpha(0);
        Log.e(TAG, "initView" + "UserFragment初始化");
        return view;
    }

    @Override
    public void initData() {
        scrollview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int[] location = new int[2];
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE://下滑是正，上滑是负
                        ibUserIconAvator.getLocationOnScreen(location);//初始状态为125,即最大值是125，全部显示不透明是40
                        float i = (location[1] - 40) / 85f;
                        tvUsercenter.setAlpha(1 - i);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.ib_user_icon_avator, R.id.tv_username, R.id.rl_all_order, R.id.tv_user_pay, R.id.tv_user_consignment, R.id.tv_user_receive, R.id.tv_user_finish, R.id.tv_user_drawback, R.id.tv_user_location, R.id.tv_user_collect, R.id.tv_user_coupon, R.id.tv_user_score, R.id.tv_user_prize, R.id.tv_user_ticket, R.id.tv_user_invitation, R.id.tv_user_callcenter, R.id.tv_user_feedback, R.id.ib_user_setting, R.id.ib_user_message})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_user_setting:       //设置中心
                Toast.makeText(mContext, "设置", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ib_user_message:       //消息中心
                Intent intent = new Intent(mContext, MessageCenterActivity.class);
                startActivity(intent);
                break;
            case R.id.ib_user_icon_avator:  // 登录注册图标
                Toast.makeText(mContext, "登录/注册", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_username:          // 登录注册文字
                Toast.makeText(mContext, "登录/注册", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rl_all_order:         // 查看全部订单
                Toast.makeText(mContext, "查看全部订单", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_user_pay:          // 待付款
                Toast.makeText(mContext, "待付款", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_user_consignment:  // 待发货
                Toast.makeText(mContext, "待发货", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_user_receive:      // 待收货
                Toast.makeText(mContext, "待发货", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_user_finish:       // 已完成订单
                Toast.makeText(mContext, "已完成订单", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_user_drawback:     // 售后/退款
                Toast.makeText(mContext, "售后/退款", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_user_location:     // 收货地址
                Toast.makeText(mContext, "收货地址", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_user_collect:      // 我的收藏
                Toast.makeText(mContext, "我的收藏", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_user_coupon:       // 我的优惠券
                Toast.makeText(mContext, "我的优惠券", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_user_score:        // 我的瓜子
                Toast.makeText(mContext, "我的瓜子", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_user_prize:        // 我的奖品
                Toast.makeText(mContext, "我的奖品", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_user_ticket:       // 我的电子票
                Toast.makeText(mContext, "我的电子票", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_user_invitation:   // 邀请分享
                Toast.makeText(mContext, "邀请分享", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_user_callcenter:   // 客服中心
                Toast.makeText(mContext, "客服中心", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_user_feedback:     // 服务反馈
                Toast.makeText(mContext, "服务反馈", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
