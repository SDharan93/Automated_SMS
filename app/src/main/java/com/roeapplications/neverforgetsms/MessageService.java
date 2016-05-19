package com.roeapplications.neverforgetsms;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by shane on 19/05/16.
 */
public class MessageService extends Service{
    private static final String TAG = MessageService.class.getSimpleName();
    private Boolean mServing = false;

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: MessageService");

        mServing = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public Boolean isServing() {
        return mServing;
    }
}
