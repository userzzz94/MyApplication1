package activity.huafeng.com.myapplication1.base.impl;

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
import activity.huafeng.com.myapplication1.base.BasePager;
import activity.huafeng.com.myapplication1.bean.Driver_CooperationBean;
import activity.huafeng.com.myapplication1.bean.LoginBean_Driver;
import activity.huafeng.com.myapplication1.bean.Scan_SureBean;
import activity.huafeng.com.myapplication1.util.ToastUtils;
import activity.huafeng.com.myapplication1.util.UrlUtil;

/**
 * Created by leovo on 2018-08-24.
 */

public class Driver_Cooperation extends BasePager{

    XRecyclerView recyclerView;
    MyRecycleAdapter adapter ;

    List<Driver_CooperationBean.OrderInfo> list;
    List <Scan_SureBean.Data> surelist;

    private  Driver_CooperationBean bean;
    private String Mxid;
    private int mPage;

    private View thisRootView;
    private boolean isInited = false;

    private CloudPushService mPushService;

    public Driver_Cooperation(BaseActivity activity) {
        super(activity);

    }

    public void initView(){

        if(isInited){
            rootView = thisRootView;
        }else {

            thisRootView = View.inflate( mActivity, R.layout.pager_driver_cooperation, null );

            //xRecycleView
            recyclerView = thisRootView.findViewById( R.id.pager_driver_cooperation_recycleview );

            // 配置xRecycleView
            configurationXRecyclerView();

            rootView = thisRootView;
            isInited = true;
        }

    }

    public void initData(){


//        recyclerView.refresh();

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
                request_order(mPage);
                recyclerView.setLoadingMoreEnabled(true);
            }

            @Override
            public void onLoadMore() {
                mPage += 1;
                request_order(mPage);

            }
        });

        recyclerView.refresh();

    }

    //xRecycleView 的适配器类
    public class MyRecycleAdapter extends XRecyclerView.Adapter<MyRecycleAdapter.MyHolder> {
        List<Driver_CooperationBean.OrderInfo > list;

        public MyRecycleAdapter(List list) {
            this.list = list;
        }

        @Override
        public MyRecycleAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pager_driver_cooperation, parent, false);

            MyRecycleAdapter.MyHolder holder = new MyRecycleAdapter.MyHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final MyRecycleAdapter.MyHolder holder, final int position) {



            holder .num.setText(list .get(position ).getRwbh());
            holder.qsd.setText(list .get(position ) .getQsd());
            holder .zdd.setText(list .get(position ).getZdd());
            holder.name.setText(list .get(position ) .getHwmc());
            holder.type.setText(list .get(position ) .getMeiz());
            holder.lname.setText(list .get(position ) .getLrr());
            holder.time.setText(list .get(position ) .getLrsj());

            
            holder.sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //int postion = holder.getAdapterPosition();

                    Mxid =bean.getData() .get(position ).getId();
                    if (Mxid == null) {
                        Log .e("mxid10","null");
                    } else {
                        Log .e("mxid11",Mxid );
                    }

                    ToastUtils.getInstance(mActivity).showToast("货物已到达！");
                    request_sure();


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
            TextView name;// 货物名称
            TextView type,lname,time;
            TextView sure;

            public MyHolder(View itemView) {

                super(itemView);
                sure = itemView.findViewById(R.id.pager_driver_cooperation_sure);


                //在这里查找holder中的控件.
                num=itemView .findViewById(R.id.pager_driver_cooperation_num) ;
                qsd = itemView.findViewById(R.id.pager_driver_cooperation_qsd_textview);
                zdd = itemView.findViewById(R.id.pager_driver_cooperation_zdd_textview);
                name=itemView .findViewById(R.id.pager_driver_cooperation_cargoname) ;
                type=itemView .findViewById(R.id.pager_driver_cooperation_cargotype) ;
                lname=itemView .findViewById(R.id.pager_driver_cooperation_lname) ;
                time=itemView .findViewById(R.id.pager_driver_cooperation_time) ;



            }
        }


    }

//    在途订单查询
    private void request_order(final int currPage){
        LoginBean_Driver.Data  driver  = Hawk.get("r_driver");
        String d_token=Hawk .get("t_driver");
        OkGo.<String>get(UrlUtil.URL_Prefix + "Task/GetSjYs")
                .params("SjId",driver.getId())
                .params("Token", d_token)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if(currPage == 1){
                            recyclerView.refreshComplete();// 告诉适配器, 刷新完成
                        }else{
                            recyclerView.loadMoreComplete();// 告诉适配器, 加载更多完成
                        }
                        Gson gson = new Gson();
                        bean = gson.fromJson(response.body(), Driver_CooperationBean.class);
                        String code = bean.getCode();
                        if("1".equals(code)){// 查询成功
                            List<Driver_CooperationBean.OrderInfo> data = bean.getData();

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
                                    ToastUtils.getInstance(mActivity).showToast("无最新数据!");
                                    mPage = mPage - 1;
                                    recyclerView.setLoadingMoreEnabled(false);
                                }
                            }
                        } else{
                            Log.e("eee","查询失败");
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

//    司机点确认到达

    private void request_sure(){
        LoginBean_Driver.Data  driver  = Hawk.get("r_driver");
        String d_token=Hawk .get("t_driver");

        OkGo.<String>get(UrlUtil.URL_Prefix + "Task/GetSjDd")

                .params("Token", d_token)
                .params("MxId",Mxid)//明细id
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        recyclerView.refresh();

                    }

                    public void onError(Response<String> response) {
                        super.onError(response);

                        ToastUtils .getInstance(mActivity).showToast("异常");
                    }
                });

    }



}
