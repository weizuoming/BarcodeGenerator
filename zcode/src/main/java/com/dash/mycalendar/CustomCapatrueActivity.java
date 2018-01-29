package com.dash.mycalendar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dash.zxinglibrary.activity.CaptureActivity;
import com.dash.zxinglibrary.activity.CaptureFragment;
import com.dash.zxinglibrary.activity.CodeUtils;
import com.dash.zxinglibrary.util.ImageUtil;

public class CustomCapatrueActivity extends AppCompatActivity {

    private LinearLayout flash_light;
    private boolean flag = false;;
    private LinearLayout pic_scan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_capatrue);

        flash_light = findViewById(R.id.flash_light);
        pic_scan = findViewById(R.id.pic_scan);


        CaptureFragment captureFragment = new CaptureFragment();

        //设置自定义的...扫描布局
        //给扫描的fragment定制一个页面
        CodeUtils.setFragmentArgs(captureFragment, R.layout.my_camera);

        //设置一个解析的监听回调
        captureFragment.setAnalyzeCallback(analyzeCallback);
        //使用扫描的fragment替换这个frameLayout
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_zxing_container, captureFragment).commit();


        flash_light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag) {
                    CodeUtils.isLightEnable(false);
                    flag = false;
                }else {
                    CodeUtils.isLightEnable(true);
                    flag = true;
                }
            }
        });

        //扫描图片的点击事件
        pic_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //隐式意图
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,1002);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1002){

            //uri路径......需要把uri路径转换为绝对路径!!!!!!!!!!!!!!!!!!!file...new file(绝对路径)
            Uri uri = data.getData();

            try {
                //解析图片的方法...ImageUtil.getImageAbsolutePath(this, uri)通过uri路径得到图片在手机中的绝对路径
                CodeUtils.analyzeBitmap(ImageUtil.getImageAbsolutePath(this, uri), new CodeUtils.AnalyzeCallback() {
                    //Bitmap mBitmap 解析的那张图片, String result解析的结果
                    @Override
                    public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                        Toast.makeText(CustomCapatrueActivity.this, "解析结果:" + result, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onAnalyzeFailed() {
                        Toast.makeText(CustomCapatrueActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

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
            setResult(RESULT_OK, resultIntent);
            finish();
        }

        @Override
        public void onAnalyzeFailed() {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
            bundle.putString(CodeUtils.RESULT_STRING, "");
            resultIntent.putExtras(bundle);
            setResult(RESULT_OK, resultIntent);
            finish();
        }
    };
}
