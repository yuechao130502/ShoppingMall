package com.wyuyc.shoppingmall.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.wyuyc.shoppingmall.R;
import com.wyuyc.shoppingmall.base.BaseFragment;
import com.wyuyc.shoppingmall.community.fragment.CommunityFragment;
import com.wyuyc.shoppingmall.home.fragment.HomeFragment;
import com.wyuyc.shoppingmall.shoppingcart.fragment.ShoppingCartFragment;
import com.wyuyc.shoppingmall.type.fragment.TypeFragment;
import com.wyuyc.shoppingmall.user.fragment.UserFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity {

    @Bind(R.id.frameLayout)
    FrameLayout frameLayout;
    @Bind(R.id.rg_main)
    RadioGroup rgMain;

    private static final String TAG = MainActivity.class.getSimpleName();

    /**
     * Fragment实例集合
     */
    private List<BaseFragment> fragments;

    private int position = 0;

    /**
     * 上次显示的Fragment
     */
    private Fragment tempFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initFragment();
        initListener();
    }

    private void initListener() {
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        position = 0;
                        break;
                    case R.id.rb_type:
                        position = 1;
                        break;
                    case R.id.rb_community:
                        position = 2;
                        break;
                    case R.id.rb_cart:
                        position = 3;
                        break;
                    case R.id.rb_user:
                        position = 4;
                        break;
                    default:
                        position = 0;
                        break;
                }
                BaseFragment baseFragment = getFrament(position);
                switchFragment(tempFragment, baseFragment);
            }
        });
        rgMain.check(R.id.rb_home);
    }

    /**
     * ShoppingCartFragment的tv_empty_cart_tobuy点击时
     * 调用该方法回到首页
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void toHome(MessageEvent event){
        rgMain.check(R.id.rb_home);
        switchFragment(tempFragment,fragments.get(0));
    }

    /**
     * @param fromFragment 上次显示的Fragment
     * @param nextFragment 当前正要显示的Fragment
     */
    public void switchFragment(Fragment fromFragment, BaseFragment nextFragment) {
        if (tempFragment != nextFragment) {
            Log.e(TAG, "switchFragment---tempFragment" + tempFragment);
            tempFragment = nextFragment;
            if (nextFragment != null) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                //判断nextFragment是否被添加
                if (!nextFragment.isAdded()) {  //nextFragment没有添加
                    //隐藏当前Fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }
                    //添加Fragment
                    transaction.add(R.id.frameLayout,nextFragment).commit();
                } else {  //nextFragment已经添加
                    //隐藏当前Fragment
                    if (fromFragment !=null) {
                        transaction.hide(fromFragment);
                    }
                    //显示Fragment
                    transaction.show(nextFragment).commit();
                }
            }
        }
    }

    private BaseFragment getFrament(int position) {
        if (fragments != null && fragments.size() > 0) {
            BaseFragment baseFragment = fragments.get(position);
            return baseFragment;
        }
        return null;
    }

    /**
     * 初始化Fragment
     */
    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new TypeFragment());
        fragments.add(new CommunityFragment());
        fragments.add(new ShoppingCartFragment());
        fragments.add(new UserFragment());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
