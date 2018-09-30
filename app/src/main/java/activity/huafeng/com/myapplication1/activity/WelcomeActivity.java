package activity.huafeng.com.myapplication1.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import activity.huafeng.com.myapplication1.R;
import activity.huafeng.com.myapplication1.bean.UpdateBean;
import activity.huafeng.com.myapplication1.util.ToastUtils;
import activity.huafeng.com.myapplication1.util.UpdateService;
import activity.huafeng.com.myapplication1.util.UrlUtil;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

/**
 * 
 *  功能描述：欢迎界面
 */

public class WelcomeActivity extends BaseActivity {

    private static final String TAG = "WelcomeActivity";

    //是否是第一次使用
    private boolean isFirstLogin;

    private String currentVersionCode;// 当前app的版本号
    private long startTime;
    private long endTime;
    private String serverDownloadPath;
    TextView version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_welcome);
        version =findViewById(R.id.version) ;


        // 检查当前版本号, 如果需要,进行版本更新
        try {
            //记录当前的时间
            startTime = SystemClock.currentThreadTimeMillis();
            //获得当前的版本号
            currentVersionCode = getPackageManager().getPackageInfo(getPackageName(),0).versionName;
            version .setText(currentVersionCode) ;
            
            requestNet_GetVersion();

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


    }


    /**
     * 检查权限的方法
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestPower(){
        //判断是否已经赋予权限
        if (checkSelfPermission( Manifest.permission.WRITE_EXTERNAL_STORAGE) != PERMISSION_GRANTED) {// 未授权则进入if中.
            //如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。
            if (shouldShowRequestPermissionRationale( Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                //这里可以写个对话框之类的项向用户解释为什么要申请权限，并在对话框的确认键后续再次申请权限
                ToastUtils.getInstance(this).showToast("不授予读写sd卡权限,无法升级应用,请重新授予权限.");
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 22);
            } else {
                //申请权限，字符串数组内是一个或多个要申请的权限，1是申请权限结果的返回参数，在onRequestPermissionsResult可以得知申请结果
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 22);
            }
        }else{
            Intent updateIntent = new Intent(WelcomeActivity.this,UpdateService.class);
            updateIntent.putExtra("appurl", serverDownloadPath);
            startService(updateIntent);

            //下载的同时, 也进入登录界面
            toLoginActivity();
        }
    }

    /**
     * 权限授予后的结果回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PERMISSION_GRANTED) {
                    Intent updateIntent = new Intent(WelcomeActivity.this,UpdateService.class);
                    updateIntent.putExtra("appurl",serverDownloadPath);
                    startService(updateIntent);

                    //下载的同时, 也进入登录界面
                    toLoginActivity();

                } else {
                    ToastUtils.getInstance(this).showToast("未授予权限,无法下载更新");
                    toLoginActivity();
                }
            }
        }
    }

    /**
     * 去登录界面的方法
     */
    private void toLoginActivity(){
        Intent intent = new Intent(WelcomeActivity .this,LoginActivity .class );
        startActivity(intent);
        finish();
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
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
                        if(bean.getType()==1){
                            String serverVersion = bean.getData().getVersion();
                            serverDownloadPath = bean.getData().getUrl();
                            String updateContent = bean.getData().getDescribe();


                            // 获取到 版本信息后, 进行判断
                            if(Double.parseDouble( String.valueOf( currentVersionCode ) ) == Double.parseDouble(serverVersion)){
                                endTime = SystemClock.currentThreadTimeMillis();
                                SystemClock.sleep(1000-(endTime-startTime));
                                toLoginActivity();

                            }else if(Double.parseDouble( String.valueOf( currentVersionCode ) ) < Double.parseDouble(serverVersion)){
                                //弹出提示框, 提示是否后台更新下载
                                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(WelcomeActivity.this);
                                builder.setPositiveButton("后台下载更新", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //在这里请求读写权限, 如果没拿到, 就没必要执行下面的操作, 直接跳登录页面即可
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            requestPower();
                                        }else{
                                            Intent updateIntent = new Intent(WelcomeActivity.this,UpdateService.class);
                                            updateIntent.putExtra("appurl", serverDownloadPath);
                                            startService(updateIntent);
                                        }
                                    }
                                });
                                builder.setNegativeButton("不更新", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();// 取消显示dialog
                                        SystemClock.sleep(500);
                                        toLoginActivity();
                                    }
                                });
                                builder.setMessage(updateContent);
                                builder.setTitle("检测到新版本: " + serverVersion);
                                builder.setCancelable(false);
                                builder.show();
                            }


                        }else{// 返回来的code 不是成功的情况下, 直接去登录页.
                            ToastUtils.getInstance(WelcomeActivity.this).showToast(bean.getMessage());
                            SystemClock.sleep(1000);
                            toLoginActivity();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastUtils.getInstance(WelcomeActivity.this).showToast("检查更新失败");
                        toLoginActivity();
                    }
                });
    }
}
