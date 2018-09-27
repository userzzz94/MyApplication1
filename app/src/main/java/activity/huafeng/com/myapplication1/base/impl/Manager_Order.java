package activity.huafeng.com.myapplication1.base.impl;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioGroup;

import java.util.List;

import activity.huafeng.com.myapplication1.R;
import activity.huafeng.com.myapplication1.activity.BaseActivity;
import activity.huafeng.com.myapplication1.base.BasePager;
import activity.huafeng.com.myapplication1.fragment.manager_search.Fragment_Manager_Search_DDFragment;
import activity.huafeng.com.myapplication1.fragment.manager_search.Fragment_Manager_Search_ZTFragment;

/**
 * Created by leovo on 2018-08-24.
 */

public class Manager_Order extends BasePager {

    private static final String TAG = "Manager_Order";
    private int frameLayout;
    private RadioGroup radioGroup;
    private Fragment_Manager_Search_DDFragment ddFragment;
    private Fragment_Manager_Search_ZTFragment ztFragment;
    private View thisRootView;
    private boolean isInited = false;


    public Manager_Order(BaseActivity activity) {
        super(activity);
    }

    public void initView(){

        if(isInited){
            rootView = thisRootView;
        }else {
            thisRootView = View.inflate(mActivity, R.layout.pager_manager_order,null);
            frameLayout = R.id.pager_manager_search_framelayout;// 拿到填充的父窗体
            radioGroup = thisRootView.findViewById(R.id.pager_manager_search_radiogroup);// 拿到radiogroup

            //在初始化布局的时候, 就要初始化需要显示的4个framgnet的布局,因为不在这里初始化, 在initData初始化的话,
            // 每一次点击屏幕底下的按钮,在切回来 都会重新初始化 4个fragment的布局、数据等, 就会非常浪费资源.
            init_Fragment();

            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    switch (i){
                        case R.id.pager_manager_search_dingdan:
                            showFragment(ddFragment,"ddFragment",false);
                            ddFragment.initData();
                            break;
                        case R.id.pager_manager_search_zaitu:
                            showFragment(ztFragment,"ztFragment",false);
                            ztFragment.initData();
                            break;
                    }
                }
            });



            rootView = thisRootView;
            isInited = true;
        }

    }

    /**
     * 初始化4个fragment的方法
     */
    private void init_Fragment(){
        ddFragment = new Fragment_Manager_Search_DDFragment(mActivity);
        ztFragment = new Fragment_Manager_Search_ZTFragment(mActivity);

    }

    public void initData() {

        //        拿到当前选中的是什么按钮, 初始化对应的fragment.
        int checkId = radioGroup.getCheckedRadioButtonId();
        switch (checkId) {
            case R.id.pager_manager_search_dingdan: // 订单
                showFragment( ddFragment, "ddFragment", false );
                ddFragment.initData();
                break;
            case R.id.pager_manager_search_zaitu:// 在途
                showFragment( ztFragment, "ztFragment", false );
                ztFragment.initData();
                break;

        }
    }


        /**
         * activity中将需要替换的id 替换为fragment 的方法. 前三个参数为replace方法自带的参数, 最后一个参数, 为是否需要添加到返回栈.
         */
    public void showFragment(Fragment fragment, String tag, boolean isAddBackStack){
        FragmentTransaction transaction = mActivity.getSupportFragmentManager().beginTransaction();
        List<Fragment> listFragment = mActivity.getSupportFragmentManager().getFragments();
        //        transaction.replace(fragmentContainer,fragment,tag);
        if(fragment.isAdded()){
            for(Fragment f : listFragment){
                transaction.hide(f);
            }
            transaction.show(fragment);
        }else{
            transaction.add(frameLayout,fragment,tag);
            for(Fragment f : listFragment){
                transaction.hide(f);
            }
            transaction.show(fragment);
        }

        if(isAddBackStack){
            transaction.addToBackStack(null);// 将这个fragment添加到返回栈中
        }
        transaction.commit();


    }
    

}
