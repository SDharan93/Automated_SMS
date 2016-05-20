package com.roeapplications.neverforgetsms;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Messenger;
import android.provider.Telephony;
import android.support.annotation.Nullable;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by shane on 19/05/16.
 */
public class MessageService extends Service{
    private static final String TAG = MessageService.class.getSimpleName();
    private Boolean mServing = false;
    private final BroadcastReceiver mReceiver = new MyReceiver();
    public Messenger mMessenger = new Messenger(new MessageHandler(this));

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: MessageService");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: MessageService");
        return mMessenger.getBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: MessageService");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: MessageService");
        //unregisterReceiver(mReceiver);
    }

    public Boolean isServing() {
        return mServing;
    }

    public void startService() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        filter.addAction(android.telephony.TelephonyManager.ACTION_PHONE_STATE_CHANGED);
        registerReceiver(mReceiver, filter);
        mServing = true;
        Log.d(TAG, "Service has started and registered");
    }

    public void stopService() {
        unregisterReceiver(mReceiver);
        mServing = false;
    }

    // use this as an inner class like here or as a top-level class
    public class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // Retrieves a map of extended data from the intent.
            final Bundle bundle = intent.getExtras();

            try {
                if (bundle != null) {
                    final Object[] pdusObj = (Object[]) bundle.get("pdus");
                    Log.d(TAG, "In OnRecieve");
                    for (int i = 0; i < pdusObj.length; i++) {

                        SmsMessage[] msgs = Telephony.Sms.Intents.getMessagesFromIntent(intent);
                        SmsMessage currentMessage = msgs[i];
                        String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                        String senderNum = phoneNumber;
                        String message = currentMessage.getDisplayMessageBody();

                        Log.d(TAG, "senderNum: " + senderNum + "; message: " + message);

                        //show alert for testing
                        //Toast.makeText(context, "senderNum: "+ senderNum + ", message: " + message, Toast.LENGTH_SHORT).show();

                    /*
                    *TODO: Make so it sends on a button click, currently it will continusouly send messages
                    try {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(senderNum, null, message, null, null);
                        Toast.makeText(context, "SMS sent.", Toast.LENGTH_LONG).show();
                    }

                    catch (Exception e) {
                        Toast.makeText(context, "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                    */
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "Exception smsReceive");
            }
        }

        public MyReceiver(){

        }
    }
}
