package activity.huafeng.com.myapplication1.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import activity.huafeng.com.myapplication1.R;
import activity.huafeng.com.myapplication1.base.BasePager;
import activity.huafeng.com.myapplication1.base.impl.Driver_Cooperation;
import activity.huafeng.com.myapplication1.base.impl.Driver_Mine;
import activity.huafeng.com.myapplication1.base.impl.Driver_Search;
import activity.huafeng.com.myapplication1.base.impl.Driver_SendCar;
import activity.huafeng.com.myapplication1.bean.LoginBean_Driver;
import activity.huafeng.com.myapplication1.util.ToastUtils;
import activity.huafeng.com.myapplication1.util.UrlUtil;

public class MainActivity_Driver extends BaseActivity {

    private static final String TAG = "MainActivity_Driver";
    //记录双击退出应用的第一次点击时间.
    private long firstTime = 0;

    private ViewPager viewPager;
    private RadioGroup radioGroup;
    private List<BasePager> pagerList;

    private RadioButton sendCar;
    private RadioButton cooperation;
    private RadioButton search;
    private RadioButton mine;

    private LocationClient mLocationClient;
    private MyBDLocationListener mBDLocationListener;
    private LoginBean_Driver.Data driver;

    //private CloudPushService mPushService;

    private View thisRootView;
    private boolean isInited = false;

