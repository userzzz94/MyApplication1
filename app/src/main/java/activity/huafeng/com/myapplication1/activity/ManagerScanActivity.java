package activity.huafeng.com.myapplication1.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.orhanobut.hawk.Hawk;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import activity.huafeng.com.myapplication1.R;
import activity.huafeng.com.myapplication1.bean.LoginBean_Manager;
import activity.huafeng.com.myapplication1.bean.Scan_ManagerBean;
import activity.huafeng.com.myapplication1.util.ToastUtils;
import activity.huafeng.com.myapplication1.util.UrlUtil;

public class ManagerScanActivity extends BaseActivity {

    List <Scan_ManagerBean.OrderInfo> list;

    EditText cargotype, pi, mao, jing,creatname, creattime, weight;
    TextView name, carnum, tel;
    TextView place, number;
    Button sure;
    TimePickerView timePickerView;

    private String name_input;
    private String tel_input;
    private String cph_input;
    private String kd_input;

    private Scan_ManagerBean bean;
    private String idrw, Rwmbh;

    private String MeiZ_input;
    private String maozhong_input;
    private double jingzhong;
    private String jingzhong_input;
    private String pizhong_input;
    private String lrr_input;
    private String lrsj_input;
    private String Dw_input;

    private String format;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_manager_scan );

        //声明一个Intent意图，用来接受Manager_Order传过来的值
        Intent intent=getIntent();
        //拿到Manager_Order传过来的值并返回一个字符串，
        //口令要一致
        Rwmbh=intent.getStringExtra( "m_string" );
        //把字符串赋值给输入框

        number.setText( Rwmbh );

        name_input=Hawk.get( "sjxm" );
        Log.d( "sjxm", name_input );
        name.setText( name_input );

        tel_input=Hawk.get( "sjdh" );
        Log.d( "sjxm", tel_input );
        tel.setText( tel_input );

        cph_input=Hawk.get( "cph" );
        Log.d( "sjxm", cph_input );
        carnum.setText( cph_input );

        kd_input=Hawk.get( "sjkd" );
        Log.d( "sjxm", kd_input );
        place.setText( kd_input );


    }

    @SuppressLint("WrongViewCast")
    public void initView() {

        number=(TextView) findViewById( R.id.manager_scan_tv_num );
        name=(TextView) findViewById( R.id.manager_scan_et_name );
        carnum=(TextView) findViewById( R.id.manager_scan_et_carnum );
        tel=(TextView) findViewById( R.id.manager_scan_et_tel );
        cargotype=(EditText) findViewById( R.id.manager_scan_et_cargotype );
        pi=(EditText) findViewById( R.id.manager_scan_weight_pi );
        mao=(EditText) findViewById( R.id.manager_scan_weight_mao );
        jing=(EditText) findViewById( R.id.manager_scan_weight_jing );
        creatname=(EditText) findViewById( R.id.manager_scan_et_creatname );
        creattime=(EditText) findViewById( R.id.manager_scan_et_creattime );
        weight=(EditText) findViewById( R.id.manager_scan_et_weight );

        place=(TextView) findViewById( R.id.manager_scan_sp_place );


        //净重
        jing .setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                maozhong_input=mao.getText().toString().trim();
                Log .d("jing",maozhong_input);
                pizhong_input=pi.getText().toString().trim();
                Log .d("jing",pizhong_input);
                //        jingzhong=(Double .parseDouble(maozhong_input) - Double .parseDouble(pizhong_input));


                Log .d("jing",maozhong_input);
                Log .d("jing",pizhong_input);
                jingzhong=(Double .parseDouble(maozhong_input) - Double .parseDouble(pizhong_input));
                Log.d("jing",String .valueOf(jingzhong ));
                jing.setText(String .valueOf(jingzhong ).trim() );

                jingzhong_input = jing.getText().toString().trim();
                Log.e( "jing", jingzhong_input  );

            }
        } ) ;

        //时间选择
        creattime.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTime();
            }
        } );

        sure=(Button) findViewById( R.id.manager_scan_btn_finish );
        sure.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (handleInput()) {
                    requestNet_up();
                }
            }
        } );

    }

    public void initData() {

        //requestNet_put();


    }

    public void getTime() {

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat( "yyyy,MM,dd" );
        format=simpleDateFormat.format( System.currentTimeMillis() );
        Log.i( "format", format );


        Calendar selectedDate=Calendar.getInstance();
        Calendar startDate=Calendar.getInstance();
        startDate.set( 2016, 1, 1 );//设置起始年份

        Calendar endDate=Calendar.getInstance();
        endDate.set( 2022, 1, 1 );//设置结束年份

        timePickerView=new TimePickerView.Builder( ManagerScanActivity.this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
                format=simpleDateFormat.format( date );
                creattime.setText( format.trim() );
            }
        } )

                //.setType(TimePickerView.Type.ALL)//默认全部显示
                .setCancelText( "取消" )//取消按钮文字
                .setSubmitText( "确定" )//确认按钮文字
                .setContentSize( 18 )//滚轮文字大小
                .setTitleSize( 20 )//标题文字大小
                .setTitleText( "选择时间" )//标题文字
                .setOutSideCancelable( true )//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic( true )//是否循环滚动
                .setTitleColor( Color.BLACK )//标题文字颜色
                .setSubmitColor( R.color.gray_color2 )//确定按钮文字颜色
                .setCancelColor( R.color.gray_color2 )//取消按钮文字颜色
                .setTitleBgColor( 0xFF666666 )//标题背景颜色 Night mode
                .setBgColor( 0xFF333333 )//滚轮背景颜色 Night mode

                .setDate( selectedDate )// 如果不设置的话，默认是系统时间*/
                .setRangDate( startDate, endDate )//起始终止年月日设定
                .setLabel( "年", "月", "日", "时", "分", "秒" )
                .isCenterLabel( true ) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog( false )//是否显示为对话框样式

                .build();
        timePickerView.show();

    }

    private void requestNet_put() {

        idrw=Hawk.get( "scanrw" );
        String m_token=Hawk.get( "t_manager" );
        if (idrw == null) {
            Log.d( "idrw", "null" );
        } else {
            Log.d( "idrw", idrw );
        }

        OkGo. <String>get( UrlUtil.URL_Prefix + "Task/GetRwmsj" )// 二维码录入信息页面查询出司机的信息
                .params( "MxId", idrw )
                .params( "Token", m_token )

                .execute( new StringCallback() {
                    @Override
                    public void onSuccess(Response <String> response) {
                        Log.d( "sss", "onSuccess" );


                    }

                    public void onError(Response <String> response) {
                        Log.d( "sss", "onError" );
                        super.onError( response );
                        ToastUtils.getInstance( ManagerScanActivity.this ).showToast( "请求失败" );

                    }
                } );


    }

    private boolean handleInput() {

        MeiZ_input=cargotype.getText().toString().trim();
        maozhong_input=mao.getText().toString().trim();
        Log .d("jing",maozhong_input);
        pizhong_input=pi.getText().toString().trim();
        Log .d("jing",pizhong_input);
        //        jingzhong=(Double .parseDouble(maozhong_input) - Double .parseDouble(pizhong_input));
        jingzhong_input = jing.getText().toString().trim();
        Log.e( "jing", jingzhong_input  );
        lrr_input=creatname.getText().toString().trim();
        lrsj_input=creattime.getText().toString().trim();
        Log.e( "TimePicker", lrsj_input );
        Dw_input=weight.getText().toString().trim();


        if ("".equals( MeiZ_input )) {
            ToastUtils.getInstance( this ).showToast( "请输入煤种" );
            return false;
        }

        if ("".equals( pizhong_input )) {
            ToastUtils.getInstance( this ).showToast( "请选择皮重" );
            return false;
        }

        if ("".equals( maozhong_input )) {
            ToastUtils.getInstance( this ).showToast( "请选择毛重" );
            return false;
        }

//        if ("".equals( jingzhong_input )) {
//            ToastUtils.getInstance( this ).showToast( "净重误差不合理，请检查" );
//            return false;
//        }


        if ("".equals( lrr_input )) {
            ToastUtils.getInstance( this ).showToast( "请选择录入人" );
            return false;
        } else if (name_input.length() <= 1) {
            ToastUtils.getInstance( ManagerScanActivity.this ).showToast( "请输入正确的姓名" );
            return false;

        }
        if ("".equals( lrsj_input )) {
            ToastUtils.getInstance( this ).showToast( "请选择录入时间" );
            return false;
        }
        if ("".equals( Dw_input )) {
            ToastUtils.getInstance( this ).showToast( "请选择吨位" );
            return false;
        }

        return true;
    }


    private void requestNet_up() {

        String m_token=Hawk.get( "t_manager" );
        LoginBean_Manager.DataManager manager=Hawk.get( "r_manager" );
        idrw=Hawk.get( "scanrw" );
        String idsj=Hawk.get( "sjidScan" );

        OkGo. <String>get( UrlUtil.URL_Prefix + "Task/GetCrwmxx" )// 绑定二维码信息
                .params( "Token", m_token )
                .params( "CdId", manager.getId() )
                .params( "SjId", idsj )
                .params( "Rwmbh", Rwmbh )
                .params( "Sjxm", name_input )
                .params( "Sjdh", tel_input )
                .params( "Cph", cph_input )
                .params( "Kd", kd_input )
                .params( "MeiZ", MeiZ_input )
                .params( "maozhong", maozhong_input )
                .params( "jingzhong", jingzhong_input )
                .params( "pizhong", pizhong_input )
                .params( "lrr", lrr_input )
                .params( "lrsj", lrsj_input )
                .params( "Dw", Dw_input )
                .params( "MxId", idrw )

                .execute( new StringCallback() {
                    @Override
                    public void onSuccess(Response <String> response) {
                        Log.d( "ggg", "onSuccess" );
                        ToastUtils.getInstance( ManagerScanActivity.this ).showToast( "二维码绑定成功" );

                        //                        Gson gson = new Gson();
                        //                        Scan_ManagerBean bean = gson.fromJson( response.body(), Scan_ManagerBean.class);
                        //
                        //                        Hawk .put("attach",bean.getData()) ;

                        Intent scan_finish=new Intent( ManagerScanActivity.this, MainActivity_Manager.class );
                        scan_finish.putExtra( "findcar", 0 );
                        startActivity( scan_finish );
                        finish();
                    }

                    public void onError(Response <String> response) {
                        Log.d( "ggg", "onError" );
                        super.onError( response );
                        ToastUtils.getInstance( ManagerScanActivity.this ).showToast( "请求失败" );

                    }
                } );


    }

}
