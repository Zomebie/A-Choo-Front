package com.example.turtle.project_achoo.view.myPage.infoEdit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.turtle.project_achoo.R;
import com.example.turtle.project_achoo.function.model.member.MemberDTO;
import com.example.turtle.project_achoo.function.service.networkService.MemberService;
import com.example.turtle.project_achoo.function.service.networkService.RetrofitInstance;
import com.example.turtle.project_achoo.view.home.HomeActivity;
import com.example.turtle.project_achoo.view.selfTest.Test1;

import org.apache.http.HttpStatus;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MbModifyActivity extends AppCompatActivity {

    private SharedPreferences appData;
    private String id;

    private ImageButton back;
    private TextView nickname;
    private Button save;
    private EditText pw, repw, pw_check, email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mb_modify);

        setView();

    }   // onCreate


    private void setView() {

        appData = getSharedPreferences("appData", MODE_PRIVATE);
        if (appData.contains("login_status")) {

            id = appData.getString("login_id", "defValue"); // 로그인한 아이디 가져오기

        }

        back = findViewById(R.id.back);
        nickname = findViewById(R.id.nickname);
        pw = findViewById(R.id.pw);
        repw = findViewById(R.id.repw);
        pw_check = findViewById(R.id.pw_check);
        email = findViewById(R.id.email);
        save = findViewById(R.id.save);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();

            }
        });

        save.setOnClickListener(view -> {
            if (validate()) {

                update();
            }

        });
    } // setView()

    private boolean validate() {

        // 비밀번호 입력 안 했을때
        if (pw.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            pw.requestFocus();
            return false;

        }

        // 비밀번호가 일치여부
        loginService(id, pw.getText().toString());

        // 새 비밀번호 입력 안 했을때
        if (repw.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "새 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            repw.requestFocus();
            return false;

        }
        //비밀번호 정규식 체크
        if (!Pattern.matches("^[a-zA-Z0-9]{4,12}$", pw.getText().toString())) {
            Toast.makeText(this, "비밀번호는 숫자와 영문자의 조합으로 4~12자리를 사용해야 합니다..", Toast.LENGTH_SHORT).show();

            return false;
        }

        // 비밀번호 확인란 입력 안 했을때
        if (pw_check.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
            pw_check.requestFocus();
            return false;

        }

        //비밀번호 확인 체크
        if (pw.getText().toString().equals(pw_check.getText().toString())) {

        } else {

            Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            pw_check.setText("");
            pw_check.requestFocus();
            return false;
        }


        // 이메일 입력 안 했을때
        if (email.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
            email.requestFocus();
            return false;

        }

        // 이메일 검사
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            Toast.makeText(this, "올바른 이메일 형식이 아닙니다", Toast.LENGTH_SHORT).show();
            email.requestFocus();
            return false;
        }


        return true;

    }

    private void update() {

        MemberService memberService=RetrofitInstance.getMemberService();
       // Call<Integer> call=memberService.modify()

    }

    private void loginService(String id, String pw) {

        MemberService memberService = RetrofitInstance.getMemberService();
        Call<MemberDTO> call = memberService.getLoginResult(id, pw);

        // 네크워크 요청
        call.enqueue(new Callback<MemberDTO>() {

            // 익명 Callback 인터페이스 구현하기
            @Override
            public void onResponse(Call<MemberDTO> call, Response<MemberDTO> response) {

                if (response.code() == HttpStatus.SC_OK) { // 로그인 성공


                } else if (response.code() == HttpStatus.SC_ACCEPTED) { // 최초 로그인 시 자가진단


                } else if (response.code() == HttpStatus.SC_UNAUTHORIZED) {

                    Toast.makeText(getApplicationContext(), "아이디가 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                } else if (response.code() == HttpStatus.SC_FORBIDDEN) {
                    Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();

                }


            }

            // 연결 실패
            @Override
            public void onFailure(Call<MemberDTO> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                Log.d(HomeActivity.TAG, "로그인 실패");
            }
        }); // call.enqueue()



    } // LoginService()

}
