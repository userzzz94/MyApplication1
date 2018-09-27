package activity.huafeng.com.myapplication1.base.impl;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.orhanobut.hawk.Hawk;

import activity.huafeng.com.myapplication1.R;
import activity.huafeng.com.myapplication1.activity.BaseActivity;
import activity.huafeng.com.myapplication1.activity.LoginActivity;
import activity.huafeng.com.myapplication1.base.BasePager;
import activity.huafeng.com.myapplication1.bean.Driver_QuitBean;
import activity.huafeng.com.myapplication1.bean.LoginBean_Driver;
import activity.huafeng.com.myapplication1.util.ToastUtils;
import activity.huafeng.com.myapplication1.util.UrlUtil;

/**
 * Created by leovo on 2018-08-24.
 */

public class Driver_Mine extends BasePager {

    TextView name,tel,carnum;
    TextView quit;

    private LoginBean_Driver.Data driver;

    private View thisRootView;
    private boolean isInited = false;

    public Driver_Mine(BaseActivity activity) {
        super(activity);
    }

    public void initView(){

        if(isInited){
            rootView = thisRootView;
        }else {

            thisRootView = View.inflate(mActivity , R.layout .pager_driver_mine,null);

        name =thisRootView .findViewById(R.id.my_driver_name) ;
        tel=thisRootView .findViewById(R.id.my_driver_tel) ;
        //carnum=view.findViewById(R.id.my_driver_carnum);

        driver = Hawk.get("r_driver");
        name.setText(driver.getTrueName());//司机姓名
        tel.setText(driver.getUserName());//电话
        //carnum.setText( driver.getCarnum() );//车牌号


        //退出登录
        quit=thisRootView .findViewById(R.id.my_driver_quit) ;
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request_order();

            }
        }) ;



        rootView = thisRootView;
        isInited = true;
    }

    }

    public void initData(){

    }

    //退出登录检查司机状态，有任务不能退出
    private void request_order(){

        String d_token=Hawk .get("t_driver");
        
        if("".equals(driver .getId()) ){
            Log.d("quit","null") ;
        }else {
            Log.d( "quit", driver .getId() );
        }
        OkGo.<String>get( UrlUtil.URL_Prefix + "Task/GetTc" )

                .params("Token", d_token)
                .params("Id",driver.getId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        Gson gson = new Gson();
                        Driver_QuitBean bean = gson.fromJson(response.body(), Driver_QuitBean.class);
                        String code = bean.getType();

                        if("1".equals( code )){

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
                            builder.setTitle("退出登录");
                            builder.show();

                        //    ToastUtils.getInstance(mActivity).showToast(bean.getMessage());

                        } else {

                            ToastUtils.getInstance(mActivity).showToast(bean.getMessage());

                        }


                    }

                    public void onError(Response<String> response) {

                        super.onError(response);
                        
                        ToastUtils.getInstance(mActivity).showToast("请求失败");
                    }
                });

    }


}
