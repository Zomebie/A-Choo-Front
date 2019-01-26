//package com.example.turtle.project_achoo.function.service.loginService;
//
//import android.app.Activity;
//import android.content.Context;
//
//import com.kakao.auth.ApprovalType;
//import com.kakao.auth.AuthType;
//import com.kakao.auth.IApplicationConfig;
//import com.kakao.auth.ISessionConfig;
//import com.kakao.auth.KakaoAdapter;
//
//public class KakaoSDKAdapter extends KakaoAdapter {
//
//    // KakaoAdapter로그인 시 사용 될, Session의 옵션 설정을 위한 인터페이스
//    // 여기서 Session은 로그인 객체를 유지시키는 객체로 Access Token을 관리
//    public ISessionConfig getSessionConfig() {
//
//
//        return new ISessionConfig() {
//            @Override
//            public AuthType[] getAuthTypes() {
//                return new AuthType[]{AuthType.KAKAO_ACCOUNT};
//            }
//
//            @Override
//            public boolean isUsingWebviewTimer() {
//                return false;
//            }
//
//            @Override
//            public boolean isSecureMode() {
//                return true;
//            }
//
//            @Override
//            public ApprovalType getApprovalType() {
//                return ApprovalType.INDIVIDUAL;
//            }
//
//            @Override
//            public boolean isSaveFormData() {
//                return true;
//            }
//        };
//    }
//
//    // 어플리케이션이 가지고 있는 정보를 전달하는 역할
//    @Override
//    public IApplicationConfig getApplicationConfig() {
//        return new IApplicationConfig() {
//
//            public Activity getTopActivity() {
//
//                return GlobalApplication.getCurrentActivity();
//            }
//
//            @Override
//            public Context getApplicationContext() {
//                return GlobalApplication.getGlobalApplicationInstance();
//            }
//        };
//    }
//}
