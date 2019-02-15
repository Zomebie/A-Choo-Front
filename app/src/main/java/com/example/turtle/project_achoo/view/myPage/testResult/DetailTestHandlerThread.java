package com.example.turtle.project_achoo.view.myPage.testResult;


import android.os.Handler;
import android.os.Message;

public class DetailTestHandlerThread implements Runnable {

    Handler handler;

    public DetailTestHandlerThread(Handler handler) {
        this.handler=handler;
    }

    @Override
    public void run() {
        Message message=new Message();
        message.what=0;
        handler.sendMessage(message);
    }
}
