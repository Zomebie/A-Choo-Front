package com.example.turtle.project_achoo.function.service.loginService;


import android.os.Handler;

import com.example.turtle.project_achoo.function.backgroundTask.HandlerThread;
import com.kakao.auth.ISessionCallback;
import com.kakao.util.exception.KakaoException;

// ISessionCallback은 Session의 상태 변경에 따른 콜백을 받도록 구성
public class SessionCallback implements ISessionCallback {


    Handler handler;
    public SessionCallback(Handler handler){

        this.handler=handler;

    }
    // 로그인 성공 : Access Token을 성공적으로 발급받은 후 사용자의 정보를 요청하여 제공 받을 수 있는 상태
    @Override
    public void onSessionOpened() {

        HandlerThread handlerThread = new HandlerThread(this.handler);
        handlerThread.run();
    }

    // 로그인 실패
    @Override
    public void onSessionOpenFailed(KakaoException exception) {


    }


}





