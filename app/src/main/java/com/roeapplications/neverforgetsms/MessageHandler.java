package com.roeapplications.neverforgetsms;

import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;

/**
 * Created by shane on 19/05/16.
 */
public class MessageHandler extends Handler {
    private MessageService mMessageService;

    public MessageHandler(MessageService messageService) {
        mMessageService = messageService;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.arg1) {
            /******** Messages sent from ActivityHandler ********/
            // The user wants to start the service
            case 0:
                mMessageService.startService();
                break;

            // The user wants to stop the service
            case 1:
                mMessageService.stopService();
                break;

            /******** Messages sent from MainActivity ********/
            // button was clicked or service was connected
            case 2:
                // is the service running currently?
                int isServing = mMessageService.isServing() ? 1: 0;
                Message message = Message.obtain();
                message.arg1 = isServing;

                if(msg.arg2 == 1) {
                    message.arg2 = 1;
                }
                message.replyTo = mMessageService.mMessenger;
                try {
                    msg.replyTo.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
