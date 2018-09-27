package activity.huafeng.com.myapplication1.base.impl;

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

import java.util.ArrayList;
import java.util.List;

import activity.huafeng.com.myapplication1.R;
import activity.huafeng.com.myapplication1.activity.BaseActivity;
import activity.huafeng.com.myapplication1.base.BasePager;
import activity.huafeng.com.myapplication1.bean.Driver_SendCarBean;
import activity.huafeng.com.myapplication1.bean.LoginBean_Driver;
import activity.huafeng.com.myapplication1.util.ToastUtils;
import activity.huafeng.com.myapplication1.util.UrlUtil;

/**
 * Created by leovo on 2018-08-24.
 */

public class Driver_SendCar extends BasePager {

    XRecyclerView recyclerView;
    MyRecycleAdapter adapter ;
    TextView consoleText;
    private int mPage;// 当前页数


    List<Driver_SendCarBean.OrderInfo> list;

    private Driver_SendCarBean bean;
    private String idcd,idrw;

    private View thisRootView;
    private boolean isInited = false;

    private double longitude;
    private double latitude;

    public Driver_SendCar(BaseActivity activity) {
        super(activity);

    }

    public void initView(){

        if(isInited){
            rootView = thisRootView;
        }else {
            thisRootView = View.inflate(mActivity ,R.layout .pager_driver_sendcar,null);

        consoleText=thisRootView .findViewById(R.id.tv_driver_consoleText) ;


        //xRecycleView
        recyclerView = thisRootView.findViewById(R.id.pager_driver_sendcar_recycleview);

        // 配置xRecycleView
        configurationXRecyclerView();



            rootView = thisRootView;
            isInited = true;
        }
    }

    public void initData(){

        recyclerView.refresh();
        
    }

    public void appendConsoleText(String text) {

        this.consoleText.append(text + "\n");

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




    }

    //xRecycleView 的适配器类
    public class MyRecycleAdapter extends XRecyclerView.Adapter<MyRecycleAdapter.MyHolder> {
        List<Driver_SendCarBean.OrderInfo > list;

        public MyRecycleAdapter(List list) {
            this.list = list;
        }

        @Override
        public MyRecycleAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pager_driver_sendcar, parent, false);

            MyRecycleAdapter.MyHolder holder = new MyRecycleAdapter.MyHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final MyRecycleAdapter.MyHolder holder, final int position) {


            holder .num.setText(list .get(position ).getJhdh());
            holder.qsd.setText(list .get(position ) .getQsd());
            holder .zdd.setText(list .get(position ).getZdd());
            holder.name.setText(list .get(position ) .getHwmc());

//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                   // ToastUtils.getInstance(mActivity).showToast("第 " + position + " 个条目被点击了");
//
//                  List<Driver_SendCarBean.OrderInfo> info=bean.getData() ;
//                  idrw=info .get(position ).getId();
//
//                }
//            });

            holder.sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    //给车队发送已装车完成的通知
                    requestNet_up();


                    ToastUtils.getInstance(mActivity).showToast("已装车完成!");
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
            TextView name;//货物名称
            TextView sure;

            public MyHolder(View itemView) {

                super(itemView);
                sure = itemView.findViewById(R.id.pager_driver_sendcar_sure);


                //在这里查找holder中的控件.
                num=itemView .findViewById(R.id.pager_driver_sendcar_num) ;
                qsd = itemView.findViewById(R.id.pager_driver_sendcar_qsd_textview);
                zdd = itemView.findViewById(R.id.pager_driver_sendcar_zdd_textview);
                name=itemView .findViewById(R.id.pager_driver_sendcar_cargoname) ;
                

            }


        }


    }



//    司机收到任务列表
    private void request_order(final int currPage){
        LoginBean_Driver.Data  driver  = Hawk.get("r_driver");
        String d_token=Hawk .get("t_driver");
        OkGo.<String>get(UrlUtil.URL_Prefix + "Task/GetSjRws")
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
                        Driver_SendCarBean bean = gson.fromJson(response.body(), Driver_SendCarBean.class);
                        String code = bean.getCode();
                        if("1".equals(code)){// 查询成功
                            List<Driver_SendCarBean.OrderInfo> data = bean.getData();

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


    /**
     * 点击确认后，给车队发送装车完成通知
     */

    private void requestNet_up() {
        
        LoginBean_Driver .Data driver  = Hawk.get("r_driver");
        String d_token=Hawk .get("t_driver");

        longitude =Hawk .get("Djd") ;
        latitude=Hawk .get("Dwd") ;

        Log.e("zcwc", String.valueOf( longitude ) );
        Log .e("zcwc", String.valueOf( latitude ) ) ;

        OkGo.<String>get(UrlUtil.URL_Prefix + "Task/GetSjzcwc")

                .params("Token", d_token)
                .params("SjId",driver.getId())
                .params("Jd",String.valueOf( longitude ))
                .params("Wd",String.valueOf( latitude ))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        Log .e("zcwc","onSuccess");
                        ToastUtils.getInstance(mActivity).showToast("给车队发送装车完成通知");
                        recyclerView.refresh();

                    }

                    public void onError(Response<String> response) {

                        super.onError(response);
                        ToastUtils.getInstance(mActivity).showToast("请求失败");

                    }
                });


    }


}
