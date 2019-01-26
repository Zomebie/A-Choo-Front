//package com.example.turtle.project_achoo.function.service.loginService;
//
//import android.app.Activity;
//import android.app.Application;
//
//import com.kakao.auth.KakaoSDK;
//
//public class GlobalApplication extends Application {
//
//    private static GlobalApplication globalApplicationInstance = null;
//    private static volatile Activity currentActivity = null;
//
//    public static GlobalApplication getGlobalApplicationInstance() {
//
//        if (globalApplicationInstance == null)
//            throw new IllegalStateException("This Applicaion does not inherit com.kakao.GlobalApplication");
//
//
//        return globalApplicationInstance;
//
//
//    }
//
//    public static Activity getCurrentActivity() {
//
//        return currentActivity;
//    }
//
//    public static void setCurrentActivity(Activity activity) {
//
//        GlobalApplication.currentActivity = activity;
//    }
//
//    public void onCreate() {
//        super.onCreate();
//        globalApplicationInstance = this;
//
//        KakaoSDK.init(new KakaoSDKAdapter()); // kakao SDK 초기화
//
//    }
//
//    public void onTerminate() {
//        super.onTerminate();
//        globalApplicationInstance = null;
//
//    }
//}
