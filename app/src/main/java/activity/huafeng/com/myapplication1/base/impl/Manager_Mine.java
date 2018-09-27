package activity.huafeng.com.myapplication1.base.impl;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;

import activity.huafeng.com.myapplication1.R;
import activity.huafeng.com.myapplication1.activity.BaseActivity;
import activity.huafeng.com.myapplication1.activity.LoginActivity;
import activity.huafeng.com.myapplication1.base.BasePager;
import activity.huafeng.com.myapplication1.bean.LoginBean_Manager;

/**
 * Created by leovo on 2018-08-24.
 */

public class Manager_Mine extends BasePager {

    TextView name,tel;
    TextView quit;
    private LoginBean_Manager.DataManager manager;

    private View thisRootView;
    private boolean isInited = false;

    public Manager_Mine(BaseActivity activity) {
        super(activity);
    }

    public void initView(){

        if(isInited){
            rootView = thisRootView;
        }else {
            thisRootView = View.inflate(mActivity , R.layout .pager_manager_mine,null);

        name =thisRootView .findViewById(R.id.my_name) ;
        tel=thisRootView .findViewById(R.id.my_tel) ;

        manager = Hawk.get("r_manager");
        name.setText(manager.getTrueName());//姓名
        tel.setText(manager.getUserName());//用户名

        //退出登录
        quit=thisRootView .findViewById(R.id.my_quit) ;
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setClass(mActivity, LoginActivity.class);
                        mActivity.startActivity(intent);
                        mActivity.finish();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();// 取消显示dialog
                    }
                });
                builder.setMessage("确认要退出登录吗");
                builder.setTitle("退出");
                builder.show();
            }
        }) ;


            rootView = thisRootView;
            isInited = true;
        }
    }

    public void initData(){

    }
}
