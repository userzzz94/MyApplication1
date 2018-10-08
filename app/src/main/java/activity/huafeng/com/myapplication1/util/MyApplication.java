package activity.huafeng.com.myapplication1.util;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
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
import com.norbsoft.typefacehelper.TypefaceCollection;
import com.norbsoft.typefacehelper.TypefaceHelper;
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


    /** Multiple custom typefaces support */
    private TypefaceCollection mJuiceTypeface;
    /** Multiple custom typefaces support */
    private TypefaceCollection mArchRivalTypeface;
    /** Multiple custom typefaces support */
    private TypefaceCollection mActionManTypeface;
    /** Multiple custom typefaces support */
    private TypefaceCollection mSystemDefaultTypeface;
    /** Multiple custom typefaces support */
    private TypefaceCollection mUbuntuTypeface;

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


        /*
          * 实现自定义字体的库
          * Android Typeface Helper
         */

        // Load helper with default custom typeface (single custom typeface)
        TypefaceHelper.init(new TypefaceCollection.Builder()
                .set( Typeface.NORMAL, Typeface.createFromAsset(getAssets(), "fonts/ubuntu/Ubuntu-R.ttf"))
                .set(Typeface.BOLD, Typeface.createFromAsset(getAssets(), "fonts/ubuntu/Ubuntu-B.ttf"))
                .set(Typeface.ITALIC, Typeface.createFromAsset(getAssets(), "fonts/ubuntu/Ubuntu-RI.ttf"))
                .set(Typeface.BOLD_ITALIC, Typeface.createFromAsset(getAssets(), "fonts/ubuntu/Ubuntu-BI.ttf"))
                .create());

        // Multiple custom typefaces support
        mJuiceTypeface = new TypefaceCollection.Builder()
                .set(Typeface.NORMAL, Typeface.createFromAsset(getAssets(), "fonts/Juice/JUICE_Regular.ttf"))
                .set(Typeface.BOLD, Typeface.createFromAsset(getAssets(), "fonts/Juice/JUICE_Bold.ttf"))
                .set(Typeface.ITALIC, Typeface.createFromAsset(getAssets(), "fonts/Juice/JUICE_Italic.ttf"))
                .set(Typeface.BOLD_ITALIC, Typeface.createFromAsset(getAssets(), "fonts/Juice/JUICE_Bold_Italic.ttf"))
                .create();

        // Multiple custom typefaces support
        mArchRivalTypeface = new TypefaceCollection.Builder()
                .set(Typeface.NORMAL, Typeface.createFromAsset(getAssets(), "fonts/arch_rival/SF_Arch_Rival.ttf"))
                .set(Typeface.BOLD, Typeface.createFromAsset(getAssets(), "fonts/arch_rival/SF_Arch_Rival_Bold.ttf"))
                .set(Typeface.ITALIC, Typeface.createFromAsset(getAssets(), "fonts/arch_rival/SF_Arch_Rival_Italic.ttf"))
                .set(Typeface.BOLD_ITALIC, Typeface.createFromAsset(getAssets(), "fonts/arch_rival/SF_Arch_Rival_Bold_Italic.ttf"))
                .create();

        // Multiple custom typefaces support
        mActionManTypeface = new TypefaceCollection.Builder()
                .set(Typeface.NORMAL, Typeface.createFromAsset(getAssets(), "fonts/Action-Man/Action_Man.ttf"))
                .set(Typeface.BOLD, Typeface.createFromAsset(getAssets(), "fonts/Action-Man/Action_Man_Bold.ttf"))
                .set(Typeface.ITALIC, Typeface.createFromAsset(getAssets(), "fonts/Action-Man/Action_Man_Italic.ttf"))
                .set(Typeface.BOLD_ITALIC, Typeface.createFromAsset(getAssets(), "fonts/Action-Man/Action_Man_Bold_Italic.ttf"))
                .create();

        // Multiple custom typefaces support
        mUbuntuTypeface = new TypefaceCollection.Builder()
                .set(Typeface.NORMAL, Typeface.createFromAsset(getAssets(), "fonts/ubuntu/Ubuntu-R.ttf"))
                .set(Typeface.BOLD, Typeface.createFromAsset(getAssets(), "fonts/ubuntu/Ubuntu-B.ttf"))
                .set(Typeface.ITALIC, Typeface.createFromAsset(getAssets(), "fonts/ubuntu/Ubuntu-RI.ttf"))
                .set(Typeface.BOLD_ITALIC, Typeface.createFromAsset(getAssets(), "fonts/ubuntu/Ubuntu-BI.ttf"))
                .create();

        // Multiple custom typefaces support
        mSystemDefaultTypeface = TypefaceCollection.createSystemDefault();


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


    /** Multiple custom typefaces support */
    public TypefaceCollection getJuiceTypeface() {
        return mJuiceTypeface;
    }

    /** Multiple custom typefaces support */
    public TypefaceCollection getArchRivalTypeface() {
        return mArchRivalTypeface;
    }

    /** Multiple custom typefaces support */
    public TypefaceCollection getActionManTypeface() {
        return mActionManTypeface;
    }

    /** Multiple custom typefaces support */
    public TypefaceCollection getSystemDefaultTypeface() {
        return mSystemDefaultTypeface;
    }

    /** Multiple custom typefaces support */
    public TypefaceCollection getUbuntuTypeface() {
        return mUbuntuTypeface;
    }

    
}

