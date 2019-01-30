package com.example.turtle.project_achoo.function.backgroundTask;

import android.app.Notification;
import android.os.Handler;
import android.os.Message;

public class HandlerThread implements Runnable {

    Handler handler;
    public HandlerThread(Handler handler){

       this.handler=handler;
    }
    @Override
    public void run() {

        Message message = new Message();
        message.what = 1;

        handler.sendMessage(message);

    }  // run


};



