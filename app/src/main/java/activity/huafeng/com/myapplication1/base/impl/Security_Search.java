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
import activity.huafeng.com.myapplication1.bean.LoginBean_Security;
import activity.huafeng.com.myapplication1.bean.Manager_OrderStateBean;
import activity.huafeng.com.myapplication1.util.ToastUtils;
import activity.huafeng.com.myapplication1.util.UrlUtil;

/**
 * Created by leovo on 2018-08-24.
 */

public class Security_Search extends BasePager {

    XRecyclerView recyclerView;
    MyRecycleAdapter adapter;
    private int mPage ;

    private Manager_OrderStateBean bean;
    List<Manager_OrderStateBean.OrderInfo> list;

    private View thisRootView;
    private boolean isInited = false;

    public Security_Search(BaseActivity activity) {
        super(activity);
    }

    public void initView(){

        if(isInited){
            rootView = thisRootView;
        }else {
            thisRootView = View.inflate(mActivity , R.layout .pager_security_mine,null);

        recyclerView = thisRootView.findViewById(R.id.pager_security_search_recycleview);

        configurationXRecyclerView();

            rootView = thisRootView;
            isInited = true;
        }
    }

    public void initData(){
        recyclerView.refresh();

    }

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

    public class MyRecycleAdapter extends XRecyclerView.Adapter<MyRecycleAdapter.MyHolder> {
        List<Manager_OrderStateBean .OrderInfo > list;

        public MyRecycleAdapter(List list) {
            this.list = list;
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pager_security_search, parent, false);

            MyHolder holder = new MyHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final MyHolder holder, final int position) {

            String zt = list.get( position ).getZt() + "";

            holder .num.setText(list .get(position ).getRwbh());
            holder.qsd.setText(list .get(position ) .getQsd());
            holder .zdd.setText(list .get(position ).getZdd());
            holder.cargoname.setText(list .get(position ) .getHwmc());
            holder.cargotype.setText(list .get(position) .getDw());
            holder.name.setText(list .get(position) .getSjxm());
            holder.carnum.setText(list .get(position) .getCph());
            holder.time.setText(list .get(position) .getLrsj());

            if ("1".equals( zt )) {
                holder.state.setText( "初始" );
            } else if ("2".equals( zt )) {
                holder.state.setText( "装车完成" );
            } else if ("3".equals( zt )) {
                holder.state.setText( "开始运输" );
            } else if ("4".equals( zt )) {
                holder.state.setText( "到达" );
            }else if ("5".equals( zt )) {
                holder.state.setText( "完成" );
            }




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
            TextView name;//司机姓名
            TextView carnum;//车牌号
            TextView time;//录入时间
            TextView state; //订单状态

            public MyHolder(View itemView) {

                super(itemView);


                //在这里查找holder中的控件.
                num=itemView .findViewById(R.id.pager_manager_search_num) ;
                qsd = itemView.findViewById(R.id.pager_manager_search_qsd_textview);
                zdd = itemView.findViewById(R.id.pager_manager_search_zdd_textview);
                cargoname = itemView.findViewById(R.id.pager_manager_search_cargoname);
                cargotype=itemView.findViewById(R.id.pager_manager_search_cargoweight);
                name =itemView.findViewById(R.id.pager_manager_search_carname);
                carnum =itemView.findViewById(R.id.pager_manager_search_carnum);
                time=itemView.findViewById(R.id.pager_manager_search_time);
                state =itemView .findViewById(R.id.pager_manager_search_zt) ;


            }
        }


    }

    private void request_order(final int currPage){
        LoginBean_Security.Data security  = Hawk.get("r_security");
        String s_token=Hawk .get("t_security");

        OkGo.<String>get( UrlUtil.URL_Prefix + "Task/GetAbLs" )

                .params("Token", s_token)
                .params("AbId",security.getId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        if(currPage == 1){
                            recyclerView.refreshComplete();// 告诉适配器, 刷新完成
                        }else{
                            recyclerView.loadMoreComplete();// 告诉适配器, 加载更多完成
                        }
                        Gson gson = new Gson();
                        bean = gson.fromJson(response.body(), Manager_OrderStateBean.class);
                        int code = bean.getCode();

                        if(code==1){// 查询成功
                            Log.e("eee","查询成功");
                            List<Manager_OrderStateBean.OrderInfo > data = bean.getData();

                            if(data != null && data.size() != 0){// 当获取的数据不为空 的时候.

                                if(currPage == 1){// 当是下拉刷新的时候,需要将list中的数据清空.
                                    list.clear();
                                }
                                //将读取到的数据, 赋值给list, 适配器将显示list中的数据.
                                list.addAll(data);
                                //通知适配器刷新, 不调用这个方法, 虽然list的数据更新了, 但页面确是没反应的,此方法执行后, 页面也会刷新.
                                adapter.notifyDataSetChanged();
                                recyclerView.setLoadingMoreEnabled(false);

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
                        ToastUtils .getInstance(mActivity).showToast(bean.getMsg());
                    }
                });

    }



}
