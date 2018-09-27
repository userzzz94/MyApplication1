package activity.huafeng.com.myapplication1.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.ArrayList;
import java.util.List;

import activity.huafeng.com.myapplication1.R;
import activity.huafeng.com.myapplication1.base.BasePager;
import activity.huafeng.com.myapplication1.base.impl.Security_Search;
import activity.huafeng.com.myapplication1.base.impl.Security_Order;
import activity.huafeng.com.myapplication1.util.ToastUtils;

public class MainActivity_Security extends BaseActivity {

    private static final String TAG = "MainActivity_Security";
    //记录双击退出应用的第一次点击时间.
    private long firstTime = 0;

    private ViewPager viewPager;
    private RadioGroup radioGroup;
    private List<BasePager> pagerList;
    private RadioButton order;
    private RadioButton mine;

    private static final int REQUEST_CODE=7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_security);
    }

    public void initView() {

        viewPager = findViewById(R.id.activity_main_security_viewpager);
        radioGroup = findViewById(R.id.activity_main_security_radiogroup);

        //查找该页面下的radiobutton , 为了解决drawtop的图片在xml中不能设置大小的问题
        order = findViewById(R.id.activity_main_security_order );
        mine = findViewById(R.id.activity_main_security_mine);

        //设置加载时, drawtop图片无法设置大小的问题
        setDrawTopImageSize();

        //TaskDialog.actionStart(this) ;

    }

    public void initData() {

        pagerList = new ArrayList<>();

        pagerList.add(new Security_Order(this) );
        pagerList.add(new Security_Search(this));


        initListener();// 这里必须在pagerList之后初始化监听,因为pagerAdapter调用 pagerList的size()方法, 为避免空指针,在这里初始化监听.

        radioGroup.check(R.id.activity_main_security_order);// 在第一次进首页面的时候选中第一个rediobutton
        pagerList.get(0).initData();// 初始化第一个radiobutton所对应页面的initdata方法.

    }

    /**
     * 初始化监听
     */
    private void initListener() {
        //设置viewpager的缓存页面, 如果离开过远,页面就destroy掉了, 因为在第一个页面引入了三方控件, 如果把第一个页面destroy掉在还原回来(已经不走initView方法了)
        //会在三方控件内出现空指针异常
        viewPager.setOffscreenPageLimit(2);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.activity_main_security_order:
                        viewPager.setCurrentItem(0,false);
                        
                        Intent intent_findcar = getIntent();
                        int findcar = intent_findcar.getIntExtra("secu_finish",0);
                        viewPager .setCurrentItem(findcar) ;

                        break;
                    case R.id.activity_main_security_mine:
                        viewPager.setCurrentItem(1,false);
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




    /**
     * 设置页面加载时 drowTop的图片没法设置大小的问题
     */
    @SuppressLint("ResourceType")
    private void setDrawTopImageSize(){

        Drawable order_d = getResources().getDrawable(R.drawable .selector_activity_main_home);
        Drawable mine_d = getResources().getDrawable(R.drawable .selector_activity_main_message);

        order_d.setBounds(0,0,70,70);
        mine_d.setBounds(0,0,70,70);

        order.setCompoundDrawables(null,order_d,null,null);
        mine.setCompoundDrawables(null,mine_d,null,null);
    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log .e("scan","onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==REQUEST_CODE) {

            if (data == null) {

                Toast.makeText( this, "扫描二维码失败:", Toast.LENGTH_LONG ).show();

            }else {

                Bundle bundle = data.getExtras();
                Log.e( "scan", "requestCode" );
                if (bundle == null) {

                    Toast.makeText( this, "未查询到与此二维码绑定的订单信息", Toast.LENGTH_LONG ).show();
                    return;
                }
                if (bundle.getInt( CodeUtils.RESULT_TYPE ) == CodeUtils.RESULT_SUCCESS) {
                    Log.e( "scan", "RESULT_OK" );

                    //这是拿到解析扫描到的信息，并转成字符串
                    String result = bundle.getString( CodeUtils.RESULT_STRING );

                    //解析扫到的二维码后就跳转页面;
                    Intent intent = new Intent( this, SecurityScanActivity.class );

                    //                Uri content_url = Uri.parse(result.toString());
                    //                intent.setData(content_url);

                    //把扫到并解析到的信息(既:字符串)带到详情页面
                    intent.putExtra( "s_string", result );

                    startActivity( intent );
                    finish();

                } else if (bundle.getInt( CodeUtils.RESULT_TYPE ) == CodeUtils.RESULT_FAILED) {
                    Log.e( "scan", "RESULT_CANCELED" );
                    //否则提示解析二维码失败
                    Toast.makeText( this, "解析二维码失败:", Toast.LENGTH_LONG ).show();

                }
            }
        }



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
                ToastUtils.getInstance(MainActivity_Security.this).showToast("再按一次退出程序");
                firstTime = secondTime;
                return true;
            } else {
                finish();
            }
        }

        return super.onKeyUp(keyCode, event);
    }



}
