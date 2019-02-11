package com.example.turtle.project_achoo.view.detailTest;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.turtle.project_achoo.R;
import com.example.turtle.project_achoo.view.detailTest.Photo.PhotoViewerActivity;
import com.example.turtle.project_achoo.view.detailTest.Photo.PhotoViewerFaceActivity;

public class DetailChoiceActivity extends Activity {

    ImageButton detail_choice_back, detail_choice_exp;
    Button parse_color, skin_tone;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_detail_choice);

        // 로그인 유지
        SharedPreferences appData = getSharedPreferences("appData", MODE_PRIVATE); // SharedPreferences 객체 가져오기

        if (appData.contains("login_status")) {

            id = appData.getString("login_id", "defValue"); // 로그인한 아이디 가져오기

        }

        detail_choice_back = (ImageButton) findViewById(R.id.detail_choice_back);
        detail_choice_exp = (ImageButton) findViewById(R.id.detail_choice_exp);
        parse_color = (Button) findViewById(R.id.parse_color);
        skin_tone = (Button) findViewById(R.id.skin_tone);

        // 뒤로가기
        detail_choice_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();
            }
        });

        // 기능설명
        detail_choice_exp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailChoiceActivity.this, DetailActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        // 퍼스널 컬러 진단
        parse_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailChoiceActivity.this, PhotoViewerActivity.class);
                startActivity(intent);
            }
        });

        // 피부톤 진단
        skin_tone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailChoiceActivity.this, PhotoViewerFaceActivity.class);
                startActivity(intent);
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
