package activity.huafeng.com.myapplication1.activity;

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
import activity.huafeng.com.myapplication1.bean.RegistDriver_CartypeBean;
import activity.huafeng.com.myapplication1.bean.RegistDriver_PlaceBean;
import activity.huafeng.com.myapplication1.bean.RegistManagerBean;
import activity.huafeng.com.myapplication1.util.ToastUtils;
import activity.huafeng.com.myapplication1.util.UrlUtil;
import activity.huafeng.com.myapplication1.util.VerificationUtil;

public class RegistDriverActivity extends BaseActivity {

    private String name_input;
    private String tel_input;
    private String id_input;
    private String cartype_input;
    private String carnum_input;
    private String account_input;
    private String pwd_input;
    private String place_input;

    EditText name,tel,id,carnum,account,pwd;
    NiceSpinner place,cartype;  //车队、车型选择
    private List<RegistDriver_CartypeBean.DataBean> cartypeDataBeanList;
    private List<RegistDriver_PlaceBean.DataBean> placeDataBeanList;

    Button regist;
    ImageView imageView;//小眼睛

    private boolean isHidePwd = true;// 输入框密码是否是隐藏的，默认为true

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_driver);
    }

    public void initView() {

        name=(EditText)findViewById(R.id.regist_driver_et_name);
        tel=(EditText)findViewById(R.id.regist_driver_et_tel);
        id=(EditText)findViewById(R.id.regist_driver_et_id);
        carnum=(EditText)findViewById(R.id.regist_driver_et_carnum);
        cartype=(NiceSpinner) findViewById(R.id.regist_driver_sp_cartype);
        account=(EditText)findViewById(R.id.regist_driver_et_account);
        pwd=(EditText)findViewById(R.id.regist_driver_et_pwd);

        place=(NiceSpinner )findViewById(R.id.regist_driver_sp_place) ;

        imageView=(ImageView) findViewById(R.id.regist_driver_img_eye ) ;
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

        regist =(Button)findViewById(R.id.regist_driver_btn_finish) ;
        regist .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (handleInput()) {


                    requestNet_Registdriver();

                }

            }
        });



    }

    public void initData() {

        requestNet_place();
        placeDataBeanList =new ArrayList<>() ;
        requestNet_cartype();
        cartypeDataBeanList=new ArrayList<>() ;

    }

    //选择车队
    private void requestNet_place() {
        OkGo.<String>get(UrlUtil.URL_Prefix + "Account/GetCd")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        RegistDriver_PlaceBean placeBean = gson.fromJson(response.body(), RegistDriver_PlaceBean.class);
                        if ("1".equals(placeBean.getType())) {// 查询成功
                            placeDataBeanList = placeBean.getData();
                            if (placeDataBeanList.size() > 0) {
                                List<String> list = new ArrayList<>();
                                for (RegistDriver_PlaceBean.DataBean d : placeDataBeanList) {
                                    list.add(d.getCdMc());
                                }

                                place.attachDataSource(list);
                            } else {
                                ToastUtils.getInstance(RegistDriverActivity.this).showToast("所属车队查询为空");
                            }
                        }else{
                            Log.e("eee","查询失败");
                            ToastUtils.getInstance(RegistDriverActivity.this).showToast(placeBean.getMsg());
                        }
                    }
                });
    }

//    选择车型
    private void requestNet_cartype() {
        OkGo.<String>get(UrlUtil.URL_Prefix + "Account/GetCx")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        RegistDriver_CartypeBean placeBean = gson.fromJson(response.body(), RegistDriver_CartypeBean.class);
                        if ("1".equals(placeBean.getType())) {// 查询成功
                            cartypeDataBeanList = placeBean.getData();
                            if (cartypeDataBeanList.size() > 0) {
                                List<String> list = new ArrayList<>();
                                for (RegistDriver_CartypeBean.DataBean d : cartypeDataBeanList) {
                                    list.add(d.getName());
                                }

                                cartype.attachDataSource(list);
                            } else {
                                ToastUtils.getInstance(RegistDriverActivity.this).showToast("所属车队查询为空");
                            }
                        }else{
                            Log.e("eee","查询失败");
                            ToastUtils.getInstance(RegistDriverActivity.this).showToast(placeBean.getMessage());
                        }
                    }
                });
    }

    private void requestNet_Registdriver() {

        OkGo.<String>post(UrlUtil.URL_Prefix + "Account/GetRegisterSj?Sjmc="+name_input+"&Sjdh="+tel_input+"&Zcm="+account_input+"&Ma="+pwd_input+"&CId="+place_input+"&Sfz="+id_input+"&Cph="+carnum_input+"&Cx="+cartype_input)
//                .params("Sjmc", name_input)
//                .params("Sjdh", tel_input)
//                .params("Zcm", account_input)
//                .params("Ma", pwd_input)
//                .params("Sfz", id_input)
//                .params("CId", place_input)
//                .params("Cph", carnum_input)
//                .params("Cx", cartype_input)

                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        Gson gson = new Gson();
                        RegistManagerBean result = gson.fromJson(response.body(), RegistManagerBean.class);
                        if ("1".equals(result.getType())) {// 注册成功
                            ToastUtils.getInstance(RegistDriverActivity.this).showToast("司机注册成功");
                            Intent intent_registdriver = new Intent(RegistDriverActivity.this, LoginActivity.class);
                            startActivity(intent_registdriver);
                            finish();
                        } else  {
                            ToastUtils.getInstance(RegistDriverActivity.this).showToast(result.getMessage());
                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastUtils.getInstance(RegistDriverActivity.this).showToast("注册失败,请稍后重试");
                    }
                });
    }



    private boolean handleInput(){

        if (placeDataBeanList != null && placeDataBeanList.size() == 0) {
            ToastUtils.getInstance(this).showToast("所属车队查询为空");
            return false;
        }
        if (cartypeDataBeanList != null && cartypeDataBeanList.size() == 0) {
            ToastUtils.getInstance(this).showToast("车辆类型查询为空");
            return false;
        }

        name_input = name.getText().toString().trim();
        place_input = placeDataBeanList.get(place .getSelectedIndex()).getId()+"";
        tel_input = tel.getText().toString().trim();
        account_input = account.getText().toString().trim();
        pwd_input = pwd.getText().toString().trim();
        carnum_input = carnum.getText().toString().trim();
        cartype_input = cartypeDataBeanList.get(cartype .getSelectedIndex()).getId()+"";
        id_input = id.getText().toString().trim();


        if("".equals(name_input)){
            ToastUtils.getInstance(this).showToast("请输入司机姓名");
            return false;
        }

        if("".equals(place_input)){
            ToastUtils.getInstance(this).showToast("请选择所属车队");
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

        if("".equals(id_input)){
            ToastUtils.getInstance(this).showToast("请输入身份证号");
            return false;
        } else{
            if(!VerificationUtil.judgeIDCard(id_input)){
                ToastUtils.getInstance(this).showToast("请输入正确的身份证号");
                return false;
            }
        }

        if("".equals(carnum_input)){
            ToastUtils.getInstance(this).showToast("请输入车牌号");
            return false;
        } else{
            if(!VerificationUtil.judgeCPH(carnum_input)){
                ToastUtils.getInstance(this).showToast("请输入正确的车牌号");
                return false;
            }
        }

        if("".equals(cartype_input)){
            ToastUtils.getInstance(this).showToast("请选择车辆类型");
            return false;
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
