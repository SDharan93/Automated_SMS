package com.roeapplications.neverforgetsms;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;

/**
 * Created by shane on 19/05/16.
 */
public class ActivityHandler extends Handler {
    private MainActivity mMainActivity;

    public ActivityHandler(MainActivity mainActivity) {
        mMainActivity = mainActivity;
    }

    @Override
    public void handleMessage(Message msg) {
        // the service currently is not running
        if (msg.arg1 == 0) {
            // set the button to "Start"
            if (msg.arg2 == 1) {
                mMainActivity.changeStartButton("Start");
            }
            // The user wants to start the service
            else {
                Message message = Message.obtain();
                message.arg1 = 0;
                try {
                    msg.replyTo.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                mMainActivity.changeStartButton("Stop");
            }
        }
        // The service is currently running
        else if (msg.arg1 == 1) {
            // app has restarted, change the button to the correct state
            if (msg.arg2 == 1) {
                mMainActivity.changeStartButton("Stop");
            }
            // The user has clicked the button and wants to stop service
            else {
                Message message = Message.obtain();
                message.arg1 = 1;
                try {
                    msg.replyTo.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                mMainActivity.changeStartButton("Start");
            }
        }
    }
}
