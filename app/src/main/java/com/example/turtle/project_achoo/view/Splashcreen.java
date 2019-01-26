package com.example.turtle.project_achoo.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.turtle.project_achoo.view.login.MainActivity;
import com.example.turtle.project_achoo.R;

public class Splashcreen extends Activity {

    public void onAttachedToWindow(){
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    /** Called when the activity is first created */

    Thread splashTread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscrean);
        StartAnimation();
    }

    private void StartAnimation(){

        ImageView iv, iv1, iv2, iv3,iv4, iv5,iv6, iv7, iv8, iv9;

        iv = (ImageView)findViewById(R.id.image_splash);
        iv1 = (ImageView)findViewById(R.id.image_splash1);
        iv2 = (ImageView)findViewById(R.id.image_splash2);
        iv3 = (ImageView)findViewById(R.id.image_splash3);
        iv4 = (ImageView)findViewById(R.id.image_splash4);
        iv5 = (ImageView)findViewById(R.id.image_splash5);
        iv6 = (ImageView)findViewById(R.id.image_splash6);
        iv7 = (ImageView)findViewById(R.id.image_splash7);
        iv8 = (ImageView)findViewById(R.id.image_splash8);
        iv9 = (ImageView)findViewById(R.id.image_splash9);

        Animation anim = AnimationUtils.loadAnimation(this,R.anim.alpha);
        anim.reset();
        iv.setAnimation(anim);

        Animation anim1 = AnimationUtils.loadAnimation(this,R.anim.poto1);
        anim1.reset();
        iv1.setAnimation(anim1);

        Animation anim2 = AnimationUtils.loadAnimation(this,R.anim.poto2);
        anim2.reset();
        iv2.setAnimation(anim2);

        Animation anim3 = AnimationUtils.loadAnimation(this,R.anim.poto3);
        anim3.reset();
        iv3.setAnimation(anim3);

        Animation anim4 = AnimationUtils.loadAnimation(this,R.anim.poto4);
        anim4.reset();
        iv4.setAnimation(anim4);


        Animation anim5 = AnimationUtils.loadAnimation(this,R.anim.poto5);
        anim5.reset();
        iv5.setAnimation(anim5);

        Animation anim6 = AnimationUtils.loadAnimation(this,R.anim.poto6);
        anim6.reset();
        iv6.setAnimation(anim6);

        Animation anim7 = AnimationUtils.loadAnimation(this,R.anim.poto7);
        anim7.reset();
        iv7.setAnimation(anim7);

        Animation anim8 = AnimationUtils.loadAnimation(this,R.anim.poto8);
        anim8.reset();
        iv8.setAnimation(anim8);
        iv9.setAnimation(anim8);

        splashTread = new Thread(){

            @Override
            public void run() {
                try{
                    int waited = 0;
                    //splash screen pause time

                    while(waited < 4500){
                        sleep(100);
                        waited += 100;
                    }
                    Intent intent = new Intent(Splashcreen.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    Splashcreen.this.finish();
                }catch (InterruptedException e){
                    // do nothing
                }finally {
                    Splashcreen.this.finish();
                }
            }
        };
        splashTread.start();
    }
}