    private String address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_driver);


        //EventBus.getDefault().register(this); // 注册EventBus

    }

    public void initView() {

        viewPager = findViewById(R.id.activity_main_driver_viewpager);
        radioGroup = findViewById(R.id.activity_main_driver_radiogroup);

        //查找该页面下的radiobutton , 为了解决drawtop的图片在xml中不能设置大小的问题

        cooperation = findViewById(R.id.activity_main_driver_cooperation);
        sendCar = findViewById(R.id.activity_main_driver_sendcar);
        search=findViewById(R.id.activity_main_driver_search) ;
        mine=findViewById(R.id.activity_main_driver_mine) ;

        //设置加载时, drawtop图片无法设置大小的问题
        setDrawTopImageSize();

    }

    public void initData() {

        pagerList = new ArrayList<>();

        pagerList.add(new Driver_Cooperation(this));
        pagerList.add(new Driver_SendCar(this));
        pagerList.add(new Driver_Search(this));
        pagerList.add(new Driver_Mine(this));

        initListener();// 这里必须在pagerList之后初始化监听,因为pagerAdapter调用 pagerList的size()方法, 为避免空指针,在这里初始化监听.

        radioGroup.check(R.id.activity_main_driver_cooperation);// 在第一次进首页面的时候选中第一个rediobutton
        pagerList.get(0).initData();// 初始化第一个radiobutton所对应页面的initdata方法.

        //获取登录的司机信息, 在更新位置信息的时候, 需要司机的手机号
        driver = Hawk.get("r_driver");

        //配置 百度地图api, 获取坐标
        mLocationClient = new LocationClient(this);//定位客户端
        mBDLocationListener = new MyBDLocationListener();
        mLocationClient.registerLocationListener(mBDLocationListener);

        Log .e("hhh","initData()") ;
        getLocation();// 启动获取坐标的方法


    }


    /**
     * 初始化监听
     */
    private void initListener() {
        //设置viewpager的缓存页面, 如果离开过远,页面就destroy掉了, 因为在第一个页面引入了三方控件, 如果把第一个页面destroy掉在还原回来(已经不走initView方法了)
        //会在三方控件内出现空指针异常
        viewPager.setOffscreenPageLimit(4);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){


                    case R.id.activity_main_driver_cooperation:
                        viewPager.setCurrentItem(0,false);
                        break;
                    case R.id.activity_main_driver_sendcar:
                        viewPager.setCurrentItem(1,false);

                        Intent intent_sendcar = getIntent();
                        int sendcar = intent_sendcar.getIntExtra("sendcar",1);
                        viewPager .setCurrentItem(sendcar) ;

                        break;
                    case R.id.activity_main_driver_search:
                        viewPager.setCurrentItem(2,false);
                        break;
                    case R.id.activity_main_driver_mine:
                        viewPager.setCurrentItem(3,false);
                        break;

                }
            }
        });
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return pagerList.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
                return view == o;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                BasePager pager = pagerList.get(position);
                pager.initView();
                container.addView(pager.rootView);
                return pager.rootView;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View)object);
            }
        });

        //在这里初始化数据的原因是 viewpager会初始化当前页的同时,也会同时初始化 当前页前后两页的数据,
        // 为避免资源浪费,只有当page被选中的时候, 才初始化当前页的数据,避免请求网络, 浪费资源.
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                BasePager pager = pagerList.get(i);
                pager.initData();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }




    /** 获得所在位置经纬度及详细地址 */
    public void getLocation() {
 //       MyApplication.mLocationClient.start();

        // 声明定位参数
        LocationClientOption option = new LocationClientOption();

        option.setLocationMode( LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，设置定位模式，默认高精度
        //LocationMode.Hight_Accuracy：高精度；
        //LocationMode. Battery_Saving：低功耗；
        //LocationMode. Device_Sensors：仅使用设备；

        option.setCoorType("bd09ll");
        //可选，设置返回经纬度坐标类型，默认GCJ02
        //GCJ02：国测局坐标；
        //BD09ll：百度经纬度坐标；
        //BD09：百度墨卡托坐标；
        //海外地区定位，无需设置坐标类型，统一返回WGS84类型坐标

        option.setScanSpan(1000);
        //可选，设置发起定位请求的间隔，int类型，单位ms
        //如果设置为0，则代表单次定位，即仅定位一次，默认为0
        //如果设置非0，需设置1000ms以上才有效

        option.setIsNeedAddress(true);// 需要地址信息

        option.setOpenGps(true);
        //可选，设置是否使用gps，默认false
        //使用高精度和仅用设备两种定位模式的，参数必须设置为true

        option.setLocationNotify(true);
        //可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false

        option.setIgnoreKillProcess(false);
        //可选，定位SDK内部是一个service，并放到了独立进程。
        //设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)

        option.SetIgnoreCacheException(false);
        //可选，设置是否收集Crash信息，默认收集，即参数为false

        option.setWifiCacheTimeOut(5*60*1000);
        //可选，V7.2版本新增能力
        //如果设置了该接口，首次启动定位时，会先判断当前Wi-Fi是否超出有效期，若超出有效期，会先重新扫描Wi-Fi，然后定位

        option.setEnableSimulateGps(false);
        //可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false

        mLocationClient.setLocOption(option);
        //mLocationClient为第二步初始化过的LocationClient对象
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        //更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
        Log .e("hhh","getLocation") ;
        // 启动定位
        mLocationClient.start();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 取消监听函数
        Log .e("hhh","onDestroy()");
        if (mLocationClient != null) {
            mLocationClient.unRegisterLocationListener(mBDLocationListener);

            if (mLocationClient.isStarted()) {
                // 获得位置之后停止定位
                mLocationClient.stop();
            }
        }

    }




    /**
     * 位置监听
     */
    private class MyBDLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {

            // 非空判断
            if (location != null) {

                // 根据BDLocation 对象获得经纬度以及详细地址信息
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();
                address = location.getAddrStr();

                Log.e("Hawk", String.valueOf( longitude ) );
                Log .e("Hawk", String.valueOf( latitude ) ) ;
                if("".equals(address) ){
                    Log .d("Hawk","null") ;
                }else {
                    Log.e( "Hawk", address );
                }

                Hawk .put("Djd", longitude ) ;
                Hawk .put("Dwd", latitude ) ;
                Hawk .put("Adress",address ) ;

                Log .d("Hawk .put", String.valueOf( longitude ) );

                requestNet_UpdateLocation(longitude,latitude);
                Log .d("UpdateLocation","requestNet_UpdateLocation");


            }else{
                log_d(TAG,"location 为 null !");
            }
        }
    }

    /**
     * 请求网络,更新司机坐标
     */
    private void requestNet_UpdateLocation(double longitude,double latitude){

        LoginBean_Driver.Data  driver  = Hawk.get("r_driver");
        String d_token=Hawk .get("t_driver");

        Log.d("UpdateLocation","GetClwz" );

        OkGo.<String>get( UrlUtil.URL_Prefix+"Task/GetClwz")
                .params("Token",d_token)
                .params("SjId",driver.getId())
                .params("Jd",longitude)
                .params("Wd",latitude)
                .params("address",address)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String json = response.body().toString();

                        Log.d("jwd", "onResponse() returned: " + json);


                    }

                    public void onError(Response<String> response) {
                        super.onError(response);

                        ToastUtils .getInstance(MainActivity_Driver.this).showToast("位置获取异常");

                    }
                });

    }




    /**
     * 设置页面加载时 drowTop的图片没法设置大小的问题
     */
    @SuppressLint("ResourceType")
    private void setDrawTopImageSize(){

        Drawable sendCar_d = getResources().getDrawable(R.drawable.selector_activity_main_home);
        Drawable cooperation_d = getResources().getDrawable(R.drawable .selector_activity_main_car);
        Drawable order_d = getResources().getDrawable(R.drawable .selector_activity_main_home);
        Drawable mine_d = getResources().getDrawable(R.drawable .selector_activity_main_user);


        sendCar_d.setBounds(0,0,70,70);
        cooperation_d.setBounds(0,0,70,70);
        order_d.setBounds(0,0,70,70);
        mine_d.setBounds(0,0,70,70);


        sendCar.setCompoundDrawables(null,sendCar_d,null,null);
        cooperation.setCompoundDrawables(null,cooperation_d,null,null);
        search.setCompoundDrawables(null,order_d,null,null);
        mine.setCompoundDrawables(null,mine_d,null,null);

    }

    /**
     * 双击退出程序 通过监听keyUp
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                ToastUtils.getInstance(MainActivity_Driver.this).showToast("再按一次退出程序");
                firstTime = secondTime;
                return true;
            } else {
                finish();
            }
        }

        return super.onKeyUp(keyCode, event);
    }


}
