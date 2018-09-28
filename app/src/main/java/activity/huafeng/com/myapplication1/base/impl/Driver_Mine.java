package activity.huafeng.com.myapplication1.base.impl;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
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
import activity.huafeng.com.myapplication1.bean.UpdateBean;
import activity.huafeng.com.myapplication1.util.ToastUtils;
import activity.huafeng.com.myapplication1.util.UpdateService;
import activity.huafeng.com.myapplication1.util.UrlUtil;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

/**
 * Created by leovo on 2018-08-24.
 */

public class Driver_Mine extends BasePager {

    TextView name,tel,carnum;
    TextView update,quit;

    private LoginBean_Driver.Data driver;

    private View thisRootView;
    private boolean isInited = false;

    private String appVersion;// 当前app的版本名
    private int currentVersionCode;// 当前app的版本号
    private String serverDownloadPath;// 服务器下载的app的路径

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

        update=thisRootView .findViewById(R.id.my_driver_update) ;
        update .setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 直接请求网络, 判断是否是最新版本
                ToastUtils.getInstance(mActivity).showToast("正在检查更新");
                //获得当前的版本号
                try {
                    currentVersionCode = mActivity.getPackageManager().getPackageInfo(mActivity.getPackageName(),0).versionCode;//版本号
                    appVersion = mActivity.getPackageManager().getPackageInfo(mActivity.getPackageName(),0).versionName;//版本名

                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

                requestNet_GetVersion();

            }
        } ) ;


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


    /**
     * 请求后台获取最新版本号
     */
    private void requestNet_GetVersion(){
        OkGo.<String>get(UrlUtil.URL_Prefix+"Account/GetVersion")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        UpdateBean bean = gson.fromJson(response.body(), UpdateBean.class);
                        if(bean.getCode()==1){
                            String serverVersion = bean.getData().getVersion();
                            serverDownloadPath = bean.getData().getUrl();
                            String updateContent = bean.getData().getDescribe();


                            // 获取到 版本信息后, 进行判断
                            if(Double.parseDouble( String.valueOf( currentVersionCode ) ) == Double.parseDouble(serverVersion)){
                                ToastUtils.getInstance(mActivity).showToast("已经是最新版本");

                            }else if(Double.parseDouble( String.valueOf( currentVersionCode ) ) < Double.parseDouble(serverVersion)){
                                //弹出提示框, 提示是否后台更新下载
                                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(mActivity);
                                builder.setPositiveButton("后台下载更新", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //在这里请求读写权限, 如果没拿到, 就没必要执行下面的操作, 直接跳登录页面即可
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            requestPower();
                                        }else{
                                            toStartService();// 去开启服务下载app的方法.
                                        }
                                    }
                                });
                                builder.setNegativeButton("不更新", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();// 取消显示dialog
                                        SystemClock.sleep(500);
                                    }
                                });
                                builder.setMessage(updateContent);
                                builder.setTitle("检测到新版本: " + serverVersion);
                                builder.setCancelable(false);
                                builder.show();
                            }


                        }else{// 返回来的code 0.
                            ToastUtils.getInstance(mActivity).showToast(bean.getMsg());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastUtils.getInstance(mActivity).showToast("检查更新失败");
                    }
                });
    }

    /**
     * 检查权限的方法
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestPower(){
        //判断是否已经赋予权限
        if (mActivity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PERMISSION_GRANTED) {// 未授权则进入if中.
            //如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。
            if (mActivity.shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                //这里可以写个对话框之类的项向用户解释为什么要申请权限，并在对话框的确认键后续再次申请权限
                ToastUtils.getInstance(mActivity).showToast("不授予读写sd卡权限,无法升级应用,请重新授予权限.");
                mActivity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            } else {
                //申请权限，字符串数组内是一个或多个要申请的权限，1是申请权限结果的返回参数，在onRequestPermissionsResult可以得知申请结果
                mActivity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }else{
            toStartService();
        }
    }

    public void  toStartService(){
        Intent updateIntent = new Intent(mActivity,UpdateService.class);
        updateIntent.putExtra("appurl", serverDownloadPath);
        mActivity.startService(updateIntent);
    }



}
