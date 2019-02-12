package com.example.turtle.project_achoo.view.selfTest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.turtle.project_achoo.R;

public class Test10 extends AppCompatActivity {

    private String imgtext;
    private String color_result; //퍼스널컬러 최종값(DataBase에 저장될 최종 값)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        final int warm = getIntent().getIntExtra("warm", 1);
        final int cool = getIntent().getIntExtra("cool", 1);
        final int sw = getIntent().getIntExtra("sw", 1);
        final int aw = getIntent().getIntExtra("aw", 1);
        final int sc = getIntent().getIntExtra("sc", 1);
        final int wc = getIntent().getIntExtra("wc", 1);
        final String result = getIntent().getStringExtra("imgtext");
        final int money1 = getIntent().getIntExtra("money1", 1);
        final int money2 = getIntent().getIntExtra("money2", 1);
        final int money3 = getIntent().getIntExtra("money3", 1);
        final int money4 = getIntent().getIntExtra("money4", 1);
        final int money5 = getIntent().getIntExtra("money5", 1);
        final int money6 = getIntent().getIntExtra("money6", 1);
        final String lip = getIntent().getStringExtra("lip");


        Log.i("test","마지막 페이지 압니다.");
        Log.i("test",warm +"입니다.");
        Log.i("test",cool+"입니다.");
        Log.i("test",sw+"입니다.");
        Log.i("test",aw+"입니다.");
        Log.i("test",sc+"입니다.");
        Log.i("test",wc+"입니다.");
        Log.i("test",result);
        Log.i("test",money1 +"입니다.");
        Log.i("test",money2 +"입니다.");
        Log.i("test",money3 +"입니다.");
        Log.i("test",money4 +"입니다.");
        Log.i("test",money5 +"입니다.");
        Log.i("test",money6 +"입니다.");
        Log.i("test",lip +"입니다.");



        if(warm > cool){ // 웜톤(warm)체크갯수가 쿨톤(cool)보다 많고
            if(sw > aw) { // 봄웜톤(sw)체크갯수가 가을웜톤(aw)보다 많으면
                color_result = "봄 웜톤"; // color_result(최종 결과값)에 "봄 웜톤"을 저장해라
                setContentView(R.layout.warm_spring);
            }else if(aw > sw){ // 가을웜톤(aw)체크갯수가 봄웜톤(sw)보다 많으면
                color_result = "가을 웜톤"; // color_result(최종결과값)에 "가을 웜톤"을 저장해라
                setContentView(R.layout.warm_fall);

            }else if(aw == sw){// 가을웜톤(aw)랑 봄웜톤(sw)가 같으면
                if(imgtext.equals("sw")){ // 이미지(imgtext, 7번 질문)에서 sw 이미지를 체크했으면
                    color_result = "봄 웜톤"; // color_result(최종 결과값)에 "봄 웜톤"을 저장해라
                    setContentView(R.layout.warm_spring);
                }else if(imgtext.equals("aw")){ // 이미지(imgtext, 7번 질문)에서 aw 이미지를 체크했으면
                    color_result = "가을 웜톤"; // color_result(최종 결과값)에 "가을 웜톤"을 저장해라
                    setContentView(R.layout.warm_fall);
                }
            } // warm > cool 끌남
        }else if(cool > warm) { // 쿨톤(cool)체크갯수가 웜톤(warm)보다 많고
            if(sc > wc) { // 여름쿨톤(sc)체크갯수가 겨울쿨톤(wc)보다 많으면
                color_result = "여름 쿨톤"; // color_result(최종 결과값)에 "여름쿨톤"을 저장해라
                setContentView(R.layout.cool_summer);
            }else if(wc > sc){ // 겨울쿨톤(wc)체크갯수가 여름쿨톤(sc)보다 많으면
                color_result = "겨울 쿨톤"; // color_result(최종 결과값)에 "겨울쿨톤"을 저장해라
                setContentView(R.layout.cool_winter);
            }else if(wc == sc){ // 겨울쿨톤(wc)랑 여름쿨톤(sc)가 같으면
                if(imgtext.equals("sc")){ // 이미지(imgtext, 7번 질문)에서 sc 이미지를 체크했으면
                    color_result = "여름 쿨톤"; // color_result(최종 결과값)에 "여름 쿨톤"을 저장해라
                    setContentView(R.layout.cool_summer);
                }else if(imgtext.equals("wc")){// 이미지(imgtext, 7번 질문)에서 wc 이미지를 체크했으면
                    color_result = "겨울 쿨톤";// color_result(최종 결과값)에 "겨울 쿨톤"을 저장해라
                    setContentView(R.layout.cool_winter);
                }
            }
        }
    }

}
