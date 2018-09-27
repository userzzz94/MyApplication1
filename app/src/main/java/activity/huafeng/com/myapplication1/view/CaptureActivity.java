package activity.huafeng.com.myapplication1.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.github.shenyuanqing.zxingsimplify.zxing.utils.BeepManager;
import com.github.shenyuanqing.zxingsimplify.zxing.utils.InactivityTimer;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import com.uuzuche.lib_zxing.R;
/**
 * Created by leovo on 2018-09-14.
 */


/**
 * Initial the camera
 *
 * 默认的二维码扫描Activity
 */
public class CaptureActivity extends AppCompatActivity {

    private InactivityTimer inactivityTimer;
    private BeepManager beepManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView( R.layout.camera);
         }


    /**
     * Handler scan result
     * @param result
     * @param barcode
     */
    public void handleDecode(String result, Bitmap barcode) {
        inactivityTimer.onActivity();
        beepManager.playBeepSoundAndVibrate();
        Bundle bundle = new Bundle();
        result = bundle.getString(CodeUtils.RESULT_STRING);
        // FIXME
        if (result.equals("")) {
            Toast.makeText(CaptureActivity.this, "扫描失败!", Toast.LENGTH_SHORT)
                    .show();
        } else {

            CaptureFragment captureFragment = new CaptureFragment();
            captureFragment.setAnalyzeCallback(analyzeCallback);
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_zxing_container, captureFragment).commit();

        }
        CaptureActivity.this.finish();
    }
    

    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
            bundle.putString(CodeUtils.RESULT_STRING, result);
            resultIntent.putExtras(bundle);
            CaptureActivity.this.setResult(RESULT_OK, resultIntent);
            CaptureActivity.this.finish();
        }

        @Override
        public void onAnalyzeFailed() {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt( CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
            bundle.putString(CodeUtils.RESULT_STRING, "");
            resultIntent.putExtras(bundle);
            CaptureActivity.this.setResult(RESULT_OK, resultIntent);
            CaptureActivity.this.finish();
        }
    };
}