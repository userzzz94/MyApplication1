package activity.huafeng.com.myapplication1.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.Toast;

import com.alibaba.sdk.android.push.CloudPushService;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.ArrayList;
import java.util.List;

import activity.huafeng.com.myapplication1.R;
import activity.huafeng.com.myapplication1.base.BasePager;
import activity.huafeng.com.myapplication1.base.impl.Manager_FindCar;
import activity.huafeng.com.myapplication1.base.impl.Manager_Message;
import activity.huafeng.com.myapplication1.base.impl.Manager_Mine;
import activity.huafeng.com.myapplication1.base.impl.Manager_Order;
import activity.huafeng.com.myapplication1.util.ToastUtils;
import activity.huafeng.com.myapplication1.view.BadgeView;

public class MainActivity_Manager extends BaseActivity {

    private static final String TAG = "MainActivity_Manager";
    //记录双击退出应用的第一次点击时间.
    private long firstTime = 0;

    //定义一个全局的静态常量
    private static final int REQUEST_CODE = 001;
    private static final int REQUEST_SCAN = 7;
    private static final int requestCode=002;
    private ViewPager viewPager;
    private RadioGroup radioGroup;
    private List<BasePager> pagerList;
    private RadioButton massage;
    private RadioButton findCar;
    private RadioButton order;
    private RadioButton mine;

    private CloudPushService mPushService;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_manager);
//        Toolbar toolbar=(Toolbar) findViewById(R.id.manager_toolbar);
//        setSupportActionBar(toolbar);



    }

    public void initView() {

        viewPager = findViewById(R.id.activity_main_manager_viewpager);
        radioGroup = findViewById(R.id.activity_main_manager_radiogroup);

        //查找该页面下的radiobutton , 为了解决drawtop的图片在xml中不能设置大小的问题
        massage  = findViewById(R.id.activity_main_manager_message );
        findCar = findViewById(R.id.activity_main_manager_findcar);
        order = findViewById(R.id.activity_main_manager_search);
        mine = findViewById(R.id.activity_main_manager_mine);


//        //覆盖在RadioGroup之上LinearLayout的第一个占位子布局
//
//        Button button1 = (Button) findViewById(R.id.btn_msg);
//        remind(button1);
//
//        //覆盖在RadioGroup之上LinearLayout的第二个占位子布局
//        Button button2 = (Button) findViewById(R.id.btn_my);
//        remind(button2);

        //设置加载时, drawtop图片无法设置大小的问题
        setDrawTopImageSize();

       // TaskDialog.actionStart(this) ;

    }

    public void initData() {

        pagerList = new ArrayList<>();

        pagerList.add(new Manager_FindCar(this));
        pagerList.add(new Manager_Message(this));
        pagerList.add(new Manager_Order(this));
        pagerList.add(new Manager_Mine(this));


        initListener();// 这里必须在pagerList之后初始化监听,因为pagerAdapter调用 pagerList的size()方法, 为避免空指针,在这里初始化监听.

        radioGroup.check(R.id.activity_main_manager_findcar);// 在第一次进首页面的时候选中第一个rediobutton
        pagerList.get(0).initData();// 初始化第一个radiobutton所对应页面的initdata方法.

    }

    private void remind(View view) {//BadgeView的具体使用
        BadgeView badge1 = new BadgeView(this, view);// 创建一个BadgeView对象，view为你需要显示提醒的控件
        badge1.setText("12"); // 需要显示的提醒类容
        badge1.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);// 显示的位置.右上角,BadgeView.POSITION_BOTTOM_LEFT,下左，还有其他几个属性
        badge1.setTextColor( Color.WHITE); // 文本颜色
        badge1.setBadgeBackgroundColor(Color.RED); // 提醒信息的背景颜色，自己设置
        badge1.setBackgroundResource(R.drawable.red); //设置背景图片
        badge1.setTextSize(12); // 文本大小
        badge1.setBadgeMargin(3, 3); // 水平和竖直方向的间距
        badge1.setBadgeMargin(5); //各边间隔
        badge1.toggle(); //显示效果，如果已经显示，则影藏，如果影藏，则显示
        badge1.show();// 只有显示
        badge1.hide();//影藏显示
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
                    case R.id.activity_main_manager_findcar:
                        viewPager.setCurrentItem(0,false);

                        Intent intent_findcar = getIntent();
                        int findcar = intent_findcar.getIntExtra("findcar",0);
                        viewPager .setCurrentItem(findcar) ;

                        break;
                    case R.id.activity_main_manager_message:
                        viewPager.setCurrentItem(1,false);

                        Intent intent_message = getIntent();
                        int message = intent_message.getIntExtra("message",1);
                        viewPager .setCurrentItem(message) ;

                        break;

                    case R.id.activity_main_manager_search:
                        viewPager.setCurrentItem(2,false);
                        break;

                    case R.id.activity_main_manager_mine:
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




    /**
     * 设置页面加载时 drowTop的图片没法设置大小的问题
     */
    @SuppressLint("ResourceType")
    private void setDrawTopImageSize(){
        Drawable message_d = getResources().getDrawable(R.drawable .selector_activity_main_message);
        Drawable findCar_d = getResources().getDrawable(R.drawable.selector_activity_main_car);
        Drawable order_d = getResources().getDrawable(R.drawable .selector_activity_main_home);
        Drawable mine_d = getResources().getDrawable(R.drawable .selector_activity_main_user);

        message_d.setBounds(0,0,70,70);
        findCar_d.setBounds(0,0,70,70);
        order_d.setBounds(0,0,70,70);
        mine_d.setBounds(0,0,70,70);


        massage .setCompoundDrawables(null,message_d,null,null);
        findCar.setCompoundDrawables(null,findCar_d,null,null);
        order.setCompoundDrawables(null,order_d,null,null);
        mine.setCompoundDrawables(null,mine_d,null,null);

    }


