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

public class SelfResultActivity extends Activity {

    Intent intent;
    private String self_result;

    ImageButton self_result_back;
    TextView self_result_tone;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_self_result);

        intent = getIntent();
        self_result =  intent.getStringExtra("self_result");


        self_result_back = (ImageButton) findViewById(R.id.self_result_back);   // 팝업창 끄기
        //self_result_color = (ImageView) findViewById(R.id.self_result_color);   // 동적으로 유저 자가 진단 결과 색 보여주기
        self_result_tone = (TextView) findViewById(R.id.self_result_tone);      // 동적으로 유저 자가 진단 결과 톤 보여주기

        // 핸들러를 사용해 네트워크 요청 후 자가진단 결과 값을 받아와서 UI로 그리기

        self_result_tone.setText(self_result);

        // 뒤로가기
        self_result_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();
            }
        });


    }   // onCreate

}
