package activity.huafeng.com.myapplication1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.orhanobut.hawk.Hawk;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.List;

import activity.huafeng.com.myapplication1.R;
import activity.huafeng.com.myapplication1.bean.LoginBean_Driver;
import activity.huafeng.com.myapplication1.bean.LoginBean_Manager;
import activity.huafeng.com.myapplication1.bean.LoginBean_Security;
import activity.huafeng.com.myapplication1.util.ToastUtils;
import activity.huafeng.com.myapplication1.util.UrlUtil;
import activity.huafeng.com.myapplication1.view.MyEditText;

public class LoginActivity extends BaseActivity {

    //记录双击退出应用的第一次点击时间.
    private long firstTime = 0;
    private static final String TAG = "LoginActivity";

    NiceSpinner loginType;// 找到这个下拉选择框
    private String chooseLoginType = "司机"; // S司机,C车队,A安保
    private String history_chooseLoginType;

    private Button loginButton;

    private MyEditText  account;
    private String account_input;
    private String password_input;
    private EditText password;
    ImageView imageView;//小眼睛

    private boolean isHidePwd = true;// 输入框密码是否是隐藏的，默认为true
    
    private TextView registdriver, registmanager;

    private CloudPushService mPushService = PushServiceFactory.getCloudPushService();
    TextView consoleText;

