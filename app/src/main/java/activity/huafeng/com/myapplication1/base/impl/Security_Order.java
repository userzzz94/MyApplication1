package activity.huafeng.com.myapplication1.base.impl;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.uuzuche.lib_zxing.activity.CaptureActivity;

import activity.huafeng.com.myapplication1.R;
import activity.huafeng.com.myapplication1.activity.BaseActivity;
import activity.huafeng.com.myapplication1.base.BasePager;

/**
 * Created by leovo on 2018-08-24.
 */

public class Security_Order extends BasePager {

    private static final int REQUEST_CODE=7;


    private Button sure;
    TextView consoleText;

    private View thisRootView;
    private boolean isInited = false;

    public Security_Order(BaseActivity activity) {
        super(activity);
//        MyApplication.setMain_SActivity(this);
        
    }

    public void appendConsoleText(String text) {

        this.consoleText.append(text + "\n");

    }

    public void initView(){

        if(isInited){
            rootView = thisRootView;
        }else {
            thisRootView = View.inflate(mActivity , R.layout .pager_security_order,null);

        consoleText=thisRootView .findViewById(R.id.security_tv_consoleText) ;

        sure =(Button )thisRootView .findViewById(R.id.pager_security_order_btn) ;
        sure .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.CAMERA}, 1);

                } else {

                    jumpScanPage();

                };
            }

        }) ;



            rootView = thisRootView;
            isInited = true;
        }
    }

    public void initData(){

    }


    @Override

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {

            case 1:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    jumpScanPage();

                } else {

                    Toast.makeText(mActivity, "拒绝", Toast.LENGTH_LONG).show();

                }

            default:

                break;

        }

    }

    private void jumpScanPage() {
        Log .e("scan","jumpScanPage");
        Intent intent_scan=new Intent(mActivity, CaptureActivity.class);
        mActivity.startActivityForResult(intent_scan  ,REQUEST_CODE);

        Log .e("scan","startActivityForResult");
    }






}