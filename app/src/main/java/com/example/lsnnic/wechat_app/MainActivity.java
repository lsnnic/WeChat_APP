package com.example.lsnnic.wechat_app;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.example.lsnnic.wechat_app.fragment.TabFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager mVpMain;

    private List<String> mTitles = new ArrayList<>(Arrays.asList("微信","通讯录","发现","我"));

    private Button mBtnWeChat;
    private Button mBtnFriend;
    private Button mBtnFind;
    private Button mBtnMine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        mVpMain = findViewById(R.id.vp_main);

        mVpMain.setOffscreenPageLimit(mTitles.size());
        /**
         * ViewPager默认缓存当前页面，当前页面左，当前页面右，共三个页面。
         * FragmentPagerAdapter：适用于很少的Fragment的情况，如微信的四个主Tab
         * FragmentStatePagerAdapter：适用于非常多Fragment，如图片浏览器。为避免内存被吃满，需要销毁
         * 前面已经浏览过的图片的Fragment
         */
        mVpMain.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return TabFragment.newInstance(mTitles.get(i));
            }

            @Override
            public int getCount() {
                return mTitles.size();
            }
        });
    }

    private void initView(){
        mBtnWeChat = (Button)findViewById(R.id.btn_wechat);
        mBtnFind = (Button)findViewById(R.id.btn_find);
        mBtnFriend = (Button)findViewById(R.id.btn_friend);
        mBtnMine = (Button)findViewById(R.id.btn_mine);
    }

}
