package activity.huafeng.com.myapplication1.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.List;

import activity.huafeng.com.myapplication1.R;
import activity.huafeng.com.myapplication1.bean.RegistManagerBean;
import activity.huafeng.com.myapplication1.bean.RegistManager_PlaceBean;
import activity.huafeng.com.myapplication1.util.ToastUtils;
import activity.huafeng.com.myapplication1.util.UrlUtil;
import activity.huafeng.com.myapplication1.util.VerificationUtil;

public class RegistManagerActivity extends BaseActivity {

    private String name_input;
    private String tel_input;
    private String account_input;
    private String pwd_input;
    private String place_input;

    EditText name,tel,account,pwd;
    NiceSpinner place;  //矿点选择
    Button regist;
    ImageView imageView;//小眼睛

    private boolean isHidePwd = true;// 输入框密码是否是隐藏的，默认为true

    private List <RegistManager_PlaceBean.DataBean> placeDataBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_manager);
        Log.e("eee","onCreate");
        initView();
        initData();
    }

    @SuppressLint("ClickableViewAccessibility")
    public void initView() {
        Log.e("eee","initView");
        name =(EditText) findViewById(R.id.regist_manager_et_name) ;
        tel =(EditText) findViewById(R.id.regist_manager_et_tel) ;
        account =(EditText) findViewById(R.id.regist_manager_et_account) ;
        place =(NiceSpinner)  findViewById(R.id.regist_manager_sp_place) ;

        pwd =(EditText) findViewById(R.id.regist_manager_et_pwd) ;
        imageView=(ImageView) findViewById(R.id.regist_manager_img_eye ) ;
        imageView.setImageResource(R.drawable.icon_eye_close);

        imageView .setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isHidePwd == true) {
                    imageView.setImageResource(R.drawable.icon_eye_open);
                    //密文
                    HideReturnsTransformationMethod method1 = HideReturnsTransformationMethod.getInstance();
                    pwd.setTransformationMethod(method1);
                    isHidePwd = false;
                } else {
                    imageView.setImageResource(R.drawable.icon_eye_close);
                    //密文
                    TransformationMethod method = PasswordTransformationMethod.getInstance();
                    pwd.setTransformationMethod(method);
                    isHidePwd = true;

                }
                // 光标的位置
                int index = pwd.getText().toString().length();
                pwd.setSelection(index);


            }
        } );
        


        regist =(Button )findViewById(R.id.regist_manager_btn_finish) ;
        regist .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (handleInput()) {

                    requestNet_Registmanager();

                }
            }
        });

    }

    public void initData() {
        Log.e( "eee", "initData" );
        placeDataBeanList = new ArrayList <>();
        requestNet_place();

    }


    private void requestNet_place() {
        OkGo.<String>get(UrlUtil.URL_Prefix + "Account/GetKd")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        Gson gson = new Gson();
                        RegistManager_PlaceBean placeBean = gson.fromJson(response.body(), RegistManager_PlaceBean.class);
                        if ("1".equals(placeBean.getType())) {// 查询成功
                            Log.e("eee","查询成功");
                            placeDataBeanList = placeBean.getData();
                            if (placeDataBeanList.size() > 0) {
                                Log.e("eee","placeDataBeanList.size() > 0");
                                List<String> list = new ArrayList<>();
                                for (RegistManager_PlaceBean.DataBean d : placeDataBeanList) {
                                    list.add(d.getKdmc());
                                }
                                Log.e("eee",list.size() + "");
                                place.attachDataSource(list);
                            } else {
                                ToastUtils.getInstance(RegistManagerActivity.this).showToast("所属矿点查询为空");
                            }
                        }else{
                            Log.e("eee","查询失败");
                            ToastUtils.getInstance(RegistManagerActivity.this).showToast(placeBean.getMsg());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.e("eee","onError");
                    }
                });
    }

    private void requestNet_Registmanager() {

        OkGo.<String>post(UrlUtil.URL_Prefix + "Account/GetRegisterCd?CdMc="+name_input+"&CdDh="+tel_input+"&Zcmc="+account_input+"&Ma="+pwd_input+"&KId="+place_input)

                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        Gson gson = new Gson();
                        RegistManagerBean result = gson.fromJson(response.body(), RegistManagerBean.class);
                        if ("1".equals(result.getType())) {// 注册成功
                            ToastUtils.getInstance(RegistManagerActivity.this).showToast("车队注册成功");
                            Intent intent_registmanager = new Intent(RegistManagerActivity.this, LoginActivity.class);
                            startActivity(intent_registmanager);
                            finish();
                        } else  {
                            ToastUtils.getInstance(RegistManagerActivity.this).showToast(result.getMessage());
                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastUtils.getInstance(RegistManagerActivity.this).showToast("注册失败,请稍后重试");
                    }
                });
    }



    private boolean handleInput(){

        if (placeDataBeanList != null && placeDataBeanList.size() == 0) {
            ToastUtils.getInstance(this).showToast("所属矿点查询为空");
            return false;
        }

         name_input = name.getText().toString().trim();
         place_input = placeDataBeanList.get(place .getSelectedIndex()).getId()+"";
         tel_input = tel.getText().toString().trim();
         account_input = account.getText().toString().trim();
         pwd_input = pwd.getText().toString().trim();


        if("".equals(name_input)){
            ToastUtils.getInstance(this).showToast("请输入车队名称");
            return false;
        }

        if("".equals(place_input)){
            ToastUtils.getInstance(this).showToast("请选择所属矿点");
            return false;
        }

        if("".equals(tel_input)){
            ToastUtils.getInstance(this).showToast("请输入联系方式");
            return false;
        } else{
            if(!VerificationUtil.judgePhoneNums(tel_input)){
                ToastUtils.getInstance(this).showToast("请输入正确的手机号");
                return false;
            }
        }
        if("".equals(account_input)){
            ToastUtils.getInstance(this).showToast("请输入用户名");
            return false;
        }
        if("".equals(pwd_input)){
            ToastUtils.getInstance(this).showToast("请输入登录密码");
            return false;
        } else if(VerificationUtil.judgePassword(pwd_input)!=1){

                ToastUtils.getInstance(this).showToast("请输入正确格式的密码");
                return false;

        }

        return true;
    }


}
