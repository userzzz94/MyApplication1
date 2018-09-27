package activity.huafeng.com.myapplication1.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.alibaba.sdk.android.push.MessageReceiver;
import com.alibaba.sdk.android.push.notification.CPushMessage;

import java.util.Map;

import activity.huafeng.com.myapplication1.activity.TaskDialog;

/**
 * Created by leovo on 2018-08-28.
 */

public class MyMessageReceiver extends MessageReceiver {


         // 消息接收部分的LOG_TAG
          public static final String REC_TAG = "receiver";


    /**
            * 推送通知的回调方法
            * @param context
            * @param title
            * @param summary
            * @param extraMap
            */
      @Override
      public void onNotification(Context context, String title, String summary, Map<String, String> extraMap) {
                  // TODO 处理推送通知
                  if ( null != extraMap ) {
                          for (Map.Entry<String, String> entry : extraMap.entrySet()) {
                                 Log.i(REC_TAG,"@Get diy param : Key=" + entry.getKey() + " , Value=" + entry.getValue());
                              }
                     } else {
                         Log.i(REC_TAG,"@收到通知 && 自定义消息为空");
                      }
                  Log.i(REC_TAG,"收到一条推送通知 ： " + title + ", summary:" + summary);


      }


    /**
          * 推送消息的回调方法
          * @param context
          * @param cPushMessage
          */

      @Override
      public void onMessage(Context context, CPushMessage cPushMessage) {
          Log.i(REC_TAG,"收到一条推送消息 ： " + cPushMessage.getTitle() + ", content:" + cPushMessage.getContent());

          Log.e("PushMessage","onMessage"  );
          Intent taskIntent = new Intent(context,TaskDialog.class);
          Log.e("PushMessage","TaskDialog"  );

          Bundle bundle = new Bundle();
          bundle.putString("title", cPushMessage.getTitle());
          bundle.putString("content",cPushMessage.getContent()); 
          taskIntent.putExtras(bundle);

          taskIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          context.startActivity(taskIntent);


      }


    /**
           * 从通知栏打开通知的扩展处理
           * @param context
           * @param title
           * @param summary
           * @param extraMap
           */
      @Override
      public void onNotificationOpened(Context context, String title, String summary, String extraMap) {
                  Log.i(REC_TAG,"onNotificationOpened ： " + " : " + title + " : " + summary + " : " + extraMap);
      }


    /**
            * 无动作通知点击回调。当在后台或阿里云控制台指定的通知动作为无逻辑跳转时,通知点击回调为onNotificationClickedWithNoAction而不是onNotificationOpened
            * @param context
            * @param title
            * @param summary
            * @param extraMap
            */
      @Override
      protected void onNotificationClickedWithNoAction(Context context, String title, String summary, String extraMap) {
                  Log.i(REC_TAG,"onNotificationClickedWithNoAction ： " + " : " + title + " : " + summary + " : " + extraMap);

      }


    /**
            * 应用处于前台时通知到达回调。注意:该方法仅对自定义样式通知有效,相关详情请参考https://help.aliyun.com/document_detail/30066.html?spm=5176.product30047.6.620.wjcC87#h3-3-4-basiccustompushnotification-api
            * @param context
            * @param title
            * @param summary
            * @param extraMap
            * @param openType
            * @param openActivity
            * @param openUrl
            */
      @Override
      protected void onNotificationReceivedInApp(Context context, String title, String summary, Map<String, String> extraMap, int openType, String openActivity, String openUrl) {
                 Log.i(REC_TAG,"onNotificationReceivedInApp ： " + " : " + title + " : " + summary + "  " + extraMap + " : " + openType + " : " + openActivity + " : " + openUrl);

      }


    @Override
    protected void onNotificationRemoved(Context context, String messageId) {
                Log.e("MyMessageReceiver", "onNotificationRemoved");
           }
}

