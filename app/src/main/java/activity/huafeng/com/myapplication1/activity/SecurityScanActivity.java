package activity.huafeng.com.myapplication1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.orhanobut.hawk.Hawk;

import java.util.List;

import activity.huafeng.com.myapplication1.R;
import activity.huafeng.com.myapplication1.bean.LoginBean_Security;
import activity.huafeng.com.myapplication1.bean.Scan_SecurityBean;
import activity.huafeng.com.myapplication1.util.ToastUtils;
import activity.huafeng.com.myapplication1.util.UrlUtil;

public class SecurityScanActivity extends BaseActivity {

    TextView number,name,carnum,place,cargotype,pi,mao,jing,creatname,creattime;
    Button sure;

    private String Rwmbh_scan;

    List<Scan_SecurityBean.Data > data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_scan);


    }

    public void initView() {

        number =(TextView)  findViewById(R.id.security_scan_tv_num ) ;
        name =(TextView )findViewById(R.id.security_scan_tv_name) ;
        carnum=(TextView )findViewById(R.id.security_scan_tv_carnum) ;
        place=(TextView )findViewById(R.id.security_scan_tv_place) ;
        cargotype=(TextView )findViewById(R.id.security_scan_tv_cargotype) ;
        pi=(TextView )findViewById(R.id.security_scan_weight_pi) ;
        mao=(TextView )findViewById(R.id.security_scan_weight_mao) ;
        jing=(TextView )findViewById(R.id.security_scan_weight_jing) ;
        creatname=(TextView )findViewById(R.id.security_scan_tv_creatname) ;
        creattime=(TextView )findViewById(R.id.security_scan_tv_creattime) ;

        sure =(Button )findViewById(R.id.security_scan_btn_finish) ;
        sure.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestNet_sure();
            }
        } );

    }

    public void initData() {

        //声明一个Intent意图，用来接受security_Order传过来的值
        Intent intent = getIntent();
        //拿到security_Order传过来的值并返回一个字符串，
        Rwmbh_scan = intent.getStringExtra("s_string");
        Log .e("rrr1",Rwmbh_scan) ;
        
        requestNet_put();

    }

    private void requestNet_put() {

        //Log .e("rrr",Rwmbh ) ;
        if (Rwmbh_scan == null) {
            Log.e( "rrr2", "null" );
        } else {
            Log.e( "rrr3", Rwmbh_scan );
        }
        String s_token=Hawk .get("t_security");
        OkGo.<String>get(UrlUtil.URL_Prefix + "Task/GetAbCx")
                .params("Rwmbh",Rwmbh_scan )
                .params("Token", s_token)

                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.d("sss", "onSuccess");

                        Gson gson = new Gson();
                        Scan_SecurityBean bean = gson.fromJson(response.body(), Scan_SecurityBean.class);
                        int code = bean.getCode();
                        if (code == 1) {// 查询成功
                            
                            Log .e("ggg", String.valueOf( bean .getData() ) );

                            data = bean.getData();

                            if(data != null && data.size() != 0) { //获取的数据不为空


                                String Rwmbh_search = data.get( 0 ).getRwmbh();
                                Log.e( "eee", Rwmbh_search );
                                Log.e( "eee", Rwmbh_scan );

                                if (Rwmbh_scan.equals( Rwmbh_search )) {
                                    Log.e( "eee", "二维码相同" );
                                    number.setText( Rwmbh_search );
                                    name.setText( data.get( 0 ).getSjxm() );
                                    carnum.setText( data.get( 0 ).getCph() );
                                    place.setText( data.get( 0 ).getKd() );
                                    cargotype.setText( data.get( 0 ).getMeiz() );
                                    pi.setText( data.get( 0 ).getPz() );
                                    mao.setText( data.get( 0 ).getMz() );
                                    jing.setText( data.get( 0 ).getJz() );
                                    creatname.setText( data.get( 0 ).getLrr() );
                                    creattime.setText( data.get( 0 ).getLrsj() );

                                }
                            }else {//查询结果为null
                                Log.e("eee", "二维码不存在");
                                ToastUtils.getInstance(SecurityScanActivity.this).showToast(bean.getMsg());


                            }


                        } else {
                            Log.d("eee", "查询失败");
                            ToastUtils.getInstance(SecurityScanActivity.this).showToast(bean.getMsg());
                        }
                    }

                    public void onError(Response<String> response) {
                        Log.d("sss","onError");
                        super.onError(response);
                        ToastUtils.getInstance(SecurityScanActivity.this).showToast("请求失败");

                    }
                });


    }


    //点击确认
    private void requestNet_sure() {
        LoginBean_Security.Data security=Hawk .get("r_security") ;
        String s_token = Hawk.get( "t_security" );
        if (data != null && data.size() != 0) { //获取的数据不为空
            String mxid = data.get( 0 ).getId();
            String rwid=data .get(0) .getRId();
            
            OkGo. <String>get( UrlUtil.URL_Prefix + "Task/GetAbQdRw" )
                    .params( "Token", s_token )
                    .params( "MxId", mxid )
                    .params( "AbId", security.getId() )
                    .params( "RwId", rwid )
                    .execute( new StringCallback() {
                        @Override
                        public void onSuccess(Response <String> response) {

                            ToastUtils.getInstance( SecurityScanActivity.this ).showToast( "订单任务完成" );

                            Intent scan_finish = new Intent( SecurityScanActivity.this, MainActivity_Security.class );
                            scan_finish.putExtra( "secu_finish", 0 );
                            startActivity( scan_finish );
                            finish();

                        }

                        public void onError(Response <String> response) {

                            super.onError( response );
                            ToastUtils.getInstance( SecurityScanActivity.this ).showToast( "任务完成失败" );



                        }
                    } );

        } else {

            ToastUtils.getInstance( SecurityScanActivity.this ).showToast( "重新扫描" );
            Intent scan_finish = new Intent( SecurityScanActivity.this, MainActivity_Security.class );
            scan_finish.putExtra( "secu_finish", 0 );
            startActivity( scan_finish );
            finish();


        }
    }

}