    private RelativeLayout parent;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        

    }

    public void initData() {

        List<String> dataList = new ArrayList<>();
        dataList.add("司机");
        dataList.add("车队");
        dataList.add("安保");
        loginType.attachDataSource(dataList);

        //还原上次登录类型的状态.
        if (history_chooseLoginType != null) {
            if ("司机".equals(history_chooseLoginType)) {
                loginType.setSelectedIndex(0);
            } else if ("车队".equals(history_chooseLoginType)) {
                loginType.setSelectedIndex(1);
            } else if ("安保".equals(history_chooseLoginType)) {
                loginType.setSelectedIndex(2);
            }
        }

    }

    public void initView() {

        Hawk.init(this ).build();

        parent = findViewById(R.id.parent);
        account = (MyEditText) findViewById(R.id.activity_login_account);// 账号输入框
        password = (EditText) findView(R.id.activity_login_password);//密码输入框
        imageView=(ImageView) findViewById(R.id.activity_login_img_eye ) ;
        imageView.setImageResource(R.drawable.icon_eye_close);

        imageView .setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isHidePwd == true) {

                    imageView.setImageResource(R.drawable.icon_eye_open);
                    //明文
                    HideReturnsTransformationMethod method1 = HideReturnsTransformationMethod.getInstance();
                    password.setTransformationMethod(method1);
                    isHidePwd = false;
                } else {
                    imageView.setImageResource(R.drawable.icon_eye_close);
                    //密文
                    TransformationMethod method = PasswordTransformationMethod.getInstance();
                    password.setTransformationMethod(method);
                    isHidePwd = true;

                }
                // 光标的位置
                int index = password.getText().toString().length();
                password.setSelection(index);


            }
        } );



        loginType = (NiceSpinner) findViewById(R.id.activity_login_logintype);// 选择登录角色的下拉选择框.
        loginType.setBackgroundColor(getResources().getColor(R.color.white));// 不设置背景色, 会有5.0样式的形状,被按压下去的效果.

        loginButton = (Button) findViewById(R.id.activity_login_loginbutton);// 登录按钮

        registdriver = findView(R.id.loginActivity_textview_registdriver);// 注册司机
        registmanager = findView(R.id.loginActivity_textview_registmanager);// 注册车队

        restoreStatus();// 还原上次登录信息
        setViewOnclickListener();//绑定点击事件

    }


    //    还原上次的登录信息
    private void restoreStatus() {
        //拿到sp对象,得到上次登录保存过的手机号\密码 和 登录类型
        String history_account = Hawk.get("tel", null);
        String history_password = Hawk.get("password", null);
        if (history_password != null) {
            password.setText(history_password);
        }
        if (history_account != null) {
            account.setText(history_account);// 将取到的值赋值给 账户输入变量中
        }

        //还原登录类型( 用户 ? 司机 )
        history_chooseLoginType = Hawk.get("type", null);// 这个initData()方法中被还原
    }


    /**
     * 将按钮的点击事件统一在这里进行绑定
     */
    private void setViewOnclickListener() {

        //找到注册司机按钮并设置跳转点击事件
        registdriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, RegistDriverActivity.class);
                startActivity(intent);
            }
        });

        //找到注册货主按钮并设置跳转点击事件
        registmanager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_registmaster = new Intent(LoginActivity.this, RegistManagerActivity.class);

                startActivity(intent_registmaster);
            }
        });

        //找到登录 按钮, 设置点击事件
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //收集登录的信息并进行非空判断
                if (handleLoginInfo()) {

                    showPopupWindow();
                    requestNet_login();

                }
            }
        });


    }


    private void showPopupWindow() {
        View view1 = View.inflate(LoginActivity.this, R.layout.popupwindow_login,null);

        popupWindow = new PopupWindow();
        popupWindow.setContentView(view1);
        popupWindow.setWidth( ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

        popupWindow.setFocusable(false);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(false);// 点击popupwindow外边能够取消显示popupwindow

        popupWindow.showAtLocation(parent, Gravity.CENTER,0,0);
    }


    private void requestNet_login() {
        OkGo.<String>get(UrlUtil.URL_Prefix + "Account/Login")
                .params("userName",account_input)
                .params("passWord",password_input)
                .params("Name",chooseLoginType)
                .execute(new StringCallback() {
                     @Override
                     public void onSuccess(Response<String> response) {
                         Log.d("yyyyyy","onSuccess");
                          Intent intent_login = new Intent();
                          if ("司机".equals(chooseLoginType)) {// 司机登录
                              intent_login.setClass(LoginActivity.this, MainActivity_Driver.class);
                              Gson gson = new Gson();
                              LoginBean_Driver driver = gson.fromJson(response.body(), LoginBean_Driver.class);

                              if("1".equals(driver.getType())) {// 登陆成功
                                  //把之前的登录信息先清空一次
                                  Hawk.delete("tel");
                                  Hawk.delete("password");
                                  Hawk.delete("type");
                                  Hawk.delete("r_driver");
                                  Hawk.delete("r_manager");
                                  Hawk.delete("r_security");

                                  // 存储下次登录需要还原的数据信息
                                  Hawk.put("password", password_input);
                                  Hawk.put("tel", account_input);
                                  Hawk.put("type", chooseLoginType);

                                  //存储登录成功返回来的信息
                                  Hawk.put("r_driver", driver.getData());
                                  Hawk .put("t_driver",driver .getToken()) ;

                                  //mPushService = PushServiceFactory.getCloudPushService();
                                  mPushService.bindAccount(driver.getData().getId(), new CommonCallback() {
                                      @Override
                                      public void onSuccess(String s) {


                                      }

                                      @Override
                                      public void onFailed(String s, String s1) {

                                      }
                                  });

                                  startActivity(intent_login);
                                  finish();
                              }else{
                                  ToastUtils.getInstance(LoginActivity.this).showToast(driver.getMessage());
                                  popupWindow.dismiss();
                              }
                     } else if ("车队".equals(chooseLoginType)) {// 车队登录
                              intent_login.setClass(LoginActivity.this, MainActivity_Manager.class);

                              Gson gson = new Gson();
                              LoginBean_Manager manager = gson.fromJson(response.body(), LoginBean_Manager.class);

                              if("1".equals(manager.getType())) {// 登陆成功

                                  //把之前的登录信息先清空一次
                                  Hawk.delete("tel");
                                  Hawk.delete("password");
                                  Hawk.delete("type");
                                  Hawk.delete("r_driver");
                                  Hawk.delete("r_manager");
                                  Hawk.delete("r_security");

                                  // 存储下次登录需要还原的数据信息
                                  Hawk.put("password", password_input);
                                  Hawk.put("tel", account_input);
                                  Hawk.put("type", chooseLoginType);

                                  //存储登录成功返回来的信息
                                  Log .e("www",manager.getData().getId());
                                  
                                  Hawk.put("r_manager", manager.getData());
                                  Hawk .put("t_manager",manager .getToken()) ;

                                  Log.d("yyyyyyy",manager .getToken());
                                  //mPushService = PushServiceFactory.getCloudPushService();
                                  String mId=manager.getData().getId();
                                  mPushService.bindAccount(mId, new CommonCallback() {
                                      @Override
                                      public void onSuccess(String s) {
                                      //    ToastUtils.getInstance(LoginActivity.this).showToast(  "阿里推送绑定成功 " + s);


                                      }

                                      @Override
                                      public void onFailed(String s, String s1) {
                                          Log .e("onFailed","\"onFailed\"");

                                          //ToastUtils.getInstance(LoginActivity.this).showToast(  "阿里推送绑定失败 " + s+  " "+s1 );

                                      }
                                  });
                                  Log.e(TAG, "devicedId1=======" + mPushService.getDeviceId());
                                  Log.e(TAG, "devicedId2=======" + mId);

                                  startActivity(intent_login);
                                  finish();
                              }else{
                                  ToastUtils.getInstance(LoginActivity.this).showToast(manager .getMessage());
                                  popupWindow.dismiss();
                              }
                     } else if ("安保".equals(chooseLoginType)) {// 安保登录
                              intent_login.setClass(LoginActivity.this, MainActivity_Security.class);

                              Gson gson = new Gson();
                              LoginBean_Security security = gson.fromJson(response.body(), LoginBean_Security.class);

                              if("1".equals(security.getType())) {// 登陆成功
                                  //把之前的登录信息先清空一次
                                  Hawk.delete("tel");
                                  Hawk.delete("password");
                                  Hawk.delete("type");
                                  Hawk.delete("r_driver");
                                  Hawk.delete("r_manager");
                                  Hawk.delete("r_security");

                                  // 存储下次登录需要还原的数据信息
                                  Hawk.put("password", password_input);
                                  Hawk.put("tel", account_input);
                                  Hawk.put("type", chooseLoginType);

                                  //存储登录成功返回来的信息
                                  Hawk.put("r_security", security.getData());
                                  Log.e("security", String.valueOf( security.getData().getId() ) ) ;
                                  Hawk .put("t_security",security .getToken()) ;

                                  Log.e("security",security .getToken());
                                  startActivity(intent_login);
                                  finish();
                              }
                              else {
                                  ToastUtils.getInstance(LoginActivity.this).showToast(security.getMessage());
                                  popupWindow.dismiss();
                              }
                          }
                          else{// 报错
                              ToastUtils.getInstance(LoginActivity.this).showToast("用户登录角色选择错误, 请检查");
                              popupWindow.dismiss();

                          }
    }
                            @Override
                            public void onError(Response<String> response) {
                                super.onError(response);
                                Log.d("yyyyyy","onError");
                                ToastUtils.getInstance(LoginActivity.this).showToast("登录失败");
                                popupWindow.dismiss();

                            }
                        });

   }



    /**
     * 处理登录信息, 主要进行非空判断
     */
    public boolean handleLoginInfo(){

        account_input = account.getText().toString().trim();
        password_input = password.getText().toString().trim();

        int index = loginType.getSelectedIndex();
        if(index==0){
            chooseLoginType = "司机";  //司机
        }else if(index==1){
            chooseLoginType = "车队";  //车队
        }else if(index==2){
            chooseLoginType = "安保";  //安保
        }

        if("".equals(account_input)){// 非空判断手机号
            ToastUtils.getInstance(this).showToast("请输入用户名");
            return false;
        }

        if("".equals(password_input)){
            ToastUtils.getInstance(this).showToast("请输入登录密码");
            return false;
        }

        return true;
    }

    //双击退出
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                ToastUtils.getInstance(LoginActivity.this).showToast("再按一次退出程序");
                firstTime = secondTime;
                return true;
            } else {
                finish();
            }
        }

        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onDestroy() {super.onDestroy();
        if(popupWindow != null && popupWindow.isShowing()){
            popupWindow.dismiss();
            popupWindow = null;
        }
    }


}

