package activity.huafeng.com.myapplication1.base.impl;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.sdk.android.push.CloudPushService;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import activity.huafeng.com.myapplication1.R;
import activity.huafeng.com.myapplication1.activity.BaseActivity;
import activity.huafeng.com.myapplication1.activity.ManagerSureActivity;
import activity.huafeng.com.myapplication1.base.BasePager;
import activity.huafeng.com.myapplication1.bean.LoginBean_Manager;
import activity.huafeng.com.myapplication1.bean.Manager_FindCarBean;
import activity.huafeng.com.myapplication1.util.ToastUtils;
import activity.huafeng.com.myapplication1.util.UrlUtil;


/**
 * Created by leovo on 2018-08-24.
 */

public class Manager_FindCar extends BasePager {

    XRecyclerView recyclerView;
    MyRecycleAdapter adapter;
    List<Manager_FindCarBean .OrderInfo > list;

    TextView consoleText;
    private int mPage ;

    private Manager_FindCarBean bean;
    private String idrw;
    private CloudPushService mPushService;

    private View thisRootView;
    private boolean isInited = false;

    public Manager_FindCar(BaseActivity activity) {
        super(activity);
//        MyApplication.setMain_Activity(this);
//        mPushService = PushServiceFactory.getCloudPushService();


    }

    public void appendConsoleText(String text) {

        this.consoleText.append(text + "\n");

    }

    public void initView() {

        if(isInited){
            rootView = thisRootView;
        }else {
            thisRootView = View.inflate(mActivity, R.layout.pager_manager_findcar, null);

        consoleText=thisRootView .findViewById(R.id.tv_manager_consoleText) ;

        //xRecycleView
        recyclerView = thisRootView.findViewById(R.id.pager_manager_findcar_recycleview);

        // 配置xRecycleView
        configurationXRecyclerView();



        rootView = thisRootView;
        isInited = true;
    }

    }

    public void initData() {

        

    }



    //配置xRecycleView
    private void configurationXRecyclerView() {

        //设置RecyclerView管理器
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));

        //设置添加或删除item时的动画，这里使用默认动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.setRefreshProgressStyle( ProgressStyle.BallPulseRise);//可以自定义下拉刷新的样式

        //先将list 初始化,在给适配器调用
        list = new ArrayList<>();


        adapter = new MyRecycleAdapter(list);
        recyclerView.setAdapter(adapter);

        //刷新
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh(){
                mPage = 1;
                request_newOrder(mPage);
                recyclerView.setLoadingMoreEnabled(true);
            }

            @Override
            public void onLoadMore() {
                mPage += 1;
                request_newOrder(mPage);

            }
        });

        recyclerView.refresh();

    }


    //xRecycleView 的适配器类
    public class MyRecycleAdapter extends XRecyclerView.Adapter<MyRecycleAdapter.MyHolder> {
        List<Manager_FindCarBean .OrderInfo > list;

        public MyRecycleAdapter(List list) {
            this.list = list;
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pager_manager_findcar, parent, false);

            MyHolder holder = new MyHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final MyHolder holder, final int position) {


            holder .num.setText(list .get(position ).getDh());
            holder.qsd.setText(list .get(position ) .getQsd());
            holder .zdd.setText(list .get(position ).getZdd());
            holder.cargoname.setText(list .get(position ) .getHwmc());
            holder.cargotype.setText(list .get(position) .getDw());

            
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    ToastUtils.getInstance(mActivity).showToast("第 " + position + " 个条目被点击了");
//
//
//                }
//            });
            holder.sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    List<Manager_FindCarBean.OrderInfo > datarw = bean.getData();
                    Hawk .put("taskId",datarw .get(position ).getRwId()) ;

                    Intent intent_sure =new Intent(mActivity , ManagerSureActivity.class) ;
                    mActivity .startActivity(intent_sure );


                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyHolder extends XRecyclerView.ViewHolder {
            TextView num;//计划单号
            TextView qsd;// 起始地文本
            TextView zdd;// 终到地文本
            TextView cargoname;//货物名称
            TextView cargotype;// 重量
            TextView sure;

            public MyHolder(View itemView) {
                
                super(itemView);
                sure = itemView.findViewById(R.id.pager_manager_findcar_sure);


                //在这里查找holder中的控件.
                num=itemView .findViewById(R.id.pager_manager_findcar_num) ;
                qsd = itemView.findViewById(R.id.pager_manager_findcar_qsd_textview);
                zdd = itemView.findViewById(R.id.pager_manager_findcar_zdd_textview);
                cargoname = itemView.findViewById(R.id.pager_manager_findcar_cargoname);
                cargotype=itemView.findViewById(R.id.pager_manager_findcar_cargoweight);
                

            }
        }


    }

    //车队获取任务订单消息
    private void request_newOrder(final int currPage){
        LoginBean_Manager.DataManager manager  = Hawk.get("r_manager");
        String m_token=Hawk .get("t_manager");
        Log.d("eee",m_token)  ;
        OkGo.<String>get(UrlUtil.URL_Prefix + "Task/GetCdRws" )
                .params("CdId",manager.getId())
                .params("Token", m_token)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("eee","onSuccess");
                        if(currPage == 1){
                            recyclerView.refreshComplete();// 告诉适配器, 刷新完成
                        }else{
                            recyclerView.loadMoreComplete();// 告诉适配器, 加载更多完成
                        }
                        Gson gson = new Gson();
                        bean = gson.fromJson(response.body(), Manager_FindCarBean.class);
                        int code = bean.getCode();
                        
                        if(code==1){// 查询成功
                            Log.e("eee","查询成功");
                            List<Manager_FindCarBean.OrderInfo > data = bean.getData();

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
                                    ToastUtils.getInstance(mActivity).showToast("暂无数据!");
                                    recyclerView.setLoadingMoreEnabled(false);// 禁用上拉加载, 因为本次都没有获取数据, 就没有可以上拉加载的必要了.
                                }else{
                                    adapter.notifyDataSetChanged();
                                    ToastUtils.getInstance(mActivity).showToast("没有新的订单消息!");
                                    mPage = mPage - 1;
                                    recyclerView.setLoadingMoreEnabled(false);
                                }
                            }
                        }else{
                            Log.e("eee","查询失败");
                            ToastUtils.getInstance(mActivity).showToast(bean.getMsg());
                        }
                    }

                    public void onError(Response<String> response) {
                        Log.e("eee","onError");
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
