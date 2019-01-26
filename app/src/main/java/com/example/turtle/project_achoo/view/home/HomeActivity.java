package com.example.turtle.project_achoo.view.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.turtle.project_achoo.R;
import com.example.turtle.project_achoo.view.recommend.RecommendActivity;
import com.example.turtle.project_achoo.view.login.MainActivity;
import com.example.turtle.project_achoo.view.myPage.MypageActivity;
import com.example.turtle.project_achoo.view.product.ProductActivity;
import com.example.turtle.project_achoo.view.detailTest.DetailActivity;

public class HomeActivity extends AppCompatActivity {

    public static final String TAG = "DEBUG";

   /* UIThread U;
    UIHandler u;
    String state;*/
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ImageButton home , product, detail, community, mypage, logout_button;
        TextView home_text;

       // u = new UIHandler();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // 로그인 유지
        SharedPreferences appData = getSharedPreferences("appData", MODE_PRIVATE); // SharedPreferences 객체 가져오기

        if (appData.contains("login_status")) {

            id = appData.getString("login_id", "defValue"); // 로그인한 아이디 가져오기

        }

        home = (ImageButton) findViewById(R.id.home);
        product = (ImageButton) findViewById(R.id.product);
        detail = (ImageButton) findViewById(R.id.detail);
        community = (ImageButton) findViewById(R.id.community);
        mypage = (ImageButton) findViewById(R.id.mypage);
        logout_button =(ImageButton) findViewById(R.id.logout_button);
        home_text =(TextView) findViewById(R.id.home_text);


        /*state = "Active";
        U = new UIThread();
        U.start();*/

        // 로그아웃
        logout_button.setOnClickListener(v -> {

            SharedPreferences.Editor editor = appData.edit();
            editor.putBoolean("login_status", false);
            editor.commit();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        });
    }

    public void onclick(View view){

        Intent intent = null;

        switch (view.getId()){

            case R.id.home : intent = new Intent(this,HomeActivity.class);
                finish();
                overridePendingTransition(0, 0);    // 액티비티 전환 애니메이션
            break;
            case R.id.product: intent = new Intent(this, ProductActivity.class);
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            break;
            case R.id.detail: intent = new Intent(this, DetailActivity.class);
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            break;
            case  R.id.community: intent = new Intent(this, RecommendActivity.class);
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            break;
            case  R.id.mypage: intent = new Intent(this, MypageActivity.class);
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            break;
            case  R.id.home_text: intent = new Intent(this, HomeActivity.class);
            finish();
                overridePendingTransition(0, 0);
            break;

        }
        startActivity(intent);
//        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }
/*

    private class UIThread extends Thread {
        Message msg;
        boolean loop = true;

        public void run() {
            try {
                while (loop) {
                    Thread.sleep(100);

                    if (Thread.interrupted()) { // 인터럽트가 들어오면 루프를 탈출
                        loop = false;
                        break;
                    }

                    msg = u.obtainMessage();
                    msg.arg1 = 1;

                    u.sendMessage(msg);
                }
            } catch (InterruptedException e) {
                // 슬립 상태에서 인터럽트가 들어오면 익셉션 발생
                loop = false;
            }
        }
    }

    private class UIHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.arg1) {
                case 1:
                    if (state.equals("DeActive"))   // Fragment가 숨겨진 상태일 때
                        break;
                    // Fragment의 UI를 변경하는 작업을 수행합니다.
            }
        }
    }

    public void onStop() {
        super.onStop();
        state = "DeActive";
        U.interrupt();
    }

    public void onResume(){
        super.onResume();
        state = "Active";
    }*/

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
