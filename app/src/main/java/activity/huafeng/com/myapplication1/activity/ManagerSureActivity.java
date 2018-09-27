package activity.huafeng.com.myapplication1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import activity.huafeng.com.myapplication1.R;
import activity.huafeng.com.myapplication1.bean.LoginBean_Manager;
import activity.huafeng.com.myapplication1.bean.Manager_SureBean;
import activity.huafeng.com.myapplication1.util.ToastUtils;
import activity.huafeng.com.myapplication1.util.UrlUtil;

//车队点击确认后，跳转到此页面选择司机

public class ManagerSureActivity extends BaseActivity {

    private static final String TAG = "ManagerSureActivity";

    XRecyclerView recyclerView;
    MyRecycleAdapter adapter;
    //列表数据
    List <Manager_SureBean.DriverInfo> list;

    private Manager_SureBean bean;
    private String idsj, idrw;


    CheckBox checkBox;
    //private List<Manager_SureBean.DriverInfo> clists=new ArrayList<>();

    TextView name, tel, carnum, cartype, carstate;

    Button sure;

    private int mPage;


    // 存储勾选框状态的map集合
    private Map <Integer, Boolean> map = new HashMap <>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_manager_sure );

    }

    public void initView() {

        Hawk.init( this ).build();

        checkBox = (CheckBox) findViewById( R.id.manager_sure_checkbox );

        name = (TextView) findViewById( R.id.manager_sure_name );
        tel = (TextView) findViewById( R.id.manager_sure_tel );
        carnum = (TextView) findViewById( R.id.manager_sure_carnum );
        cartype = (TextView) findViewById( R.id.manager_sure_cartype );
        carstate = (TextView) findViewById( R.id.manager_sure_carstate );

        idrw = Hawk.get( "taskId" );
        sure = (Button) findViewById( R.id.manager_sure_btnsure );
        sure.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // clists.clear() ;
                String ids = "";
                
                //获取你选中的item
                Map<Integer, Boolean> map = adapter.getMap();
                for (int i = 0; i < map .size(); i++) {
                    if (map.get( i ).equals( true )) {
                        Log.e( "getMap", "你选了第：" + i + "项" );
                        ids+= list.get(i).getId() +",";
                        ids+= list.get(i).getCId() +",";
                        ids+= idrw +"|";

                    }
                }
                adapter.notifyDataSetChanged();

                //消息通知
                requestNet_up(ids);

                //ToastUtils.getInstance( ManagerSureActivity.this ).showToast( "司机任务推送成功 " );


            }
        } );


        //xRecycleView
        recyclerView = (XRecyclerView) findViewById( R.id.pager_manager_sure_recycleview );

        // 配置xRecycleView
        configurationXRecyclerView();

    }

    public void initData() {


        recyclerView.refresh();


    }


    private void configurationXRecyclerView() {

        //设置RecyclerView管理器
        recyclerView.setLayoutManager( new LinearLayoutManager( this, LinearLayoutManager.VERTICAL, false ) );


        //设置添加或删除item时的动画，这里使用默认动画
        recyclerView.setItemAnimator( new DefaultItemAnimator() );

        //先将list 初始化,在给适配器调用

        list = new ArrayList <>();

        adapter = new ManagerSureActivity.MyRecycleAdapter( list );
        recyclerView.setAdapter( adapter );

        //刷新
        recyclerView.setLoadingListener( new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mPage = 1;
                request_driverInfo( mPage );
                recyclerView.setLoadingMoreEnabled( true );
            }

            @Override
            public void onLoadMore() {
                mPage += 1;
                request_driverInfo( mPage );
                recyclerView.setLoadingMoreEnabled( false );
            }
        } );


    }

    //xRecycleView 的适配器类
    public class MyRecycleAdapter extends XRecyclerView.Adapter <ManagerSureActivity.MyRecycleAdapter.MyHolder> {
        List <Manager_SureBean.DriverInfo> list;

        // 存储勾选框状态的map集合
        private Map <Integer, Boolean> map = new HashMap <>();

        public MyRecycleAdapter(List list) {
            this.list = list;


            initMap();

        }


        //初始化map集合,默认为不选中
        private void initMap() {
            for (int i = 0; i < list.size(); i++) {
                map.put( i, false );
            }
        }


        //点击item选中CheckBox
        public void setSelectItem(int position) {

            //对当前状态取反
            if (map.get( position )) {
                map.put( position, false );

            } else {
                map.put( position, true );

            }
            notifyItemChanged( position );
        }


        //返回集合给ManagerSureActivity
        public Map <Integer, Boolean> getMap() {
            return map;
        }


        @Override
        public MyRecycleAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.item_activity_manager_sure, parent, false );

            ManagerSureActivity.MyRecycleAdapter.MyHolder holder = new ManagerSureActivity.MyRecycleAdapter.MyHolder( view );
            return holder;
        }


        @Override
        public void onBindViewHolder(final MyRecycleAdapter.MyHolder holder, final int position) {


            String zt = list.get( position ).getZt() + "";
            holder.name.setText( list.get( position ).getSjmc() );
            holder.tel.setText( list.get( position ).getSjdh() );
            holder.carnum.setText( list.get( position ).getCph() );
            holder.cartype.setText( list.get( position ).getCx() );

            if ("1".equals( zt )) {
                holder.state.setText( "在线" );
            } else if ("2".equals( zt )) {
                holder.state.setText( "离线" );
            } else if ("3".equals( zt )) {
                holder.state.setText( "工作中" );
            }


            holder.itemView.setTag( position );//将每一个itemView设置一个当前的Tag值，这样每个itemView都有了一个固定的标识

            holder.ckb.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    //用map集合保存

                    Log.e( "checkcheckcheck", position + ":" + isChecked );

                    map.put( position, isChecked );

                }
            } );


            if (map.get( position ) == null) {
                map.put( position, false );

            }


            holder.itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //ToastUtils.getInstance( ManagerSureActivity.this ).showToast( "第 " + position + " 个条目被点击了" );

                    setSelectItem( position );
                    adapter.notifyDataSetChanged();

                    List <Manager_SureBean.DriverInfo> info = bean.getData();
                    idsj = info.get( position ).getId();
                    Log.e( "ItemCount1", idsj );
                    Hawk.put( "idsj", idsj );

                }
            } );

            holder.ckb.setChecked( map.get( position ) );

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public Object getItem(int position) {

            return list.get( position );

        }


        public long getItemId(int position) {

            return position;

        }


        public class MyHolder extends XRecyclerView.ViewHolder {
            TextView name;//司机姓名
            TextView tel;// 联系电话
            TextView carnum;// 车牌号
            TextView cartype;// 车辆类型
            TextView state;  //车辆状态
            CheckBox ckb;
            private int checkedNum = 0;


            public MyHolder(View itemView) {

                super( itemView );


                //在这里查找holder中的控件.

                name = itemView.findViewById( R.id.manager_sure_name );
                tel = itemView.findViewById( R.id.manager_sure_telnum );
                carnum = itemView.findViewById( R.id.manager_sure_carnum );
                cartype = itemView.findViewById( R.id.manager_sure_cartype );
                state = itemView.findViewById( R.id.manager_sure_carstate );
                ckb = itemView.findViewById( R.id.manager_sure_checkbox );

            }


        }


    }

    //获得可用司机列表
    private void request_driverInfo(final int currPage) {
        LoginBean_Manager.DataManager manager = Hawk.get( "r_manager" );
        String m_token = Hawk.get( "t_manager" );
        Log.d( "eee", "m_token" );
        OkGo. <String>get( UrlUtil.URL_Prefix + "Task/GetSj" )
                .params( "CdId", manager.getId() )
                .params( "Token", m_token )
                .execute( new StringCallback() {
                    @Override
                    public void onSuccess(Response <String> response) {
                        Log.d( "eee", response.body().toString() );
                        if (currPage == 1) {
                            recyclerView.refreshComplete();// 告诉适配器, 刷新完成
                        } else {
                            recyclerView.loadMoreComplete();// 告诉适配器, 加载更多完成
                        }
                        Gson gson = new Gson();
                        bean = gson.fromJson( response.body(), Manager_SureBean.class );
                        int code = bean.getCode();
                        if (code == 1) {// 查询成功

                            List <Manager_SureBean.DriverInfo> data = bean.getData();

                            if (data != null && data.size() != 0) {// 当获取的数据不为空 的时候.

                                if (currPage == 1) {// 当是下拉刷新的时候,需要将list中的数据清空.
                                    list.clear();
                                }
                                //将读取到的数据, 赋值给list, 适配器将显示list中的数据.
                                list.addAll( data );
                                //通知适配器刷新, 不调用这个方法, 虽然list的数据更新了, 但页面确是没反应的,此方法执行后, 页面也会刷新.
                                adapter.notifyDataSetChanged();
                                recyclerView.setLoadingMoreEnabled( false );
                                ToastUtils.getInstance( ManagerSureActivity.this ).showToast( bean.getMsg() );

                            } else {// 查询结果为null的情况.

                                if (currPage == 1) {
                                    list.clear();
                                    adapter.notifyDataSetChanged();
                                    ToastUtils.getInstance( ManagerSureActivity.this ).showToast( "暂无司机信息!" );
                                    recyclerView.setLoadingMoreEnabled( false );// 禁用上拉加载, 因为本次都没有获取数据, 就没有可以上拉加载的必要了.
                                } else {
                                    adapter.notifyDataSetChanged();
                                    ToastUtils.getInstance( ManagerSureActivity.this ).showToast( "无最新数据!" );
                                    mPage = mPage - 1;
                                    recyclerView.setLoadingMoreEnabled( false );
                                }
                            }
                        } else {
                            Log.d( "eee", "查询失败" );
                            ToastUtils.getInstance( ManagerSureActivity.this ).showToast( bean.getMsg() );
                        }
                    }

                    public void onError(Response <String> response) {
                        Log.d( "eee", "onError" );
                        super.onError( response );
                        if (mPage == 1) {
                            recyclerView.refreshComplete();
                        } else {
                            recyclerView.loadMoreComplete();
                        }
                        ToastUtils.getInstance( ManagerSureActivity.this ).showToast( bean.getMsg() );
                    }
                } );

    }

    //点击确认后，上传信息
    private void requestNet_up(String ids) {

        //获取订单信息

        //LoginBean_Manager.DataManager manager = Hawk.get( "r_manager" );

        String m_token = Hawk.get( "t_manager" );
        Log.e( "ItemCount2", ids );
        
        OkGo. <String>get( UrlUtil.URL_Prefix + "Task/GetSjRw" )

                .params( "str", ids) //司机ID，车队ID，任务ID
                .params( "Token", m_token )

                .execute( new StringCallback() {
                    @Override
                    public void onSuccess(Response <String> response) {
                        Log.d( "123", "onSuccess" );
                        ToastUtils.getInstance( ManagerSureActivity.this ).showToast( bean.getMsg() );

                        Intent scan_finish =new Intent(ManagerSureActivity .this ,  MainActivity_Manager .class ) ;
                        scan_finish.putExtra("findcar", 0);
                        startActivity(scan_finish );
                        finish();

                    }

                    public void onError(Response <String> response) {
                        Log.d( "123", "onError" );
                        super.onError( response );
                        ToastUtils.getInstance( ManagerSureActivity.this ).showToast( bean.getMsg() );

                    }
                } );


    }


}
