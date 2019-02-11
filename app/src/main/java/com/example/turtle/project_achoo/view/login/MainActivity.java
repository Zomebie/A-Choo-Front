package com.example.turtle.project_achoo.view.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.turtle.project_achoo.function.model.member.MemberDTO;
import com.example.turtle.project_achoo.R;
import com.example.turtle.project_achoo.function.model.member.NaverResponseDTO;
import com.example.turtle.project_achoo.function.model.member.NaverResponseDTO_info;
import com.example.turtle.project_achoo.function.service.loginService.OAuthLoginNaver;
import com.example.turtle.project_achoo.function.service.loginService.SessionCallback;
import com.example.turtle.project_achoo.function.service.networkService.MemberService;
import com.example.turtle.project_achoo.function.service.networkService.NaverLoginService;
import com.example.turtle.project_achoo.function.service.networkService.RetrofitInstance;
import com.example.turtle.project_achoo.view.home.HomeActivity;
import com.example.turtle.project_achoo.view.selfTest.Test1;
import com.google.gson.Gson;
import com.kakao.auth.AuthType;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import org.apache.http.HttpStatus;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    // 로그인 쿠키
    private SharedPreferences appData;
    private String id;

    // UI 요소
    private EditText editText_id, editText_pw;
    private Button login_button;
    private Button joinus_button;
    private Intent intent;

    // naver 로그인
    private OAuthLoginButton mOAuthLoginButton;
    public static Context mContext;
    public static OAuthLogin mOAuthLoginInstance;


    // naver 로그인 성공
    private NaverResponseDTO_info naverResponseDTO_info;
    private NaverResponseDTO naverResponseDTO;

    // kakao 로그인
    private ImageButton custom_kakao_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        setView();

    } // Oncreate

    /* public static Context context() {

         if (mContext == null) {

             mContext = LoginActivity.context();

         }
         return mContext;
     }
 */
    private void setView() {

        appData = getSharedPreferences("appData", MODE_PRIVATE); // SharedPreferences 객체 가져오기

        if (appData.getBoolean("login_status", false)) {

            intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
            finish();
        }

        editText_id = findViewById(R.id.editText_id);
        editText_pw = findViewById(R.id.editText_pw);
        login_button = findViewById(R.id.login_button);
        joinus_button = findViewById(R.id.joinus_button);

        mOAuthLoginButton = findViewById(R.id.buttonNaverLogin);

        OAuthLoginNaver();
        KakaoLogin();


        // 로그인 버튼 클릭 시
        login_button.setOnClickListener(v -> {


            if (validate()) {

                loginService(editText_id.getText().toString(), editText_pw.getText().toString());


            } // validate()
        }); // login.setOnClickListener()

        joinus_button.setOnClickListener(view -> {

            intent = new Intent(this, JoinusActivity.class);
            startActivity(intent);
        });
    }

    private void loginService(String id, String pw) {

        MemberService memberService = RetrofitInstance.getMemberService();
        Call<MemberDTO> call = memberService.getLoginResult(id, pw);

        // 로그인 성공한 아이디와 로그인 상태 sharedPreferene 에 저장
        SharedPreferences.Editor editor = appData.edit(); // 에디터 객체 생성


        // 네크워크 요청
        call.enqueue(new Callback<MemberDTO>() {

            // 익명 Callback 인터페이스 구현하기
            @Override
            public void onResponse(Call<MemberDTO> call, Response<MemberDTO> response) {

                if (response.code() == HttpStatus.SC_OK) { // 로그인 성공

                    MemberDTO memberDTO = response.body();

                    editor.putString("login_id", memberDTO.getNick());
                    editor.putBoolean("login_status", true);
                    editor.apply();

                    intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);

                } else if (response.code() == HttpStatus.SC_ACCEPTED) { // 최초 로그인 시 자가진단

                    MemberDTO memberDTO = response.body();

                    editor.putString("login_id", memberDTO.getNick());
                    editor.putBoolean("login_status", true);
                    editor.apply();

                    intent = new Intent(getApplicationContext(), Test1.class);
                    startActivity(intent);


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


    private void OAuthLoginNaver() {

//        mOAuthLoginInstance = OAuthLogin.getInstance();
//        mOAuthLoginInstance.init(
//                mContext
//                , "lqGzHPHrDe6GNx0uUQrO"
//                , "Dehn4Zgcrs"
//                , "A-choo"
//
//        );

        mOAuthLoginInstance = OAuthLoginNaver.getOAuthLoginInstance(mContext);

        // naver 로그인 핸들러
        OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {

            @Override
            public void run(boolean success) {
                if (success) {


                    String accessToken = mOAuthLoginInstance.getAccessToken(mContext); // 유저 정보 가져오기 위한 인증 토큰
                    naverLoginService(accessToken);


//                    NaverProfileTask naverProfileTask = new NaverProfileTask();
//                    naverProfileTask.execute(accessToken);


                } else {
//                    String errorCode = mOAuthLoginInstance.getLastErrorCode(mContext).getCode();
//                    String errorDesc = mOAuthLoginInstance.getLastErrorDesc(mContext);
                    Toast.makeText(mContext, "네이버 로그인 실패 ", Toast.LENGTH_SHORT).show();
                }
            }

        };

        mOAuthLoginButton.setOAuthLoginHandler(mOAuthLoginHandler);


    } // OAuthLoginNaver()

    private void naverLoginService(String accessToken) {

        NaverLoginService naverLoginService = RetrofitInstance.getNaverLoginService();
        Call<NaverResponseDTO_info> call = naverLoginService.naverLogin("Bearer " + accessToken);

        call.enqueue(new Callback<NaverResponseDTO_info>() {
            @Override
            public void onResponse(Call<NaverResponseDTO_info> call, Response<NaverResponseDTO_info> response) {

                if (response.code() == HttpStatus.SC_OK) { // 정상 호출, 상태 코드 200

                    naverResponseDTO_info = response.body();
                    naverResponseDTO = naverResponseDTO_info.getResponse();

                    Log.d(HomeActivity.TAG, naverResponseDTO.getId());
                    Log.d(HomeActivity.TAG, naverResponseDTO.getEmail());
                    Log.d(HomeActivity.TAG, naverResponseDTO.getNickname());

                    naverJoinService(); // 자동 회원가입

                } else {

                }

            }

            @Override
            public void onFailure(Call<NaverResponseDTO_info> call, Throwable t) {
                Log.d(HomeActivity.TAG, t + "");
            }
        });


    } // naverLoginService()

    private void naverJoinService() {

        MemberDTO memberDTO = new MemberDTO(naverResponseDTO.getId(), "naver", naverResponseDTO.getNickname(), naverResponseDTO.getEmail());

        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(memberDTO));

        MemberService memberService = RetrofitInstance.getMemberService();
        Call<Integer> call = memberService.join(requestBody);

        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {

                int result = response.body();
                switch (result) {
                    case 1: { // 처음 네아로를 통해 어플에 접속한 회원을 자동 회원가입

//                        SharedPreferences.Editor editor = appData.edit();
//                        editor.putString("login_id", memberDTO.getNick());
//                        editor.putBoolean("login_status", true);
//
//                        editor.apply();
//
//                        intent=new Intent(getApplicationContext(), Test1.class);
//                        startActivity(intent);

                        loginService(memberDTO.getId(), memberDTO.getPw());

                        break;
                    }

                    case 2: { // 전에 네이버 회원정보로 회원가입 된 회원
//                        Log.d(HomeActivity.TAG, "아이디 존재");
//
//                        SharedPreferences.Editor editor = appData.edit();
//                        editor.putString("login_id", memberDTO.getNick());
//                        editor.putBoolean("login_status", true);
//
//                        editor.apply();
//
//                        intent = new Intent(getApplicationContext(), HomeActivity.class);
//                        startActivity(intent);

                        loginService(memberDTO.getId(), memberDTO.getPw());

                        break;
                    }
                    case 3: {
//                        Log.d(HomeActivity.TAG, "닉네임 존재");
//                        SharedPreferences.Editor editor = appData.edit();
//                        editor.putString("login_id", memberDTO.getNick());
//                        editor.putBoolean("login_status", true);
//
//                        editor.apply();
//
//                        intent = new Intent(getApplicationContext(), HomeActivity.class);
//                        startActivity(intent);

                        loginService(memberDTO.getId(), memberDTO.getPw());

                        break;
                    }


                }

            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.d(HomeActivity.TAG, "회원가입 실패");
            }
        });


    } // naverJoinService()

    // 카톡 로그인
    private void KakaoLogin() {

        custom_kakao_login = findViewById(R.id.custom_kakao_login);

        custom_kakao_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Log.d(HomeActivity.TAG, "kakaoLogin");
                Session session = Session.getCurrentSession();

                Handler handler = new Handler() {


                    public void handleMessage(Message msg) {

                        if (msg.what == 1) {

                            // 로딩 화면 띄우기
                            Log.d(HomeActivity.TAG, "핸들러..");
                            requestMe();
                        }
                    }
                };

                session.addCallback(new SessionCallback(handler));
                session.open(AuthType.KAKAO_ACCOUNT, MainActivity.this);


            }
        });
        //  kakao_login = findViewById(R.id.kakao_login);


    }

    // kakao 사용자 정보 요청
    public void requestMe() {

        // 사용자정보 요청 결과에 대한 Callback
        UserManagement.requestMe(new MeResponseCallback() {

            // 세션오픈 실패
            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.d(HomeActivity.TAG, "onSessionClosed : " + errorResult.getErrorMessage());

            }

            //회원이 아닌 경우
            @Override
            public void onNotSignedUp() {

            }

            // 사용자 정보 요청에 성공한 경우
            //로그인에 성공하면 로그인한 사용자의 일련번호, 닉네임, 이미지url등을 리턴
            //사용자 ID는 보안상의 문제로 제공하지 않고 일련번호는 제공
            @Override
            public void onSuccess(UserProfile userProfile) {

                Log.d(HomeActivity.TAG, "onSuccess ");

                long id = userProfile.getId();

                String nickname = userProfile.getNickname();

                String email = userProfile.getEmail();

                //String profileImagePath = userProfile.getProfileImagePath();

                //String thumnailPath = userProfile.getThumbnailImagePath();

                //String UUID = userProfile.getUUID();


                Log.d(HomeActivity.TAG, "카카오톡 로그인 성공 아이디 " + id);
                Log.d(HomeActivity.TAG, "카카오톡 로그인 성공 닉네임 " + nickname);
                Log.d(HomeActivity.TAG, "카카오톡 로그인 성공 이메일 " + email);

                kakaoJoinService(id, nickname, email);

            }

            // 사용자 정보 요청 실패
            public void onFaliure(ErrorResult errorResult) {

                Log.d(HomeActivity.TAG, "onFaliure : " + errorResult.getErrorMessage());
            }
        });


    }


    private void kakaoJoinService(long id, String nickname, String email) {
        MemberDTO memberDTO = new MemberDTO(id + "", "kakao", nickname, email);

        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(memberDTO));

        MemberService memberService = RetrofitInstance.getMemberService();
        Call<Integer> call = memberService.join(requestBody);

        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {

                int result = response.body();
                switch (result) {
                    case 1: { // 처음 카톡 계정를 통해 어플에 접속한 회원을 자동 회원가입

//                        SharedPreferences.Editor editor = appData.edit();
//                        editor.putString("login_id", memberDTO.getNick());
//                        editor.putBoolean("login_status", true);
//                        editor.apply();
//
//                        intent=new Intent(getApplicationContext(), Test1.class);
//                        startActivity(intent);

                        loginService(memberDTO.getId(), memberDTO.getPw());

                        break;
                    }

                    case 2: { // 전에 카톡 회원정보로 회원가입 된 회원
//                        Log.d(HomeActivity.TAG, "아이디 존재");
//
//                        SharedPreferences.Editor editor = appData.edit();
//                        editor.putString("login_id", memberDTO.getNick());
//                        editor.putBoolean("login_status", true);
//
//                        editor.apply();
//
//                        intent = new Intent(getApplicationContext(), HomeActivity.class);
//                        startActivity(intent);


                        loginService(memberDTO.getId(), memberDTO.getPw());

                        break;
                    }
                    case 3: {
                       /* Log.d(HomeActivity.TAG, "닉네임 존재");

                        SharedPreferences.Editor editor = appData.edit();
                        editor.putString("login_id", memberDTO.getNick());
                        editor.putBoolean("login_status", true);

                        editor.apply();

                        intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);*/


                        loginService(memberDTO.getId(), memberDTO.getPw());

                        break;
                    }


                }

            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.d(HomeActivity.TAG, "회원가입 실패");
            }
        });

    }

    private boolean validate() {

        // 아이디 입력 안 했을때
        if (editText_id.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
            editText_id.requestFocus();
            return false;

        }
        // 비밀번호 입력 안했을때
        if (editText_pw.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            editText_pw.requestFocus();
            return false;
        }
        return true;

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // setResult를 통해 받아온 요청번호, 상태, 데이터
//        Log.d("RESULT", requestCode + "");
//        Log.d("RESULT", resultCode + "");
//        Log.d("RESULT", data + "");
//
//        if (requestCode == 1000 && resultCode == RESULT_OK) {
//            Toast.makeText(LoginActivity.this, "회원가입을 완료했습니다!", Toast.LENGTH_SHORT).show();
//            editText_id.setText(data.getStringExtra("id"));
//            editText_pw.setText("");
//        }
//    }


/*    public void onclick(View view){

        intent = null;

        switch (view.getId()){

            case R.id.login_button: intent = new Intent(this, HomeActivity.class);
            break;
        }

        startActivity(intent);
    }*/

    /* private class UIThread extends Thread {
         MessageHandler msg;
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
         public void handleMessage(MessageHandler msg) {
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
        overridePendingTransition(0, 0);
    }

}
