package com.example.turtle.project_achoo.view.home;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.turtle.project_achoo.R;
import com.example.turtle.project_achoo.function.service.loginService.OAuthLoginNaver;
import com.example.turtle.project_achoo.view.detailTest.DetailChoiceActivity;
import com.example.turtle.project_achoo.view.login.LoginActivity;
import com.example.turtle.project_achoo.view.recommend.RecommendActivity;
import com.example.turtle.project_achoo.view.login.MainActivity;
import com.example.turtle.project_achoo.view.myPage.MypageActivity;
import com.example.turtle.project_achoo.view.product.ProductActivity;
import com.example.turtle.project_achoo.view.detailTest.DetailActivity;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.nhn.android.naverlogin.OAuthLogin;

public class HomeActivity extends AppCompatActivity {

    public static final String TAG = "YAYAYA";

    // 로그인 쿠키
    private SharedPreferences appData;
    private String id;

    public static OAuthLogin mOAuthLoginInstance;


    // UI 요소
    private ImageButton home, product, detail, community, mypage, logout_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        setView();

    }

    private void setView() {

        // 로그인 유지
        appData = getSharedPreferences("appData", MODE_PRIVATE); // SharedPreferences 객체 가져오기

        if (appData.contains("login_status")) {

            id = appData.getString("login_id", "defValue"); // 로그인한 아이디 가져오기

        }

        home = (ImageButton) findViewById(R.id.home);
        product = (ImageButton) findViewById(R.id.product);
        detail = (ImageButton) findViewById(R.id.detail);
        community = (ImageButton) findViewById(R.id.community);
        mypage = (ImageButton) findViewById(R.id.mypage);

        logout_button = (ImageButton) findViewById(R.id.logout_button);


        // 마이페이지 전환 애니메이션
        mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MypageActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        // 상세진단
        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, DetailChoiceActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                builder.setTitle("logout");
                builder.setMessage("로그아웃 하시겠습니까?");
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        SharedPreferences.Editor editor = appData.edit();
                        editor.putBoolean("login_status", false);
                        editor.commit();

                        // 카카오톡 로그아웃
                        UserManagement.requestLogout(new LogoutResponseCallback() {

                            @Override

                            public void onCompleteLogout() {


                            }

                        });

                        // 네이버 로그아웃
                        /*mOAuthLoginInstance=OAuthLoginNaver.getOAuthLoginInstance(LoginActivity.mContext);
                        mOAuthLoginInstance.logout(LoginActivity.mContext);*/


                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);

                        Snackbar.make(v, "로그아웃 되었습니다.", Snackbar.LENGTH_LONG).show();
                        finish();
                    }
                });
                builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.setCancelable(false);
                builder.setIcon(R.drawable.home1);

                builder.show();
            }
        });
    }

    public void onclick(View view) {

        Intent intent = null;

        switch (view.getId()) {

            case R.id.home:
                intent = new Intent(this, HomeActivity.class);
                finish();
                overridePendingTransition(0, 0);    // 액티비티 전환 애니메이션
                break;
            case R.id.product:
                intent = new Intent(this, ProductActivity.class);
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
//            case R.id.detail:
//                intent = new Intent(this, DetailActivity.class);
//                finish();
//                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                break;
            case R.id.community:
                intent = new Intent(this, RecommendActivity.class);
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
//            case R.id.mypage:
//                intent = new Intent(this, MypageActivity.class);
//                finish();
//                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                break;
            case R.id.home_text:
                intent = new Intent(this, HomeActivity.class);
                finish();
                overridePendingTransition(0, 0);
                break;

        }
        startActivity(intent);
//        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("아름다움을 추천하다")
                .setMessage("정말로 앱을 종료하시겠습니까?")
                .setPositiveButton("종료", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                    }
                })
                .setNegativeButton("취소", null)
                .show();
    }
}
