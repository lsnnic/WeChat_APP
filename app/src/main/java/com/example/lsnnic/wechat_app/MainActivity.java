package com.example.lsnnic.wechat_app;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.lsnnic.wechat_app.fragment.TabFragment;
import com.example.lsnnic.wechat_app.utils.L;

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

    private SparseArray<TabFragment> mFragments = new SparseArray<>();

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
        setContentView(R.layout.activity_main);
        L.d("activity onCreate");
        initView();

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

                if(i==0){
                    fragment.setOnTitleClickListener(new TabFragment.OnTitleClickListener() {
                        @Override
                        public void onClick(String title) {
                            changeWeChatTab(title);
                        }
                    });
                }

                return fragment;
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
        mVpMain = findViewById(R.id.vp_main);

        mBtnWeChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TabFragment tabFragment = mFragments.get(0);
                if(tabFragment != null){
                    tabFragment.changeTitle("微信 Changed！");
                }
            }
        });

    }

    public void changeWeChatTab(String title){
        mBtnWeChat.setText(title);
    }

}
