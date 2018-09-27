package activity.huafeng.com.myapplication1.util;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.alibaba.sdk.android.ams.common.global.AmsGlobalHolder;
import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.SPCookieStore;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.orhanobut.hawk.Hawk;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;

//import com.alibaba.sdk.android.push.register.GcmRegister;
//import com.alibaba.sdk.android.push.register.HuaWeiRegister;
//import com.alibaba.sdk.android.push.register.MiPushRegister;

/**
 * Created by leovo on 2018-08-28.
 */

public class MyApplication extends Application {
    private static final String TAG = "Init";

    public static boolean isDebug = true;


    @Override
    public void onCreate() {
        
        MultiDex.install(this);

        super.onCreate();

        //以下可以写 各种第三方空间的初始化.

        //OKGO
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory();
        builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
        //设置cookie
        builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        loggingInterceptor.setColorLevel(Level.INFO );
        builder.addInterceptor(loggingInterceptor);
        OkGo.getInstance().init(this)
                .setOkHttpClient(builder.build());

        Hawk.init(this).build();


        //初始化ZXING包
        ZXingLibrary.initDisplayOpinion(this);


        // 在SDK各功能组件使用之前都需要调用 SDKInitializer.initialize(getApplicationContext());
        // 因此我们建议该方法放在Application的初始化方法中
        SDKInitializer.initialize(this);
        SDKInitializer.setCoordType( CoordType.BD09LL);// 告诉百度,使用的是百度坐标

        //云推送通道
        initCloudChannel(this);


    }




    /**
      * 初始化云推送通道
      * @param applicationContext
      */
           private void initCloudChannel(Context applicationContext) {

               PushServiceFactory.init(applicationContext);
               final CloudPushService pushService = PushServiceFactory.getCloudPushService();
               pushService.register(applicationContext, new CommonCallback() {
                   @Override
                   public void onSuccess(String response) {
                       Log.d(TAG, "init CloudPushService success , Appkey: " + AmsGlobalHolder.getAppMetaData("com.alibaba.app.appkey"));


                   }
                   @Override
                   public void onFailed(String errorCode, String errorMessage) {
                       Log.d(TAG, "init CloudPushService failed. errorcode: " + errorCode + ", errorMessage: " + errorMessage + ", deviceId:" + pushService.getDeviceId());

                   }
               });

               String device = pushService.getDeviceId();
               Log.e(TAG, "devicedId=======" + device);

    }

    
}

