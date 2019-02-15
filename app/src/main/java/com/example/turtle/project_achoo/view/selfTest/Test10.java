package com.example.turtle.project_achoo.view.selfTest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.example.turtle.project_achoo.R;
import com.example.turtle.project_achoo.function.service.networkService.MemberService;
import com.example.turtle.project_achoo.function.service.networkService.RetrofitInstance;
import com.example.turtle.project_achoo.view.home.HomeActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Test10 extends AppCompatActivity {


    private SharedPreferences appData;
    private Intent intent;

    private int money_result = 0;
    private String lip_result;

    private String imgtext;
    //private String color_result; // 퍼스널컬러 최종값(DataBase에 저장될 최종 값)

    private Button test_result;

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
        final int money = getIntent().getIntExtra("money", 0);

        final String lip = getIntent().getStringExtra("lip_color");

        money_result = money;
        lip_result = lip;
        imgtext = result;

        Log.d("test", money+" 돈돈돈돈ㄷ");
        Log.d("test", money_result+" 돈돈돈돈ㄷ");


        if (warm > cool) { // 웜톤(warm)체크갯수가 쿨톤(cool)보다 많고
            if (sw > aw) { // 봄웜톤(sw)체크갯수가 가을웜톤(aw)보다 많으면
               // color_result = "봄 웜톤"; // color_result(최종 결과값)에 "봄 웜톤"을 저장해라
                setContentView(R.layout.warm_spring);

                test_result = findViewById(R.id.test_result);
                test_result.setOnClickListener(view -> {

                    postTestResult("sw");


                });

            } else if (aw > sw) { // 가을웜톤(aw)체크갯수가 봄웜톤(sw)보다 많으면
                //color_result = "가을 웜톤"; // color_result(최종결과값)에 "가을 웜톤"을 저장해라
                setContentView(R.layout.warm_fall);

                test_result = findViewById(R.id.test_result);
                test_result.setOnClickListener(view -> {

                    postTestResult("aw");


                });


            } else if (aw == sw) {// 가을웜톤(aw)랑 봄웜톤(sw)가 같으면
                if (imgtext.equals("sw")) { // 이미지(imgtext, 7번 질문)에서 sw 이미지를 체크했으면
                    //color_result = "봄 웜톤"; // color_result(최종 결과값)에 "봄 웜톤"을 저장해라
                    setContentView(R.layout.warm_spring);

                    test_result = findViewById(R.id.test_result);
                    test_result.setOnClickListener(view -> {

                        postTestResult("sw");


                    });

                } else if (imgtext.equals("aw")) { // 이미지(imgtext, 7번 질문)에서 aw 이미지를 체크했으면
                    //color_result = "가을 웜톤"; // color_result(최종 결과값)에 "가을 웜톤"을 저장해라
                    setContentView(R.layout.warm_fall);

                    test_result = findViewById(R.id.test_result);
                    test_result.setOnClickListener(view -> {

                        postTestResult("aw");


                    });

                }
            } // warm > cool 끌남
        } else if (cool > warm) { // 쿨톤(cool)체크갯수가 웜톤(warm)보다 많고
            if (sc > wc) { // 여름쿨톤(sc)체크갯수가 겨울쿨톤(wc)보다 많으면
                //color_result = "여름 쿨톤"; // color_result(최종 결과값)에 "여름쿨톤"을 저장해라
                setContentView(R.layout.cool_summer);

                test_result = findViewById(R.id.test_result);
                test_result.setOnClickListener(view -> {

                    postTestResult("sc");


                });


            } else if (wc > sc) { // 겨울쿨톤(wc)체크갯수가 여름쿨톤(sc)보다 많으면
               // color_result = "겨울 쿨톤"; // color_result(최종 결과값)에 "겨울쿨톤"을 저장해라
                setContentView(R.layout.cool_winter);

                test_result = findViewById(R.id.test_result);
                test_result.setOnClickListener(view -> {

                    postTestResult("wc");


                });

            } else if (wc == sc) { // 겨울쿨톤(wc)랑 여름쿨톤(sc)가 같으면
                if (imgtext.equals("sc")) { // 이미지(imgtext, 7번 질문)에서 sc 이미지를 체크했으면
                   // color_result = "여름 쿨톤"; // color_result(최종 결과값)에 "여름 쿨톤"을 저장해라
                    setContentView(R.layout.cool_summer);

                    test_result = findViewById(R.id.test_result);
                    test_result.setOnClickListener(view -> {

                        postTestResult("sc");


                    });


                } else if (imgtext.equals("wc")) {// 이미지(imgtext, 7번 질문)에서 wc 이미지를 체크했으면
                   // color_result = "겨울 쿨톤";// color_result(최종 결과값)에 "겨울 쿨톤"을 저장해라
                    setContentView(R.layout.cool_winter);

                    test_result = findViewById(R.id.test_result);
                    test_result.setOnClickListener(view -> {

                        postTestResult("wc");


                    });

                }
            }
        }
    }

    private void postTestResult(String color_result) {

        // 로그인 유지
        appData = getSharedPreferences("appData", MODE_PRIVATE); // SharedPreferences 객체 가져오기

        String nick = null;
        if (appData.contains("login_status")) {

            nick = appData.getString("login_id", "defValue"); // 로그인한 아이디 가져오기

        }

        MemberService memberService = RetrofitInstance.getMemberService();
        Call<Integer> call = memberService.setSelfTestResult(color_result, nick, money_result, lip_result);

        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                int res = response.body();

                switch (res) {

                    case 1:
                        intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                        break;

                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });

    }


}
