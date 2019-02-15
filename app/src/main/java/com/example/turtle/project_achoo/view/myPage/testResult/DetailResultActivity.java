package com.example.turtle.project_achoo.view.myPage.testResult;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.turtle.project_achoo.R;

public class DetailResultActivity extends Activity {

    Intent intent;
    private int RGB;
    ImageButton detail_result_back;
    ImageView detail_result_skintone;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_detail_result);

        intent = getIntent();
        RGB =  intent.getIntExtra("RGB", 0);

        detail_result_back = (ImageButton) findViewById(R.id.detail_result_back);   // 팝업창 끄기
        detail_result_skintone = (ImageView) findViewById(R.id.detail_result_skintone);   // 동적으로 유저 상세 진단 결과 색 보여주기



        detail_result_skintone.setBackgroundColor(RGB);

        detail_result_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();
            }
        });


    }   // onCreate


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }

}