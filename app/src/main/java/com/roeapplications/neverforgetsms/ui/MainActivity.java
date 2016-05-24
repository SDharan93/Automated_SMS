package com.roeapplications.neverforgetsms.ui;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.roeapplications.neverforgetsms.services.ActivityHandler;
import com.roeapplications.neverforgetsms.services.MessageService;
import com.roeapplications.neverforgetsms.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by shane on 2016-01-08.
 */
public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_SMS_HANDLE_PERMISSIONS = 1;
    public static final String PREFS_FILE = "com.roeapplications.neverforgetsms.preferences";
    public static final String MESSAGE_KEY = "MESSAGE_KEY";
    private static final String TIME_KEY = "TIME_KEY";
    public static final String COUNT_KEY = "COUNT_KEY";

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    //private Realm mRealm;
    private Button mStartButton;
    private Button mListButton;
    private EditText mMessageLabel;
    private TextView mTimeLabel;
    private TextView mMessageCount;
    private SwipeRefreshLayout mSwipleLayout;

    private Boolean mBound = false;
    private Boolean mRunning = false;
    private Boolean mReceiverIsRegistered = false;

    private Messenger mServiceMessenger;
    private Messenger mActivityMessenger = new Messenger(new ActivityHandler(this));
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mServiceMessenger = new Messenger(service);
            Message message = Message.obtain();
            message.arg1 = 2;
            message.arg2 = 1;
            message.replyTo = mActivityMessenger;
            try {
                mServiceMessenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            int count = intent.getIntExtra("Count", 0);
            mMessageCount.setText(count + "");
            mEditor.putInt(COUNT_KEY, count);
            mEditor.apply();
            Log.d(TAG, "Count is now: " + count);
        }
        // Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            requestPermissions();
        }
        setup();
    }

    private void requestPermissions() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            int hasReceieveSmsPermission = checkSelfPermission(Manifest.permission.RECEIVE_SMS);
            int hasSmsReadPermission = checkSelfPermission(Manifest.permission.READ_SMS);
            int hasSmsSendPermission = checkSelfPermission(Manifest.permission.SEND_SMS);
            List<String> permissions = new ArrayList<String>();

            // check if the permissions are already granted
            if( hasReceieveSmsPermission != PackageManager.PERMISSION_GRANTED ) {
                permissions.add( Manifest.permission.RECEIVE_SMS );
            }

            if( hasSmsReadPermission != PackageManager.PERMISSION_GRANTED ) {
                permissions.add( Manifest.permission.READ_SMS );
            }

            if( hasSmsSendPermission != PackageManager.PERMISSION_GRANTED ) {
                permissions.add( Manifest.permission.SEND_SMS );
            }

            if( !permissions.isEmpty() ) {
                requestPermissions( permissions.toArray( new String[permissions.size()] ), REQUEST_SMS_HANDLE_PERMISSIONS );
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch ( requestCode ) {
            case REQUEST_SMS_HANDLE_PERMISSIONS: {
                for( int i = 0; i < permissions.length; i++ ) {
                    if( grantResults[i] == PackageManager.PERMISSION_GRANTED ) {
                        Log.d( "Permissions", "Permission Granted: " + permissions[i] );
                    } else if( grantResults[i] == PackageManager.PERMISSION_DENIED ) {
                        Log.d( "Permissions", "Permission Denied: " + permissions[i] );
                    }
                }
            }
            break;
            default: {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }

    public void setup() {
        //mRealm = Realm.getInstance(this);
        mSharedPreferences = getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

        mStartButton = (Button)findViewById(R.id.startButton);
        mListButton = (Button) findViewById(R.id.listButton);
        mMessageLabel = (EditText) findViewById(R.id.messageLabel);
        mTimeLabel = (TextView) findViewById(R.id.timeLabel);
        mMessageCount = (TextView) findViewById(R.id.sentLabel);
        mSwipleLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipleLayout.setOnRefreshListener(this);

        String message = mSharedPreferences.getString(MESSAGE_KEY, "");
        mMessageLabel.setText(message);

        String timeLabel = mSharedPreferences.getString(TIME_KEY, "--");
        mTimeLabel.setText(timeLabel);

        int count = mSharedPreferences.getInt(COUNT_KEY, 0);
        mMessageCount.setText(count + "");

        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBound) {
                    Intent intent = new Intent(MainActivity.this, MessageService.class);
                    startService(intent);

                    Message message = Message.obtain();
                    message.arg1 = 2;
                    message.replyTo = mActivityMessenger;
                    try {
                        mServiceMessenger.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        mListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.putString(MESSAGE_KEY, mMessageLabel.getText().toString());
                mEditor.apply();
                Toast.makeText(MainActivity.this, "Saved new message!", Toast.LENGTH_SHORT).show();
            }
        });

        if(!mReceiverIsRegistered) {
            registerReciever();
        }
    }

    public void changeStartButton(String text) {
        mStartButton.setText(text);
        if(text.equals("Stop")) {
            String time = mSharedPreferences.getString(TIME_KEY, "--");
            int count = mSharedPreferences.getInt(COUNT_KEY, 0);
            if(time.equals("--")) {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss a");
                String formattedDate = df.format(c.getTime());
                mTimeLabel.setText(formattedDate);
                mMessageCount.setText(0 + "");
                mEditor.putInt(COUNT_KEY, 0);
                mEditor.putString(TIME_KEY, formattedDate);
                mRunning = true;
            }
            else {
                mMessageCount.setText(count + "");
                mTimeLabel.setText(time);
                mRunning = true;
            }
        }
        else {
            mTimeLabel.setText("--");
            mEditor.putString(TIME_KEY, "--");
            mMessageCount.setText("--");
            mEditor.putInt(COUNT_KEY, 0);
            mRunning = false;
        }
        mEditor.apply();
    }

    private void updateCount() {
        Log.d(TAG, "count on update: " + mSharedPreferences.getInt(COUNT_KEY, 0) + " package name: " + MessageService.PACKAGE);
        mMessageCount.setText(mSharedPreferences.getInt(COUNT_KEY, 0) + "");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(MainActivity.this, MessageService.class);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!mReceiverIsRegistered) {
            registerReciever();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mBound) {
            unbindService(mServiceConnection);
            mBound = false;
        }
    }

    @Override
    public void onRefresh() {
        if (mRunning) {
            updateCount();
        }
        else {
            mMessageCount.setText("--");
        }
        mSwipleLayout.setRefreshing(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mReceiverIsRegistered) {
            unregisterReceiver(mMessageReceiver);
            mReceiverIsRegistered = false;
        }
    }

    private void registerReciever() {
        registerReceiver(mMessageReceiver, new IntentFilter(MessageService.PACKAGE));
        mReceiverIsRegistered = true;
        Log.d(TAG, "Registered the reciever");
    }
}
