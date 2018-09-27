package activity.huafeng.com.myapplication1.base;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import activity.huafeng.com.myapplication1.activity.BaseActivity;

public abstract class BasePager {

    public BaseActivity mActivity;
    //子页面的根布局
    public View rootView;

    public BasePager(BaseActivity activity) {
        mActivity = activity;
//        rootView = initView();
    }
    // 初始化布局的方法, 子类必须重写
    public abstract void initView();

    // 初始化数据的方法, 由子类继承.
    public void initData(){

    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//       super.onActivityResult(requestCode, resultCode, data);


    }


}
