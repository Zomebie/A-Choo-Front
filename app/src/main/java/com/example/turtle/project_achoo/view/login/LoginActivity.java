package com.example.turtle.project_achoo.view.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.turtle.project_achoo.function.model.member.MemberDTO;
import com.example.turtle.project_achoo.R;
import com.example.turtle.project_achoo.function.service.networkService.MemberService;
import com.example.turtle.project_achoo.function.service.networkService.RetrofitInstance;
import com.example.turtle.project_achoo.view.home.HomeActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    public static String TAG = "DEBUG";

    private Button login_button;
    private EditText id, pw;
    private Intent intent;
/*

    UIThread U;
    UIHandler u;
    String state;
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

      //  u = new UIHandler();

        id = findViewById(R.id.editText_id);
        pw = findViewById(R.id.editText_pw);
        login_button = (Button)findViewById(R.id.login_button);

        SharedPreferences appData=getSharedPreferences("appData", MODE_PRIVATE); // SharedPreferences 객체 가져오기

        // login_status가 true면 자동로그인
        if (appData.getBoolean("login_status", false)) {

            intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
            finish();
        }

        // 로그인 버튼 클릭 시
        login_button.setOnClickListener(v -> {



            if (validate()) {

                MemberService memberService = RetrofitInstance.getMemberService();
                Call<MemberDTO> call = memberService.getLoginResult(id.getText().toString(), pw.getText().toString());

                //네크워크 요청
                call.enqueue(new Callback<MemberDTO>() {

                    //익명 Callback 인터페이스 구현하기
                    @Override
                    public void onResponse(Call<MemberDTO> call, Response<MemberDTO> response) {

                        if (response.code() == 200) { // 로그인 성공

                            MemberDTO memberDTO = response.body();

                            // 로그인 성공한 아이디와 로그인 상태 sharedPreferene 에 저장
                            SharedPreferences.Editor editor=appData.edit(); // 에디터 객체 생성
                            editor.putString("login_id", memberDTO.getId());
                            editor.putString("selfT",memberDTO.getSelfT());
                            editor.putBoolean("login_status", true);
                            editor.apply();


                            intent = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(intent);

                        } else if (response.code() == 401) {

                            Toast.makeText(getApplicationContext(), "아이디가 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                        } else if (response.code() == 403) {
                            Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();

                        }


                    }

                    // 연결 실패
                    @Override
                    public void onFailure(Call<MemberDTO> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "로그인 실패");
                    }
                }); // call.enqueue()

            } // validate()
        }); // login.setOnClickListener()
/*
        state = "Active";
        U = new UIThread();
        U.start();*/

    } // Oncreate

    private boolean validate() {

        // 아이디 입력 안 했을때
        if (id.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
            id.requestFocus();
            return false;

        }
        // 비밀번호 입력 안했을때
        if (pw.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            pw.requestFocus();
            return false;
        }
        return true;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // setResult를 통해 받아온 요청번호, 상태, 데이터
        Log.d("RESULT", requestCode + "");
        Log.d("RESULT", resultCode + "");
        Log.d("RESULT", data + "");

        if (requestCode == 1000 && resultCode == RESULT_OK) {
            Toast.makeText(LoginActivity.this, "회원가입을 완료했습니다!", Toast.LENGTH_SHORT).show();
            id.setText(data.getStringExtra("id"));
            pw.setText("");
        }
    }


/*    public void onclick(View view){

        intent = null;

        switch (view.getId()){

            case R.id.login_button: intent = new Intent(this, HomeActivity.class);
            break;
        }

        startActivity(intent);
    }*/

   /* private class UIThread extends Thread {
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
    }
*/
    //액티비티 애니메이션 없에기
    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0,0);
    }

}
