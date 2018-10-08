package activity.huafeng.com.myapplication1.activity;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.norbsoft.typefacehelper.TypefaceHelper;

import activity.huafeng.com.myapplication1.R;
import activity.huafeng.com.myapplication1.util.ToastUtils;
import activity.huafeng.com.myapplication1.util.ToolUtil;

import com.norbsoft.typefacehelper.TypefaceHelper;

import static com.norbsoft.typefacehelper.TypefaceHelper.typeface;


public abstract class BaseActivity extends AppCompatActivity {
    /** 是否沉浸状态栏 **/
    private boolean isSetStatusBar = true;
    /** 是否禁止旋转屏幕 **/
    private boolean isAllowScreenRoate = true;

    public static boolean isDebug = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        MyApplication application = (MyApplication)getApplication();

        //typeface字体设置
        typeface(this);

        // 设置是否沉浸状态栏
        if (isSetStatusBar) {
            steepStatusBar();
        }

        // 设置是否旋转屏幕
        if (!isAllowScreenRoate) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        // 当使用Bundle传参的时候, 使用initParam()方法进行详细处理.
        if (savedInstanceState != null) {
            initParam(savedInstanceState);
        } else if (getIntent() != null && getIntent().getExtras() != null) {
            initParam(getIntent().getExtras());
        }

        //出入场动画
        //overridePendingTransition(R.anim.activity_alpha_in,R.anim.activity_alpha_out);

    }

    @Override
    public void finish() {
        super.finish();
        //出入场动画
        //overridePendingTransition(R.anim.activity_alpha_in,R.anim.activity_alpha_out);

    }

    /**
        简单的打印日志的工具方法
     */
    public void log_d(String activityName, String msg) {
        if (isDebug) {
            Log.d(activityName, msg);
        }
    }

    /**
       封装findviewbyid,使用起来不需要强转,更方便一些
     */
    protected <T extends View> T findView(int id) {
        return (T) findViewById(id);
    }
    /**
        在setContentView方法内重写initView()和initData()方法, 子类在重写setContentView的时候, 必须重写这两个方法.
     */
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        initView();
        initData();
    }

    /**
     * 初始化页面空间的方法.
     */
    public abstract void initView();

    /**
     * 页面需要的数据, 在这里进行获取.
     */
    public abstract void initData();

    /**
        如果activity之间有传参的话,直接调用此方法进行 处理即可.
     */
    protected void initParam(Bundle bundle) {

    }

    /**
        吐司工具类
     */
    public void toast(String text) {
        ToastUtils.getInstance(this).showToast(text);
    }

    /**
        吐司工具类
     */
    public void toast(int resId) {
        ToastUtils.getInstance(this).showToast(String.valueOf(resId));
    }

    /**
     * 点击软键盘之外的空白处，隐藏软件盘
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (ToolUtil.isShouldHideInput(v, ev)) {
                hideSoftInput();// 隐藏软键盘
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    /**
     * 隐藏软件盘
     */
    public void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * 显示软键盘
     */
    public void showInputMethod(){
        if (getCurrentFocus() != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.showSoftInputFromInputMethod(getCurrentFocus().getWindowToken(),0);
        }
    }

    /**
     * [沉浸状态栏]
     */
    private void steepStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
//            getWindow().addFlags(
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(getResources().getColor(R.color.main_button_checked));//设置标题栏颜色.
            }
            // 透明导航栏
//            getWindow().addFlags(
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 子类设置是否沉浸状态栏,默认为true.
     * @param isSetStatusBar
     */
    public void setSteepStatusBar(boolean isSetStatusBar) {
        this.isSetStatusBar = isSetStatusBar;
    }

    /**
     * 子类设置是否允许屏幕旋转,默认为false.
     * @param isAllowScreenRoate
     */
    public void setScreenRoate(boolean isAllowScreenRoate) {
        this.isAllowScreenRoate = isAllowScreenRoate;
    }

    /**
     * 防止快速点击
     * @return
     */
    long lastClickTime = 0;
    private boolean isfastClick() {

        if (System.currentTimeMillis() - lastClickTime <= 300) {
            ToastUtils.getInstance(BaseActivity.this).showToast("点击过快,休息一下");
            return false;
        }
        lastClickTime = System.currentTimeMillis();
        return true;
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1)//非默认值
            getResources();
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }



}
