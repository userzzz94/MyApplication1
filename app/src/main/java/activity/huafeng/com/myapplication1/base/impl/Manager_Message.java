package activity.huafeng.com.myapplication1.base.impl;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.orhanobut.hawk.Hawk;
import com.uuzuche.lib_zxing.activity.CaptureActivity;

import java.util.ArrayList;
import java.util.List;

import activity.huafeng.com.myapplication1.R;
import activity.huafeng.com.myapplication1.activity.BaseActivity;
import activity.huafeng.com.myapplication1.base.BasePager;
import activity.huafeng.com.myapplication1.bean.LoginBean_Manager;
import activity.huafeng.com.myapplication1.bean.Manager_MessageBean;
import activity.huafeng.com.myapplication1.util.ToastUtils;
import activity.huafeng.com.myapplication1.util.UrlUtil;

/**
 * Created by leovo on 2018-08-24.
 */

public class Manager_Message extends BasePager {


    private static final int REQUEST_CODE=001;
    private static final int REQUEST_SCAN=9;
    
    XRecyclerView recyclerView ;
    MyRecycleAdapter adapter ;

    List<Manager_MessageBean .Message> list;

    private Manager_MessageBean bean;
    private String idrw;

    private int mPage ;


    private View thisRootView;
    private boolean isInited = false;

    public Manager_Message(BaseActivity activity) {
        super(activity);

        
    }

    public void initView(){

        if(isInited){
            rootView = thisRootView;
        }else {
            thisRootView = View.inflate(mActivity , R.layout .pager_manager_message,null);

        //xRecycleView
        recyclerView = thisRootView.findViewById(R.id.pager_manager_message_recycleview);

        // 配置xRecycleView
        configurationXRecyclerView();

            rootView = thisRootView;
            isInited = true;
        }
    }

    public void initData(){

        recyclerView .refresh() ;


    }

