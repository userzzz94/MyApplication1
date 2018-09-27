package activity.huafeng.com.myapplication1.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import activity.huafeng.com.myapplication1.R;
import activity.huafeng.com.myapplication1.bean.Manager_OrderStateBean;
import activity.huafeng.com.myapplication1.util.ToastUtils;

public class Location_LookDriverActivity extends BaseActivity {

    MapView mapView;
    BaiduMap mBaiduMap;
    TextView record;

    ImageView back;

    TextView qsd, zdd, num, type, weight, time, state;
    TextView address, name, tel, cph, cd;

    private String latitude;
    private String longitude;
    private boolean isFirstLoc = true;
    private LatLng latLng;

    private List <Manager_OrderStateBean.OrderInfo> data = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_location__look_driver );
    }

    public void initView() {

        back =findViewById(R.id.activity_master_look_driver_back) ;
        back .setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish() ;
            }
        } );

        //获取地图引用
        mapView = findView( R.id.activity_master_look_driver_map );

        //找到页面的标记
        record = findView( R.id.record );
        //监听record这个 textview , 一旦值变成了 特定的, 就说明了
        record.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if ("OK".equals( s.toString() )) {
                    

                } else {
                    ToastUtils.getInstance( Location_LookDriverActivity.this ).showToast( "值变了,但不是OK" );
                }
            }
        } );


        qsd = findView( R.id.activity_master_look_driver_qsd );
        zdd = findView( R.id.activity_master_look_driver_zdd );
        num = findView( R.id.activity_master_look_driver_num );
        type = findView( R.id.activity_master_look_driver_cargotype );
        weight = findView( R.id.activity_master_look_driver_weight );
        time = findView( R.id.activity_master_look_driver_time );
//        state = findView( R.id.activity_master_look_driver_cargoresource_state );

        //司机信息相关
        address = findView( R.id.activity_master_look_driver_address );
        name = findView( R.id.activity_master_look_driver_drivername );
        cph = findView( R.id.activity_master_look_driver_drivercph );
        tel = findView( R.id.activity_master_look_driver_driverphone );
        cd = findView( R.id.activity_master_look_driver_cdname );


    }

    public void initData() {

       // data=Hawk .get("ztLocation");
        qsd.setText(String.valueOf(Hawk .get("qsdtxt") ));
        zdd.setText(String.valueOf(Hawk .get("zddtxt") ));
        num.setText(String.valueOf(Hawk .get("numtxt") ));
        type.setText(String.valueOf(Hawk .get("cargonametxt") ));
        weight.setText(String.valueOf(Hawk .get("cargotypetxt") ));
        time.setText(String.valueOf(Hawk .get("timetxt") ));

        name.setText(String.valueOf(Hawk .get("nametxt") ));
        tel.setText(String.valueOf(Hawk .get("dhtxt") ));
        cph.setText(String.valueOf(Hawk .get("carnumtxt") ));
        cd.setText(String.valueOf(Hawk .get("cxtxt") ));
        address.setText(String.valueOf(Hawk .get("addresstxt") )) ;

        boolean flag = true;


        // 纬度
        latitude = Hawk .get("lattxt");
        if ("".equals( latitude ) || latitude == null) {
            flag = false;
        }


        // 经度
        longitude = Hawk .get("longtxt");
        if ("".equals( longitude ) || longitude == null) {
            flag = false;
        }

        if (flag) {
            record.setText( "OK" );
        }

        initMap(  latitude,longitude );// 初始化地图

        Log .d("map", latitude) ;
        Log .d("map",longitude) ;

    }

    /**
     * 初始化地图
     */
    private void initMap( String latitude,String longitude ) {
        mBaiduMap = mapView.getMap();
        mBaiduMap.setMapType( BaiduMap.MAP_TYPE_NORMAL );
        //开启定位图层
        mBaiduMap.setMyLocationEnabled( true );

        LatLng cenpt = new LatLng(  Double.parseDouble( latitude ),Double.parseDouble( longitude ) );
        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
                .target( cenpt )
                .zoom( 14 )
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
                .newMapStatus( mMapStatus );
        //改变地图状态
        mBaiduMap.setMapStatus( mMapStatusUpdate );

        //构建Marker图标
        BitmapDescriptor bitmap = null;
        bitmap = BitmapDescriptorFactory.fromResource( R.drawable.icon_location_map );
        // 构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions().position( cenpt ).icon( bitmap );
        // 在地图上添加Marker，并显示
        mBaiduMap.addOverlay( option );

//        String adressget=Hawk .get("Adress") ;
//        if("".equals(adressget) ){
//        Log .d("map","null") ;
//    }else {
//        Log.d( "map", adressget );
//    }
//        address.setText(adressget) ;

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }



}
