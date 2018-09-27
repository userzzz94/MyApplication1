package activity.huafeng.com.myapplication1.util;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by xian on 2018/9/5.
 * 获取位置信息的服务
 */

public class GetLocationService extends Service {

    private MyBinder myBinder = new MyBinder();

    /**
     * MyBinder类
     */
    public class  MyBinder extends Binder {
        public GetLocationService getLocationService(){
            return GetLocationService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

}
