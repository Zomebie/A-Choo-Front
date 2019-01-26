package com.example.turtle.project_achoo.view.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.turtle.project_achoo.R;
import com.example.turtle.project_achoo.view.home.HomeActivity;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "DEBUG";

    private Button login_button;
    private Button joinus_button;
    private Intent intent;

    //private UIThread uiThread;
    //UIHandler uiHandler;
    //String state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //uiHandler = new UIHandler();


        login_button = (Button) findViewById(R.id.login_button);
        joinus_button = (Button) findViewById(R.id.joinus_button);

        SharedPreferences appData = getSharedPreferences("appData", MODE_PRIVATE); // SharedPreferences 객체 가져오기
        // login_status가 true면 자동로그인
        if (appData.getBoolean("login_status", false)) {

            intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
            finish();
        }
//        state = "Active";
//        uiThread = new UIThread();
//        uiThread.start();

    }

    public void onclick(View view) {

        intent = null;

        switch (view.getId()) {

            case R.id.login_button:
                intent = new Intent(this, LoginActivity.class);
                //finish();
                break;
            case R.id.joinus_button:
                intent = new Intent(this, JoinusActivity.class);
                //finish();
                break;
        }

        startActivity(intent);
    }

//    private class UIThread extends Thread {
//        Message msg;
//        boolean loop = true;
//
//        public void run() {
//            try {
//                while (loop) {
//                    Thread.sleep(100);
//
//                    if (Thread.interrupted()) { // 인터럽트가 들어오면 루프를 탈출
//                        loop = false;
//                        break;
//                    }
//
//                    msg = u.obtainMessage();
//                    msg.arg1 = 1;
//
//                    u.sendMessage(msg);
//                }
//            } catch (InterruptedException e) {
//                // 슬립 상태에서 인터럽트가 들어오면 익셉션 발생
//                loop = false;
//            }
//        }
//    }

//    private class UIHandler extends Handler {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.arg1) {
//                case 1:
//                    if (state.equals("DeActive"))   // Fragment가 숨겨진 상태일 때
//                        break;
//                    // Fragment의 UI를 변경하는 작업을 수행합니다.
//            }
//        }
//    } // UIHandler
//
//    public void onStop() {
//        super.onStop();
//        state = "DeActive";
//        //uiThread.interrupt();
//    }
//
//    public void onResume(){
//        super.onResume();
//        state = "Active";
//    }
//
//    //액티비티 애니메이션 없에기
//    @Override
//    protected void onPause() {
//        super.onPause();
//        overridePendingTransition(0,0);
//    }

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
