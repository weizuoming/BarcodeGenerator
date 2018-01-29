package com.dash.mycalendar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.dash.zxinglibrary.activity.CodeUtils;

public class ShengChengActivity extends AppCompatActivity {

    private EditText editText;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheng_cheng);

        editText = findViewById(R.id.edit_text);
        imageView = findViewById(R.id.image_view);

    }

    public void normal(View view) {

        String s = editText.getText().toString();

        //可以判断一下非空

        Bitmap bitmap = CodeUtils.createImage(s, 500, 500, null);

        imageView.setImageBitmap(bitmap);
    }

    public void logo(View view) {

        String s = editText.getText().toString();

        //可以判断一下非空

        //如何把资源目录下的图片转成bitmap
        Bitmap logo = BitmapFactory.decodeResource(getResources(), R.drawable.sss3);
        Bitmap bitmap = CodeUtils.createImage(s, 500, 500,logo);

        imageView.setImageBitmap(bitmap);

    }
}