    //配置xRecycleView
    private void configurationXRecyclerView() {

        //设置RecyclerView管理器
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));

        //设置添加或删除item时的动画，这里使用默认动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //先将list 初始化,在给适配器调用
         list = new ArrayList<>();


        adapter = new MyRecycleAdapter(list);
        recyclerView.setAdapter(adapter);


        //刷新
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh(){
                mPage = 1;
                request_message(mPage);
                recyclerView.setLoadingMoreEnabled(true);
            }

            @Override
            public void onLoadMore() {
                mPage += 1;
                request_message(mPage);

            }
        });



    }

    //xRecycleView 的适配器类
    public class MyRecycleAdapter extends XRecyclerView.Adapter<MyRecycleAdapter.MyHolder> {
        List<Manager_MessageBean.Message> list;

        public MyRecycleAdapter(List list) {
            this.list = list;
        }

        @Override
        public MyRecycleAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pager_manager_message, parent, false);

            MyRecycleAdapter.MyHolder holder = new MyRecycleAdapter.MyHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final MyRecycleAdapter.MyHolder holder, final int position) {

            holder .num .setText(list .get(position ).getRwbh() ) ;
            holder .name.setText(list .get(position ).getSjxm() ) ;
            holder.carnum .setText(list .get(position ).getCph() ) ;
            holder .tel .setText(list .get(position ).getSjdh() ) ;
            holder .kd .setText(list .get(position ) .getKd()) ;
            //holder .time.setText(list .get(position ).getLrsj() );
            
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //ToastUtils.getInstance(mActivity).showToast("第 " + position + " 个条目被点击了");

                    List<Manager_MessageBean.Message> msg=bean.getData();
                    idrw=msg .get(position ).getId();
                    if(idrw==null){
                        Log.d("idrw1","null");
                    }else{
                        Log.d("idrw1",idrw);
                    }
                    Hawk.put("scanrw",idrw) ;

                    String sjidScan=msg.get(position ) .getSjId();
                    Hawk .put("sjidScan",sjidScan) ;

                    String sjxm=msg .get(position ).getSjxm();
                    Hawk.put("sjxm",sjxm) ;

                    String sjdh=msg .get(position ).getSjdh();
                    Hawk.put("sjdh",sjdh) ;

                    String sjkd=msg .get(position ).getKd();
                    Hawk.put("sjkd",sjkd) ;

                    String cph=msg .get(position ).getCph();
                    Hawk.put("cph",cph) ;

                    getRuntimeRight();

                }
            });

        }



        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyHolder extends XRecyclerView.ViewHolder {

            TextView name;//司机姓名
            TextView tel;// 联系电话
            TextView carnum;//车牌号
            TextView num;//任务单号
            TextView time;
            TextView kd;


            public MyHolder(View itemView) {

                super(itemView);

                name =itemView .findViewById(R.id.message_name) ;
                num=itemView .findViewById(R.id.message_number) ;
                carnum =itemView .findViewById(R.id.message_carnum) ;
                tel=itemView .findViewById(R.id.message_tel) ;
                kd=itemView .findViewById(R.id.message_kd) ;
                //time=itemView .findViewById(R.id.message_time) ;

            }
        }
    }

    /**

     * 获得运行时权限

     */

    private void getRuntimeRight() {

        if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.CAMERA}, 1);

        } else {

            jumpScanPage();

        }

    }
    



    /**

     * 跳转到扫码页

     */

    private void jumpScanPage() {
        Log .e("scan","jumpScanPage");
        Intent intent_scan=new Intent(mActivity, CaptureActivity.class);
        mActivity.startActivityForResult(intent_scan  ,REQUEST_CODE);

        Log .e("scan","startActivityForResult");
    }


    private void request_message(final int currPage){
        LoginBean_Manager.DataManager  manager  = Hawk.get("r_manager");
        String m_token=Hawk .get("t_manager");
        OkGo.<String>get(UrlUtil.URL_Prefix + "Task/GetZcwc")
                .params("CdId",manager.getId())
                .params("Token", m_token)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if(currPage == 1){
                            recyclerView.refreshComplete();// 告诉适配器, 刷新完成
                        }else{
                            recyclerView.loadMoreComplete();// 告诉适配器, 加载更多完成
                        }
                        Gson gson = new Gson();
                        bean = gson.fromJson(response.body(), Manager_MessageBean.class);
                        String code = bean.getCode();
                        if("1".equals(code)){// 查询成功
                            List<Manager_MessageBean.Message> data = bean.getData();

                            if(data != null && data.size() != 0){// 当获取的数据不为空 的时候.

                                if(currPage == 1){// 当是下拉刷新的时候,需要将list中的数据清空.
                                    list.clear();
                                }
                                //将读取到的数据, 赋值给list, 适配器将显示list中的数据.
                                list.addAll(data);
                                //通知适配器刷新, 不调用这个方法, 虽然list的数据更新了, 但页面确是没反应的,此方法执行后, 页面也会刷新.
                                adapter.notifyDataSetChanged();

                            }else{// 查询结果为null的情况.
                                if(currPage == 1){

                                    list.clear();
                                    adapter.notifyDataSetChanged();
                                    ToastUtils.getInstance(mActivity).showToast("暂无消息!");
                                    recyclerView.setLoadingMoreEnabled(false);// 禁用上拉加载, 因为本次都没有获取数据, 就没有可以上拉加载的必要了.
                                }else{
                                    adapter.notifyDataSetChanged();
                                    ToastUtils.getInstance(mActivity).showToast("无最新消息!");
                                    mPage = mPage - 1;
                                    recyclerView.setLoadingMoreEnabled(false);
                                }
                            }
                        }else{

                            ToastUtils.getInstance(mActivity).showToast(bean.getMsg());
                        }
                    }

                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (mPage ==1){
                            recyclerView .refreshComplete() ;
                        } else {
                            recyclerView .loadMoreComplete() ;
                        }
                        ToastUtils .getInstance(mActivity).showToast("异常");
                    }
                });

    }


}
