package activity.huafeng.com.myapplication1.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import activity.huafeng.com.myapplication1.activity.BaseActivity;

/**
 * Created by xian on 2018/7/26.
 */

public abstract class BaseFragment extends Fragment {
    public BaseActivity mActivity;// fragment依附的activity,当做context去使用
    public View rootView;// fragment的根布局

    //fragment创建
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (BaseActivity) getActivity();//获取fragment所依赖的activity的对象
    }

    /**
     * 初始化fragment的布局
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = initView();
        return rootView;
    }

    /**
     * 当activity被创建的时候, 初始化数据
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    protected abstract View initView();

    /**
     * 初始化数据, 子类可以不实现
     */
    protected  void initData(){

    }

}
