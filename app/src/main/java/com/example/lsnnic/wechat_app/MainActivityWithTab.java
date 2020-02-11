package com.example.lsnnic.wechat_app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.lsnnic.wechat_app.fragment.TabFragment;
import com.example.lsnnic.wechat_app.utils.L;
import com.example.lsnnic.wechat_app.view.TabView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivityWithTab extends AppCompatActivity {

    private ViewPager mVpMain;

    private List<String> mTitles = new ArrayList<>(Arrays.asList("微信","通讯录","发现","我"));

    private TabView mBtnWeChat;
    private TabView mBtnFriend;
    private TabView mBtnFind;
    private TabView mBtnMine;

    private SparseArray<TabFragment> mFragments = new SparseArray<>();

    private List<TabView> mTabs = new ArrayList<>();

    private static final String BUNDLE_KEY_POS = "bundle_key_pos";

    private int mCurTabPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /**
         * 屏幕旋转之后，onCreate会重新执行，但getItem方法不会重新执行，如果使用一次性new四个Fragment的方法，
         * 并通过 fragmentName.method()调用方法，很可能会发现你的操作旋转后的界面不会响应，因为那时候显示的仍然是
         * 旧的Fragment，而你操作的是新的new出来的Fragment，他们并不是对应的，所以你的操作无效。
         *
         * 为什么呢？
         *
         * 这是因为当屏幕发生旋转时，相关的FragmentManager和PagerAdapter会将各Fragment保存下来然后旋转之后
         * 恢复回去。
         */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);
//        L.d("activity onCreate");
        if(savedInstanceState!=null){
            mCurTabPos = savedInstanceState.getInt(BUNDLE_KEY_POS,0);
        }
        initView();
        initViewPager();
        initEvents();
    }

    private void initView(){
        mVpMain = findViewById(R.id.vp_main);
        mBtnWeChat = (TabView)findViewById(R.id.tab_wechat);
        mBtnFind = (TabView)findViewById(R.id.tab_find);
        mBtnFriend = (TabView)findViewById(R.id.tab_friend);
        mBtnMine = (TabView)findViewById(R.id.tab_mine);

        mBtnWeChat.setIconAndText(R.drawable.wechat,R.drawable.wechat_select,"微信");
        mBtnFriend.setIconAndText(R.drawable.friend,R.drawable.friend_select,"通讯录");
        mBtnFind.setIconAndText(R.drawable.find,R.drawable.find_select,"发现");
        mBtnMine.setIconAndText(R.drawable.mine,R.drawable.mine_select,"我");

        mTabs.add(mBtnWeChat);
        mTabs.add(mBtnFriend);
        mTabs.add(mBtnFind);
        mTabs.add(mBtnMine);

        setCurrentTab(mCurTabPos);

    }

    private void setCurrentTab(int pos){
        for(int i=0;i<mTabs.size();i++){
            TabView tabView = mTabs.get(i);
            if(i==pos){
                tabView.setProgress(1);
            }
            else{
                tabView.setProgress(0);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(BUNDLE_KEY_POS,mVpMain.getCurrentItem());
        super.onSaveInstanceState(outState);
    }

    private void initEvents(){
        for (int i=0;i<mTabs.size();i++){
            TabView tabView = mTabs.get(i);
            final int finalI = i;
            tabView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mVpMain.setCurrentItem(finalI,false);
                    setCurrentTab(finalI);
                }
            });
        }
    }

    private void initViewPager(){
        mVpMain.setOffscreenPageLimit(mTitles.size());
        /**
         * ViewPager默认缓存当前页面，当前页面左，当前页面右，共三个页面。
         * FragmentPagerAdapter：适用于很少的Fragment的情况，如微信的四个主Tab
         * FragmentStatePagerAdapter：适用于非常多Fragment，如图片浏览器。为避免内存被吃满，需要销毁
         * 前面已经浏览过的图片的Fragment
         */
        mVpMain.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                TabFragment fragment = (TabFragment)super.instantiateItem(container,position);
                mFragments.put(position,fragment);
                return fragment;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                mFragments.remove(position);
                super.destroyItem(container, position, object);
            }

            @Override
            public Fragment getItem(int i) {
//                L.d("Fragment getItem i = "+i);
                TabFragment fragment = TabFragment.newInstance(mTitles.get(i));

                return fragment;
            }

            @Override
            public int getCount() {
                return mTitles.size();
            }
        });

        mVpMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                // position positionOffset positionOffsetPixels
                L.d("onPageScrolled pos = "+ i + ", positionOffset = "+v);
                // 左-》右 0->1 left position right pos + 1， offset 0->1
                if(v>0){
                    TabView left =  mTabs.get(i);
                    TabView right = mTabs.get(i+1);
                    left.setProgress(1-v);
                    right.setProgress(v);
                }

            }

            @Override
            public void onPageSelected(int i) {
                L.d("onPageSelected pos = "+i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

}
