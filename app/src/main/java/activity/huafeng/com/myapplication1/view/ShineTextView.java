package activity.huafeng.com.myapplication1.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by leovo on 2018-09-29.
 */

@SuppressLint("AppCompatCustomView")
public class ShineTextView extends TextView {

    /*
      *LinearGradient 是线性渐变渲染 ，代表颜色的渐变呈直线方向递变
      *RadialGradient 是环形渐变渲染 ，代表颜色的渐变呈圆环形递变。
      *我们可以使用RadialGradient 来实现小米开机动画的效果。
      * 通过控制mTranslate 亮度位移距离来使颜色产生动态变化的效果
     */
    private LinearGradient mLinearGradient;  // 线性渐变渲染
    private RadialGradient mRadialGradient; //环形渐变渲染
    private Matrix mGradientMatrix;// 渲染矩阵
    private Paint mPaint;// 画笔
    private int mViewWidth = 0;
    private int mViewHeight = 0;
    private int mTranslate = 0; // 亮度位移距离

    private int mTranslateX;
    private int mTranslateY ;

    public ShineTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    /*
      * 在OnSizeChange方法中，
      * 我们主要完成一些初始化操作，
      * 比如获取控件宽高、初始化颜色渲染器和设置字体阴影效果。

     */
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mViewWidth == 0 || mViewHeight==0)
        {
            mViewWidth = getMeasuredWidth();
            mViewHeight=getMeasuredHeight();
            if (mViewWidth > 0 ||  mViewHeight>0 )
            {
                mPaint = getPaint();
                // 创建RadialGradient对象
                // 第一个,第二个参数表示渐变圆中心坐标
                // 第三个参数表示半径
                // 第四个,第五个,第六个与线性渲染相同
                mRadialGradient = new RadialGradient(mTranslateX/2, mTranslateY/2,80,
                        new int[] {0x11D52B2B ,0xffD52B2B,0x55D52B2B},//0x11FEB726, 0xffFEB726, 0x33FEB726
                        new float[] { 0, 0.5f, 1 }, Shader.TileMode.CLAMP);

                // 创建LinearGradient对象
                // 起始点坐标（-mViewWidth, 0） 终点坐标（0，0）
                // 第一个,第二个参数表示渐变起点 可以设置起点终点在对角等任意位置
                // 第三个,第四个参数表示渐变终点
                // 第五个参数表示渐变颜色
                // 第六个参数可以为空,表示坐标,值为0-1
                // 如果这是空的，颜色均匀分布，沿梯度线。
                // 第七个表示平铺方式
                // CLAMP如果渲染器超出原始边界范围，则会复制边缘颜色对超出范围的区域进行着色
                // MIRROR重复着色的图像水平或垂直方向已镜像方式填充会有翻转效果
                // REPEAT重复着色的图像水平或垂直方向
                //mLinearGradient = new LinearGradient(-mViewWidth, 0, 0, 0,
                        //new int[] { Color .YELLOW , Color.TRANSPARENT, Color.RED },
                        //new float[] { 0, 0.5f, 1 }, Shader.TileMode.CLAMP);
                mPaint.setShader(mRadialGradient);
                mPaint.setColor( Color.parseColor("#ffffffff"));
                //设置字体阴影效果
                //第一个参数代表阴影的半径
                //第二个参数代表阴影在X方向的延伸像素
                //第三个参数代表阴影在Y方向的延伸像素

                mPaint.setShadowLayer(3, 2, 2, 0xFFFF00FF); //0xFFFF00FF
                mGradientMatrix = new Matrix();
            }
        }
    }

    /*
       * 在onDraw方法中绘制效果，并且通过Matrix矩阵来使渐变效果产生位移
     */
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        if ( mGradientMatrix != null)
        {
            mTranslateX += mViewWidth/10;
            mTranslateY += mViewHeight / 10;
            if (mTranslateX > 2 * mViewWidth || mTranslateY >2*mViewHeight)
            {
                mTranslateX = -mViewWidth;
                mTranslateY = -mViewHeight;
            }
            mGradientMatrix.setTranslate(mTranslateX, mTranslateY);

            // mLinearGradient.setLocalMatrix(mGradientMatrix);
            mRadialGradient.setLocalMatrix(mGradientMatrix);
            postInvalidateDelayed(50);
        }
    }
    


}
