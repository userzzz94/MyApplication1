package activity.huafeng.com.myapplication1.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by leovo on 2018-08-15.
 */

public class AutoScrollTextView extends TextView {

    public AutoScrollTextView(Context context) {

        super(context);

    }



    public AutoScrollTextView(Context context, AttributeSet attrs) {

        super(context, attrs);

    }



    public AutoScrollTextView(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);

    }



    @Override

    public boolean isFocused() {//必须重写，且返回值是true

        return true;

    }
}
