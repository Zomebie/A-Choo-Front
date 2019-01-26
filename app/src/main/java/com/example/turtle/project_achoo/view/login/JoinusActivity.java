package com.example.turtle.project_achoo.view.login;

import android.content.Intent;
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
import com.google.gson.Gson;

import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinusActivity extends AppCompatActivity {

    private static final String TAG = "DEBUG";

    EditText id, pw, pwcheck, nickname, email;
    Button id_confirm, nickname_confirm, joinus_button;

    UIThread U;
    UIHandler u;
    String state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joinus);

        u = new UIHandler();

        joinus_button = (Button) findViewById(R.id.joinus_button);
        id = (EditText) findViewById(R.id.id);
        id_confirm = (Button) findViewById(R.id.id_confirm);
        pw = (EditText) findViewById(R.id.pw);
        pwcheck = (EditText) findViewById(R.id.pwcheck);
        nickname = (EditText) findViewById(R.id.nickname);
        nickname_confirm = (Button) findViewById(R.id.nickname_confirm);
        email = (EditText) findViewById(R.id.email);

        id_confirm.setOnClickListener(v -> {

            // 아이디 입력 안 했을때
            if (id.getText().toString().length() == 0) {
                Toast.makeText(getApplicationContext(), "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                id.requestFocus();


            } else {

                MemberService memberService = RetrofitInstance.getMemberService();
                Call<Integer> call = memberService.duplicationCheckId(id.getText().toString());

                call.enqueue(new Callback<Integer>() {
                                 @Override
                                 public void onResponse(Call<Integer> call, Response<Integer> response) {
                                     int result = response.body();
                                     if (result == 1) {
                                         Toast.makeText(getApplicationContext(), "이미 존재하는 아이디입니다.", Toast.LENGTH_SHORT).show();
                                         id.requestFocus();

                                     } else if (result == 2) {
                                         Toast.makeText(getApplicationContext(), "사용가능한 아이디입니다.", Toast.LENGTH_SHORT).show();


                                     }
                                 }

                                 @Override
                                 public void onFailure(Call<Integer> call, Throwable t) {

                                 }
                             }
                );
            }

        }); // id_confirm.setOnClickListener()

        nickname_confirm.setOnClickListener(v -> {
            if (nickname.getText().toString().length() == 0) {
                Toast.makeText(getApplicationContext(), "닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show();
                nickname.requestFocus();

            } else {
                MemberService memberService =RetrofitInstance.getMemberService();
                Call<Integer> call= memberService.duplicationCheckNick(nickname.getText().toString());

                call.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        int result = response.body();
                        if (result == 1) {
                            Toast.makeText(getApplicationContext(), "이미 존재하는 닉네임입니다.", Toast.LENGTH_SHORT).show();
                            id.requestFocus();

                        } else if (result == 2) {
                            Toast.makeText(getApplicationContext(), "사용가능한 닉네임입니다.", Toast.LENGTH_SHORT).show();


                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {

                    }
                });
            }
        }); // nickname_confirm.setOnClickListener()

        joinus_button.setOnClickListener(v -> {

            if (validate()) { // 유효성 검사 (front-end)


                MemberDTO memberDTO = new MemberDTO(id.getText().toString(), pw.getText().toString(), nickname.getText().toString(), email.getText().toString());

                Gson gson = new Gson();
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(memberDTO));

                MemberService memberService = RetrofitInstance.getMemberService();
                Call<Integer> call = memberService.join(requestBody); // memberDTO 객체를 @RequestBody로 넘겨주기


                call.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {

                        int result = response.body();
                        switch (result) {
                            case 1: {
                                Intent intent = new Intent();
                                intent.putExtra("id", id.getText().toString());
                                // 자신을 호출한 Activity로 데이터를 보낸다.
                                setResult(RESULT_OK, intent);
                                finish();
                                break;
                            }

                            case 2: {
                                Toast.makeText(getApplicationContext(), "이미 존재하는 아이디입니다.", Toast.LENGTH_SHORT).show();
                                id.requestFocus();
                                break;
                            }
                            case 3: {
                                Toast.makeText(getApplicationContext(), "이미 존재하는 닉네임입니다.", Toast.LENGTH_SHORT).show();
                                nickname.requestFocus();
                                break;
                            }


                        }

                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        Log.d(JoinusActivity.TAG, "회원가입 실패");
                    }
                });
            }//if
        }); // submit.setOnclickListener

        state = "Active";
        U = new UIThread();
        U.start();

    }

    private boolean validate() {


        // 아이디 입력 안 했을때
        if (id.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
            id.requestFocus();
            return false;

        }

        // 아이디 정규식 체크
        if (!Pattern.matches("^[a-zA-Z0-9]{4,12}$", id.getText().toString())) {
            Toast.makeText(this, "아이디는 숫자와 영문자의 조합으로 4~12자리를 사용해야 합니다.", Toast.LENGTH_SHORT).show();

            return false;
        }

        // 비밀번호 입력 안했을때
        if (pw.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            pw.requestFocus();
            return false;
        }

        //비밀번호 정규식 체크

        if (!Pattern.matches("^[a-zA-Z0-9]{4,12}$", pw.getText().toString())) {
            Toast.makeText(this, "비밀번호는 숫자와 영문자의 조합으로 4~12자리를 사용해야 합니다..", Toast.LENGTH_SHORT).show();

            return false;
        }


        // 비밀번호 확인란 입력 안했을 때
        if (pwcheck.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "비밀번호 확인란을 입력해주세요.", Toast.LENGTH_SHORT).show();
            pwcheck.requestFocus();
            return false;
        }

        //비밀번호 확인 체크
        if (pw.getText().toString().equals(pwcheck.getText().toString())) {

        } else {

            Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            pwcheck.setText("");
            pwcheck.requestFocus();
            return false;
        }

        // 닉네임 입력 안했을 때
        if (nickname.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show();
            nickname.requestFocus();
            return false;
        }

        // 이메일 입력 안했을 때
        if (email.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
            pwcheck.requestFocus();
            return false;
        }

        // 이메일 검사
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            Toast.makeText(this, "올바른 이메일 형식이 아닙니다", Toast.LENGTH_SHORT).show();
            email.requestFocus();
            return false;
        }


        return true;

    } // validate ()함수끝

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
    }

    //액티비티 애니메이션 없에기
    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0,0);
    }


}
