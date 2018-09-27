package activity.huafeng.com.myapplication1.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;

import activity.huafeng.com.myapplication1.R;

//新的订单通知

/**

 * 任务弹窗

 */
public class TaskDialog extends Activity {


    String type= Hawk .get("type") ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_popup);
        Log.e("PushMessage","news_popup"  );


         /*获取Intent中的Bundle对象*/
        Bundle bundle = this.getIntent().getExtras();
        /*获取Bundle中的数据，注意类型和key*/
        String title = bundle.getString("title");
        String content=bundle.getString("content") ;

//        AlertDialog.Builder  builder = new AlertDialog.Builder(TaskDialog .this) ;
//
//        builder .setTitle(title)
//                .setMessage(content)
//
//                .setPositiveButton("接单", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(TaskDialog.this ,"您已接单",Toast .LENGTH_SHORT ) .show() ;
//                        dialog .dismiss() ;
//
//                        if("车队".equals(type)) {
//                            Intent intent_FindCar = new Intent(TaskDialog.this, MainActivity_Manager.class);
//                            intent_FindCar.putExtra("findcar", 0);
//                            startActivity(intent_FindCar);
//                        } else if("司机".equals(type)){
//                            Intent intent_SendCar = new Intent(TaskDialog.this, MainActivity_Driver.class);
//                            intent_SendCar.putExtra("sendcar", 0);
//                            startActivity(intent_SendCar);
//
//                        }
//
//                    }
//                })
//
//                 .setNegativeButton("消息查看", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.cancel();
//
//                            if("车队".equals(type)) {
//                                Intent intent_Message = new Intent(TaskDialog.this, MainActivity_Manager.class);
//                                intent_Message.putExtra("message", 1);
//                                startActivity(intent_Message);
//                            }
//
//                        }
//                    }).create() .show() ;


        // 创建对话框构建器
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 获取布局
        View view2 = View.inflate(TaskDialog.this, R.layout.layout_taskdialog, null);

        // 获取布局中的控件
        final TextView tvtitle = (TextView) view2.findViewById(R.id.taskdialog_tv_title);
        final TextView tvcontent = (TextView) view2.findViewById(R.id.taskdialog_tv_context);
//        final Button btnnews = (Button) view2.findViewById(R.id.taskdialog_btn_news);
        final Button btnorder = (Button) view2.findViewById(R.id.taskdialog_btn_order);
        // 设置参数
        builder.setView(view2);
//        .setTitle("您有一条新订单")


        // 创建对话框
        final AlertDialog alertDialog = builder.create();

//        tvtitle.setText("您有一条新订单"  );
//        tvcontent.setText( content );

        if("车队".equals(type)) {
            
//            btnnews.setVisibility( View.VISIBLE );
//
//            btnnews .setOnClickListener( new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    Intent intent_Message = new Intent(TaskDialog.this, MainActivity_Manager.class);
//                    intent_Message.putExtra("message", 1);
//                    startActivity(intent_Message);
//                    alertDialog.dismiss();// 对话框消失
//
//                }
//            } );

            btnorder.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Toast.makeText(TaskDialog.this ,"您已接单",Toast .LENGTH_SHORT ) .show() ;
                    Intent intent_FindCar = new Intent(TaskDialog.this, MainActivity_Manager.class);
                    intent_FindCar.putExtra("findcar", 0);
                    startActivity(intent_FindCar);
                    alertDialog.dismiss();// 对话框消失
                }
            } );



        } else if("司机".equals(type)){
            btnorder.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Toast.makeText(TaskDialog.this ,"您已接单",Toast .LENGTH_SHORT ) .show() ;
                    Intent intent_SendCar = new Intent(TaskDialog.this, MainActivity_Driver.class);
                    intent_SendCar.putExtra("sendcar", 1);
                    startActivity(intent_SendCar);
                    alertDialog.dismiss();// 对话框消失
                }
            } );

        }
        
        alertDialog.show();



    }





    //车队：
    //司机装车完成通知，点击跳转到车队扫码
    //车队收到的订单消息，点击跳转到订单详情

     //    司机：
    //    订单消息，点击跳转到司机发车界面
    // 车队扫码完成点击确认，订单状态变成在途,list.add

}