//    @Override
//
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Log .e("scan","onActivityResult");
//        super.onActivityResult(requestCode, resultCode, data);
//
//            if (requestCode == REQUEST_SCAN && resultCode == RESULT_OK) {
//                Log .e("scan","RESULT_OK");
//
//                Bundle bundle = data.getExtras();
//                //这是拿到解析扫描到的信息，并转成字符串
//                String result = bundle.getString(CodeUtils.RESULT_STRING);
//                //解析扫到的二维码后就跳转页面;
//                Intent intent =new Intent(this , ManagerScanActivity.class) ;
//
//                //把扫到并解析到的信息(既:字符串)带到详情页面
//                intent.putExtra("m_string", result);
//
//                startActivity(intent );
//                finish() ;
//
//            }
//            else {
//                // 数据返回
//                Toast.makeText(this, "解析失败", Toast.LENGTH_LONG).show();
//
//        }

        //扫描回传值
            protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                Log .e("scan","onActivityResult");
                super.onActivityResult(requestCode, resultCode, data);
                if (requestCode==REQUEST_CODE) {
                    if (data == null) {

                        Toast.makeText( this, "扫描二维码失败:", Toast.LENGTH_LONG ).show();

                    }else {
                        Bundle bundle = data.getExtras();
                        Log.e( "scan", "requestCode" );
                        if (bundle == null) {
                            Log.e( "scan", String.valueOf( bundle ) );
                            return;

                        }
                        if (bundle.getInt( CodeUtils.RESULT_TYPE ) == CodeUtils.RESULT_SUCCESS) {
                            Log.e( "scan", "RESULT_OK" );

                            //这是拿到解析扫描到的信息，并转成字符串
                            String result = bundle.getString( CodeUtils.RESULT_STRING );
                            //                绑定二维码信息

                            //解析扫到的二维码后就跳转页面;
                            Intent intent = new Intent( this, ManagerScanActivity.class );

                            //                            Uri content_url = Uri.parse(result.toString());
                            //                            intent.setData(content_url);

                            //把扫到并解析到的信息(既:字符串)带到详情页面
                            intent.putExtra( "m_string", result );

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
                ToastUtils.getInstance(MainActivity_Manager .this).showToast("再按一次退出程序");
                firstTime = secondTime;
                return true;
            } else {
                finish();
            }
        }

        return super.onKeyUp(keyCode, event);
    }



}
