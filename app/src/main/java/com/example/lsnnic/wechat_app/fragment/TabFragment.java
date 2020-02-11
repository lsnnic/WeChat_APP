package com.example.lsnnic.wechat_app.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lsnnic.wechat_app.R;
import com.example.lsnnic.wechat_app.utils.L;

public class TabFragment extends Fragment {

    private static final String BUNDLE_KEY_TITLE = "key_title";

    private TextView mTvTitle;
    private String mTitle;

    public static interface OnTitleClickListener{
        void onClick(String title);
    }

    private OnTitleClickListener mListener;

    public void setOnTitleClickListener(OnTitleClickListener listener){
        mListener = listener;
    }

    public static TabFragment newInstance(String title){
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_KEY_TITLE,title);
        TabFragment tabFragment = new TabFragment();
        tabFragment.setArguments(bundle);
        return tabFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if(arguments!=null){
            mTitle = arguments.getString(BUNDLE_KEY_TITLE,"");
        }
//        L.d("onCreate, title = "+mTitle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        L.d("onCreateView, title = "+mTitle);
        return inflater.inflate(R.layout.fragment_tab,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTvTitle = view.findViewById(R.id.tv_title);
        mTvTitle.setText(mTitle);
        mTvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * 获取 Activity 对象
                 * 写法1：
                 * MainActivity activity = (MainActivity) getActivity();
                 * activity.changeWeChatTab("微信 Changed");
                 * 写法2：
                 * Activity activity = getActivity();
                 * if(activity instanceof MainActivity){
                 *     ((MainActivity) activity).changeWeChatTab("微信 Changed!")
                 * }
                 *
                 * 问题在于：我们Fragment会触发一些事件，Activity去相应这些事件。
                 * 就是说，Fragment只负责触发，如何执行具体功能是Activity的事。
                 *
                 */
                if(mListener!=null){
                    mListener.onClick("微信Changed！");
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        L.d("onDestroy, title = "+mTitle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        L.d("onDestroyView, title = "+mTitle);
    }

    public void changeTitle(String title){
        // 注意这里，如果一个Fragment还未初始化onCreate onCreateView时就调用一定会报错，所以在任何暴露给外界的方法中
        // 一定要做好判断
        if(!isAdded()){
            return;
        }
        mTvTitle.setText(title);
    }

}
