package activity.huafeng.com.myapplication1.fragment.manager_search;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import activity.huafeng.com.myapplication1.activity.BaseActivity;

/**
 * Created by xian on 2018/8/14.
 * */

@SuppressLint("ValidFragment")
public abstract class BaseFragment_No_InitData extends Fragment {
    protected BaseActivity mActivity;
    private View view;

    @SuppressLint("ValidFragment")
    public BaseFragment_No_InitData(BaseActivity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = initView();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return view;
    }
    /**
     * 初始化布局的方法
     */
    protected abstract View initView();
    /**
     * 初始化数据的方法, 子类可以不实现
     */
    protected void initData(){

    }
}
