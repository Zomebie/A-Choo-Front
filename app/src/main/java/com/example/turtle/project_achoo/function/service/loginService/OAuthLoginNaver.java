package com.example.turtle.project_achoo.function.service.loginService;


import android.content.Context;

import com.example.turtle.project_achoo.view.login.LoginActivity;
import com.nhn.android.naverlogin.OAuthLogin;


public class OAuthLoginNaver {

    private static OAuthLogin oAuthLogin = null;
   /* private static Context context;

    private OAuthLoginNaver(Context context) {

        //this.context=context;


    }*/

    public static OAuthLogin getOAuthLoginInstance(Context context) {

        if (oAuthLogin == null) {
            oAuthLogin = OAuthLogin.getInstance();
            oAuthLogin.init(

                    context
                    , "lqGzHPHrDe6GNx0uUQrO"
                    , "Dehn4Zgcrs"
                    , "A-choo"

            );

        }
        return oAuthLogin;
    }


}
