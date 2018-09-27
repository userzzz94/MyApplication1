package activity.huafeng.com.myapplication1.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by xian on 2018/8/30.
 */

public class MyMapView extends FrameLayout {
    public MyMapView(@NonNull Context context) {
        super(context);
    }

    public MyMapView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyMapView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyMapView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            getParent().requestDisallowInterceptTouchEvent(true);//拦截父类事件

        } else if (ev.getAction() == MotionEvent.ACTION_UP) {
            getParent().requestDisallowInterceptTouchEvent(false);
        }
        return super.dispatchTouchEvent(ev);
    }
}
